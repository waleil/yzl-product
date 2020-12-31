package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.ClassifyAttributeBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassifyAttributeBeanMapper {
    int insert(ClassifyAttributeBean record);

    int insertSelective(ClassifyAttributeBean record);

    List<Integer> selectAll(@Param("id") Integer id);

    void deleteByCid(@Param("id") Integer id);
}