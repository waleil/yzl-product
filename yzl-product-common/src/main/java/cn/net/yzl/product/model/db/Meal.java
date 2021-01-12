package cn.net.yzl.product.model.db;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "Meal",description = "套餐实体类")
public class Meal extends BaseObject {

    @ApiModelProperty(value = "主键",name = "id")
    private Integer id;

    @ApiModelProperty(value = "套餐编码",name = "mealNo")
    private String mealNo;

    @ApiModelProperty(value = "套餐名称",name = "name")
    private String name;

    @ApiModelProperty(value = "套餐价,以分为单位",name = "price")
    private Integer price;

    @ApiModelProperty(value = "套餐最低优惠折扣价,以分为单位",name = "discountPrice")
    private Integer discountPrice;

    @ApiModelProperty(value = "积分",name = "integral")
    private Integer integral;

    @ApiModelProperty(value = "套餐描述",name = "descri")
    private String descri;

    @ApiModelProperty(value = "0代表下架,1代表上架",name = "status")
    private Integer status;

    @ApiModelProperty(value = "删除标识(0否,1是)",name = "delFlag")
    private Integer delFlag;

    @ApiModelProperty(value = "旧id",name = "oldId")
    private Integer oldId;

    @ApiModelProperty(value = "销售开始时间",name = "saleStartTime")
    private Date saleStartTime;

    @ApiModelProperty(value = "销售结束时间",name = "saleEndTime")
    private Date saleEndTime;

    @ApiModelProperty(value = "套餐图片地址",name = "imageUrl")
    private String imageUrl;

    @ApiModelProperty(value = "创建时间",name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "修改时间",name = "updateTime")
    private Date updateTime;

    @ApiModelProperty(value = "套餐商品信息",name = "mealProductList")
    private List<MealProduct> mealProductList;


    @ApiModelProperty(value = "修改人",name = "updateNo")
    private String updateNo;

}