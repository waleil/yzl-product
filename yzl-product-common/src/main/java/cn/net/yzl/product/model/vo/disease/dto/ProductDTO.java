package cn.net.yzl.product.model.vo.disease.dto;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDTO
 * @description 商品信息
 * @date: 2021/1/10 4:42 下午
 */
@Data
public class ProductDTO extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo",value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "quickSearchCode", value = "快速检索码")
    private String quickSearchCode;

    @ApiModelProperty(name = "name", required = true, value = "商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(name = "imageUrl", value = "商品主图")
    private String imageUrl;

}
