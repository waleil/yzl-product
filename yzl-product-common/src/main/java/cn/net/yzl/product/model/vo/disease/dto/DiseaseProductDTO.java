package cn.net.yzl.product.model.vo.disease.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: DiseaseProductDTO
 * @description 病症关联商品信息
 * @date: 2021/1/10 4:40 下午
 */
@Data
public class DiseaseProductDTO {

    private Integer diseaseId;
    private String diseaseName;
    private List<DiseaseProductDTO> diseaseProductDTOS;
    private List<ProductDTO> productDTOS;
}
