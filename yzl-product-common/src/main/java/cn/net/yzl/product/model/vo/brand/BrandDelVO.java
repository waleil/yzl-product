package cn.net.yzl.product.model.vo.brand;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

/**
 * @author lichanghong
 * @version 1.0
 * @title: BrandVo
 * @description 接受前端传入的值
 * @date: 2020/12/31 9:50 下午
 */
@Data
@ApiModel(description = "接收实体")
public class BrandDelVO extends BaseObject {
    @ApiModelProperty(name = "updateNo", value = "操作人工号")
    @NotEmpty(message = "操作人工号,不能为空!")
    private String updateNo;
    @ApiModelProperty(name = "id", value = "品牌主键")
    @DecimalMin("1")
    private Integer id;
}
