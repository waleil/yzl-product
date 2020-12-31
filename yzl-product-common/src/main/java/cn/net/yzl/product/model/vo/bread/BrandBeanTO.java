package cn.net.yzl.product.model.vo.bread;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BrandBeanTO extends BaseObject {
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name", value = "品牌名称")
    private String name;
    @ApiModelProperty(name = "status", value = "是否启用")
    private boolean status;
    @ApiModelProperty(name = "sort", value = "排序")
    private int sort;
    @ApiModelProperty(name = "productCount", value = "关联商品数量")
    private int productCount;

}
