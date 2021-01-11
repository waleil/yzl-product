package cn.net.yzl.product.model.vo.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ProductUpdateTimeRequestVO {

    @ApiModelProperty(name = "productCode", value = "商品唯一编码")
    @NotEmpty(message = "商品ID不能为空")
    private List<String> productCodeList;

    @ApiModelProperty(name = "saleStartTime", value = "销售开始日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "销售开始日期不能为空")
    private Date saleStartTime;

    @ApiModelProperty(name = "saleEndTime", value = "销售结束日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "销售结束日期不能为空")
    private Date saleEndTime;

}
