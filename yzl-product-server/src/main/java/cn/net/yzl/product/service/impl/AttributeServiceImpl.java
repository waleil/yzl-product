package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.AttributeDao;
import cn.net.yzl.product.model.db.AttributeBean;
import cn.net.yzl.product.model.db.AttributeValueBean;
import cn.net.yzl.product.service.AttributeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品属性接口
 */
@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeDao attributeDao;

    /**
     * 添加商品基本属性
     *
     * @param attributeBean
     */
    @Override
    public void insertAttribute(AttributeBean attributeBean) {
        try {
            attributeDao.insertAttribute(attributeBean);
            String values = attributeBean.getValues();
            //属性类型
            Integer attributeType = attributeBean.getAttributeType();
            //id
            Integer id = attributeBean.getId();

            List<AttributeValueBean> attributeValueBeanList = null;
            if (3 != attributeType) {
                attributeValueBeanList = splictAttributeValues(values, id);
            }

            if (attributeValueBeanList != null && attributeValueBeanList.size() != 0) {
                insertAttributeValue(attributeValueBeanList);
            }
            if (attributeBean.getClassifyId() != null){//如果分类id不等于null,将属性id和商品分类id插入中间表
                attributeDao.insertattributeClassify(id,attributeBean.getClassifyId());

            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 商品属性查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ComResponse selectPageAttribute(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<AttributeBean> attributeBeanList = null;
        try {
            attributeBeanList = attributeDao.selectPageAttribute();
        } catch (Exception e) {
            return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
        }
        for (int i = 0; i < attributeBeanList.size(); i++) {
            AttributeBean attributeBean = attributeBeanList.get(i);
            List<AttributeValueBean> attributeValueBeanList = attributeDao.selectAttributeValue(attributeBean.getId());
            attributeBean.setAttributeValueBeanList(attributeValueBeanList);
        }
        //将返回内容放入到pageinfo中
        PageInfo page = new PageInfo(attributeBeanList);
        ComResponse<PageInfo> comResponse = ComResponse.success(page);
        return comResponse;
    }




    @Override
    public ComResponse selectByclassifyIdAttribute(Integer id) {
        List<AttributeBean> attributeBeanList = null;
        try {
            attributeBeanList = attributeDao.selectByClassifyId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
        }
        return ComResponse.success(attributeBeanList);
    }


    /**
     * 根据商品属性id查询属性信息
     *
     * @param id
     * @return
     */
    @Override
    public ComResponse selectById(Integer id) {
        AttributeBean attributeBean = null;
        try {
            attributeBean = attributeDao.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
        }
        return ComResponse.success(attributeBean);
    }

    /**
     * 修改商品属性
     *
     * @param attributeBean
     * @return
     */
    @Override
    public ComResponse updateAttribute(AttributeBean attributeBean) {
        if (attributeBean == null) {
            return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
        }

        attributeDao.updateAttribute(attributeBean);

        String values = attributeBean.getValues();
        Integer id = attributeBean.getId();
        List<AttributeValueBean> attributeValueBeanList = splictAttributeValues(values, id);
        //执行修改操作，del_flag=1，条件del_flag=0
        attributeDao.updateAttributeValue(id, 1, 0);
        if (attributeValueBeanList != null) {
            attributeDao.insertAttributeValue(attributeValueBeanList);
        }
        return ComResponse.success();
    }


    /**
     * 添加商品属性值
     *
     * @param attributeValueBeanList
     */
    public void insertAttributeValue(List<AttributeValueBean> attributeValueBeanList) {
        attributeDao.insertAttributeValue(attributeValueBeanList);
    }


    /**
     * 按逗号切割属性值，返回集合
     *
     * @param values
     * @param id
     * @return
     */
    public List<AttributeValueBean> splictAttributeValues(String values, Integer id) {
        if (values == null && !values.isEmpty()) {
            return null;
        }
        List<AttributeValueBean> attributeValueBeanList = new ArrayList<>();
        String[] attributeArr = values.split(",");
        for (String value : attributeArr) {
            AttributeValueBean attributeValueBean = new AttributeValueBean();
            attributeValueBean.setAttributeId(id);
            attributeValueBean.setAvalue(value);
            attributeValueBeanList.add(attributeValueBean);
        }
        return attributeValueBeanList;
    }

}
