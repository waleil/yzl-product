package cn.net.yzl.product.model.pojo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "MealProduct",description = "套餐商品信息")
public class MealProduct {

    @ApiModelProperty(value = "主键",name = "id")
    private Integer id;

    @ApiModelProperty(value = "套餐编码",name = "mealNo")
    private Integer mealNo;

    @ApiModelProperty(value = "商品编码",name = "productCode")
    private String productCode;

    @ApiModelProperty(value = "商品数量",name = "productNum")
    private Integer productNum;

    @ApiModelProperty(value = "是否赠品(0代表否,1代表是)",name = "mealGiftFlag")
    private Boolean mealGiftFlag;

    @ApiModelProperty(value = "创建时间",name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "修改时间",name = "updateTime")
    private Date updateTime;


}