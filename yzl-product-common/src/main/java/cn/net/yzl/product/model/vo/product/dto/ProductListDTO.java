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
    @ApiModelProperty(name = "productCode", value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo", value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "name", value = "商品名称")
    private String name;

    @ApiModelProperty(name = "engName", value = "英文名称")
    private String engName;

    @ApiModelProperty(name = "goodsSource", value = "商品来源：1-自营 2-三方")
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

    @ApiModelProperty(name = "brandName",value = "品牌名称")
    private String brandName;

    @ApiModelProperty(name = "saleStartTime",value = "售卖开始时间")
    private Date saleStartTime;

    @ApiModelProperty(name = "saleEndTime",value = "售卖结束时间")
    private Date saleEndTime;

    @ApiModelProperty(name = "status", value = "上下架状态：0-下架 1-上架")
    private int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "imageUrl", value = "商品主图片")
    private String imageUrl;

    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "diseasePid", value = "主治病症上级主键")
    private Integer diseasePid;

    @ApiModelProperty(name = "fastDFSUrl", value = "图片库地址,需要把imageUrl进行拼接")
    private String fastDFSUrl;

    @ApiModelProperty(name = "diseaseStr", value = "关联的主治病症名称")
    private String diseaseStr;

    @ApiModelProperty(name = "categoryStr", value = "关联的类目名称")
    private String categoryStr;

}
