package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.DiseaseBean;
import cn.net.yzl.product.model.ProductDiseaseBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiseaseBeanMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DiseaseBean record);

    int insertSelective(DiseaseBean record);

    DiseaseBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiseaseBean record);

    int updateByPrimaryKey(DiseaseBean record);

    List<DiseaseBean> selectAll();

    List<DiseaseBean> findRoot();

    void deleteRelationOfDiseaseAndProduct(@Param("did") Integer did, @Param("pCode") String pCode);

    void insertRelationOfDiseaseAndProduct(ProductDiseaseBean productDiseaseBean);

    List<Integer> getProductsByDid(@Param("id") Integer id);

    List<DiseaseBean> selectByPid(@Param("pid") Integer pid);
}