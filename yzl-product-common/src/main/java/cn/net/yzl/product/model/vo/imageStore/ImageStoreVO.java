package cn.net.yzl.product.model.vo.imageStore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ImageStoreVO {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("封面图url")
    private String imageUrl;
    @ApiModelProperty("描述")
    private String descri;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("类型（0：图片库，1：视频库）")
    @NotEmpty(message = "类型不能为空！")
    private Byte type;
    @NotEmpty(message = "创建人不能为空！")
    private String createNo;
    @ApiModelProperty("图片库名称")
    @NotNull(message = "图片库名称不能为空！")
    private String name;
    private Date updateTime;

    private String updateNo;

}
