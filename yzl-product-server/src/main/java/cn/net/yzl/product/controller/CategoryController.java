package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.category.Category;
import cn.net.yzl.product.model.vo.category.CategoryVO;
import cn.net.yzl.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "逻辑删除分类信息")
    @DeleteMapping("/category/v1/deleteCategory")
    public ComResponse<Category> deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }
    @ApiOperation(value = "修改分类的展示状态")
    @PutMapping("/category/v1/changeCategoryStatus")
    public ComResponse<Category> changeCategoryStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryStatus(id, flag);
    }
    @ApiOperation("修改品牌移动端展示状态")
    @PutMapping("/category/v1/changeCategoryAppStatus")
    public ComResponse<Category> changeCategoryAppStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryAppStatus(id, flag);
    }
    @ApiOperation("根据父id的值查询该父类下的所有子类品牌信息")
    @GetMapping("/category/v1/getCategoriesByPid")
    public ComResponse<List<CategoryVO>> getCategoriesByPid(@RequestParam Integer pid) {
        return categoryService.getCategoryByPid(pid);
    }
    @ApiOperation("将一个分类中的商品转移到另一个分类中")
    @PutMapping("/category/v1/transferCategories")
    public ComResponse<Category> transferCategories(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        return categoryService.transferCategories(sourceId, targetId);
    }
    @ApiOperation("查询所有分类信息")
    @GetMapping("/category/v1/selectAllCategories")
    public ComResponse<List<Category>> selectAllCategories() {
        return categoryService.selectAll();
    }

}
