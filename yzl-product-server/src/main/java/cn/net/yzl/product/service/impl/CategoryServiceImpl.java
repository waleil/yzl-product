package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.AttributeDao;
import cn.net.yzl.product.dao.CategoryBeanMapper;
import cn.net.yzl.product.dao.ClassifyAttributeBeanMapper;
import cn.net.yzl.product.model.db.AttributeBean;
import cn.net.yzl.product.model.db.CategoryBean;
import cn.net.yzl.product.model.db.CategoryTO;
import cn.net.yzl.product.model.db.ClassifyAttributeBean;
import cn.net.yzl.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryBeanMapper categoryBeanMapper;

    @Autowired
    private ClassifyAttributeBeanMapper classifyAttributeMapper;

    @Autowired
    private AttributeDao attributeDao;

    public ComResponse<CategoryTO> getCategoryById(Integer id) {
        if(null == id){
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
        }else {
            try {
                //找到对应的实体
                CategoryBean categoryBean = categoryBeanMapper.selectByPrimaryKey(id);
                if (null == categoryBean) {
                    return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());
                }
                //创建返回结果集
                CategoryTO categoryTO = new CategoryTO();
                //
                List<AttributeBean> list = attributeDao.selectByClassifyId(id);
                categoryTO.setCategoryBean(categoryBean);
                categoryTO.setAttributeBeans(list);
                return ComResponse.success(categoryTO);
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
        }
    }

    public ComResponse<CategoryBean> saveOrUpdateCategory(CategoryTO categoryTO) {
        if (categoryTO == null) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
        }else {
            //新增
            if (categoryTO.getCategoryBean().getId()==null) {
                try {
                    //获取品牌实例并新增
                    CategoryBean categoryBean = categoryTO.getCategoryBean();
                    categoryBeanMapper.insertSelective(categoryBean);
                    //获取属性列表循环新增
                    List<AttributeBean> attributeBeans = categoryTO.getAttributeBeans();
                    attributeBeans.forEach(attr->{
                        ClassifyAttributeBean classifyAttributeBean = new ClassifyAttributeBean();
                        classifyAttributeBean.setAttributeId(attr.getId());
                        classifyAttributeBean.setClassifyId(categoryBean.getId());
                        classifyAttributeMapper.insertSelective(classifyAttributeBean);
                    });
                    return ComResponse.success();
                } catch (Exception e) {
                    return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
                }
            }else {
                try {
                    classifyAttributeMapper.deleteByCid(categoryTO.categoryBean.getId());
                    categoryTO.getAttributeBeans().forEach(attr->{
                        ClassifyAttributeBean classifyAttributeBean = new ClassifyAttributeBean();
                        classifyAttributeBean.setAttributeId(attr.getId());
                        classifyAttributeBean.setClassifyId(categoryTO.categoryBean.getId());
                        classifyAttributeMapper.insertSelective(classifyAttributeBean);
                    });
                    categoryTO.getCategoryBean().setUpdateTime(new Date());
                    categoryBeanMapper.updateByPrimaryKeySelective(categoryTO.getCategoryBean());
                    return ComResponse.success();
                } catch (Exception e) {
                    return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
                }
            }
        }

    }


    public ComResponse<CategoryBean> deleteCategory(Integer id) {
        if (id == null) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
        }else {
            try {
                categoryBeanMapper.deleteByPrimaryKey(id);
                classifyAttributeMapper.deleteByCid(id);
                return ComResponse.success();
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
        }
    }

    public ComResponse<CategoryBean> chooseCategoryStatus(Integer id, Integer flag) {
        if (flag == null||!(flag==1||flag==0)||id==null) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
        }else {
            try {
                categoryBeanMapper.chooseCategoryStatus(id,flag);
                return ComResponse.success();
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
        }
    }

    public ComResponse<CategoryBean> chooseCategoryAppStatus(Integer id, Integer flag) {
        if (flag == null||!(flag==1||flag==0)||id==null) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
        }else {
            try {
                categoryBeanMapper.chooseCategoryAppStatus(id,flag);
                return ComResponse.success();
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
        }
    }

    public ComResponse<List<CategoryTO>> getCategoryByPid(Integer pid) {
       if (pid == null){
           return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
       }else{
           try {
               List<CategoryBean> categoryBeans = categoryBeanMapper.selectByPid(pid);
               List<CategoryTO> list = categoryBeans.stream().map(categoryBean -> {
                   CategoryTO categoryTO = new CategoryTO();
                   categoryTO.setCategoryBean(categoryBean);
                   categoryTO.setProductCount(categoryBeanMapper.getProductCountByCid(categoryBean.getId()));
                   return categoryTO;
               }).collect(Collectors.toList());
               return ComResponse.success(list);
           } catch (Exception e) {
               return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
           }
       }
    }

    public ComResponse<CategoryBean> transferCategories(Integer sourceId, Integer targetId) {
        if (sourceId == null||targetId == null) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
        }else {
            try {
                categoryBeanMapper.transferCategories(sourceId, targetId);
                return ComResponse.success();
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
        }
    }

    public ComResponse<List<CategoryBean>> selectAll(){
        List<CategoryBean> categoryBeans = categoryBeanMapper.selectAll();
        return ComResponse.success(categoryBeans);
    }

}
