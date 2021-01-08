package cn.net.yzl.product.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.config.FastDFSConfig;
import cn.net.yzl.product.dao.ProductDiseaseMapper;
import cn.net.yzl.product.dao.ProductImageMapper;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.model.pojo.product.Product;
import cn.net.yzl.product.model.vo.brand.BrandBeanTO;
import cn.net.yzl.product.model.vo.product.dto.ProductListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductDiseaseVO;
import cn.net.yzl.product.model.vo.product.vo.ProductImageVO;
import cn.net.yzl.product.model.vo.product.vo.ProductSelectVO;
import cn.net.yzl.product.model.vo.product.vo.ProductVO;
import cn.net.yzl.product.service.product.ProductService;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
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
        PageHelper.startPage(vo.getPageNo(),vo.getPageSize());
        List <ProductListDTO> list= productMapper.queryListProduct(vo);
        if(!CollectionUtils.isEmpty(list)){
            for(ProductListDTO dto:list){
                dto.setSalePriceD(new BigDecimal(String.valueOf(dto.getSalePrice()/100d))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                dto.setFastDFSUrl(dfsConfig.getUrl());
            }
        }
        //分页查询
        Page<ProductListDTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
    }
    /**
     * @Author: lichanghong
     * @Description:   编辑商品信息/包含新增
     * @Date: 2021/1/8 10:39 上午
     * @param vo
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ComResponse editProduct(ProductVO vo) {
        //处理金额
        Product product = translateProduct(vo);
        String productCode=vo.getProductCode();
        Map<Integer,List<ProductImageVO>> integerListMap = translateImages(vo.getImages());
        String mainImage = null;
        //判断图片
        if(!CollectionUtils.isEmpty(integerListMap.get(1))){
            List<ProductImageVO> list =integerListMap.get(1);
            if(!CollectionUtils.isEmpty(list)){
                mainImage = list.get(0).getImageUrl();
            }
        }
        product.setImageUrl(mainImage);
        //代表新增
        if(StringUtils.isEmpty(productCode)){
            //获取商品ID
            String cacheKey = CacheKeyUtil.maxProductCacheKey();
            long maxProductCode=redisUtil.incr(cacheKey,1);
             productCode = String.valueOf(maxProductCode);
            product.setProductCode(productCode);
            productMapper.insertSelective(product);
        }else{
         //修改
            productMapper.updateByPrimaryKeySelective(product);
            productDiseaseMapper.deleteByProductCode(productCode);
            productImageMapper.deleteByProductCode(productCode);
        }


        //新增关联病症
        if(!CollectionUtils.isEmpty(vo.getDiseaseVOS())){
            List<ProductDiseaseVO> tempVOS=vo.getDiseaseVOS().stream().filter(d->d.getDiseaseId()!=null).collect(Collectors.toList());
            for (ProductDiseaseVO diseaseVO:tempVOS){
                diseaseVO.setProductCode(productCode);
            }
            productDiseaseMapper.insertArray(tempVOS);
        }
        if(!CollectionUtils.isEmpty(integerListMap)){
            List<ProductImageVO> list = new ArrayList();
            integerListMap.keySet().forEach(key->{
                List<ProductImageVO> tt= integerListMap.get(key);
                if(!CollectionUtils.isEmpty(tt)){
                    list.addAll(tt.stream().filter(t->StringUtils.hasText(t.getImageUrl())).collect(Collectors.toList()));
                }
            });
            for(ProductImageVO productImageVO:list){
                productImageVO.setProductCode(productCode);
                if(productImageVO.getMainFlag()==null){
                    productImageVO.setMainFlag(0);
                }
            }
            productImageMapper.insertArray(list);
        }
        return ComResponse.success();
    }
    /**
     * @Author: lichanghong
     * @Description: 转换图片
     * @Date: 2021/1/8 3:36 下午
     * @param list
     * @Return: java.util.Map<java.lang.Integer,java.util.List<cn.net.yzl.product.model.vo.product.vo.ProductImageVO>>
     */
    private Map<Integer,List<ProductImageVO>> translateImages(List<ProductImageVO> list){

        if(!CollectionUtils.isEmpty(list)){
            Map<Integer,List<ProductImageVO>> temp = new HashMap<>();
            List<ProductImageVO> mainList = new ArrayList<>();
            List<ProductImageVO> otherList = new ArrayList<>();
            for(ProductImageVO productImageVO:list){
                if(productImageVO.getImageUrl()==null){
                    continue;
                }
                if(productImageVO.getMainFlag()!=null&&productImageVO.getMainFlag()==1){
                    mainList.add(productImageVO);
                }else{
                    otherList.add(productImageVO);
                }
            }
            temp.put(1,mainList);
            temp.put(0,otherList);
            return temp;
        }else{
            return Collections.EMPTY_MAP;
        }
    }
    /**
     * @Author: lichanghong
     * @Description: 转换
     * @Date: 2021/1/8 11:44 上午
     * @param vo
     * @Return: cn.net.yzl.product.model.pojo.product.Product
     */
    private Product translateProduct(ProductVO vo){
        Product product = BeanUtil.copyProperties(vo,Product.class);
        //售卖价
        if(vo.getSalePriceD()!=null){
            product.setSalePrice((int)(vo.getSalePriceD()*100));
        }
        //成本价
        if(vo.getCostPriceD()!=null){
            product.setCostPrice((int)(vo.getCostPriceD()*100));
        }
        //最低优惠价
        if(vo.getLimitDownPriceD()!=null){
            product.setLimitDownPrice((int)(vo.getLimitDownPriceD()*100));
        }
        return product;
    }
}
