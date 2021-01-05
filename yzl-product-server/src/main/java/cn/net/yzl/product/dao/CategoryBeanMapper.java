package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.category.Category;
import cn.net.yzl.product.model.vo.category.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(CategoryVO record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryVO record);

    List<Category> selectByPid(@Param("pid") Integer pid);

    void chooseCategoryStatus(@Param("id") Integer id, @Param("flag") Integer flag);

    void chooseCategoryAppStatus(@Param("id") Integer id, @Param("flag") Integer flag);

    void transferCategories(@Param("source") Integer sourceId, @Param("target") Integer targetId);

    int getProductCountByCid(@Param("cid") Integer cid);

    List<Category> selectAll();
}