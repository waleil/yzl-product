package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.db.ProductDiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;

import java.util.List;

public interface DiseaseService {
    /**
     * @author lichanghong
     * @description 新增病症
     * @date: 2021/1/5 11:03 下午
     * @param diseaseVo:
     * @return: null
     */
    ComResponse<Void> insertDisease(DiseaseVo diseaseVo);


    ComResponse<Void> deleteByPrimaryKey(DiseaseDelVo delVo);


    ComResponse<List<DiseaseBean>> selectAllDiseases();

    ComResponse<List<DiseaseBean>> getDiseaseByPid(Integer pid);

}
