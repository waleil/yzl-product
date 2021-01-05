package cn.net.yzl.product.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.ProductBeanMapper;
import cn.net.yzl.product.model.db.ProductBean;
import cn.net.yzl.product.model.vo.product.ProductBO;
import cn.net.yzl.product.model.vo.product.ProductTO;
import cn.net.yzl.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductBeanMapper productBeanMapper;


    public ComResponse<Void> insertRelationOfProductAndImgUrl(String pCode, Integer imgId, Integer type) {
        if (type .equals(0) ){
            productBeanMapper.insertRelationOfProductAndImgUrl(pCode,imgId,type,null);
        }else{
            productBeanMapper.insertRelationOfProductAndImgUrl(null,imgId,type,pCode);
        }
        return null;
    }

    @Override
    public ComResponse<Void> deleteRelationOfProductAndImgId(Integer id,Integer type) {

        productBeanMapper.deleteRelationOfProductAndImgId(id,type);

        return null;
    }

    @Override
    public ComResponse selectByIdList(String productIds) {
        List<ProductBean> productBeans = productBeanMapper.selectByIdList(productIds.split(","));
        List<ProductTO> list = productBeans.stream().map(productBean -> {
            ProductTO productTO = new ProductTO();
            BeanUtils.copyProperties(productBean, productTO);
            return productTO;
        }).collect(Collectors.toList());
        return ComResponse.success(list);
    }

    @Override
    public ComResponse selectByCondition(ProductBO productBO) {
        List<ProductBean> productBeans = productBeanMapper.select(productBO);
        return null;
    }

    @Override
    public ComResponse reduceStock(Integer productNo, Integer stock) {
        Integer result = productBeanMapper.reduceStock(productNo, stock);
        if (result < 1) {
            ComResponse.fail(10086, "商品不存在或库存不足！");
        }
        return ComResponse.success();
    }

    @Override
    public ComResponse increaseStock(Integer productNo, Integer stock) {
        productBeanMapper.increaseStock(productNo, stock);
        return ComResponse.success();
    }


}
