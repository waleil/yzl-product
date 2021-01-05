package cn.net.yzl.product.model.db;

import cn.net.yzl.product.model.BaseObject;
import cn.net.yzl.product.model.db.AttributeBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    @ApiModelProperty(name = "displayFlag", value = "是否展示")
    private Boolean displayFlag;//是否展示
    @ApiModelProperty(name = "displayAppFlag", value = "是否在移动端显示：0代表不显示，1代表显示")
    private Boolean displayAppFlag;//是否在移动端显示
    @ApiModelProperty(name = "status", value = "状态（是否启用：0代表未启用，1代表启用）")
    private Integer status;//状态（是否有效）
    @ApiModelProperty(name = "unit", value = "计量单位")
    private String unit;//计量单位
    @ApiModelProperty(name = "imageUrl", value = "分类图标的url")
    private String imageUrl;//分类图标的url
    @ApiModelProperty(name = "keyWord", value = "关键词")
    private String keyWord;//关键词
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