package cn.net.yzl.product.model.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "商品信息条件查询的条件实体")
public class ProductBO {

    @ApiModelProperty(name = "keywords", value = "关键词")
    private String keywords;

    @ApiModelProperty(name = "cid", value = "分类")
    private Integer cid;

    @ApiModelProperty(name = "did", value = "病症id")
    private Integer did;

    @ApiModelProperty(name = "bid", value = "品牌id")
    private Integer bid;

    @ApiModelProperty(name = "minPrice", value = "最低价")
    private Integer minPrice;

    @ApiModelProperty(name = "maxPrice", value = "最低价")
    private Integer maxPrice;

    @ApiModelProperty(name = "minSaleVolume", value = "最小销量")
    private Integer minSaleVolume;

    @ApiModelProperty(name = "maxSaleVolume", value = "最大销量")
    private Integer maxSaleVolume;

}
