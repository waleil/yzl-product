package cn.net.yzl.product.model.vo.category;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lichanghong
 * @description 用来接收传入的实体对象
 * @date: 2021/1/5 8:43 上午
 */
@Data
public class CategorySelectTO extends BaseObject {
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name",required = true, value = "分类名称")
    private String name;//分类名称
}
