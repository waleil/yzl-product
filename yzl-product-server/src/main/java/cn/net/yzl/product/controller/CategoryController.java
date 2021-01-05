package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.category.Category;
import cn.net.yzl.product.model.vo.category.CategoryVO;
import cn.net.yzl.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: CategoryController
 * @description todo
 * @date: 2020/12/31 9:17 上午
 */
@RestController
@Api(tags = "分类管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @ApiOperation(value = "通过id字段对category_dict表进行条件检索")
    @GetMapping("/category/v1/getCategoryById")
    public ComResponse<CategoryVO> getCategoryByid(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }
    @ApiOperation(value = "新增分类管理")
    @PostMapping("/category/v1/insertCategory")
    public ComResponse<Void> insertCategory(@RequestBody CategoryVO CategoryVO) {
        return categoryService.saveOrUpdateCategory(CategoryVO);
    }
    @ApiOperation(value = "修改分类管理")
    @PutMapping("/category/v1/updateCategory")
    public ComResponse<Void> updateCategory(@RequestBody CategoryVO CategoryVO) {
        return categoryService.saveOrUpdateCategory(CategoryVO);
    }

    @DeleteMapping("/category/v1/deleteCategory")
    public ComResponse<Category> deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/category/v1/changeCategoryStatus")
    public ComResponse<Category> changeCategoryStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryStatus(id, flag);
    }

    @PutMapping("/category/v1/changeCategoryAppStatus")
    public ComResponse<Category> changeCategoryAppStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryAppStatus(id, flag);
    }

    @GetMapping("/category/v1/getCategoriesByPid")
    public ComResponse<List<CategoryVO>> getCategoriesByPid(@RequestParam Integer pid) {
        return categoryService.getCategoryByPid(pid);
    }

    @PutMapping("/category/v1/transferCategories")
    public ComResponse<Category> transferCategories(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        return categoryService.transferCategories(sourceId, targetId);
    }

    @GetMapping("/category/v1/selectAllCategories")
    public ComResponse<List<Category>> selectAllCategories() {
        return categoryService.selectAll();
    }

}
