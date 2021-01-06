package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import cn.net.yzl.product.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseBeanMapper diseaseBeanMapper;
    /**
     * @author lichanghong
     * @description 新增病症
     * @date: 2021/1/5 11:03 下午
     * @param diseaseVo:
     * @return: null
     */
    @Override
    public ComResponse<Void> insertDisease(DiseaseVo diseaseVo){
        diseaseVo.setUpdateTime(null);
        diseaseBeanMapper.insertSelective(diseaseVo);
    return ComResponse.success();
    }
    @Override
    public ComResponse<Void> deleteByPrimaryKey(DiseaseDelVo delVo) {
        //首先判断是否有子集
        List<DiseaseBean> list = diseaseBeanMapper.selectByPid(delVo.getId());
        if(!CollectionUtils.isEmpty(list)){
           return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(),"存在子项，无法删除!");
        }
        //判断是否有商品
        // TODO: 2021/1/5  查询商品信息
        diseaseBeanMapper.deleteByPrimaryKey(delVo);
        return ComResponse.success();
    }
    @Override
    public ComResponse<List<DiseaseBean>> selectAllDiseases() {
        List<DiseaseBean> diseaseBeans = diseaseBeanMapper.selectAll();
        return ComResponse.success(diseaseBeans);
    }
    @Override
    public ComResponse<List<DiseaseBean>> getDiseaseByPid(Integer pid) {

        List<DiseaseBean> list = diseaseBeanMapper.selectByPid(pid);

        return ComResponse.success(list);
    }

}
