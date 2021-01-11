package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: MealListProductDTO
 * @description 商品套餐关联表返回对象
 * @date: 2021/1/9 11:20 上午
 *
 */
@Data
public class MealListProductDTO extends BaseObject {

    @ApiModelProperty(name = "productCode", value = "'产品唯一编号'")
    private String productCode;

    @ApiModelProperty(name = "productNum", value = "'商品数量'")
    private Integer productNum;

    @ApiModelProperty(name = "mealGiftFlag", value = "是否套餐赠品(0代表否,1代表是)")
    private Integer mealGiftFlag;

    @ApiModelProperty(name = "mealNO", value = "套餐编号")
    private String mealNO;

}
