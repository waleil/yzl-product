package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author wujianing
 * @version 1.0
 * @title: ProductMealLinkDTO
 * @description 商品套餐关联表返回对象
 * @date: 2021/1/9 11:20 上午
 *
 */
@Data
public class ProductMealLinkDTO extends BaseObject {
    @ApiModelProperty(name = "id",value = "表ID")
    private Integer id;

    @ApiModelProperty(name = "MealNo",value = "套餐唯一标识")
    private Integer mealNo;

    @ApiModelProperty(name = "productCode", value = "'产品唯一编号'")
    private String productCode;

    @ApiModelProperty(name = "productNum", value = "'商品数量'")
    private Integer productNum;

    @ApiModelProperty(name = "mealGiftFlag", value = "是否套餐赠品(0代表否,1代表是)")
    private Integer mealGiftFlag;

    @ApiModelProperty(name = "productName", value = "商品名称")
    private Integer productName;

    @ApiModelProperty(name = "salePrice", required = true, value = "市场价(售卖价),以分为单位")
    private Integer salePrice;

    @ApiModelProperty(name = "salePriceD", value = "市场价(售卖价)单位为元")
    private Double salePriceD;

    @ApiModelProperty(name = "brandName", value = "品牌名称")
    private Integer brandName;

    @ApiModelProperty(name = "brandNo", value = "品牌编号")
    private Integer brandNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private Date updateTime;

}
