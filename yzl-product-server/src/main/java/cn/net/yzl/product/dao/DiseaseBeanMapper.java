package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.db.ProductDiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiseaseBeanMapper {

    int deleteByPrimaryKey(DiseaseDelVo delVo);

    /**
     * @author lichanghong
     * @description 新增病症
     * @date: 2021/1/5 11:03 下午
     * @param diseaseVo:
     * @return: null
     */
    int insertSelective(DiseaseVo diseaseVo);

    DiseaseBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiseaseBean record);

    List<DiseaseBean> selectAll();

    List<DiseaseBean> findRoot();

    void deleteRelationOfDiseaseAndProduct(@Param("did") Integer did, @Param("pCode") String pCode);

    void insertRelationOfDiseaseAndProduct(ProductDiseaseBean productDiseaseBean);

    List<Integer> getProductsByDid(@Param("id") Integer id);

    List<DiseaseBean> selectByPid(@Param("pid") Integer pid);
}