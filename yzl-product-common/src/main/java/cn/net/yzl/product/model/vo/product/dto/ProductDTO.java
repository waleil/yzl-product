package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
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
public class ProductDTO extends BaseObject {
    @ApiModelProperty(name = "code", value = "商品编号")
    private String code;
    @ApiModelProperty(name = "name", value = "商品名称")
    private String name;
    @ApiModelProperty(name = "source", value = "商品来源")
    private Integer source;
    @ApiModelProperty(name = "imageUrl", value = "图片地址")
    private String imageUrl;


}
