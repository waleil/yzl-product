package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: ProductUpdateTimeVO
 * @description 修改时间实体
 * @date: 2021/1/9 13:00 下午
 */
@Data
public class ProductUpdateTimeVO extends BaseObject {
    @ApiModelProperty(name = "productCode", value = "商品唯一编码")
    @NotEmpty(message = "商品ID不能为空")
    private List<String> productCodeList;

    @ApiModelProperty(name = "updateNo", value = "编辑人编号")
    @NotEmpty(message = "编辑员工编号不能为空")
    private String updateNo;

    @ApiModelProperty(name = "saleStartTime", value = "销售开始日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "销售开始日期不能为空")
    private Date saleStartTime;

    @ApiModelProperty(name = "saleEndTime", value = "销售结束日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "销售结束日期不能为空")
    private Date saleEndTime;
}
