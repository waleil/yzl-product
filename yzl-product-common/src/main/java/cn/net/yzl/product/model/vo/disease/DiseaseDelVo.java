package cn.net.yzl.product.model.vo.disease;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lichanghong
 * @version 1.0
 * @title: DiseaseVo
 * @description 病症删除实体
 * @date: 2021/1/5 10:39 下午
 */
@Data
public class DiseaseDelVo extends BaseObject {
    @ApiModelProperty(name = "id", required = true,value = "主键")
    @NotNull(message = "病症主键不能为空")
    private Integer id;
    @ApiModelProperty(name = "pId",required = true, value = "上级主键")
    @NotNull(message = "病症上级主键不能为空")
    private Integer pId;
    @ApiModelProperty(name = "updateNo", value = "员工编号")
    private String updateNo;
}
