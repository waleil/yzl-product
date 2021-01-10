package cn.net.yzl.product.model.vo.disease.dto;

import cn.net.yzl.product.model.BaseObject;
import cn.net.yzl.product.model.vo.product.dto.ProductDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: DiseaseTreePageDTO
 * @description 分页查询病症信息
 * @date: 2021/1/10 6:06 下午
 */
@Data
public class DiseaseTreePageDTO extends BaseObject {
    @ApiModelProperty(name = "id", value = "病症编号")
    private Integer id;

    @ApiModelProperty(name = "name", value = "商品名称")
    private String name;

    @ApiModelProperty(name = "pid", value = "上级病症编号")
    private Integer pid;

    @ApiModelProperty(name = "productDTOS", value = "关联的商品信息")
    private List<ProductDTO> productDTOS;

    @ApiModelProperty(name = "diseaseTreePageDTOS", value = "二级病症信息")
    private List<DiseaseTreePageDTO> diseaseTreePageDTOS;


}
