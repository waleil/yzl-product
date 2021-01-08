package cn.net.yzl.product.model.db;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ProductAtlasBean",description = "商品图谱实体类")
public class ProductAtlasBean {

    @ApiModelProperty(name = "productCode",value ="商品编码" )
    private String productCode;

    @ApiModelProperty(name = "name",value ="商品名称" )
    private String name;

    @ApiModelProperty(name = "salePrice",value ="商品主图图片url" )
    private String imageUrl;

    @ApiModelProperty(name = "salePrice", value = "市场价(售卖价)单位为分",hidden = true)
    private Integer salePrice;

    @ApiModelProperty(name = "salePriceD", value = "市场价(售卖价)单位为元")
    private Double salePriceD;

    @ApiModelProperty(name = "goodsSource",value ="商品来源(1-自营 2-三方)" )
    private Integer goodsSource;

    @ApiModelProperty(name = "unit",value ="商品单位" )
    private String unit;

    @ApiModelProperty(name = "id",value ="病症id" )
    private Integer id;

    @ApiModelProperty(name = "brandName",value ="品牌名称" )
    private String brandName;

}
