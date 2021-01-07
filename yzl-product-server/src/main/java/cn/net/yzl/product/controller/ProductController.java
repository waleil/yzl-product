package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.ProductMainInfoBean;
import cn.net.yzl.product.model.vo.product.ProductBO;
import cn.net.yzl.product.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "商品服务")
@RequestMapping("product")
public class ProductController {


    @Autowired
    private ProductService productService;


    @PostMapping("/v1/insertProductImgUrl")
    public ComResponse<Void> insertRelationOfProductAndImgUrl(@RequestParam(value = "id",required = false) String id,
                                                              @RequestParam(value = "imgId",required = false)Integer imgId,
                                                              @RequestParam(value = "type",required = false)Integer type){
        return productService.insertRelationOfProductAndImgUrl(id, imgId,type);
    }


    @DeleteMapping("/v1/deleteProductImgId")
    public ComResponse<Void> deleteRelationOfProductAndImgId(@RequestParam("id")Integer id,
                                                             @RequestParam("type")Integer type){
        return productService.deleteRelationOfProductAndImgId(id, type);
    }


    @GetMapping("/v1/selectByIdList")
    public ComResponse selectByIdList(String productIds){
        return productService.selectByIdList(productIds);
    }

    @GetMapping("selectByCondition")
    public ComResponse selectByCondition(ProductBO productBO){
        return productService.selectByCondition(productBO);
    }

    @PostMapping("reduceStock")
    public ComResponse reduceStock(Integer productNo,Integer stock){
        return productService.reduceStock(productNo, stock);
    }

    @PostMapping
    public ComResponse increaseStock(Integer productNo,Integer stock){
        return productService.increaseStock(productNo, stock);
    }

    @GetMapping
    public List<ProductMainInfoBean> getProductMainInfoPage(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize){
        return productService.getProductMainInfoPage(pageNo, pageSize);
    }
    
    @ApiOperation("提供接口根据id列表查询商品的部分信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "id列表，如需查询全部，则键入空值或空字符串",paramType = "query"),
            @ApiImplicitParam(name = "status",value = "是否上架（0-下架 1-上架）",paramType = "query")
    })
    @GetMapping("/v1/getMainInfoByIds")
    public ComResponse<List<ProductMainInfoBean>> getMainInfoByIds(@RequestParam("ids") String ids,@RequestParam("status") Integer status){
        return productService.getMainInfoByIds(ids,status);
    }

}

