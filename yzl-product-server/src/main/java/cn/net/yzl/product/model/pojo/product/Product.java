package cn.net.yzl.product.model.pojo.product;

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
 * @title: ProductVO
 * @description 商品编辑实体类
 * @date: 2021/1/7 4:32 下午
 */
@Data
public class Product extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo",value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "businessCode", value = "商家编码")
    private String businessCode;

    @ApiModelProperty(name = "quickSearchCode", value = "快速检索码")
    private String quickSearchCode;

    @ApiModelProperty(name = "prove", value = "产品投放许可")
    private String prove;

    @ApiModelProperty(name = "name", value = "商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(name = "nickname", value = "商品简称")
    private String nickname;

    @ApiModelProperty(name = "engName", value = "英文名称")
    private String engName;

    @ApiModelProperty(name = "goodsSource", value = "商品来源：1-自营 2-三方")
    @DecimalMin(value = "1",message = "最小只能为1")
    private Integer goodsSource;

    @ApiModelProperty(name = "brandNo", value = "品牌编号")
    private Integer brandNo;

    @ApiModelProperty(name = "categoryDictCode", value = "商品分类")
    @DecimalMin(value = "1",message = "商品分类不能为空")
    private Integer categoryDictCode;

    @ApiModelProperty(name = "barCode", value = "商品条形码")
    private String barCode;

    @ApiModelProperty(name = "giftFlag", value = "是否可作为赠品,0否,1:是")
    private Integer giftFlag;

    @ApiModelProperty(name = "jfExchangeFlag", value = "是否可以积分兑换 0-否 1-是")
    private Integer jfExchangeFlag;

    @ApiModelProperty(name = "jfPrice", value = "兑换所需积分")
    private Integer jfPrice;

    @ApiModelProperty(name = "salePrice", value = "市场价(售卖价)以分为单位")
    private Integer salePrice;

    @ApiModelProperty(name = "memberPrice", value = "会员价,以分为单位")
    private Integer memberPrice;

    @ApiModelProperty(name = "costPrice", value = "成本价,以分为单位")
    private Integer costPrice;

    @ApiModelProperty(name = "bestPrice", value = "特惠价,以分为单位")
    private Integer bestPrice;

    @ApiModelProperty(name = "limitDownPrice", value = "最低价格,所有的优惠扣减不能低于此价格,以分为单位")
    private Integer limitDownPrice;

    @ApiModelProperty(name = "ykPrice", value = "银卡价,以分为单位")
    private Integer ykPrice;

    @ApiModelProperty(name = "jkPrice", value = "金卡价,以分为单位")
    private Integer jkPrice;

    @ApiModelProperty(name = "zkPrice", value = "钻卡价,以元为单位")
    private Integer zkPrice;
    @ApiModelProperty(name = "zkPrice", value = "钻卡价,以元为单位")
    private Double zkPriceD;
    @ApiModelProperty(name = "stock", value = "库存,-1代表不限制库存")
    private Integer stock;

    @ApiModelProperty(name = "stockThreshold", value = "库存阈值")
    private Integer stockThreshold;

    @ApiModelProperty(name = "salePattern", value = "销售模式(0现售,1代表预售)")
    private Integer salePattern;

    @ApiModelProperty(name = "marketingRule", value = "营销准则")
    private String marketingRule;

    @ApiModelProperty(name = "saleStartTime", value = "销售开始日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date saleStartTime;

    @ApiModelProperty(name = "saleEndTime", value = "销售结束日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date saleEndTime;

    @ApiModelProperty(name = "status", value = "上下架状态：0-下架 1-上架")
    private int status;

    @ApiModelProperty(name = "patentCode", value = "发明专利号")
    private String patentCode;

    @ApiModelProperty(name = "approvalNumber", value = "批准文号")
    private String approvalNumber;

    @ApiModelProperty(name = "approvalType", value = "批文类型，0代表国药准字，1代表国药健字")
    private int approvalType;

    @ApiModelProperty(name = "weight", value = "重量(g)")
    private Integer weight;

    @ApiModelProperty(name = "importFlag", value = "是否进口商品，0代表非进口，1代表是进口")
    private Integer importFlag;

    @ApiModelProperty(name = "productionTime", value = "生产日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date productionTime;

    @ApiModelProperty(name = "expirationDate", value = "保质期")
    @DecimalMin(value = "1",message = "保质期为空")
    private Integer expirationDate;

    @ApiModelProperty(name = "validDate", value = "有效期至")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date validDate;

    @ApiModelProperty(name = "unit", value = "计量单位")
    private String unit;

    @ApiModelProperty(name = "totalUseNum", value = "规格")
    @DecimalMin(value = "1",message = "规格不能为空")
    private Integer totalUseNum;

    @ApiModelProperty(name = "oneUseNum", value = "每次数量")
    @DecimalMin(value = "1",message = "每次数量不能为空")
    private Integer oneUseNum;

    @ApiModelProperty(name = "oneToTimes", value = "每日几次")
    @DecimalMin(value = "1",message = "每日几次不能为空")
    private Integer oneToTimes;

    @ApiModelProperty(name = "cjName", value = "厂家名称")
    @NotEmpty(message = "厂家名称不能为空")
    private String cjName;

    @ApiModelProperty(name = "cjCountryNo", value = "厂家国家")
    private Integer cjCountryNo;

    @ApiModelProperty(name = "cjProvinceNo", value = "厂家省")
    private Integer cjProvinceNo;

    @ApiModelProperty(name = "cjCityNo", value = "厂家市")
    private Integer cjCityNo;

    @ApiModelProperty(name = "cjAreaNo", value = "厂家区/县")
    private Integer cjAreaNo;

    @ApiModelProperty(name = "cjAddr", value = "厂家详细地址")
    private String cjAddr;

    @ApiModelProperty(name = "updateNo", value = "编辑员工工号")
    private String updateNo;

    @ApiModelProperty(name = "descri", value = "描述")
    private String descri;

    @ApiModelProperty(name = "applicable", value = "适用人群")
    @NotEmpty(message = "适用人群不能为空")
    private String applicable;

    @ApiModelProperty(name = "forbidden", value = "禁用人群")
    @NotEmpty(message = "禁用人群不能为空")
    private String forbidden;

    @ApiModelProperty(name = "rawStock", value = "原材料")
    @NotEmpty(message = "原材料不能为空")
    private String rawStock;

    @ApiModelProperty(name = "videoUrl", value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(name = "financeCode", value = "财务编码")
    private String financeCode;

    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "diseasePid", value = "主治病症上级主键")
    private Integer diseasePid;

    @ApiModelProperty(name = "imageUrl", value = "商品主图")
    private String imageUrl;

    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private Date updateTime;
}
