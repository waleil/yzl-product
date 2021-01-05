package cn.net.yzl.product.model.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProductTO {

    @ApiModelProperty(name = "id",value = "商品id")
    private Integer id;

    @ApiModelProperty(name = "productCode", value = "商品编码")
    private String productCode;

    @ApiModelProperty(name = "productNo", value = "商品spuId")
    private Integer productNo;

    @ApiModelProperty(name = "businessCode", value = "商家编码")
    private String businessCode;

    @ApiModelProperty(name = "name", value = "名称")
    private String name;

    @ApiModelProperty(name = "nickname", value = "商品简称")
    private String nickname;

    @ApiModelProperty(name = "categoryDictCode", value = "商品分类id")
    private Integer categoryDictCode;

    @ApiModelProperty(name = "spec", value = "规格")
    private String spec;

    @ApiModelProperty(name = "unit", value = "单位")
    private String unit;

    @ApiModelProperty(name = "barCode", value = "商品条形码")
    private String barCode;

    @ApiModelProperty(name = "giftFlag", value = "是否可作为赠品")
    private Boolean giftFlag;

    @ApiModelProperty(name = "jfExchangeFlag", value = "是否可积分兑换")
    private Boolean jfExchangeFlag;

    @ApiModelProperty(name = "jfPrice", value = "兑换所需积分")
    private Integer jfPrice;

    @ApiModelProperty(name = "financeCode", value = "财务编码")
    private String financeCode;


    @ApiModelProperty(name = "salePrice", value = "市场价 售价")
    private Integer salePrice;

    @ApiModelProperty(name = "memberPrice", value = "会员价（最低折扣价）")
    private Integer memberPrice;

    @ApiModelProperty(name = "bestPrice", value = "特惠价")
    private Integer bestPrice;

    @ApiModelProperty(name = "ykPrice", value = "银卡价")
    private Integer ykPrice;

    @ApiModelProperty(name = "jkPrice", value = "金卡价")
    private Integer jkPrice;

    @ApiModelProperty(name = "zkPrice", value = "钻卡价")
    private Integer zkPrice;

    @ApiModelProperty(name = "stock", value = "库存")
    private Integer stock;

    @ApiModelProperty(name = "availableStock", value = "可供货量")
    private Integer availableStock;

    @ApiModelProperty(name = "salePattern", value = "0-下架 1-商家")
    private Boolean salePattern;

    @ApiModelProperty(name = "validDate", value = "有效期")
    private Date validDate;

}
