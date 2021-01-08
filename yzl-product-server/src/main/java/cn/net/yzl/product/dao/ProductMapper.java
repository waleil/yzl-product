package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.ProductAtlasBean;
import cn.net.yzl.product.model.pojo.product.Product;
import cn.net.yzl.product.model.vo.product.dto.ProductListDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductSelectVO;
import cn.net.yzl.product.model.vo.product.vo.ProductVO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    int insertSelective(Product product);


    ProductVO selectByPrimaryKey(String productCode);



    int updateByPrimaryKeySelective(Product product);
    /**
     * @Author: lichanghong
     * @Description: 查询最大商品编号
     * @Date: 2021/1/7 3:12 下午
     * @Return: java.util.String
     */
    String queryMaxProductCode();

    List<ProductStatusCountDTO> queryCountByStatus();

    List<ProductListDTO> queryListProduct(ProductSelectVO vo);

    List<ProductAtlasBean> queryProductListAtlas(@Param("productName") String productName, @Param("diseaseId") Integer diseaseId);

}