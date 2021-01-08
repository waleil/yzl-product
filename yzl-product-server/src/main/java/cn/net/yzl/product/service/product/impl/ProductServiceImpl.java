package cn.net.yzl.product.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.model.pojo.product.Product;
import cn.net.yzl.product.model.vo.brand.BrandBeanTO;
import cn.net.yzl.product.model.vo.product.dto.ProductListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
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
import java.util.List;

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
        //代表新增
        if(StringUtils.isEmpty(vo.getProductCode())){
            //获取商品ID
            String cacheKey = CacheKeyUtil.maxProductCacheKey();
            long maxProductCode=redisUtil.incr(cacheKey,1);
            product.setProductCode(String.valueOf(maxProductCode));

            if(!CollectionUtils.isEmpty(vo.getDiseaseVOS())){

            }
        }else{
         //修改

        }
        return null;
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
