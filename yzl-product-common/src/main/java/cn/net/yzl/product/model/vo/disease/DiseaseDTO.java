package cn.net.yzl.product.model.vo.disease;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author lichanghong
 * @version 1.0
 * @title: DiseaseDTO
 * @description todo
 * @date: 2021/1/6 1:56 下午
 */
@Data
public class DiseaseDTO extends BaseObject {
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name", value = "病症名称")
    private String name;
    @ApiModelProperty(name = "pid", value = "父级主键")
    private Integer pid;
}
