package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import cn.net.yzl.product.model.vo.product.vo.ProductDiseaseVO;
import cn.net.yzl.product.model.vo.product.vo.ProductImageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: ProductVO
 * @description 商品详情返回对象
 * @date: 2021/1/9 3:00 下午
 */
@Data
public class ProductDetailVO extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "productNo",value = "商品编号")
    private String productNo;

    @ApiModelProperty(name = "quickSearchCode", value = "快速检索码")
    private String quickSearchCode;

    @ApiModelProperty(name = "name", required = true, value = "商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(name = "nickname", value = "商品简称")
    private String nickname;

    @ApiModelProperty(name = "engName", value = "英文名称")
    private String engName;

    @ApiModelProperty(name = "goodsSource", required = true, value = "商品来源：1-自营 2-三方")
    private Integer goodsSource;

    @ApiModelProperty(name = "brandNo", value = "品牌编号")
    private Integer brandNo;

    @ApiModelProperty(name = "categoryDictCode", value = "商品分类")
    private Integer categoryDictCode;

    @ApiModelProperty(name = "categoryPDictCode", value = "商品一级分类")
    private Integer categoryPDictCode;

    @ApiModelProperty(name = "salePrice", required = true, value = "市场价(售卖价),以分为单位")
    private Integer salePrice;

    @ApiModelProperty(name = "costPrice", required = true, value = "成本价,以分为单位")
    private Integer costPrice;

    @ApiModelProperty(name = "limitDownPrice", required = true, value = "最低价格,所有的优惠扣减不能低于此价格,以分为单位")
    private Integer limitDownPrice;

    @ApiModelProperty(name = "salePriceD", required = true, value = "市场价(售卖价),以元为单位")
    private Double salePriceD;

    @ApiModelProperty(name = "costPriceD", required = true, value = "成本价,以元为单位")
    private Double costPriceD;

    @ApiModelProperty(name = "limitDownPriceD", required = true, value = "最低价格,所有的优惠扣减不能低于此价格,以元为单位")
    private Double limitDownPriceD;

    @ApiModelProperty(name = "stock", required = true, value = "库存,-1代表不限制库存")
    private Integer stock;

    @ApiModelProperty(name = "stockThreshold", value = "库存阈值")
    private Integer stockThreshold;

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


    @ApiModelProperty(name = "importFlag", required = true, value = "是否进口商品，0代表非进口，1代表是进口")
    private Integer importFlag;

    @ApiModelProperty(name = "productionTime", required = true, value = "生产日期")
    private String productionTime;

    @ApiModelProperty(name = "expirationDate", required = true, value = "保质期")
    private Integer expirationDate;

    @ApiModelProperty(name = "validDate", required = true, value = "有效期至")
    private String validDate;

    @ApiModelProperty(name = "unit", value = "计量单位")
    private String unit;

    @ApiModelProperty(name = "totalUseNum", required = true, value = "规格")
    private Integer totalUseNum;

    @ApiModelProperty(name = "oneUseNum", required = true, value = "每次数量")
    private Integer oneUseNum;

    @ApiModelProperty(name = "oneToTimes", required = true, value = "每日几次")
    private Integer oneToTimes;

    @ApiModelProperty(name = "cjName", required = true, value = "厂家名称")
    private String cjName;

    @ApiModelProperty(name = "cjCountryNo", value = "厂家国家")
    private Integer cjCountryNo;

    @ApiModelProperty(name = "cjProvinceNo", value = "厂家省")
    private Integer cjProvinceNo;

    @ApiModelProperty(name = "cjCityNo", value = "厂家市")
    private Integer cjCityNo;

    @ApiModelProperty(name = "cjAreaNo", value = "厂家区/县")
    private Integer cjAreaNo;
    @ApiModelProperty(name = "cjCountryName", value = "厂家国家名称")
    private String cjCountryName;

    @ApiModelProperty(name = "cjProvinceName", value = "厂家省名称")
    private String cjProvinceName;

    @ApiModelProperty(name = "cjCityName", value = "厂家市名称")
    private String cjCityName;

    @ApiModelProperty(name = "cjAreaName", value = "厂家区/县名称")
    private String cjAreaName;

    @ApiModelProperty(name = "cjAddr", value = "厂家详细地址")
    private String cjAddr;

    @ApiModelProperty(name = "descri", value = "描述")
    private String descri;

    @ApiModelProperty(name = "applicable", required = true,value = "适用人群")
    private String applicable;

    @ApiModelProperty(name = "forbidden", required = true, value = "禁用人群")
    private String forbidden;

    @ApiModelProperty(name = "rawStock", required = true, value = "原材料")
    private String rawStock;

    @ApiModelProperty(name = "videoUrl", value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "diseaseName", value = "商品主治病症名称")
    private String diseaseName;

    @ApiModelProperty(name = "diseasePid", value = "主治病症上级主键")
    private Integer diseasePid;

    @ApiModelProperty(name = "imageUrl", value = "商品主图")
    private String imageUrl;
    @ApiModelProperty(name = "images", value = "商品图片")
    private List<ProductImageVO> images;
    @ApiModelProperty(name = "diseaseVOS", value = "商品关联病症")
    private List<ProductDiseaseVO> diseaseVOS;

    @ApiModelProperty(name = "fastDFSUrl", value = "图片库地址,需要把imageUrl进行拼接")
    private String fastDFSUrl;

}
