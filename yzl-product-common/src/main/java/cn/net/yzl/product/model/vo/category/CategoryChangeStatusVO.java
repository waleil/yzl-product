package cn.net.yzl.product.model.vo.category;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author lichanghong
 * @version 1.0
 * @title: CategoryDelVO
 * @description 逻辑删除实体类
 * @date: 2021/1/5 2:12 下午
 */
@Data
public class CategoryChangeStatusVO extends BaseObject {
    @ApiModelProperty(name = "id",required = true, value = "主键")
    @DecimalMin(value = "1",message = "主键为正整数")
    private Integer id;
    @ApiModelProperty(name = "status",required = true, value = "状态：0代表未启用，1代表启用")
    @NotNull(message = "状态不能为空!")
    private Boolean status;
    @ApiModelProperty(name = "updateNo", required = true,value = "最近更新操作员id")
    @NotEmpty(message = "操作人员不能为空,不能为空!")
    private String updateNo;
}
