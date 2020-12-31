package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.ProductBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductBean record);

    int insertSelective(ProductBean record);

    ProductBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductBean record);

    int updateByPrimaryKey(ProductBean record);

    void insertRelationOfProductAndImgUrl(@Param("pCode") String pCode, @Param("imgId") Integer imgId, @Param("type") Integer type, @Param("mealNo") String mealNo);

    void deleteRelationOfProductAndImgId(@Param("id") Integer id, @Param("type") Integer type);

    List<ProductBean> selectByIdList(@Param("ids") String[] ids);

    List<ProductBean> getProductByBid(@Param("bid") Integer bid);
}