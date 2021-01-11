package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDTO
 * @description 商品信息
 * @date: 2021/1/6 3:30 下午
 */
@Data
public class ProductDTO extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo",value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "name",  value = "商品名称")
    private String name;

    @ApiModelProperty(name = "imageUrl", value = "商品主图")
    private String imageUrl;

    @ApiModelProperty(name = "salePriceD",  value = "市场价(售卖价),以元为单位")
    private Double salePriceD;

    @ApiModelProperty(name = "salePrice",  value = "市场价(售卖价),以分为单位")
    @JsonIgnore
    private Integer salePrice;

    @ApiModelProperty(name = "fastDFSUrl", value = "图片库地址,需要把imageUrl进行拼接")
    private String fastDFSUrl;

    @ApiModelProperty(name = "stock", value = "库存,-1代表不限制库存")
    private Integer stock;

    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "diseasePid", value = "主治病症上级主键")
    private Integer diseasePid;
}
