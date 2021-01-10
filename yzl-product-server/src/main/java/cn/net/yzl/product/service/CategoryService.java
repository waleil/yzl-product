package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.product.model.db.Category;
import cn.net.yzl.product.model.vo.category.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
    /**
     * @author lichanghong
     * @description 根据主键查询
     * @date: 2021/1/5 3:27 下午
     * @param id:
     * @return: null
     */
    ComResponse<Category> getCategoryById(Integer id);

    ComResponse<Void> saveOrUpdateCategory(CategoryVO categoryVO);
    /**
     * @author lichanghong
     * @description 逻辑删除
     * @date: 2021/1/5 3:07 下午
     * @param vo:
     * @return: null
     */
    ComResponse<Void> deleteCategory(CategoryDelVO vo);
    /**
     * @author lichanghong
     * @description 修改分类状态
     * @date: 2021/1/5 2:28 下午
     * @param vo:
     * @return: null
     */
    ComResponse<Void> chooseCategoryStatus(CategoryChangeStatusVO vo);
    /**
     * @author lichanghong
     * @description 修改移动端展示状态
     * @date: 2021/1/5 2:28 下午
     * @param vo:
     * @return: null
     */
    ComResponse<Void> chooseCategoryAppStatus(CategoryChangeStatusVO vo);
    /**
     * @author lichanghong
     * @description 根据父级编号查询子项
     * @date: 2021/1/5 2:28 下午
     * @param pid:
     * @return: null
     */
    ComResponse<List<Category>> getCategoryByPid(Integer pid);
    /**
     * @author lichanghong
     * @description 分页查询
     * @date: 2021/1/5 4:47 下午
     * @param pid:
     * @return: null
     */
    ComResponse<Page<CategoryTO>> queryPageByPid(Integer pid, Integer pageNo, Integer pageSize);

    /**
     * @author lichanghong
     * @description 下拉列表查询
     * @date: 2021/1/5 6:09 下午
     * @param pid:
     * @return: null
     */
    ComResponse<List<CategorySelectTO>> query4SelectOption(@Param("pid") Integer pid);

    cn.net.yzl.product.model.pojo.category.Category queryById(@Param("id") Integer id);
}
