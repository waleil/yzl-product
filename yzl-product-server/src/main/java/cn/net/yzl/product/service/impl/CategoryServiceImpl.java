package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.common.util.JsonUtil;
import cn.net.yzl.product.dao.CategoryBeanMapper;
import cn.net.yzl.product.model.db.Category;
import cn.net.yzl.product.model.pojo.disease.Disease;
import cn.net.yzl.product.model.vo.category.*;
import cn.net.yzl.product.service.CategoryService;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    //缓存时间，12个小时
    private static long CACHE_TIME = 60 * 60 * 12;
    //空字符串，表示数据库中不存在
    private static String NULL_STR = "null";
    @Autowired
    private CategoryBeanMapper categoryBeanMapper;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public ComResponse<Category> getCategoryById(Integer id) {
        if(null == id){
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
        }else {
            try {
                //找到对应的实体
                Category category = categoryBeanMapper.selectByPrimaryKey(id);
                if (null == category) {
                    return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());
                }
                return ComResponse.success(category);
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
        if(categoryVO.getId()!=null&&categoryVO.getId()>0){
            categoryBeanMapper.updateByPrimaryKeySelective(categoryVO);
        }else{
            String cacheKey = CacheKeyUtil.maxCategoryCacheKey();
            long maxId= redisUtil.incr(cacheKey,1);
            categoryVO.setId(Integer.parseInt(String.valueOf(maxId)));
            categoryBeanMapper.insertSelective(categoryVO);
        }
        return ComResponse.success();

    }

    @Override
    public ComResponse<Void> deleteCategory(CategoryDelVO vo) {
            try {
                deleteCache(vo.getId());
                categoryBeanMapper.deleteByPrimaryKey(vo);
                return ComResponse.success();
            } catch (Exception e) {
                return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
            }
    }
    @Override
    public ComResponse<Void> chooseCategoryStatus(CategoryChangeStatusVO vo) {
        try {
            categoryBeanMapper.chooseCategoryStatus(vo);
            return ComResponse.success();
        }catch (Exception ex){
        log.error("修改分类状态失败,",ex);
        return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
        }

    }
    @Override
    public ComResponse<Void> chooseCategoryAppStatus(CategoryChangeStatusVO vo) {
        try {
            categoryBeanMapper.chooseCategoryAppStatus(vo);
            return ComResponse.success();
        }catch (Exception ex){
            log.error("修改分类APP显示状态失败,",ex);
            return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
        }
    }
    @Override
    public ComResponse<List<Category>> getCategoryByPid(Integer pid) {
       if (pid == null){
           return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getMessage());
       }else{
           try {
               List<Category> categories = categoryBeanMapper.selectByPid(pid);
               // TODO: 2021/1/5 缺少查询商品数量
               return ComResponse.success(categories);
           } catch (Exception e) {
               return ComResponse.fail(ResponseCodeEnums.ERROR.getCode(), ResponseCodeEnums.ERROR.getMessage());
           }
       }
    }

    @Override
    public ComResponse<Page<CategoryTO>> queryPageByPid(Integer pid, Integer pageNo, Integer pageSize) {
        //开启分页
        PageHelper.startPage(pageNo,pageSize);
        List<CategoryTO> list =categoryBeanMapper.queryPageById(pid);
        // TODO: 2021/1/5 缺少查询商品数量
        // 说明查询是的是一级分类
        if(pid==null && pid==0){

        }else{

        }
        //分页查询
        Page<CategoryTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
    }

    @Override
    public ComResponse<List<CategorySelectTO>> query4SelectOption(Integer pid) {
        return ComResponse.success(categoryBeanMapper.query4SelectOption(pid));
    }

    @Override
    public cn.net.yzl.product.model.pojo.category.Category queryById(Integer id) {

        //先从缓存中查询
        String cacheKey = CacheKeyUtil.categoryCacheKey(id);
        String result = redisUtil.getStr(cacheKey);
        if (StringUtils.hasText(result)) {
            if (!NULL_STR.equals(result)) {
                return JsonUtil.getObjectFromJSONString(result, cn.net.yzl.product.model.pojo.category.Category.class);
            } else {
                return null;
            }
        } else {
            cn.net.yzl.product.model.pojo.category.Category category = categoryBeanMapper.queryById(id);
            if (category != null) {
                redisUtil.set(cacheKey,JsonUtil.toJsonStr(category), CACHE_TIME);
            } else {
                //缓存5分钟
                redisUtil.set(cacheKey, NULL_STR, 300);
            }
            return category;
        }
    }
    /**
     * @Author: lichanghong
     * @Description: 删除缓存数据
     * @Date: 2021/1/9 10:20 下午
     * @param id    主键
     * @Return: void
     */
    private void deleteCache(Integer id){
        String cacheKey = CacheKeyUtil.categoryCacheKey(id);
        redisUtil.del(cacheKey);
    }
}
