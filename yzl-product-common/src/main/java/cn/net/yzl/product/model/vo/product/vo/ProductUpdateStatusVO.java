package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductUpdateStatusVO
 * @description 修改状态实体
 * @date: 2021/1/8 9:34 下午
 */
@Data
public class ProductUpdateStatusVO extends BaseObject {
    @ApiModelProperty(name = "productCode", value = "商品唯一编码")
    @NotEmpty(message = "商品ID不能为空")
    private String productCode;

    @ApiModelProperty(name = "updateNo", value = "编辑人编号")
    @NotEmpty(message = "编辑员工编号不能为空")
    private String updateNo;

    @ApiModelProperty(name = "status", value = "编辑人编号")
    @DecimalMin(value = "0",message = "非法状态")
    @DecimalMax(value = "1",message = "非法状态")
    private Integer status;
}
