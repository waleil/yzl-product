package cn.net.yzl.product.model.vo.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductReduceVO
 * @description 商品消费信息
 * @date: 2021/1/11 8:59 下午
 */
@Data
public class ProductReduceVO {
    @ApiModelProperty(name = "productCode", required = true,value = "商品编号")
    @NotEmpty(message = "商品编号不能为空!")
    private String productCode;
    @ApiModelProperty(name = "num",required = true, value = "商品数量")
    @DecimalMin(value = "1",message = "商品数量只能为正整数!")
    private Integer num;
//    @ApiModelProperty(name = "orderNo", value = "订单编号")
//    @NotEmpty(message = "订单编号不能为空!")
//    private String orderNo;

}
