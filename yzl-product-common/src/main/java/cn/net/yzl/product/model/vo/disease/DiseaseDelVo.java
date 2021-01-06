package cn.net.yzl.product.model.vo.disease;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "updateNo", value = "员工编号")
    private String updateNo;
}
