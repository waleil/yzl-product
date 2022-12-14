package cn.net.yzl.product.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.common.util.JsonUtil;
import cn.net.yzl.product.config.FastDFSConfig;
import cn.net.yzl.product.config.distributedlock.RedissonLockUtil;
import cn.net.yzl.product.dao.BrandBeanMapper;
import cn.net.yzl.product.dao.ProductDiseaseMapper;
import cn.net.yzl.product.dao.ProductImageMapper;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.model.db.BrandBean;
import cn.net.yzl.product.model.db.ProductAtlasBean;
import cn.net.yzl.product.model.pojo.category.Category;
import cn.net.yzl.product.model.pojo.disease.Disease;
import cn.net.yzl.product.model.pojo.product.Product;
import cn.net.yzl.product.model.pojo.product.ProductDisease;
import cn.net.yzl.product.model.pojo.product.ProductStatus;
import cn.net.yzl.product.model.pojo.product.ProductStockDO;
import cn.net.yzl.product.model.vo.disease.DiseaseDTO;
import cn.net.yzl.product.model.vo.disease.DiseaseTreeNode;
import cn.net.yzl.product.model.vo.product.dto.*;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.service.CategoryService;
import cn.net.yzl.product.service.DiseaseService;
import cn.net.yzl.product.service.product.ProductService;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
 * @description ?????????????????????
 * @date: 2021/1/7 10:00 ??????
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
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandBeanMapper brandBeanMapper;

    /**
     * @Author: lichanghong
     * @Description: ?????????????????????????????????
     * @Date: 2021/1/7 10:37 ??????
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO>
     */
    @Override
    public List<ProductStatusCountDTO> queryCountByStatus() {
        return productMapper.queryCountByStatus();
    }

    @Override
    public ComResponse<Page<ProductListDTO>> queryListProduct(ProductSelectVO vo) {
        //????????????
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<ProductListDTO> list = productMapper.queryListProduct(vo);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProductListDTO dto : list) {
                dto.setSalePriceD(new BigDecimal(String.valueOf(dto.getSalePrice() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                dto.setFastDFSUrl(dfsConfig.getUrl());
                //????????????????????????
                if (dto.getCategoryDictCode() != null && dto.getCategoryDictCode() > 0) {
                    //???????????????????????????
                    Category category = categoryService.queryById(dto.getCategoryDictCode());
                    StringBuilder sb = new StringBuilder();
                    if (category != null) {
                        Category pCategory = categoryService.queryById(category.getPid());
                        if (pCategory != null) {
                            sb.append(pCategory.getName()).append(">");
                        }
                        sb.append(category.getName());
                        dto.setCategoryStr(sb.toString());
                    }
                }
                //????????????????????????
                if (dto.getDiseaseId() != null && dto.getDiseaseId() > 0) {
                    //??????????????????
                    Disease disease = diseaseService.queryById(dto.getDiseaseId(), dto.getDiseasePid());
                    if (disease != null && disease.getPid() > 0) {
                        StringBuilder sb = new StringBuilder();
                        //???????????????????????????
                        Disease p = diseaseService.queryById(disease.getPid(), 0);
                        if (p != null) {
                            sb.append(p.getName()).append(">");
                        }
                        sb.append(disease.getName());
                        dto.setDiseaseStr(sb.toString());
                    }
                }
            }
        }
        //????????????
        Page<ProductListDTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
    }

    @Override
    public ComResponse<Page<ProductListDTO>> queryProducts(ProductSelectVO vo) {
        //????????????
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<ProductListDTO> list = productMapper.queryListProduct(vo);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProductListDTO dto : list) {
                dto.setSalePriceD(new BigDecimal(String.valueOf(dto.getSalePrice() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                dto.setFastDFSUrl(dfsConfig.getUrl());
            }
        }
        //????????????
        Page<ProductListDTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
    }

    /**
     * @param vo
     * @param vo
     * @Author: lichanghong
     * @Description: ??????????????????/????????????
     * @Date: 2021/1/8 10:39 ??????
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ComResponse editProduct(ProductVO vo) {
        //????????????
        Product product = translateProduct(vo);
        String productCode = vo.getProductCode();
        Map<Integer, List<ProductImageVO>> integerListMap = translateImages(vo.getImages());
        String mainImage = null;
        //????????????
        if (!CollectionUtils.isEmpty(integerListMap.get(1))) {
            List<ProductImageVO> list = integerListMap.get(1);
            if (!CollectionUtils.isEmpty(list)) {
                mainImage = list.get(0).getImageUrl();
            }
        }
        product.setImageUrl(mainImage);
        //????????????
        if (StringUtils.isEmpty(productCode)) {
            //????????????ID
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
            //????????????????????????????????????
            if (productStatus.getStatus() == 1) {
                return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), "?????????????????????????????????");
            }
            //??????
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
            //???????????????????????????
            if (vo.getStatus() == 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("nowTime", new Date());
                map.put("list", vo.getProductCodeList());
                List<String> list = productMapper.querySaleEndTimeByCodes(map);
                if (vo.getProductCodeList().size() != list.size()) {
                    return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), "????????????????????????????????????,????????????????????????!");
                }
            }
            productMapper.updateStatusByProductCode(vo);
            return ComResponse.success();
        } catch (Exception ex) {
            log.error("?????????????????????????????????,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "????????????");
    }

    /**
     * @param productCode    ????????????
     * @param integerListMap ????????????
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/8 7:05 ??????
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
     * @param list        ??????
     * @param productCode ????????????
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/8 7:04 ??????
     * @Return: void
     */
    private void handleDisease(List<ProductDiseaseVO> list, String productCode) {
        //??????????????????
        if (!CollectionUtils.isEmpty(list)) {
            List<ProductDiseaseVO> tempVOS = list.stream().filter(d -> d.getDiseaseId() != null
                    && d.getDiseasePid() != null)
                    .collect(Collectors.toList());
            for (ProductDiseaseVO diseaseVO : tempVOS) {
                diseaseVO.setProductCode(productCode);
            }
            productDiseaseMapper.insertArray(tempVOS);
        }
    }

    /**
     * @param list
     * @Author: lichanghong
     * @Description: ????????????
     * @Date: 2021/1/8 3:36 ??????
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
     * @Description: ??????
     * @Date: 2021/1/8 11:44 ??????
     * @Return: cn.net.yzl.product.model.pojo.product.Product
     */
    private Product translateProduct(ProductVO vo) {
        Product product = BeanUtil.copyProperties(vo, Product.class);
        //?????????
        if (vo.getSalePriceD() != null) {
            product.setSalePrice((int) (vo.getSalePriceD() * 100));
        }
        //?????????
        if (vo.getCostPriceD() != null) {
            product.setCostPrice((int) (vo.getCostPriceD() * 100));
        }
        //???????????????
        if (vo.getLimitDownPriceD() != null) {
            product.setLimitDownPrice((int) (vo.getLimitDownPriceD() * 100));
        }
        return product;
    }


    /**
     * ??????????????????
     *
     * @param productName ????????????(????????????)
     * @param id          ??????id
     * @return
     */
    @Override
    public ComResponse<List<ProductAtlasDTO>> queryProductListAtlas(String productName, Integer id, Integer pid) {
        if (StringUtils.isEmpty(productName) && (id == null || pid == null))
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());

        //??????????????????(????????????)
        List<ProductAtlasBean> productAtlasBeanList = productMapper.queryProductListAtlas(productName, id, pid);

        //?????????????????????
        if (productAtlasBeanList == null || productAtlasBeanList.size() == 0)
            return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());



        /*??????????????????????????????????????????????????????    ????????????*/
        Map<Integer, List<ProductAtlasBean>> mapResult = new HashMap<>();
        if (id == null) {
            for (ProductAtlasBean productAtlasBean : productAtlasBeanList) {
                //??????id
                Integer disId = productAtlasBean.getId();
                if (mapResult.get(disId) == null) {
                    List<ProductAtlasBean> atlasBeanList = new ArrayList<>();
                    mapResult.put(disId, atlasBeanList);
                }
                mapResult.get(disId).add(productAtlasBean);
            }
        } else {
            mapResult.put(id, productAtlasBeanList);
        }


        /**
         * ????????????
         * ??????????????????    ?????????????????????
         *                ??????????????? ????????????+?????????????????????(???????????????,??????????????????????????????????????????4)
         *                ????????? ????????????????????? ??????????????????
         *                       ???????????????????????? ??????????????????
         */


        //????????????????????????
        List<ProductAtlasDTO> productAtlasDTOListResult = new ArrayList<>();


        //???????????????????????????????????????????????????
        StringBuffer brandProductKeyBuffer = new StringBuffer();

        Map<Integer, List<ProductAtlasBean>> productsourceMap = null;
        List<ProductAtlasBean> productAtlasBeanListCopy = null;

        for (List<ProductAtlasBean> atlasBeanList : mapResult.values()) {
            /**
             * ???map   ???????????? ????????????    key???????????????+????????????
             * ???map     key???1????????????   2???????????????
             *list    ????????????????????????
             */
            Map<String, Map<Integer, List<ProductAtlasBean>>> productAtlasListMap = new HashMap<>();


            for (ProductAtlasBean productAtlasBean : atlasBeanList) {
                //???????????????(?????????)
                productAtlasBean.setSalePriceD(new BigDecimal(String.valueOf(productAtlasBean.getSalePrice() / 100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //????????????
                Integer goodsSource = productAtlasBean.getGoodsSource();
                //??????????????????
                String brandName = productAtlasBean.getBrandName();
                //????????????(?????????)
                String name = productAtlasBean.getName();
                if (name != null && name.length() >= 4) {
                    name = productAtlasBean.getName().substring(0, 4);
                }
                //????????????????????????+????????????(?????????)=key????????????
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

            //??????????????????
            List<Map<Integer, List<ProductAtlasBean>>> productAtlasResult = new ArrayList<>();

            /**
             * ??????    ?????? ?????? ????????????
             *              ????????? ????????????
             */
            for (String key : productAtlasListMap.keySet()) {
                productsourceMap = productAtlasListMap.get(key);
                for (Integer goodSource : productsourceMap.keySet()) {
                    productAtlasBeanList = productsourceMap.get(goodSource);
                    if (goodSource == 1) {//??????
                        productAtlasBeanList.sort(Comparator.comparing(ProductAtlasBean::getSalePriceD).reversed());//??????
                    } else {//?????????
                        productAtlasBeanList.sort(Comparator.comparing(ProductAtlasBean::getSalePriceD));//??????
                    }
                }
                productAtlasResult.add(productsourceMap);
            }


            //??????
            ProductAtlasDTO productAtlasDTO = new ProductAtlasDTO();
            productAtlasDTO.setProductAtlasResult(productAtlasResult);
            productAtlasDTOListResult.add(productAtlasDTO);

        }

        return ComResponse.success(productAtlasDTOListResult);
    }

    //????????????????????????
    @Override
    public ComResponse updateTimeByProductCode(ProductUpdateTimeVO vo) {
        try {
            productMapper.updateTimeByProductCode(vo);
            return ComResponse.success();
        } catch (Exception ex) {
            log.error("??????????????????????????????,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "??????????????????????????????");
    }

    @Override
    public ComResponse<ProductDetailVO> queryProductDetail(String productCode) {
        try {
            ProductDetailVO productVO = productMapper.selectByProductCode(productCode);
            //????????????
            productVO.setSalePriceD(new BigDecimal(String.valueOf(productVO.getSalePrice() / 100d))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            productVO.setCostPriceD(new BigDecimal(String.valueOf(productVO.getCostPrice() / 100d))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            productVO.setLimitDownPriceD(new BigDecimal(String.valueOf(productVO.getLimitDownPrice() / 100d))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //????????????
            productVO.setImages(productImageMapper.queryByProductCode(productCode));
            productVO.setFastDFSUrl(dfsConfig.getUrl());
            //?????????????????????
            if (productVO.getCategoryDictCode() != null) {
                Category category = categoryService.queryById(productVO.getCategoryDictCode());
                if (category != null) {
                    productVO.setCategoryPDictCode(category.getPid());
                }
            }
            //??????????????????
            List<ProductDiseaseVO> productDiseaseVOList = productDiseaseMapper.queryByProductCode(productCode);
            if (!CollectionUtils.isEmpty(productDiseaseVOList)) {
                for (ProductDiseaseVO v : productDiseaseVOList) {
                    Disease d = diseaseService.queryById(v.getDiseaseId(), v.getDiseasePid());
                    v.setDiseaseName(d.getName());
                }
                productVO.setDiseaseVOS(productDiseaseVOList);
            }

            return ComResponse.success(productVO);
        } catch (Exception ex) {
            log.error("??????????????????????????????,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "??????????????????????????????");
    }

    @Override
    public ProductPortraitDTO queryProductPortrait(String productCode) {
        ProductDetailVO productVO = productMapper.selectByProductCode(productCode);
        ProductPortraitDTO dto = transformProductPortraitDTO(productVO);
        dto.setCategoryDictName(getFullCategoryName(productVO.getCategoryDictCode()));
        //??????
        if (checkInteger(productVO.getBrandNo())) {
            BrandBean brandBean = brandBeanMapper.selectByPrimaryKey(productVO.getBrandNo());
            if (brandBean != null) {
                dto.setBrandName(brandBean.getName());
            }
        }
        //??????????????????
        List<ProductDiseaseVO> diseaseVOS = productDiseaseMapper.queryByProductCode(productCode);
        if (!CollectionUtils.isEmpty(diseaseVOS)) {
            for (ProductDiseaseVO v : diseaseVOS) {
                Disease d = diseaseService.queryById(v.getDiseaseId(), v.getDiseasePid());
                v.setDiseaseName(d.getName());
            }
        }
        dto.setDiseaseVOS(diseaseVOS);
        Map<Integer, DiseaseDTO> root = queryRootDisease();
        List<DiseaseTreeNode> list = new ArrayList<>();
        //?????????????????????
        if (dto.getDiseaseId() != null && dto.getDiseasePid() != null) {
            DiseaseTreeNode diseaseTreeNode = new DiseaseTreeNode();
            DiseaseDTO dto1 = root.get(dto.getDiseasePid());
            diseaseTreeNode.setName(dto1.getName());
            diseaseTreeNode.setPid(0);
            diseaseTreeNode.setId(dto1.getId());
            List<DiseaseTreeNode> diseaseTreeNodes = new ArrayList<>();
            Disease d = diseaseService.queryById(dto.getDiseaseId(), dto.getDiseasePid());
            DiseaseTreeNode node = new DiseaseTreeNode();
            node.setPid(d.getPid());
            node.setId(d.getId());
            dto.setDiseaseName(d.getName());
            node.setName(d.getName());
            diseaseTreeNodes.add(node);
            diseaseTreeNode.setNodeList(diseaseTreeNodes);
            list.add(diseaseTreeNode);
        }
        //TODO ?????????????????????
//        //??????????????????
//        if (!CollectionUtils.isEmpty(diseaseVOS)) {
//            Map<Integer, List<ProductDiseaseVO>> integerListMap =
//                    diseaseVOS.stream().collect(Collectors.groupingBy(ProductDiseaseVO::getDiseasePid));
//            integerListMap.keySet().stream().forEach(key -> {
//                DiseaseTreeNode diseaseTreeNode = new DiseaseTreeNode();
//                DiseaseDTO dto1 = root.get(key);
//                diseaseTreeNode.setId(key);
//                diseaseTreeNode.setName(dto1.getName());
//                diseaseTreeNode.setPid(0);
//                List<DiseaseTreeNode> list1 = new ArrayList<>();
//                integerListMap.get(key).forEach(v -> {
//                    DiseaseTreeNode node = new DiseaseTreeNode();
//                    Disease d = diseaseService.queryById(v.getDiseaseId(), v.getDiseasePid());
//                    node.setName(d.getName());
//                    node.setPid(key);
//                    node.setId(v.getDiseaseId());
//                    list1.add(node);
//                });
//                diseaseTreeNode.setNodeList(list1);
//                list.add(diseaseTreeNode);
//            });
//        }
        dto.setDiseaseTreeNodes(list);
        return dto;
    }

    /**
     * @param productCode
     * @Author: lichanghong
     * @Description: ??????????????????????????????
     * @Date: 2021/1/10 4:03 ??????
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductDiseaseDTO>
     */
    @Override
    public List<ProductDiseaseDTO> queryDiseaseByProductCode(String productCode) {
        //??????????????????
        List<ProductDiseaseDTO> list = new ArrayList<>(32);
        ProductDisease disease = productMapper.queryDiseaseByProductCode(productCode);
        if (disease != null && disease.getDiseaseId() != null && disease.getDiseaseId() > 0) {
            Disease d = diseaseService.queryById(disease.getDiseaseId(), disease.getDiseasePid());
            if (d != null) {
                ProductDiseaseDTO diseaseDTO = new ProductDiseaseDTO();
                Disease pd = diseaseService.queryById(d.getPid(), 0);
                if (pd != null) {
                    diseaseDTO.setDiseaseId(d.getId());
                    diseaseDTO.setDiseaseName(d.getName());
                    diseaseDTO.setDiseasePid(pd.getId());
                    diseaseDTO.setDiseasePName(pd.getName());
                    diseaseDTO.setProductCode(productCode);
                    list.add(diseaseDTO);
                }
            }
        }
        //TODO ?????????????????????
//        List<ProductDiseaseVO> diseaseVOS = productDiseaseMapper.queryByProductCode(productCode);
//        if (!CollectionUtils.isEmpty(diseaseVOS)) {
//            diseaseVOS.stream().forEach(v -> {
//                if (v != null && v.getDiseaseId() != null && v.getDiseaseId() > 0) {
//                    Disease d = diseaseService.queryById(v.getDiseaseId(), v.getDiseasePid());
//                    if (d != null) {
//                        ProductDiseaseDTO diseaseDTO = new ProductDiseaseDTO();
//                        Disease pd = diseaseService.queryById(d.getPid(), 0);
//                        if (pd != null) {
//                            diseaseDTO.setDiseaseId(d.getId());
//                            diseaseDTO.setDiseaseName(d.getName());
//                            diseaseDTO.setDiseasePid(pd.getId());
//                            diseaseDTO.setDiseasePName(pd.getName());
//                            diseaseDTO.setProductCode(productCode);
//                            list.add(diseaseDTO);
//                        }
//                    }
//                }
//            });
//        }
        return list;
    }

    @Override
    public List<ProductDTO> queryByProductCodes(List<String> codes) {
        List<ProductDTO> list = productMapper.queryByProductCodes(codes);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        for (ProductDTO dto : list) {
            //????????????
            dto.setSalePriceD(new BigDecimal(String.valueOf(dto.getSalePrice() / 100d))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            dto.setFastDFSUrl(dfsConfig.getUrl());
        }
        return list;
    }

    /**
     * @param orderProductVO ????????????
     * @Author: lichanghong
     * @Description: ????????????
     * @Date: 2021/1/11 11:34 ??????
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @Override
    public ComResponse productReduce(OrderProductVO orderProductVO) {
        //????????????
        List<String> codes = orderProductVO.getProductReduceVOS()
                .stream()
                .map(ProductReduceVO::getProductCode)
                .collect(Collectors.toList());
        List<ProductStockDO> productStockDOS= this.queryStockByCodes(codes);
        for(ProductStockDO stockDO:productStockDOS){
            //????????????
            if(stockDO.getLimitFlag()){
               //????????????

            }
        }
        //todo ???????????????
        return ComResponse.success();
    }

    /**
     * @param orderProductVO
     * @Author: lichanghong
     * @Description: ????????????
     * @Date: 2021/1/11 11:30 ??????
     * @Return:
     */
    @Override
    public ComResponse increaseStock(OrderProductVO orderProductVO) {
        //todo ???????????????
        return ComResponse.success();
    }

    /**
     * @param list ????????????
     * @Author: lichanghong
     * @Description: ??????????????????
     * @Date: 2021/1/10 6:28 ??????
     * @Return:
     */
    private List<ProductStockDO> queryStockByCodes(@Param("list") List<String> list) {
        Set<String> cacheKeys = new HashSet<>();
        for (String str : list) {
            String cacheKey = CacheKeyUtil.generateProductStockCacheKey(str);
            cacheKeys.add(cacheKey);
        }
        List<Object> objects = redisUtil.multiGet(cacheKeys);
        List<ProductStockDO> productStockDOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(objects)) {
            for (Object obj : objects) {
                ProductStockDO stockDO = JsonUtil.getObjectFromJSONString(String.valueOf(obj), ProductStockDO.class);
                if (stockDO != null && StringUtils.hasText(stockDO.getProductCode())) {
                    list.remove(stockDO.getProductCode());
                    productStockDOS.add(stockDO);
                }
            }
        }
        if (list.size() > 0) {
            synchronized (ProductServiceImpl.class) {
                List<ProductStockDO> productStockDOList = productMapper.queryStockByCodes(list);
                if (!CollectionUtils.isEmpty(productStockDOList)) {
                    productStockDOS.addAll(productStockDOList);
                    Map<String, Object> map = new HashMap<>();
                    //????????????
                    for (ProductStockDO stockDO : productStockDOList) {
                        //?????????????????????
                        if(stockDO.getStock()==-1){
                            stockDO.setLimitFlag(false);
                        }
                        String cacheKey = CacheKeyUtil.generateProductStockCacheKey(stockDO.getProductCode());
                        map.put(cacheKey, JsonUtil.toJsonStr(stockDO));
                    }
                    redisUtil.multiSet(map);
                }
            }
        }
        return productStockDOS;
    }

    /**
     * @param
     * @Author: lichanghong
     * @Description: ???????????????
     * @Date: 2021/1/10 3:07 ??????
     * @Return: java.util.Map<java.lang.Integer, cn.net.yzl.product.model.vo.disease.DiseaseDTO>
     */
    private Map<Integer, DiseaseDTO> queryRootDisease() {

        //????????????????????????
        List<DiseaseDTO> list = diseaseService.queryByPID(0);
        Map<Integer, DiseaseDTO> map = new HashMap<>(list.size() * 2);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(diseaseDTO -> {
                map.put(diseaseDTO.getId(), diseaseDTO);
            });
        }
        return map;
    }

    /**
     * @param integer
     * @Author: lichanghong
     * @Description: ????????????????????????
     * @Date: 2021/1/10 2:08 ??????
     * @Return: boolean
     */
    private boolean checkInteger(Integer integer) {
        if (integer == null) {
            return false;
        }
        if (integer <= 0) {
            return false;
        }
        return true;
    }

    private ProductPortraitDTO transformProductPortraitDTO(ProductDetailVO productVO) {
        ProductPortraitDTO dto = BeanUtil.copyProperties(productVO, ProductPortraitDTO.class);
        //????????????
        dto.setSalePriceD(new BigDecimal(String.valueOf(productVO.getSalePrice() / 100d))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        dto.setCostPriceD(new BigDecimal(String.valueOf(productVO.getCostPrice() / 100d))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        dto.setLimitDownPriceD(new BigDecimal(String.valueOf(productVO.getLimitDownPrice() / 100d))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        return dto;
    }

    public String getFullCategoryName(Integer id) {
        ComResponse<cn.net.yzl.product.model.db.Category> category = categoryService.getCategoryById(id);
        if (category.getData() == null) {
            return null;
        } else if (category.getData().getPid() == null) {
            return category.getData().getName();
        }
        String pre = category.getData().getName();
        String stuf = categoryService.getCategoryById(category.getData().getPid()).getData().getName();
        return pre + " - " + stuf;
    }


}
