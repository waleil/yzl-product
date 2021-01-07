package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.db.ProductDiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDTO;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseTreeNode;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import org.apache.ibatis.annotations.Param;

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
    /**
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/6
     * @Param:
     * @Return:
     */
    ComResponse<List<DiseaseTreeNode>> queryTreeNode();

    /**
     * @author lichanghong
     * @description 查询下拉列表为其他服务提供
     * @date: 2021/1/6 2:00 下午
     * @param pid:  父级编号
     * @return: List<DiseaseDTO>
     */
    List<DiseaseDTO> queryByPID(@Param("pid") Integer pid);
}
