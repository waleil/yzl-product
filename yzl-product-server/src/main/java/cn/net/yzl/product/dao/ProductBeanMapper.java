package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.ProductBean;
import cn.net.yzl.product.model.vo.product.ProductBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductBeanMapper {
    int reduceStock(Integer productNo, Integer stock);

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

    List<ProductBean> select(ProductBO productBO);

    void increaseStock(Integer productNo, Integer stock);

    List<ProductBean> selectMainByIdList(@Param("ids") String[] ids, @Param("status") Integer status);

    List<ProductBean> selectByDid(int id);
}