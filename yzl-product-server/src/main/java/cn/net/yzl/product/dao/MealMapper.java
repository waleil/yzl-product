package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.pojo.product.Meal;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;

import java.util.List;

public interface MealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Meal record);

    int insertSelective(Meal record);

    Meal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Meal record);

    int updateByPrimaryKey(Meal record);

    List<ProductStatusCountDTO> queryCountByStatus();
}