package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.pojo.product.ProductDisease;

import java.util.List;

import cn.net.yzl.product.model.vo.product.vo.ProductDiseaseVO;

public interface ProductDiseaseMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductDisease record);

    int insertSelective(ProductDisease record);

    int updateByPrimaryKeySelective(ProductDisease record);

    int insertArray(List<ProductDiseaseVO> list);

    int deleteByProductCode(String productCode);

    List<ProductDiseaseVO> queryByProductCode(String productCode);
}