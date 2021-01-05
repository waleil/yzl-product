package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.category.Category;
import cn.net.yzl.product.model.vo.category.CategoryVO;

import java.util.List;

public interface CategoryService {

    ComResponse<CategoryVO> getCategoryById(Integer id);

    ComResponse<Void> saveOrUpdateCategory(CategoryVO categoryVO);

    ComResponse<Category> deleteCategory(Integer id);

    ComResponse<Category> chooseCategoryStatus(Integer id, Integer flag);

    ComResponse<Category> chooseCategoryAppStatus(Integer id, Integer flag);

    ComResponse<List<CategoryVO>> getCategoryByPid(Integer pid);

    ComResponse<Category> transferCategories(Integer sourceId, Integer targetId);

    ComResponse<List<Category>> selectAll();

}
