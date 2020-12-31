package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.db.ProductBean;
import cn.net.yzl.product.model.db.ProductDiseaseBean;
import cn.net.yzl.product.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @PostMapping("insertDisease")
    public ComResponse<Void> insertDisease(@RequestBody DiseaseBean diseaseBean) {
        return diseaseService.insertDisease(diseaseBean);
    }

    @DeleteMapping("deleteRelationOfDiseaseAndProduct")
    public ComResponse<Void> deleteRelationOfDiseaseAndProduct(@RequestParam Integer did, @RequestParam String pCode) {
        return diseaseService.deleteRelationOfDiseaseAndProduct(did, pCode);
    }

    @DeleteMapping("deleteDisease")
    public ComResponse<Void> deleteDisease(@RequestParam Integer id) {
        return diseaseService.deleteByPrimaryKey(id);
    }

    @PostMapping("insertRelationOfDiseaseAndProduct")
    public ComResponse<Void> insertRelationOfDiseaseAndProduct(@RequestBody ProductDiseaseBean productDiseaseBean) {
        return diseaseService.insertRelationOfDiseaseAndProduct(productDiseaseBean);
    }

    @PostMapping("insertRelationOfProductAndImgId")
    public ComResponse<Void> insertRelationOfProductAndImgUrl(@RequestParam(value = "id",required = false) String id,
                                                              @RequestParam(value = "imgId",required = false)Integer imgId,
                                                              @RequestParam(value = "type",required = false)Integer type){
        return productService.insertRelationOfProductAndImgUrl(id, imgId,type);
    }

    @PostMapping("insertImage")
    public ComResponse<Integer> insertImage(@RequestParam("url")String url,@RequestParam("type") Integer type){
        Integer id = imageService.insertImage(url,type);
        return ComResponse.success(id);
    }

    @DeleteMapping("deleteRelationOfProductAndImgId")
    public ComResponse<Void> deleteRelationOfProductAndImgId(@RequestParam("id")Integer id,
                                                             @RequestParam("type")Integer type){
        return productService.deleteRelationOfProductAndImgId(id, type);
    }

    @GetMapping("selectAllDiseases")
    public ComResponse<List<DiseaseBean>> selectAllDiseases(){
        return diseaseService.selectAllDiseases();
    }

    @GetMapping("getProductsByDid")
    ComResponse<String> getProductsByDid(@RequestParam("did") Integer id){
        return diseaseService.getProductsByDid(id);
    }

    @GetMapping("selectByIdList")
    ComResponse<List<ProductBean>> selectByIdList(String productIds){
        return productService.selectByIdList(productIds);
    }

    @GetMapping("getDiseaseByPid")
    public ComResponse<List<DiseaseBean>> getDiseaseByPid(@Param("pid") Integer pid){
        return diseaseService.getDiseaseByPid(pid);
    }
}

