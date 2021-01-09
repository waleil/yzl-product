package cn.net.yzl.product.service.meal.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.MealMapper;
import cn.net.yzl.product.dao.MealProductMapper;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.pojo.product.Meal;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductMealVO;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.service.meal.ProductMealService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import cn.net.yzl.product.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private RedisUtil redisUtil;

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

    @Override
    public ComResponse<Page<ProductMealDTO>> queryListProductMeal(ProductMealVO vo) {
        //开启分页
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<ProductMealDTO> list = mealMapper.queryListProductMeal(vo);
        for (ProductMealDTO p : list) {
            //List<ProductMealLinkDTO> pList =  productMealLinkMapper.queryProductMealLink(p.getMealNo());
        }
        //分页查询
        Page<ProductMealDTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
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
    public ComResponse editProductMeal(ProductMealVO vo) {

//        Meal meal = translateProductMeal(vo);
//        Long mealNo = vo.getMealNo();
//
//        if (StringUtils.isEmpty("mealNo")) {
//            //获取商品ID
//            String cacheKey = CacheKeyUtil.maxProductCacheKey();
//            long mealno = redisUtil.incr(cacheKey, 1);
//            mealNo = String.valueOf(mealno);
//            meal.setMealNo(mealNo);
//            mealMapper.insertSelective(meal);
//        } else {
//            MealStatus MealStatus = mealMapper.queryProductStatusByProductCode(mealNo);
//            if (MealStatus == null) {
//                return ComResponse.fail(ResponseCodeEnums.NO_MATCHING_RESULT_CODE.getCode(), ResponseCodeEnums.NO_MATCHING_RESULT_CODE.getMessage());
//            }
//            if (MealStatus.getUpdateTime().getTime() > vo.getUpdateTime().getTime()) {
//                return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
//            }
        return null;
    }


    private Meal translateProductMeal(ProductMealVO vo) {
        return null;
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

