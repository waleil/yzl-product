package cn.net.yzl.product.model.vo.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: OrderProductVO
 * @description 下单操作商品信息
 * @date: 2021/1/12 10:01 上午
 */
@Data
public class OrderProductVO {
    @ApiModelProperty(name = "productReduceVOS", value = "商品对象集合")
   private List<ProductReduceVO> productReduceVOS;
    @ApiModelProperty(name = "orderNo", value = "订单编号")
   private String orderNo;
}
