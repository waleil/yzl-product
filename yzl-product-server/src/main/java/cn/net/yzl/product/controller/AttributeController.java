package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.AttributeBean;
import cn.net.yzl.product.service.AttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lichanghong
 * @version 1.0
 * @title: AttributeController
 * @description todo
 * @date: 2020/12/31 9:10 上午
 */
@RestController
@RequestMapping("productServer")
@Api(tags = "商品属性管理")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;
    /**
     * 添加商品属性信息
     *
     * @param attributeBean
     * @return
     */
    @ApiOperation(value = "插入属性")
    @PostMapping("insertAttribute")
    public ComResponse insertProductAttribute(@RequestBody AttributeBean attributeBean) {
        attributeService.insertAttribute(attributeBean);
        return ComResponse.success();
    }


    /**
     * 分页查询商品属性列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取属性")
    @GetMapping("selectPageAttribute")
    public ComResponse selectPageAttribute(int pageNo, int pageSize) {
        return attributeService.selectPageAttribute(pageNo, pageSize);
    }


    @ApiOperation(value = "通过id精确匹配属性")
    @GetMapping("selectById")
    public ComResponse selectById(Integer id) {
        return attributeService.selectById(id);
    }

    @GetMapping("selectByclassifyIdAttribute")
    public ComResponse selectByclassifyIdAttribute(Integer id) {
        return attributeService.selectByclassifyIdAttribute(id);
    }

    @ApiOperation(value = "更新属性信息")
    @PostMapping("updateAttribute")
    public ComResponse updateAttribute(@RequestBody AttributeBean attributeBean) {
        ComResponse comResponse = attributeService.updateAttribute(attributeBean);
        return comResponse;
    }

}
