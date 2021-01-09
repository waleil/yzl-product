package cn.net.yzl.product.controller;


import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.service.meal.ProductMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanghuasheng
 */
@RestController
@Api(tags = "商品套餐服务")
@RequestMapping("productMeal")
public class ProductMealController {
    @Autowired
    private ProductMealService productMealService;

    /**
     * @param
     * @Author: wanghuasheng
     * @Description:
     * @Date: 2021/1/9 09:20 上午
     * @Return: cn.net.yzl.common.entity.ComResponse<java.util.List < cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO>>
     */
    @GetMapping(value = "v1/queryCountByStatus")
    @ApiOperation("按照上下架状态查询商品套餐数量")
    public ComResponse<List<ProductStatusCountDTO>> queryCountByStatus() {
        List<ProductStatusCountDTO> list = productMealService.queryCountByStatus();
        return ComResponse.success(list);
    }

}

