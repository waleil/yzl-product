package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.vo.product.ProductVO;
public interface ProductMapper {

    int insertSelective(ProductVO record);


    ProductVO selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(ProductVO record);
    /**
     * @Author: lichanghong
     * @Description: 查询最大商品编号
     * @Date: 2021/1/7 3:12 下午
     * @Return: java.util.String
     */
    String queryMaxProductCode();

}