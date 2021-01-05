package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.product.model.db.category.Category;
import cn.net.yzl.product.model.vo.category.*;
import cn.net.yzl.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "通过id字段对category_dict表进行条件检索")
    @GetMapping("/category/v1/getCategoryById")
    public ComResponse<Category> getCategoryByid(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }

    @ApiOperation(value = "新增分类管理")
    @PostMapping("/category/v1/insertCategory")
    public ComResponse<Void> insertCategory(@RequestBody @Valid CategoryVO CategoryVO) {
        return categoryService.saveOrUpdateCategory(CategoryVO);
    }

    @ApiOperation(value = "修改分类管理")
    @PostMapping("/category/v1/updateCategory")
    public ComResponse<Void> updateCategory(@RequestBody @Valid CategoryVO CategoryVO) {
        return categoryService.saveOrUpdateCategory(CategoryVO);
    }

    @ApiOperation(value = "逻辑删除分类信息")
    @PostMapping("/category/v1/deleteCategory")
    public ComResponse<Void> deleteCategory(@RequestBody @Valid CategoryDelVO vo) {
        return categoryService.deleteCategory(vo);
    }
    @ApiOperation(value = "修改分类的展示状态")
    @PostMapping("/category/v1/changeCategoryStatus")
    public ComResponse<Void> changeCategoryStatus(@RequestBody @Valid CategoryChangeStatusVO vo) {
        return categoryService.chooseCategoryStatus(vo);
    }

    @ApiOperation("修改品牌移动端展示状态")
    @PostMapping("/category/v1/changeCategoryAppStatus")
    public ComResponse<Void> changeCategoryAppStatus(@RequestBody @Valid CategoryChangeStatusVO vo) {
        return categoryService.chooseCategoryAppStatus(vo);
    }

    @ApiOperation("根据父id的值查询该父类下的所有子类品牌信息")
    @GetMapping("/category/v1/getCategoriesByPid")
    public ComResponse<List<Category>> getCategoriesByPid(@RequestParam Integer pid) {
        return categoryService.getCategoryByPid(pid);
    }

    /**
     * @param pid:
     * @author lichanghong
     * @description 分页查询
     * @date: 2021/1/5 4:47 下午
     * @return: null
     */
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", paramType = "query", value = "父级编号", dataType = "int", defaultValue = "0"),
            @ApiImplicitParam(name = "pageNo", paramType = "query", value = "页码", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", paramType = "query", value = "每页显示记录数", dataType = "int", defaultValue = "15")
    })
    @GetMapping("/category/v1/queryPageByPid")
    public ComResponse<Page<CategoryTO>> queryPageByPid(int pid,
                                                        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                        @RequestParam(required = false, defaultValue = "15") Integer pageSize) {
        if (pageSize > 50) {
            pageSize = 50;
        }
        return categoryService.queryPageByPid(pid, pageNo, pageSize);
    }
    @ApiOperation("下拉列表查询")
    @ApiImplicitParam(name = "pid",paramType = "query",value = "父级编号",defaultValue = "0",required = true)
    @GetMapping("/category/v1/query4SelectOption")
    public ComResponse<List<CategorySelectTO>> query4SelectOption(@RequestParam(value ="pid") Integer pid) {
        return categoryService.query4SelectOption(pid);
    }
}
