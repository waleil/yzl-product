package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @description
 * @Author: dongjunmei
 * @Date: 2021-01-09 16:18
 */
@Data
public class ProductMealUpdateTimeVO extends BaseObject {

    @ApiModelProperty(name = "mealNo", value = "套餐唯一编码")
    @NotEmpty(message = "套餐ID不能为空")
    private List<String> mealNoList;

    @ApiModelProperty(name = "saleStartTime", value = "销售开始日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "销售开始日期不能为空")
    private Date saleStartTime;

    @ApiModelProperty(name = "saleEndTime", value = "销售结束日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "销售结束日期不能为空")
    private Date saleEndTime;
}
