package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.pojo.product.ProductImage;

import java.util.List;

import cn.net.yzl.product.model.vo.product.vo.ProductImageVO;
import org.springframework.data.repository.query.Param;

public interface ProductImageMapper {

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    int insertArray(List<ProductImageVO> list);

    int deleteByProductCode(String productCode);

    List<ProductImageVO> queryByProductCode(@Param(value = "productCode") String productCode);
}