package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.Category;
import cn.net.yzl.product.model.vo.category.CategoryTO;
import cn.net.yzl.product.model.vo.category.CategoryVO;
import cn.net.yzl.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @ApiOperation(value = "通过id字段对category_dict表进行条件检索")
    @GetMapping("v1/getById")
    @ApiImplicitParam(name="id",value = "id",required = true,paramType = "query")
    public ComResponse<CategoryTO> getCategoryByid(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }
    @ApiOperation(value = "新增分类管理")
    @PostMapping("v1/insert")
    public ComResponse<Void> insertCategory(@RequestBody @Valid CategoryVO CategoryVO) {
        return categoryService.saveOrUpdateCategory(CategoryVO);
    }
    @ApiOperation(value = "修改分类管理")
    @PutMapping("v1/update")
    public ComResponse<Void> updateCategory(@RequestBody @Valid CategoryVO CategoryVO) {
        return categoryService.saveOrUpdateCategory(CategoryVO);
    }
    @ApiOperation(value = "逻辑删除分类信息")
    @DeleteMapping("v1/delete")
    @ApiImplicitParam(name = "id",value = "id",paramType = "query",required = true)
    public ComResponse<Category> deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

    @ApiOperation(value = "修改分类的展示状态")
    @PutMapping("v1/changeStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag", value = "状态（0：不显示，1：显示）", paramType = "query", required = true),
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true)
    })
    public ComResponse<Category> changeCategoryStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryStatus(id, flag);
    }
    @ApiOperation("修改品牌移动端展示状态")
    @PutMapping("v1/changeAppStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag", value = "状态（0：不显示，1：显示）", paramType = "query", required = true),
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true)
    })
    public ComResponse<Category> changeCategoryAppStatus(@RequestParam Integer flag, @RequestParam Integer id) {
        return categoryService.chooseCategoryAppStatus(id, flag);
    }
    @ApiOperation("根据父id的值查询该父类下的所有子类品牌信息")
    @GetMapping("v1/getByPid")
    @ApiImplicitParam(name="pid",value = "父级id(如需查询一级分类则输入0)",paramType = "query", required = true)
    public ComResponse<List<CategoryVO>> getCategoriesByPid(@RequestParam Integer pid) {
        return categoryService.getCategoryByPid(pid);
    }
    @ApiOperation("将一个分类中的商品转移到另一个分类中")
    @PutMapping("v1/transfer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "源id", paramType = "query", required = true),
            @ApiImplicitParam(name = "targetId", value = "目标id", paramType = "query", required = true)
    })
    public ComResponse<Category> transferCategories(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        return categoryService.transferCategories(sourceId, targetId);
    }
    @ApiOperation("查询所有分类信息")
    @GetMapping("v1/selectAll")
    public ComResponse<List<Category>> selectAllCategories() {
        return categoryService.selectAll();
    }

}
