package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.dao.ProductBeanMapper;
import cn.net.yzl.product.model.DiseaseBean;
import cn.net.yzl.product.model.ProductDiseaseBean;
import cn.net.yzl.product.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseBeanMapper diseaseBeanMapper;

    @Autowired
    private ProductBeanMapper productBeanMapper;

    @Override
    public ComResponse<Void> insertDisease(DiseaseBean diseaseBean) {
        diseaseBeanMapper.insertSelective(diseaseBean);
        return ComResponse.success();
    }
    @Override
    public ComResponse<Void> deleteRelationOfDiseaseAndProduct(Integer did, String pCode) {
        diseaseBeanMapper.deleteRelationOfDiseaseAndProduct(did, pCode);
        return ComResponse.success();
    }
    @Override
    public ComResponse<Void> deleteByPrimaryKey(Integer id) {
        diseaseBeanMapper.deleteByPrimaryKey(id);
        return ComResponse.success();
    }
    @Override
    public ComResponse<Void> insertRelationOfDiseaseAndProduct(ProductDiseaseBean productDiseaseBean) {
        diseaseBeanMapper.insertRelationOfDiseaseAndProduct(productDiseaseBean);
        return null;
    }

    @Override
    public ComResponse<List<DiseaseBean>> selectAllDiseases() {
        List<DiseaseBean> diseaseBeans = diseaseBeanMapper.selectAll();
        return ComResponse.success(diseaseBeans);
    }

    @Override
    public ComResponse<String> getProductsByDid(Integer id) {
        List<Integer> list = diseaseBeanMapper.getProductsByDid(id);
        List<String> result = new ArrayList<>();
        result.add("");
        list.forEach(temp ->{
            result.set(0, result.get(0)+temp+",");
        });
        String substring = result.get(0).contains(",")?result.get(0).substring(0, result.get(0).lastIndexOf(",")):result.get(0);
        return ComResponse.success(substring);
    }

    @Override
    public ComResponse<List<DiseaseBean>> getDiseaseByPid(Integer pid) {

        List<DiseaseBean> list = diseaseBeanMapper.selectByPid(pid);

        return ComResponse.success(list);
    }

}
