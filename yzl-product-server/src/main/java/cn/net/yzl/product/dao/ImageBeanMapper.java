package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.ImageBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageBeanMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ImageBean record);

    int insertSelective(ImageBean record);

    ImageBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImageBean record);

    int updateByPrimaryKey(ImageBean record);
}