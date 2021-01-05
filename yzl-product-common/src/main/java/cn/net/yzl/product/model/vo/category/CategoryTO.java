package cn.net.yzl.product.model.vo.category;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryTO extends BaseObject {

    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name",required = true, value = "分类名称")
    private String name;
    @ApiModelProperty(name = "pid", value = "父类id")
    private Integer pid;
    @ApiModelProperty(name = "unit", value = "计量单位")
    private String unit;
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;
    @ApiModelProperty(name = "status", value = "启用状态：true代表启用，false代表不启用")
    private boolean status;
    @ApiModelProperty(name = "displayAppFlag", value = "是否在移动端显示：0代表不显示，1代表显示")
    private boolean displayAppFlag;
    @ApiModelProperty(name = "imageUrl", value = "分类图标的url")
    private String imageUrl;
    @ApiModelProperty(name = "descri", value = "描述")
    private String descri;

}
