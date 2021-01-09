package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @author wujianing
 * @version 1.0
 * @title: ProductMealDTO
 * @description 商品套餐列表返回对象
 * @date: 2021/1/9 11:20 上午
 */
@Data
public class ProductMealDTO extends BaseObject {
    @ApiModelProperty(name = "id",value = "套餐ID")
    private Integer id;

    @ApiModelProperty(name = "MealNo",value = "套餐唯一标识")
    private Integer mealNo;

    @ApiModelProperty(name = "name", value = "套餐名称")
    @NotEmpty(message = "套餐名称不能为空")
    private String name;

    @ApiModelProperty(name = "price", value = "套餐价格")
    private Integer price;

    @ApiModelProperty(name = "discountPrice", value = "套餐最低优惠折扣价")
    private Integer discount_price;

    @ApiModelProperty(name = "integral", value = "积分")
    private Integer integral;

    @ApiModelProperty(name = "descri", value = "描述")
    private String descri;

    @ApiModelProperty(name = "status", value = "套餐状态")
    private Integer status;

    @ApiModelProperty(name = "delFlag", value = "是否删除")
    private Integer delFlag;

    @ApiModelProperty(name = "oldId", value = "旧id")
    private Integer oldId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(name = "list", value = "套餐包含产品详细")
    private List<ProductMealLinkDTO> list;

}
