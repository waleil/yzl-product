package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.BrandBean;
import cn.net.yzl.product.model.vo.brand.BrandBeanTO;
import cn.net.yzl.product.model.vo.brand.BrandDelVo;
import cn.net.yzl.product.model.vo.brand.BrandVo;
import cn.net.yzl.product.service.BrandService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lichanghong
 * @version 1.0
 * @title: BrandController
 * @description todo
 * @date: 2020/12/31 9:08 上午
 */
@RestController

@Api(tags = "商品品牌管理", description = "包含：增删改查")
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("v1/getBrandById")
    @ApiOperation(value = "根据主键查询详情", notes = "根据主键查询详情")
    @ApiImplicitParam(name = "id", value = "主键信息", required = true, dataType = "Integer")
    public ComResponse<BrandBean> getBrandById(@RequestParam("id") Integer id) {
        return brandService.getBrandById(id);
    }

    @PutMapping("v1/changeBrandStatus")
    public ComResponse<Void> changeBrandStatus(@RequestParam("flag") Integer flag, @RequestParam("id") Integer id) {
        return brandService.changeBrandStatus(flag, id);
    }
    @ApiOperation(value = "编辑", notes = "编辑")
    @PostMapping("v1/editBrand")
    public ComResponse<Void> editBrand(@RequestBody @Valid BrandVo brand) {
        return brandService.editBrand(brand);
    }
    @ApiOperation(value = "删除品牌", notes = "删除品牌")
    @PostMapping("v1/deleteBrandById")
    public ComResponse<Void> deleteBrandById(@RequestBody @Valid BrandDelVo brandDelVo){
        return brandService.deleteBrandById(brandDelVo);
    }
    /**
     * @author lichanghong
     * @description 查询列表
     * @date: 2020/12/31 10:15 下午
     * @param keyWord:  关键词
     * @param pageNo: 页码
     * @param pageSize:每页限制数量
     * @return: null
     */
    @ApiOperation(value = "列表", notes = "列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", paramType="query",value = "关键词", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", paramType="query",value = "页码", dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",paramType="query", value = "每页显示记录数", dataType = "int",defaultValue = "15")
    })
    @GetMapping("v1/getAllBrands")
    public ComResponse<PageInfo<BrandBeanTO>> getAllBrands(@RequestParam(required = false,defaultValue = "1")Integer pageNo,
                                                           @RequestParam(required = false,defaultValue = "15")  Integer pageSize,
                                                           String keyWord
    ) {
        return brandService.getAllBrands(pageNo,pageSize,keyWord);
    }
}
