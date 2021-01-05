package cn.net.yzl.product.model.db.category;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Category extends BaseObject {
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name", value = "分类名称")
    private String name;//分类名称
    @ApiModelProperty(name = "pid", value = "父类id")
    private Integer pid;//父类id
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;//排序
    @ApiModelProperty(name = "displayAppFlag", value = "是否在移动端显示：0代表不显示，1代表显示")
    private Boolean displayAppFlag;//是否在移动端显示
    @ApiModelProperty(name = "status", value = "状态（是否启用：0代表未启用，1代表启用）")
    private Boolean status;//状态（是否有效）
    @ApiModelProperty(name = "imageUrl", value = "分类图标的url")
    private String imageUrl;//分类图标的url
    @ApiModelProperty(name = "descri", value = "描述")
    private String descri;//描述
    @ApiModelProperty(name = "delFlag", value = "是否删除")
    private boolean delFlag;//是否删除
    @ApiModelProperty(name = "createNo", value = "增加操作员id")
    private String createNo;//增加操作员id
    @ApiModelProperty(name = "createTime", value = "添加时间")
    private Date createTime;//添加时间
    @ApiModelProperty(name = "updateNo", value = "最近更新操作员id")
    private String updateNo;//最近更新操作员id
    @ApiModelProperty(name = "updateTime", value = "最近更新时间")
    private Date updateTime;//最近更新时间


}