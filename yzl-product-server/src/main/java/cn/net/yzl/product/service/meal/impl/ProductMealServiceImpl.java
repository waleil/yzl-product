package cn.net.yzl.product.service.meal.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.config.FastDFSConfig;
import cn.net.yzl.product.dao.MealMapper;
import cn.net.yzl.product.dao.MealProductMapper;
import cn.net.yzl.product.model.pojo.product.*;
import cn.net.yzl.product.model.vo.product.dto.*;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.service.meal.ProductMealService;
import cn.net.yzl.product.utils.BeanCopyUtil;
import cn.net.yzl.product.utils.CacheKeyUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import cn.net.yzl.product.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanghuahseng
 * @version 1.0
 * @title: ProductMealServiceImpl
 * @description 商品套餐服务实现类
 * @date: 2021/1/7 10:00 下午
 */
@Service
@Slf4j
public class ProductMealServiceImpl implements ProductMealService {
    @Autowired
    private MealMapper mealMapper;
    @Autowired
    private MealProductMapper mealProductMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FastDFSConfig dfsConfig;

    /**
     * @Author: wanghuasheng
     * @Description: 按照上下架查询商品套餐数量
     * @Date: 2021/1/9 09:25 上午
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO>
     */
    @Override
    public List<ProductStatusCountDTO> queryCountByStatus() {
        return mealMapper.queryCountByStatus();
    }

    /**
     * @param vo:
     * @Description: 修改套餐上下架状态
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:42
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    @Override
    public ComResponse updateStatusByMealCode(ProductMealUpdateStatusVO vo) {
        try {
            mealMapper.updateStatusByMealCode(vo);
            return ComResponse.success();
        } catch (Exception ex) {
            log.error("修改套餐上下架状态失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "修改失败");
    }

    /**
     * @Description: 修改套餐售卖时间
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:43
     * @param:
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    @Override
    public ComResponse updateTimeByMealCode(ProductMealUpdateTimeVO vo) {
        try {
            mealMapper.updateTimeByMealCode(vo);
            return ComResponse.success();
        } catch (Exception ex) {
            log.error("修改套餐售卖时间失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "修改套餐售卖时间失败");
    }

    /**
     * @param vo:
     * @Description: 编辑商品信息/包含新增
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:52
     * @return: cn.net.yzl.common.entity.ComResponse<java.lang.Void>
     **/
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ComResponse editProductMeal(MealVO vo) {
        //处理金额
        Meal meal = translateMeal(vo);
        Long mealNo = vo.getMealNo();
        //代表新增
        if (null == mealNo) {
            //获取套餐ID
            String cacheKey = CacheKeyUtil.maxProductCacheKey();
            long maxProductCode = redisUtil.incr(cacheKey, 1);
            meal.setMealNo(maxProductCode);
            mealMapper.insertSelective(meal);
            //套餐商品新增
            List<MealProductVO> mealProducts = vo.getMealProducts();
            List<MealProduct> mealProductList = BeanCopyUtil.copyListProperties(mealProducts, MealProduct::new);
            mealProductList.stream().forEach(n -> n.setMealNo(maxProductCode));
            mealProductMapper.insertSelectiveList(mealProductList);
        } else {
            MealStatus mealStatus = mealMapper.queryMealStatusByMaelNo(mealNo);
            if (mealStatus == null) {
                return ComResponse.fail(ResponseCodeEnums.NO_MATCHING_RESULT_CODE.getCode(), ResponseCodeEnums.NO_MATCHING_RESULT_CODE.getMessage());
            }
            if (mealStatus.getUpdateTime().getTime() > vo.getUpdateTime().getTime()) {
                return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
            }
            //修改
            mealMapper.updateByPrimaryKeySelective(meal);
            mealProductMapper.deleteByMealNo(mealNo);
            List<MealProductVO> mealProducts = vo.getMealProducts();
            List<MealProduct> mealProductList = BeanCopyUtil.copyListProperties(mealProducts, MealProduct::new);
            mealProductList.stream().forEach(n -> {
                        n.setMealNo(mealStatus.getMealNo());
                        n.setUpdateTime(mealStatus.getUpdateTime());
                    }
            );
            mealProductMapper.insertSelectiveList(mealProductList);
        }
        return ComResponse.success();
    }

    private Meal translateMeal(MealVO vo) {
        Meal meal = BeanUtil.copyProperties(vo, Meal.class);
        //售卖价
        if (vo.getPriceD() != null) {
            meal.setPrice((int) (vo.getPriceD() * 100));
        }
        //成本价
        if (vo.getDiscountPriceD() != null) {
            meal.setDiscountPrice((int) (vo.getDiscountPriceD() * 100));
        }
        return meal;
    }


    /**
     * @Description: 查看套餐
     * @Author: dongjunmei
     * @Date: 2021-01-09 16:50
     * @param:
     * @return: cn.net.yzl.common.entity.ComResponse<cn.net.yzl.product.model.vo.product.vo.ProductMealVO>
     **/
    @Override
    public ComResponse<ProductMealDetailVO> queryMealDetail(Meal meal) {
        try {
            ProductMealDetailVO mealVO = mealMapper.selectBymealNo(meal);
            return ComResponse.success(mealVO);
        } catch (Exception ex) {
            log.error("查询套餐详情信息失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "查询套餐详情信息失败");
    }

    //查询商品套餐列表
    @Override
    public ComResponse<Page<ProductMealListDTO>> queryProductMealList(ProductMealSelectVO vo) {
        //开启分页
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        //查询套餐列表
        List<ProductMealListDTO> ProductMealList = mealMapper.queryListProductMeal(vo);
        if (!CollectionUtils.isEmpty(ProductMealList)) {
            List<Integer> mealNoList = new ArrayList<Integer>();
            for (ProductMealListDTO dto : ProductMealList) {
                dto.setPriceD(new BigDecimal(String.valueOf(dto.getPrice() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                dto.setFastDFSUrl(dfsConfig.getUrl());
                mealNoList.add(dto.getMealNo());
            }
            //查询套餐关联产品
            if (!CollectionUtils.isEmpty(mealNoList)) {
                List<MealListProductDTO> mealProducts = mealProductMapper.queryMealProductByMealNos(mealNoList);
                if (!CollectionUtils.isEmpty(mealProducts)) {
                    //根据套餐no转换为map
                    Map<Integer, List<MealListProductDTO>> MealListProductMap = mealProducts.stream().collect(Collectors.groupingBy(MealListProductDTO::getMealNO, LinkedHashMap::new, Collectors.toList()));
                    //设置套餐关联商品
                    ProductMealList.stream().forEach(productMealListDTO -> {
                        productMealListDTO.setMealProductList(MealListProductMap.get(productMealListDTO.getMealNo()));
                    });
                }
            }
        }
        //分页查询
        Page<ProductMealListDTO> pageInfo = AssemblerResultUtil.resultAssembler(ProductMealList);
        return ComResponse.success(pageInfo);
    }

    @Override
    public ComResponse<ProductMealDTO> queryProductMealPortray(Integer mealNo) {
        try {
            ProductMealDTO productMealDTO = mealMapper.queryProductMealPortray(mealNo);
            return ComResponse.success(productMealDTO);
        } catch (Exception ex) {
            log.error("查询套餐画像信息失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "查询套餐画像信息失败");
    }

}

