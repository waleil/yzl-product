package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductListDTO
 * @description 商品列表返回对象
 * @date: 2021/1/7 11:29 下午
 */
@Data
public class ProductListDTO extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo",value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "name", value = "商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(name = "engName", value = "英文名称")
    private String engName;

    @ApiModelProperty(name = "goodsSource", value = "商品来源：1-自营 2-三方")
    @DecimalMin(value = "1",message = "最小只能为1")
    private Integer goodsSource;

    @ApiModelProperty(name = "brandNo", value = "品牌编号")
    private Integer brandNo;

    @ApiModelProperty(name = "categoryDictCode", value = "商品分类")
    private Integer categoryDictCode;

    @ApiModelProperty(name = "salePrice", value = "市场价(售卖价)单位为分")
    private Integer salePrice;

    @ApiModelProperty(name = "salePriceD", value = "市场价(售卖价)单位为元")
    private Double salePriceD;

    @ApiModelProperty(name = "stock", value = "库存,-1代表不限制库存")
    private Integer stock;

    @ApiModelProperty(name = "status", value = "上下架状态：0-下架 1-上架")
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "imageUrl", value = "商品主图片")
    private String imageUrl;
    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

}
