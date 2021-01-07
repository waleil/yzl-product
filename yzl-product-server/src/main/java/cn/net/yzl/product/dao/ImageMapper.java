package cn.net.yzl.product.dao;


import cn.net.yzl.product.model.db.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);

    List<Image> selectByStoreId(@Param("id") Integer id);
}