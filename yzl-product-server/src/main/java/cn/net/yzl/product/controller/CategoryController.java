package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.CategoryBean;
import cn.net.yzl.product.model.db.CategoryTO;
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
@Api(tags = "商品品牌服务")
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "通过id字段对category_dict表进行条件检索")
    @GetMapping("v1/selectById")
    public ComResponse<CategoryTO> getCategoryByid(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }

    @ApiOperation(value = "新增分类")
    @PostMapping("v1/insert")
    public ComResponse<CategoryBean> insertCategory(@RequestBody CategoryTO categoryTO) {
        return categoryService.saveOrUpdateCategory(categoryTO);
    }

    @ApiOperation(value = "修改分类信息")
    @PutMapping("v1/update")
    public ComResponse<CategoryBean> updateCategory(@RequestBody CategoryTO CategoryTO) {
        return categoryService.saveOrUpdateCategory(CategoryTO);
    }

    @ApiOperation(value = "逻辑删除分类信息")
    @DeleteMapping("v1/delete")
    public ComResponse<CategoryBean> deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

    @ApiOperation(value = "修改分类的展示状态")
    @PutMapping("v1/changeStatus")
    public ComResponse<CategoryBean> changeCategoryStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryStatus(id, flag);
    }

    @ApiOperation("修改品牌移动端展示状态")
    @PutMapping("v1/changeAppStatus")
    public ComResponse changeCategoryAppStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryAppStatus(id, flag);
    }

    @ApiOperation("根据父id的值查询该父类下的所有子类品牌信息")
    @GetMapping("v1/getByPid")
    public ComResponse getCategoriesByPid(@RequestParam Integer pid) {
        return categoryService.getCategoryByPid(pid);
    }

    @ApiOperation("将一个分类中的商品转移到另一个分类中")
    @PutMapping("v1/transfer")
    public ComResponse transferCategories(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        return categoryService.transferCategories(sourceId, targetId);
    }

    @ApiOperation("查询所有分类信息")
    @GetMapping("v1/selectAll")
    public ComResponse selectAllCategories() {
        return categoryService.selectAll();
    }

}
