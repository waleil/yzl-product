package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.CategoryBean;
import cn.net.yzl.product.model.CategoryTO;
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
@RequestMapping("productServer")
@Api(tags = "商品品牌服务")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @ApiOperation(value = "通过id字段对category_dict表进行条件检索")
    @GetMapping("getCategoryById")
    public ComResponse<CategoryTO> getCategoryByid(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("insertCategory")
    public ComResponse<CategoryBean> insertCategory(@RequestBody CategoryTO CategoryTO) {
        return categoryService.saveOrUpdateCategory(CategoryTO);
    }

    @PutMapping("updateCategory")
    public ComResponse<CategoryBean> updateCategory(@RequestBody CategoryTO CategoryTO) {
        return categoryService.saveOrUpdateCategory(CategoryTO);
    }

    @DeleteMapping("deleteCategory")
    public ComResponse<CategoryBean> deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("changeCategoryStatus")
    public ComResponse<CategoryBean> changeCategoryStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryStatus(id, flag);
    }

    @PutMapping("changeCategoryAppStatus")
    public ComResponse<CategoryBean> changeCategoryAppStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryAppStatus(id, flag);
    }

    @GetMapping("getCategoriesByPid")
    public ComResponse<List<CategoryTO>> getCategoriesByPid(@RequestParam Integer pid) {
        return categoryService.getCategoryByPid(pid);
    }

    @PutMapping("transferCategories")
    public ComResponse<CategoryBean> transferCategories(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        return categoryService.transferCategories(sourceId, targetId);
    }

    @GetMapping("selectAllCategories")
    public ComResponse<List<CategoryBean>> selectAllCategories() {
        return categoryService.selectAll();
    }

}
