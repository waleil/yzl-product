package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.Category;
import cn.net.yzl.product.model.vo.category.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryBeanMapper {

    int deleteByPrimaryKey(CategoryDelVO vo);
    /**
     * @author lichanghong
     * @description 新增分类
     * @date: 2021/1/5 2:26 下午
     * @param record:
     * @return: null
     */
    int insertSelective(CategoryVO record);
    /**
     * @author lichanghong
     * @description 根据主键查询分类
     * @date: 2021/1/5 2:26 下午
     * @param id:
     * @return: null
     */
    Category selectByPrimaryKey(Integer id);
    /**
     * @author lichanghong
     * @description 修改分类
     * @date: 2021/1/5 2:27 下午
     * @param record:
     * @return: null
     */
    int updateByPrimaryKeySelective(CategoryVO record);
    /**
     * @author lichanghong
     * @description 根据主键查询子分类
     * @date: 2021/1/5 2:27 下午
     * @param pid:
     * @return: null
     */
    List<Category> selectByPid(@Param("pid") Integer pid);
    /**
     * @author lichanghong
     * @description 根据主键查询子分类
     * @date: 2021/1/5 2:27 下午
     * @param pid:
     * @return: null
     */
    List<CategoryTO> queryPageById(@Param("pid") Integer pid);
    /**
     * @author lichanghong
     * @description 修改分类状态
     * @date: 2021/1/5 2:28 下午
     * @param vo:
     * @return: null
     */
    void chooseCategoryStatus(CategoryChangeStatusVO vo);
    /**
     * @author lichanghong
     * @description 修改分类移动端展示
     * @date: 2021/1/5 2:28 下午
     * @param vo:
     * @return: null
     */
    void chooseCategoryAppStatus(CategoryChangeStatusVO vo);

    /**
     * @author lichanghong
     * @description 下拉列表查询
     * @date: 2021/1/5 6:09 下午
     * @param pid:
     * @return: null
     */
    List<CategorySelectTO> query4SelectOption(@Param("pid") Integer pid);
    /**
     * @Author: lichanghong
     * @Description: 查询最大主键
     * @Date: 2021/1/7 6:40 下午
     * @Return: Integer
     */
    int queryMaxId();

    cn.net.yzl.product.model.pojo.category.Category queryById(@Param("id") Integer id);
}