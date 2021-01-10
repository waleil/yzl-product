package cn.net.yzl.product.model.vo.disease;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@ApiModel(value = "DiseaseAllDTO",description = "病症分组信息")
public class DiseaseAllDTO {
//
//    @ApiModelProperty(value = "主键",name = "id")
//    private Integer id;
    @ApiModelProperty(value = "一级病症名称",name = "name")
    private String name;
    @ApiModelProperty(value = "二级病症列表",name = "diseaseDTOSet")
    private Set<String> diseaseDTOSet;
}
