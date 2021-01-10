package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDiseaseDTO
 * @description 商品关联的病症
 * @date: 2021/1/10 3:49 下午
 */
@Data
public class ProductDiseaseDTO extends BaseObject {
    @ApiModelProperty(name = "productCode",value = "商品ID(product唯一标识)")
    private String productCode;

    @ApiModelProperty(name = "diseaseId",value = "病症编号")
    private Integer diseaseId;

    @ApiModelProperty(name = "diseaseName",value = "病症名称")
    private String diseaseName;

    @ApiModelProperty(name = "diseasePName",value = "上级病症名称")
    private String diseasePName;

    @ApiModelProperty(name = "diseasePid",value = "上级病症编号")
    private Integer diseasePid;

}
