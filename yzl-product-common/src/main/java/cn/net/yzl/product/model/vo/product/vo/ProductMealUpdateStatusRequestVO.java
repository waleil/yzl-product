package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(value = "productMealUpdateStatusRequestVO",description = "修改上下架状态实体")
public class ProductMealUpdateStatusRequestVO extends BaseObject {
    @ApiModelProperty(name = "mealNo", value = "套餐唯一编码")
    @NotEmpty(message = "套餐ID不能为空")
    private List<String> mealNoList;


    @ApiModelProperty(name = "status", value = "上下架状态 0下架1上架")
    @DecimalMin(value = "0",message = "下架")
    @DecimalMax(value = "1",message = "上架")
    private Integer status;

}