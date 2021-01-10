package cn.net.yzl.product.controller;


import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.pojo.product.Meal;
import cn.net.yzl.product.model.vo.product.dto.ProductMealListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.service.meal.ProductMealService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    public ComResponse<Page<ProductMealListDTO>> queryListProductMeal(ProductMealSelectVO vo) {
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
        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getKeyword())) {
            String str = vo.getKeyword();
            vo.setKeyword(str.replace("%", "\\%"));
        }
        return productMealService.queryProductMealList(vo);
    }
    /**
     * @Description:
     * @Author: dongjunmei
     * @Date: 2021-01-09 11:12 上午
     * @param：
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    @PostMapping(value = "v1/updateStatus")
    @ApiOperation("修改套餐上下架状态")

    public ComResponse updateStatusByMealCode(@RequestBody @Valid ProductMealUpdateStatusVO vo) {
        if (CollectionUtils.isEmpty(vo.getMealNoList())) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), "套餐code不能为空");
        }
        return productMealService.updateStatusByMealCode(vo);
    }

    /**
     * @Description:
     * @Author: dongjunmei
     * @Date: 2021-01-09 11:30
     * @param :
     * @return: cn.net.yzl.common.entity.ComResponse<java.lang.Void>
     **/
    @PostMapping(value = "v1/edit")
    @ApiOperation("编辑套餐")
    public ComResponse<Void> editProductMeal(@RequestBody @Valid MealVO vo) {
        String str = checkParams(vo);
        if (StringUtils.isNotBlank(str)) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), str);
        }
        return productMealService.editProductMeal(vo);
    }

    /**
     * @Description: 参数效验
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:51
     * @param vo:
     * @return: java.lang.String
     **/
    public String checkParams(MealVO vo) {
        if (StringUtils.isEmpty(vo.getName())){
            return "套餐名称不能为空";
        }
        if (vo.getPriceD()==null){
            return "套餐价格不能为空";
        }
        if (vo.getDiscountPriceD()==null){
            return "套餐优惠折扣价不能为空";
        }
        if (vo.getUpdateTime() == null) {
            return "最后修改时间不能为空!";
        }
        if (vo.getUpdateNo() == null) {
            return "编辑员工编码不能为空!";
        }
        return null;
    }

    /**
     * @Description:
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:00
     * @param:
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    @PostMapping(value = "v1/queryMealDetail")
    @ApiOperation("查询商品详情")
    public ComResponse<ProductMealDetailVO> queryMealDetail(@RequestBody Meal meal) {
        if(meal.getMealNo()!=null){
            return productMealService.queryMealDetail(meal);
        }
        return null;
    }

    /**
     * @Description:
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:26
     * @param vo:
     * @param result:
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    @PostMapping(value = "v1/updateTime")
    @ApiOperation("修改套餐售卖时间")
    ComResponse updateTimeByMealCode(@RequestBody @Valid ProductMealUpdateTimeVO vo, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                sb.append(error.getDefaultMessage() + ",");
            }
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), sb.toString());
        }
        if (CollectionUtils.isEmpty(vo.getMealNoList())) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), "套餐code不能为空");
        }
        if (vo.getSaleEndTime().compareTo(vo.getSaleStartTime()) < 0) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), "日期错误");
        }
        return productMealService.updateTimeByMealCode(vo);
    }

    @GetMapping(value = "v1/queryProductMealPortray")
    @ApiOperation("查询商品套餐画像")
    public ComResponse<ProductMealDTO> queryProductMealPortray(Integer mealNo) {
        return productMealService.queryProductMealPortray(mealNo);
    }
}