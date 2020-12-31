package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.CategoryBean;
import cn.net.yzl.product.model.CategoryTO;

import java.util.List;

public interface CategoryService {

    ComResponse<CategoryTO> getCategoryById(Integer id);

    ComResponse<CategoryBean> saveOrUpdateCategory(CategoryTO categoryTO);

    ComResponse<CategoryBean> deleteCategory(Integer id);

    ComResponse<CategoryBean> chooseCategoryStatus(Integer id, Integer flag);

    ComResponse<CategoryBean> chooseCategoryAppStatus(Integer id, Integer flag);

    ComResponse<List<CategoryTO>> getCategoryByPid(Integer pid);

    ComResponse<CategoryBean> transferCategories(Integer sourceId, Integer targetId);

    ComResponse<List<CategoryBean>> selectAll();

}
