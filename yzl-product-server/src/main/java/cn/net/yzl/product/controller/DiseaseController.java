package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.db.ProductDiseaseBean;
import cn.net.yzl.product.service.DiseaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @PostMapping("v1/insert")
    public ComResponse insertDisease(@RequestBody DiseaseBean diseaseBean) {
        return diseaseService.insertDisease(diseaseBean);
    }

    @DeleteMapping("v1/deleteDiseaseProduct")
    public ComResponse deleteRelationOfDiseaseAndProduct(@RequestParam Integer did, @RequestParam String pCode) {
        return diseaseService.deleteRelationOfDiseaseAndProduct(did, pCode);
    }

    @DeleteMapping("v1/deleteById")
    public ComResponse deleteDisease(@RequestParam Integer id) {
        return diseaseService.deleteByPrimaryKey(id);
    }

    @PostMapping("v1/insertDiseaseProduct")
    public ComResponse insertRelationOfDiseaseAndProduct(@RequestBody ProductDiseaseBean productDiseaseBean) {
        return diseaseService.insertRelationOfDiseaseAndProduct(productDiseaseBean);
    }

    @GetMapping("v1/selectAll")
    public ComResponse selectAllDiseases(){
        return diseaseService.selectAllDiseases();
    }

    @GetMapping("v1/selectById")
    ComResponse getProductsByDid(@RequestParam("did") Integer id){
        return diseaseService.getProductsByDid(id);
    }

    @GetMapping("v1/selectByPid")
    public ComResponse getDiseaseByPid(@Param("pid") Integer pid){
        return diseaseService.getDiseaseByPid(pid);
    }

}