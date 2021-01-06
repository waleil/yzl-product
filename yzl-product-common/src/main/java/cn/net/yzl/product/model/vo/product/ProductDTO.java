package cn.net.yzl.product.model.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDTO
 * @description todo
 * @date: 2021/1/6 3:30 下午
 */
@Data
public class ProductDTO {
    @ApiModelProperty(name = "code", value = "商品编号")
    private String code;
    @ApiModelProperty(name = "name", value = "商品名称")
    private String name;
    @ApiModelProperty(name = "source", value = "商品来源")
    private Integer source;
}
