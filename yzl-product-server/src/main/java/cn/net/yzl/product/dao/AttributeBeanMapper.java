package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.AttributeBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttributeBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AttributeBean record);

    int insertSelective(AttributeBean record);

    AttributeBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AttributeBean record);

    int updateByPrimaryKey(AttributeBean record);

    List<AttributeBean> selectIn(@Param("ids") String ids);

    void deleteByIds(@Param("ids") String id);
}