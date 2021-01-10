package cn.net.yzl.product.service.meal.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.dao.MealMapper;
import cn.net.yzl.product.dao.MealProductMapper;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.model.db.MealProduct;
import cn.net.yzl.product.model.pojo.disease.Disease;
import cn.net.yzl.product.model.pojo.product.Meal;
import cn.net.yzl.product.model.vo.disease.DiseaseAllDTO;
import cn.net.yzl.product.model.vo.product.dto.MealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductDetailVO;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductMealVO;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.service.meal.ProductMealService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import cn.net.yzl.product.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;
import java.math.BigDecimal;
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

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DiseaseBeanMapper diseaseBeanMapper;

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


    /**
     * 查询套餐详情信息
     * @param mealNo  套餐编号
     * @return
     */
    @Override
    public ComResponse<MealDTO> queryProductMealPortray(Integer mealNo) {
        try {
            //查询套餐信息
            cn.net.yzl.product.model.db.Meal meal = mealMapper.queryProductMealPortray(mealNo);
            if (meal == null){
                return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(),ResponseCodeEnums.NO_DATA_CODE.getMessage());
            }

            //适宜人群集合
            Set<String> applicableSet = new HashSet<>();
            //禁忌人群集合
            Set<String> forbiddenSet = new HashSet<>();

            //病症分组
            Map<String,Set<String>> mapDisNameGroup = new HashMap<>();

            if (meal != null){
                List<MealProduct> mealProductList = meal.getMealProductList();
                for (MealProduct mealProduct : mealProductList) {
                    //根据商品code查询商品
                    ProductDetailVO productDetailVO = productMapper.selectByProductCode(mealProduct.getProductCode());
                    //市场价元
                    double priceD = new BigDecimal(String.valueOf(productDetailVO.getSalePrice() / 100d))
                            .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    productDetailVO.setSalePriceD(priceD);
                    mealProduct.setSalePriceD(priceD);
                    mealProduct.setName(productDetailVO.getName());
                    mealProduct.setImageUrl(productDetailVO.getImageUrl());

                    //适宜人群
                    String applicable = productDetailVO.getApplicable();
                    if (applicable != null && !applicable.isEmpty())
                    applicableSet.addAll(Arrays.asList(applicable.split(",")));

                    //禁忌人群
                    String forbidden = productDetailVO.getForbidden();
                    if (forbidden != null && !forbidden.isEmpty())
                        forbiddenSet.addAll(Arrays.asList(forbidden.split(",")));

                    //病症pid
                    Integer diseasePid = productDetailVO.getDiseasePid();
                    //病症id
                    Integer diseaseId = productDetailVO.getDiseaseId();
                    if (diseasePid !=null && diseaseId !=null){
                        //一级病症
                        Disease disease = diseaseBeanMapper.queryById(diseasePid, 0);
                        //一级病症名称
                        String disname = disease.getName();

                        //病症分组
                        if (mapDisNameGroup.get(disname) == null){
                            Set<String> setDis = new HashSet<>();
                            mapDisNameGroup.put(disname,setDis);
                        }
                        Set<String> setDisName = mapDisNameGroup.get(disname);

                        Disease diseaseChil = diseaseBeanMapper.queryById(diseaseId,diseasePid);
                        setDisName.add(diseaseChil.getName());
                    }
                }
            }

            //病症
            List<DiseaseAllDTO> diseaseAllDTOList = new ArrayList<>();
            for (String key : mapDisNameGroup.keySet()) {
                DiseaseAllDTO diseaseAllDTO = new DiseaseAllDTO();
                diseaseAllDTO.setName(key);
                diseaseAllDTO.setDiseaseDTOSet(mapDisNameGroup.get(key));
                diseaseAllDTOList.add(diseaseAllDTO);
            }


            MealDTO mealDTO = new MealDTO();
            mealDTO.setMeal(meal);
            mealDTO.setDiseaseAllDTOList(diseaseAllDTOList);
            mealDTO.setApplicableSet(applicableSet);
            mealDTO.setForbiddenSet(forbiddenSet);
            return ComResponse.success(mealDTO);
        } catch (Exception ex) {
            log.error("查询套餐画像信息失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "查询套餐画像信息失败");
    }

}

