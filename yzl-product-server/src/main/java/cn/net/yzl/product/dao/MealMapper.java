package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.pojo.product.Meal;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductMealVO;
import cn.net.yzl.product.model.vo.product.vo.*;

import java.util.List;

public interface MealMapper {

    ProductMealDetailVO selectBymealNo(Meal meal);


    int deleteByPrimaryKey(Integer id);

    int insert(Meal record);

    int insertSelective(Meal record);

    Meal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Meal record);

    int updateByPrimaryKey(Meal record);

    List<ProductStatusCountDTO> queryCountByStatus();

    //修改套餐的售卖时间
    void updateTimeByMealCode(ProductMealUpdateTimeVO vo);

    //修改套餐的上下架状态
    void updateStatusByMealCode(ProductMealUpdateStatusVO vo);

    List<ProductMealDTO> queryListProductMeal(ProductMealVO vo);

    ProductMealDTO queryProductMealPortray(Integer mealNo);
}