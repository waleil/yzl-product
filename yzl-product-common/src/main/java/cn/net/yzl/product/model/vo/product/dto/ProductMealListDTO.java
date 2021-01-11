package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: ProductMealListDTO
 * @description 商品套餐列表返回对象
 * @date: 2021/1/10 12:20 上午
 */
@Data
public class ProductMealListDTO extends BaseObject {

    @ApiModelProperty(name = "MealNo",value = "套餐唯一标识")
    private String mealNo;

    @ApiModelProperty(name = "name", value = "套餐名称")
    @NotEmpty(message = "套餐名称不能为空")
    private String name;

    @ApiModelProperty(name = "price", value = "套餐价格")
    private Integer price;

    @ApiModelProperty(name = "priceD", value = "市场价(售卖价)单位为元")
    private Double priceD;

    @ApiModelProperty(name = "imageUrl", value = "套餐图片地址")
    private String imageUrl;

    @ApiModelProperty(name = "discountPrice", value = "套餐最低优惠折扣价")
    private Integer discountPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "saleStartTime", value = "售卖开始时间")
    private Date saleStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "saleEndTime", value = "售卖结束时间")
    private Date saleEndTime;

    @ApiModelProperty(name = "fastDFSUrl", value = "图片库地址,需要把imageUrl进行拼接")
    private String fastDFSUrl;

    @ApiModelProperty(name = "mealProductList", value = "套餐包含产品详细")
    private List<MealListProductDTO> mealProductList;
}
