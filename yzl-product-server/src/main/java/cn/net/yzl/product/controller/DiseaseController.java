package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.db.ProductDiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import cn.net.yzl.product.service.DiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("disease")
@Api(tags = "病症管理")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;
    @ApiOperation("新增病症")
    @PostMapping("v1/insert")
    public ComResponse insertDisease(@RequestBody @Valid DiseaseVo diseaseVo) {
        return diseaseService.insertDisease(diseaseVo);
    }
    @ApiOperation("删除病症")
    @PostMapping("v1/deleteById")
    public ComResponse deleteDisease(@RequestBody @Valid DiseaseDelVo delVo) {
        return diseaseService.deleteByPrimaryKey(delVo);
    }

    @GetMapping("v1/selectAll")
    public ComResponse selectAllDiseases(){
        return diseaseService.selectAllDiseases();
    }

    @GetMapping("v1/selectByPid")
    public ComResponse getDiseaseByPid(@Param("pid") Integer pid){
        return diseaseService.getDiseaseByPid(pid);
    }

}