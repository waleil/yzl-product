package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.vo.disease.DiseaseDTO;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import cn.net.yzl.product.service.DiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @ApiOperation("查询所有病症")
    @GetMapping("v1/selectAll")
    public ComResponse selectAllDiseases(){
        return diseaseService.selectAllDiseases();
    }

    @ApiOperation("查询树形结构，包含商品信息")
    @GetMapping("v1/queryTreeNode")
    public ComResponse queryTreeNode(){
        return diseaseService.queryTreeNode();
    }
    /**
     * @author lichanghong
     * @description 查询病症，主要针对下拉列表
     * @date: 2021/1/6 2:08 下午 
     * @param pid:
     * @return: ComResponse<List<DiseaseDTO>>
     */
    @ApiOperation("根据父级编号查询病症")
    @GetMapping("v1/queryByPID")
    public ComResponse<List<DiseaseDTO>> queryByPID(@RequestParam(value = "pid",defaultValue = "0",required = false) Integer pid){
        List<DiseaseDTO> list = diseaseService.queryByPID(pid);
        return ComResponse.success(list);
    }

}