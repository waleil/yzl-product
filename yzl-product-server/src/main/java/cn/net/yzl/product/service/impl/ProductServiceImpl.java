package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.dao.ProductBeanMapper;
import cn.net.yzl.product.model.ProductBean;
import cn.net.yzl.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ComResponse<List<ProductBean>> selectByIdList(String productIds) {
        List<ProductBean> productBeans = productBeanMapper.selectByIdList(productIds.split(","));
        return ComResponse.success(productBeans);
    }



}
