package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductImageVO
 * @description 商品图片关联关系
 * @date: 2021/1/8 8:55 上午
 */
@Data
public class ProductImageVO extends BaseObject {
    @ApiModelProperty(name = "productCode", value = "商品ID")
    private String productCode;
    @ApiModelProperty(name = "imageUrl", value = "图片地址")
    private String imageUrl;
}
