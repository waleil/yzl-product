package cn.net.yzl.product.model.vo.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "MealProduct",description = "套餐商品信息")
public class MealProductVO {

    @ApiModelProperty(value = "套餐编码",name = "mealNo")
    private String mealNo;

    @ApiModelProperty(value = "商品编码",name = "productCode")
    private String productCode;

    @ApiModelProperty(value = "商品数量",name = "productNum")
    private Integer productNum;

    @ApiModelProperty(value = "是否赠品(0代表否,1代表是)",name = "mealGiftFlag")
    private Integer mealGiftFlag;

    @ApiModelProperty(value = "创建时间",name = "createTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间",name = "updateTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


}