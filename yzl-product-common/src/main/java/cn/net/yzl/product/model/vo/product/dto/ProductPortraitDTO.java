package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import cn.net.yzl.product.model.vo.product.vo.ProductDiseaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductPortraitDTO
 * @description 商品画像实体类
 * @date: 2021/1/10 12:12 下午
 */
@Data
public class ProductPortraitDTO extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo",value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "name", required = true, value = "商品名称")
    private String name;

    @ApiModelProperty(name = "nickname", value = "商品简称")
    private String nickname;

    @ApiModelProperty(name = "engName", value = "英文名称")
    private String engName;

    @ApiModelProperty(name = "goodsSource", required = true, value = "商品来源：1-自营 2-三方")
    private Integer goodsSource;

    @ApiModelProperty(name = "brandName", value = "品牌名称")
    private String brandName;

    @ApiModelProperty(name = "categoryDictName", value = "商品分类名称")
    private String categoryDictName;

    @ApiModelProperty(name = "salePriceD", required = true, value = "市场价(售卖价),以元为单位")
    private Double salePriceD;

    @ApiModelProperty(name = "costPriceD", required = true, value = "成本价,以元为单位")
    private Double costPriceD;

    @ApiModelProperty(name = "limitDownPriceD", required = true, value = "最低价格,所有的优惠扣减不能低于此价格,以元为单位")
    private Double limitDownPriceD;

    @ApiModelProperty(name = "stock", required = true, value = "库存,-1代表不限制库存")
    private Integer stock;

    @ApiModelProperty(name = "marketingRule", value = "营销准则")
    private String marketingRule;

    @ApiModelProperty(name = "saleStartTime", value = "销售开始日期")
    private String saleStartTime;

    @ApiModelProperty(name = "saleEndTime", value = "销售结束日期")
    private String saleEndTime;

    @ApiModelProperty(name = "patentCode", value = "发明专利号")
    private String patentCode;

    @ApiModelProperty(name = "approvalNumber", value = "批准文号")
    private String approvalNumber;

    @ApiModelProperty(name = "approvalType", value = "批文类型，0代表国药准字，1代表国药健字")
    private int approvalType;

    @ApiModelProperty(name = "importFlag", value = "是否进口商品，0代表非进口，1代表是进口")
    private Integer importFlag;

    @ApiModelProperty(name = "productionTime",  value = "生产日期")
    private String productionTime;

    @ApiModelProperty(name = "expirationDate",  value = "保质期")
    private Integer expirationDate;

    @ApiModelProperty(name = "validDate",  value = "有效期至")
    private String validDate;

    @ApiModelProperty(name = "unit", value = "计量单位")
    private String unit;

    @ApiModelProperty(name = "totalUseNum",  value = "规格")
    private Integer totalUseNum;

    @ApiModelProperty(name = "oneUseNum",  value = "每次数量")
    private Integer oneUseNum;

    @ApiModelProperty(name = "oneToTimes",  value = "每日几次")
    private Integer oneToTimes;

    @ApiModelProperty(name = "cjName",  value = "厂家名称")
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

    @ApiModelProperty(name = "cjCountryName", value = "厂家国家名称")
    private String cjCountryName;

    @ApiModelProperty(name = "cjProvinceName", value = "厂家省名称")
    private String cjProvinceName;

    @ApiModelProperty(name = "cjCityName", value = "厂家市名称")
    private String cjCityName;

    @ApiModelProperty(name = "cjAreaName", value = "厂家区/县名称")
    private String cjAreaName;

    @ApiModelProperty(name = "applicable", value = "适用人群")
    private String applicable;

    @ApiModelProperty(name = "forbidden", value = "禁用人群")
    private String forbidden;

    @ApiModelProperty(name = "rawStock",  value = "原材料")
    private String rawStock;

    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "diseaseName", value = "商品主治病症名称")
    private String diseaseName;

    @ApiModelProperty(name = "diseasePid", value = "主治病症上级主键")
    private Integer diseasePid;

    @ApiModelProperty(name = "diseaseVOS", value = "商品关联病症")
    private List<ProductDiseaseVO> diseaseVOS;



}
