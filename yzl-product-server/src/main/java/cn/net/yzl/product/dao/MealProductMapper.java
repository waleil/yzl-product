package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.pojo.product.MealProduct;
import cn.net.yzl.product.model.vo.product.dto.MealListProductDTO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface MealProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MealProduct record);

    int insertSelective(MealProduct record);

    MealProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MealProduct record);

    int updateByPrimaryKey(MealProduct record);

    /**
     * 根据套餐号查询关联产品
     * @param mealNoList
     * @return
     */
    List<MealListProductDTO> queryMealProductByMealNos(@Param("mealNoList") List<Integer> mealNoList);

    void deleteByMealNo(Long mealNo);

    void insertSelectiveList(List<cn.net.yzl.product.model.pojo.product.MealProduct> mealProductList);
}