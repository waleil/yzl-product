package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.AttributeValueBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttributeValueBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AttributeValueBean record);

    int insertSelective(AttributeValueBean record);

    AttributeValueBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AttributeValueBean record);

    int updateByPrimaryKey(AttributeValueBean record);

}