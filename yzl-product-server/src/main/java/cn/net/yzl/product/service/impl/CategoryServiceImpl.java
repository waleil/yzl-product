package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.AttributeDao;
import cn.net.yzl.product.dao.CategoryBeanMapper;
import cn.net.yzl.product.dao.ClassifyAttributeBeanMapper;
import cn.net.yzl.product.model.db.AttributeBean;
import cn.net.yzl.product.model.db.Category;
import cn.net.yzl.product.model.vo.category.CategoryTO;
import cn.net.yzl.product.model.vo.category.CategoryVO;
import cn.net.yzl.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                Category category = categoryBeanMapper.selectByPrimaryKey(id);
                if (null == category) {
                    return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());
                }
                //创建返回结果集
                CategoryTO categoryTO = new CategoryTO();
                BeanUtils.copyProperties(category, categoryTO);
                return ComResponse.success(categoryTO);
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
        }
    }
    /**
     * @author lichanghong
     * @description 新增/编辑分类管理
     * @date: 2021/1/5 9:15 上午
     * @param categoryVO:
     * @return: null
     */
    @Override
    public ComResponse<Void> saveOrUpdateCategory(CategoryVO categoryVO) {
        if(categoryVO.getId()!=null){
            categoryBeanMapper.updateByPrimaryKeySelective(categoryVO);
        }else{
            categoryBeanMapper.insertSelective(categoryVO);
        }
        return ComResponse.success();

    }


    public ComResponse<Category> deleteCategory(Integer id) {
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

    public ComResponse<Category> chooseCategoryStatus(Integer id, Integer flag) {
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

    public ComResponse<Category> chooseCategoryAppStatus(Integer id, Integer flag) {
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

    public ComResponse<List<CategoryVO>> getCategoryByPid(Integer pid) {
       if (pid == null){
           return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
       }else{
           try {
               List<Category> categories = categoryBeanMapper.selectByPid(pid);
               List<CategoryVO> list = categories.stream().map(categoryBean -> {
                   CategoryVO categoryVO = new CategoryVO();
                   BeanUtils.copyProperties(categoryBean, categoryVO);
                   return categoryVO;
               }).collect(Collectors.toList());
               return ComResponse.success(list);
           } catch (Exception e) {
               e.printStackTrace();
               return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
           }
       }
    }

    public ComResponse<Category> transferCategories(Integer sourceId, Integer targetId) {
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

    public ComResponse<List<Category>> selectAll(){
        List<Category> categories = categoryBeanMapper.selectAll();
        return ComResponse.success(categories);
    }

}
