package cn.net.yzl.product.service.meal.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.config.FastDFSConfig;
import cn.net.yzl.product.dao.MealMapper;
import cn.net.yzl.product.dao.MealProductMapper;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.model.db.Meal;
import cn.net.yzl.product.model.db.MealProduct;
import cn.net.yzl.product.model.pojo.disease.Disease;
import cn.net.yzl.product.model.vo.disease.DiseaseAllDTO;
import cn.net.yzl.product.model.vo.product.dto.MealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductDetailVO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
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
        String mealNo = vo.getMealNo();
        //代表新增
        if (null == mealNo) {
            //获取套餐ID
            String cacheKey = CacheKeyUtil.maxMealCacheKey();
            long maxProductCode = redisUtil.incr(cacheKey, 1);
            String maxProductCodeStr= String.format("T%07d", maxProductCode);
            meal.setMealNo(maxProductCodeStr);
            meal.setCreateNo(maxProductCodeStr);
            mealMapper.insertSelective(meal);
            //套餐商品新增
            List<MealProductVO> mealProducts = vo.getMealProducts();
            List<MealProduct> mealProductList = BeanCopyUtil.copyListProperties(mealProducts, MealProduct::new);
//            mealProductList.stream().forEach(n -> n.setMealNo(maxProductCode));
            mealProductList.stream().forEach(n -> n.setMealNo(cacheKey));
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
        List<ProductMealListDTO> productMealList = mealMapper.queryListProductMeal(vo);
        if (!CollectionUtils.isEmpty(productMealList)) {
            List<String> mealNoList = new ArrayList<String>();
            for (ProductMealListDTO dto : productMealList) {

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
                    Map<String, List<MealListProductDTO>> mealListProductMap = mealProducts.stream().collect(Collectors.groupingBy(MealListProductDTO::getMealNO, LinkedHashMap::new, Collectors.toList()));
                    //设置套餐关联商品
                    productMealList.stream().forEach(productMealListDTO -> {
                        //品牌名称集合
                        Set<String> brandNames = new HashSet<>();
                        //商品编码集合
                        Set<String> productCodes = new HashSet<>();

                        List<Integer> listSort = new ArrayList<>();

                        List<MealListProductDTO> mealListProductDTOList = mealListProductMap.get(productMealListDTO.getMealNo());
                        for (MealListProductDTO mealListProductDTO : mealListProductDTOList) {
                            String brandName = mealListProductDTO.getBrandName();
                            if (brandName != null){
                                brandNames.add(brandName);
                            }
                            String productCode = mealListProductDTO.getProductCode();
                            if (productCode !=null)
                            productCodes.add(productCode);

                            //单个商品库存
                            Integer stock = mealListProductDTO.getStock();
                            //商品数量
                            Integer productNum = mealListProductDTO.getProductNum();
                            if (stock==0){
                                listSort.add(0);
                            }else if (productNum==0){
                                listSort.add(stock);
                            }else {
                                if (stock==-1){//无限制库存
                                    stock=99999999;
                                }
                                double floor = Math.floor(stock / productNum);
                                listSort.add(new Double(floor).intValue());
                            }
                        }

                        if (listSort.size()>0){
                            Collections.sort(listSort, new Comparator<Integer>() {
                                @Override
                                public int compare(Integer o1, Integer o2) {
                                    return o2.compareTo(o1);
                                }
                            });
                            productMealListDTO.setStoreNum(listSort.get(0));

                        }else {
                            productMealListDTO.setStoreNum(0);
                        }
                        productMealListDTO.setProductCodes(productCodes);
                        productMealListDTO.setBrandNames(brandNames);
                        productMealListDTO.setMealProductList(mealListProductMap.get(productMealListDTO.getMealNo()));
                    });
                }
            }
        }
        //分页查询
        Page<ProductMealListDTO> pageInfo = AssemblerResultUtil.resultAssembler(productMealList);
        return ComResponse.success(pageInfo);
    }

    @Override
    public ComResponse<MealDTO> queryProductMealPortray(Integer mealNo) {
        try {
            //查询套餐信息
            Meal meal = mealMapper.queryProductMealPortray(mealNo);
            if (meal == null) {
                return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());
            }

            //套餐价(元)
            Integer price = meal.getPrice();
            if (price != null){
                meal.setPriceD(new BigDecimal(String.valueOf(meal.getPrice() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }

            //优惠价(元)
            Integer discountPrice = meal.getDiscountPrice();
            if (discountPrice != null){
                meal.setDiscountPriceD(new BigDecimal(String.valueOf(meal.getDiscountPriceD() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }

            //适宜人群集合
            Set<String> applicableSet = new HashSet<>();
            //禁忌人群集合
            Set<String> forbiddenSet = new HashSet<>();

            //病症分组
            Map<String, Set<String>> mapDisNameGroup = new HashMap<>();

            if (meal != null) {
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
                    mealProduct.setMealNo(meal.getMealNo());

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
                    if (diseasePid != null && diseaseId != null) {
                        //一级病症
                        Disease disease = diseaseBeanMapper.queryById(diseasePid, 0);
                        //一级病症名称
                        String disname = disease.getName();

                        //病症分组
                        if (mapDisNameGroup.get(disname) == null) {
                            Set<String> setDis = new HashSet<>();
                            mapDisNameGroup.put(disname, setDis);
                        }
                        Set<String> setDisName = mapDisNameGroup.get(disname);

                        Disease diseaseChil = diseaseBeanMapper.queryById(diseaseId, diseasePid);
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

