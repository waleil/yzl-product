package cn.net.yzl.product.model.vo.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDiseaseVO
 * @description 商品病症关系表
 * @date: 2021/1/8 8:42 上午
 */
@Data
public class ProductDiseaseVO {
    @ApiModelProperty(name = "productCode", value = "商品ID")
    private String productCode;
    @ApiModelProperty(name = "diseaseId", value = "病症主键")
    private Integer diseaseId;
    @ApiModelProperty(name = "diseaseName", value = "病症名称")
    private String diseaseName;
}
