package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.db.Meal;
import cn.net.yzl.product.model.vo.disease.DiseaseAllDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ApiModel(value = "MealDTO",description = "套餐详情")
public class MealDTO {

    @ApiModelProperty(value = "套餐",name = "meal")
    private Meal meal;
    //适宜人群集合
    @ApiModelProperty(value = "适宜人群集合",name = "applicableSet")
    private Set<String> applicableSet ;
    //禁忌人群集合
    @ApiModelProperty(value = "禁忌人群",name = "forbiddenSet")
    private Set<String> forbiddenSet;

    @ApiModelProperty(value = "分组病症集合",name = "diseaseAllDTOList")
    private List<DiseaseAllDTO> diseaseAllDTOList;
}
