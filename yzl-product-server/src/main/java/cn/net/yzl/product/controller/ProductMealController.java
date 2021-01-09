package cn.net.yzl.product.controller;


import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductMealVO;
import cn.net.yzl.product.model.vo.product.vo.ProductUpdateStatusVO;
import cn.net.yzl.product.service.meal.ProductMealService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @GetMapping(value = "v1/queryPageProductMeal")
    @ApiOperation("分页查询商品套餐列表")
    public ComResponse<Page<ProductMealDTO>> queryListProductMeal(ProductMealVO vo) {
        //价格必须成对出现
        if ((vo.getPriceUp() != null && vo.getPriceDown() == null)
                || (vo.getPriceUp() == null && vo.getPriceDown() != null)) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
        }
        if (vo.getPriceUp() != null) {
            vo.setUpPrice((int) (vo.getPriceUp() * 100));
        }
        if (vo.getPriceDown() != null) {
            vo.setDownPrice((int) (vo.getPriceDown() * 100));
        }
        if (vo.getPageNo() == null) {
            vo.setPageNo(1);
        }
        if (vo.getPageSize() == null) {
            vo.setPageSize(15);
        }
        if (vo.getPageSize() > 50) {
            vo.setPageSize(50);
        }
        if (StringUtils.isNotBlank(vo.getKeyword())) {
            String str = vo.getKeyword();
            vo.setKeyword(str.replace("%", "\\%"));
        }
        return productMealService.queryListProductMeal(vo);
    }

    @GetMapping(value = "v1/queryProductMealPortray")
    @ApiOperation("查询商品套餐画像")
    public ComResponse<ProductMealDTO> queryProductMealPortray(Integer mealNo) {
        return productMealService.queryProductMealPortray(mealNo);
    }

}