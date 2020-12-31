package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.AttributeBean;
import cn.net.yzl.product.model.AttributeValueBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeDao{

    void insertAttribute(AttributeBean attributeBean);
    void insertAttributeValue(List<AttributeValueBean> attributeValueBeanList);
    List<AttributeBean> selectPageAttribute();
    List<AttributeValueBean> selectAttributeValue(@Param("id") Integer id);
    List<AttributeBean> selectByClassifyId(@Param("classifyId") Integer classifyId);
    AttributeBean selectById(@Param("id") Integer id);

    void updateAttribute(AttributeBean attributeBean);

    void updateAttributeValue(@Param("attribute_id") Integer attribute_id, @Param("del_flag") int del_flag, @Param("expectedDelFlag") int expectedDelFlag);

    void insertattributeClassify(@Param("id") Integer id, @Param("classifyId") String classifyId);
}
