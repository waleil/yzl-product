package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.DiseaseBean;
import cn.net.yzl.product.model.ProductDiseaseBean;

import java.util.List;

public interface DiseaseService {

    ComResponse<Void> insertDisease(DiseaseBean diseaseBean);

    ComResponse<Void> deleteRelationOfDiseaseAndProduct(Integer did, String pCode);

    ComResponse<Void> deleteByPrimaryKey(Integer id);

    ComResponse<Void> insertRelationOfDiseaseAndProduct(ProductDiseaseBean productDiseaseBean);

    ComResponse<List<DiseaseBean>> selectAllDiseases();

    ComResponse<String> getProductsByDid(Integer id);

    ComResponse<List<DiseaseBean>> getDiseaseByPid(Integer pid);

}
