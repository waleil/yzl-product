package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.Meal;
import cn.net.yzl.product.model.pojo.product.MealStatus;
import cn.net.yzl.product.model.vo.product.dto.ProductMealListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    //查询商品套餐列表
    List<ProductMealListDTO> queryListProductMeal(ProductMealSelectVO vo);

    Meal queryProductMealPortray(String mealNo);

    //查询最大套餐编号
    String queryMaxMealNo();

    MealStatus queryMealStatusByMaelNo(String mealNo);

    List<String> queryBySaleEndTimeAndCodes(@Param("params") Map<String,Object> map);
}