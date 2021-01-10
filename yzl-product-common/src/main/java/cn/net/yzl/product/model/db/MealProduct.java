package cn.net.yzl.product.model.db;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@ApiModel(value = "MealProduct",description = "套餐商品信息")
public class MealProduct {

    @ApiModelProperty(value = "主键",name = "id")
    private Integer id;

    @ApiModelProperty(value = "套餐编码",name = "mealNo")
    private Long mealNo;

    @ApiModelProperty(value = "商品编码",name = "productCode")
    private String productCode;

    @ApiModelProperty(name = "name", required = true, value = "商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(name = "brandNo", value = "品牌编号")
    private Integer brandNo;
    @ApiModelProperty(name = "brandName", value = "品牌名称")
    private Integer brandName;

    @ApiModelProperty(name = "salePriceD", required = true, value = "市场价(售卖价),以元为单位")
    private Double salePriceD;

    @ApiModelProperty(value = "商品数量",name = "productNum")
    private Integer productNum;

    @ApiModelProperty(value = "是否赠品(0代表否,1代表是)",name = "mealGiftFlag")
    private Integer mealGiftFlag;

    @ApiModelProperty(name = "imageUrl", value = "商品主图")
    private String imageUrl;

    @ApiModelProperty(value = "创建时间",name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "修改时间",name = "updateTime")
    private Date updateTime;


}