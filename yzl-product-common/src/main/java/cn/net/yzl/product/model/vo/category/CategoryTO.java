package cn.net.yzl.product.model.vo.category;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author lichanghong
 * @description 用来接收传入的实体对象
 * @date: 2021/1/5 8:43 上午
 */
@Data
public class CategoryTO extends BaseObject {

    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name",required = true, value = "分类名称")
    @NotEmpty(message = "分类名称不能为空！")
    private String name;//分类名称
    @ApiModelProperty(name = "pid", value = "父类id")
    private Integer pid;//父类id
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;//排序
    @ApiModelProperty(name = "status", value = "启用状态：true代表启用，false代表不启用")
    private Integer status;//是否启用
    @ApiModelProperty(name = "displayAppFlag", value = "是否在移动端显示：0代表不显示，1代表显示")
    private Integer displayAppFlag;//是否在移动端显示
    @ApiModelProperty(name = "imageUrl", value = "分类图标的url")
    private String imageUrl;//分类图标的url
    @ApiModelProperty(name = "descri", value = "描述")
    private String descri;//描述
    private String updateNo;//最近更新操作员id
    private int productCount;//商品数量

}
