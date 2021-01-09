package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wujianing
 * @version 1.0
 * @title: ProductMealVO
 * @description 商品套餐列表查询条件
 * @date: 2021/1/9 11:39 上午
 */
@Data
public class ProductMealDetailVO extends BaseObject {

    private  Integer id;

    @ApiModelProperty(name = "mealNo", value = "套餐ID")
    private Long mealNo;

    @ApiModelProperty(name = "name", value = "套餐名称")
    private String name;

    @ApiModelProperty(name = "pirce", value = "套餐价格")
    private Integer price;

    @ApiModelProperty(name = "discountPrice", value = "套餐优惠折扣价")
    private Integer discountPrice;

    private Integer integral;

    @ApiModelProperty(name = " descri", value = "套餐描述")
    private String  descri;


    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "status", value = "上下架状态：0代表下架，1代表上架，null代表全部")
    private Integer status;

    private Integer delFlag;

    private Integer oldId;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "updateTime", value = "更新时间")
    private Date updateTime;
}
