package cn.net.yzl.product.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.config.FastDFSConfig;
import cn.net.yzl.product.dao.ProductDiseaseMapper;
import cn.net.yzl.product.dao.ProductImageMapper;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.model.db.ProductAtlasBean;
import cn.net.yzl.product.model.pojo.product.Product;
import cn.net.yzl.product.model.pojo.product.ProductStatus;
import cn.net.yzl.product.model.vo.brand.BrandBeanTO;
import cn.net.yzl.product.model.vo.product.dto.ProductAtlasDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductDetailVO;
import cn.net.yzl.product.model.vo.product.dto.ProductListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.service.product.ProductService;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDaoImpl
 * @description 商品服务实现类
 * @date: 2021/1/7 10:00 下午
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ProductDiseaseMapper productDiseaseMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private FastDFSConfig dfsConfig;

    /**
     * @Author: lichanghong
     * @Description: 按照上下架查询商品数量
     * @Date: 2021/1/7 10:37 下午
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO>
     */
    @Override
    public List<ProductStatusCountDTO> queryCountByStatus() {
        return productMapper.queryCountByStatus();
    }

    @Override
    public ComResponse<Page<ProductListDTO>> queryListProduct(ProductSelectVO vo) {
        //开启分页
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<ProductListDTO> list = productMapper.queryListProduct(vo);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProductListDTO dto : list) {
                dto.setSalePriceD(new BigDecimal(String.valueOf(dto.getSalePrice() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                dto.setFastDFSUrl(dfsConfig.getUrl());
            }
        }
        //分页查询
        Page<ProductListDTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
    }

    /**
     * @param vo
     * @param vo
     * @Author: lichanghong
     * @Description: 编辑商品信息/包含新增
     * @Date: 2021/1/8 10:39 上午
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ComResponse editProduct(ProductVO vo) {
        //处理金额
        Product product = translateProduct(vo);
        String productCode = vo.getProductCode();
        Map<Integer, List<ProductImageVO>> integerListMap = translateImages(vo.getImages());
        String mainImage = null;
        //判断图片
        if (!CollectionUtils.isEmpty(integerListMap.get(1))) {
            List<ProductImageVO> list = integerListMap.get(1);
            if (!CollectionUtils.isEmpty(list)) {
                mainImage = list.get(0).getImageUrl();
            }
        }
        product.setImageUrl(mainImage);
        //代表新增
        if (StringUtils.isEmpty(productCode)) {
            //获取商品ID
            String cacheKey = CacheKeyUtil.maxProductCacheKey();
            long maxProductCode = redisUtil.incr(cacheKey, 1);
            productCode = String.valueOf(maxProductCode);
            product.setProductCode(productCode);
            productMapper.insertSelective(product);
        } else {
            ProductStatus productStatus = productMapper.queryProductStatusByProductCode(productCode);
            if (productStatus == null) {
                return ComResponse.fail(ResponseCodeEnums.NO_MATCHING_RESULT_CODE.getCode(), ResponseCodeEnums.NO_MATCHING_RESULT_CODE.getMessage());
            }
            if (productStatus.getUpdateTime().getTime() > vo.getUpdateTime().getTime()) {
                return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
            }
            //修改
            productMapper.updateByPrimaryKeySelective(product);
            productDiseaseMapper.deleteByProductCode(productCode);
            productImageMapper.deleteByProductCode(productCode);
        }
        handleDisease(vo.getDiseaseVOS(), productCode);
        handleImages(productCode, integerListMap);
        return ComResponse.success();
    }

    @Override
    public ComResponse updateStatusByProductCode(ProductUpdateStatusVO vo) {
        try {
            productMapper.updateStatusByProductCode(vo);
            return ComResponse.success();
        } catch (Exception ex) {
            log.error("修改商品上下架状态失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "修改失败");
    }

    /**
     * @param productCode    商品编号
     * @param integerListMap 图片相关
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/8 7:05 下午
     * @Return: void
     */
    private void handleImages(String productCode, Map<Integer, List<ProductImageVO>> integerListMap) {

        if (!CollectionUtils.isEmpty(integerListMap)) {
            List<ProductImageVO> list = new ArrayList();
            integerListMap.keySet().forEach(key -> {
                List<ProductImageVO> tt = integerListMap.get(key);
                if (!CollectionUtils.isEmpty(tt)) {
                    list.addAll(tt.stream().filter(t -> StringUtils.hasText(t.getImageUrl())).collect(Collectors.toList()));
                }
            });
            for (ProductImageVO productImageVO : list) {
                productImageVO.setProductCode(productCode);
                if (productImageVO.getMainFlag() == null) {
                    productImageVO.setMainFlag(0);
                }
            }
            productImageMapper.insertArray(list);
        }
    }

    /**
     * @param list        病症
     * @param productCode 商品编号
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/8 7:04 下午
     * @Return: void
     */
    private void handleDisease(List<ProductDiseaseVO> list, String productCode) {
        //新增关联病症
        if (!CollectionUtils.isEmpty(list)) {
            List<ProductDiseaseVO> tempVOS = list.stream().filter(d -> d.getDiseaseId() != null).collect(Collectors.toList());
            for (ProductDiseaseVO diseaseVO : tempVOS) {
                diseaseVO.setProductCode(productCode);
            }
            productDiseaseMapper.insertArray(tempVOS);
        }
    }

    /**
     * @param list
     * @Author: lichanghong
     * @Description: 转换图片
     * @Date: 2021/1/8 3:36 下午
     * @Return: java.util.Map<java.lang.Integer, java.util.List < cn.net.yzl.product.model.vo.product.vo.ProductImageVO>>
     */
    private Map<Integer, List<ProductImageVO>> translateImages(List<ProductImageVO> list) {

        if (!CollectionUtils.isEmpty(list)) {
            Map<Integer, List<ProductImageVO>> temp = new HashMap<>();
            List<ProductImageVO> mainList = new ArrayList<>();
            List<ProductImageVO> otherList = new ArrayList<>();
            for (ProductImageVO productImageVO : list) {
                if (productImageVO.getImageUrl() == null) {
                    continue;
                }
                if (productImageVO.getMainFlag() != null && productImageVO.getMainFlag() == 1) {
                    mainList.add(productImageVO);
                } else {
                    otherList.add(productImageVO);
                }
            }
            temp.put(1, mainList);
            temp.put(0, otherList);
            return temp;
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    /**
     * @param vo
     * @Author: lichanghong
     * @Description: 转换
     * @Date: 2021/1/8 11:44 上午
     * @Return: cn.net.yzl.product.model.pojo.product.Product
     */
    private Product translateProduct(ProductVO vo) {
        Product product = BeanUtil.copyProperties(vo, Product.class);
        //售卖价
        if (vo.getSalePriceD() != null) {
            product.setSalePrice((int) (vo.getSalePriceD() * 100));
        }
        //成本价
        if (vo.getCostPriceD() != null) {
            product.setCostPrice((int) (vo.getCostPriceD() * 100));
        }
        //最低优惠价
        if (vo.getLimitDownPriceD() != null) {
            product.setLimitDownPrice((int) (vo.getLimitDownPriceD() * 100));
        }
        return product;
    }


    /**
     * 查询商品图谱
     *
     * @param productName 商品名称(模糊查询)
     * @param id          病症id
     * @return
     */
    @Override
    public ComResponse<ProductAtlasDTO> queryProductListAtlas(String productName, Integer id) {
        if (productName == null || id == null)
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());

        //查询商品数据(商品图谱)
        List<ProductAtlasBean> productAtlasBeanList = productMapper.queryProductListAtlas(productName, id);

        //判断是否有数据
        if (productAtlasBeanList == null || productAtlasBeanList.size() == 0)
            return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());

        /**
         * 组装数据
         * 根据产品需求    横轴：同一病症
         *                纵轴：分组 商品品牌+商品名称四个字(添加商品时,录入商品名称长度必须大于等于4)
         *                排序： 自营：由下至上 价格由小到大
         *                       第三方：由上至下 价格由小到大
         */


        /**
         * 大map   存储横轴 同一病症    key：商品品牌+商品名称
         * 小map     key：1代表自营   2代表第三方
         *list    存储纵轴商品数据
         */
        Map<String, Map<Integer, List<ProductAtlasBean>>> productAtlasListMap = new HashMap<>();

        //拼接商品品牌名称和商品名称前四个字
        StringBuffer brandProductKeyBuffer = new StringBuffer();

        Map<Integer, List<ProductAtlasBean>> productsourceMap = null;
        List<ProductAtlasBean> productAtlasBeanListCopy = null;

        for (ProductAtlasBean productAtlasBean : productAtlasBeanList) {
            //商品市场价(分转元)
            productAtlasBean.setSalePriceD(new BigDecimal(String.valueOf(productAtlasBean.getSalePrice() / 100d))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //商品来源
            Integer goodsSource = productAtlasBean.getGoodsSource();
            //商品品牌名称
            String brandName = productAtlasBean.getBrandName();
            //商品名称(前四位)
            String name = productAtlasBean.getName();
            if (name != null && name.length() >= 4) {
                name = productAtlasBean.getName().substring(0, 4);
            }
            //根据商品品牌名称+商品名称(前四位)=key进行分组
            String key = brandProductKeyBuffer.append(brandName).append(name).toString();
            brandProductKeyBuffer.setLength(0);

            if (productAtlasListMap.get(key) == null) {
                productsourceMap = new HashMap<>();
                productAtlasListMap.put(key, productsourceMap);
            }

            if (productsourceMap.get(goodsSource) == null) {
                productAtlasBeanListCopy = new ArrayList<>();
                productsourceMap.put(goodsSource, productAtlasBeanListCopy);
            }

            productAtlasBeanListCopy.add(productAtlasBean);

        }


        //排好序的结果
        List<Map<Integer, List<ProductAtlasBean>>> productAtlasResult = new ArrayList<>();

        /**
         * 排序    价格 自营 由大到小
         *              第三方 由小到大
         */
        for (String key : productAtlasListMap.keySet()) {
            productsourceMap = productAtlasListMap.get(key);
            for (Integer goodSource : productsourceMap.keySet()) {
                productAtlasBeanList = productsourceMap.get(goodSource);
                if (goodSource == 1) {//自营
                    productAtlasBeanList.sort(Comparator.comparing(ProductAtlasBean::getSalePriceD).reversed());//反序
                } else {//第三方
                    productAtlasBeanList.sort(Comparator.comparing(ProductAtlasBean::getSalePriceD));//正序
                }
            }
            productAtlasResult.add(productsourceMap);
        }


        //包装
        ProductAtlasDTO productAtlasDTO = new ProductAtlasDTO();
        productAtlasDTO.setProductAtlasResult(productAtlasResult);

        return ComResponse.success(productAtlasDTO);
    }

    //修改商品售卖时间
    @Override
    public ComResponse updateTimeByProductCode(ProductUpdateTimeVO vo) {
        try {
            productMapper.updateTimeByProductCode(vo);
            return ComResponse.success();
        } catch (Exception ex) {
            log.error("修改商品售卖时间失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "修改商品售卖时间失败");
    }

    @Override
    public ComResponse<ProductDetailVO> queryProducDetail(String productCode) {
        try {
            ProductDetailVO productVO = productMapper.selectByProductCode(productCode);
            return ComResponse.success(productVO);
        } catch (Exception ex) {
            log.error("查询商品详情信息失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "查询商品详情信息失败");
    }


}
