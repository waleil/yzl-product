package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.pojo.product.MealProduct;

public interface MealProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MealProduct record);

    int insertSelective(MealProduct record);

    MealProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MealProduct record);

    int updateByPrimaryKey(MealProduct record);
}