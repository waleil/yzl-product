package cn.net.yzl.product.dao;


import cn.net.yzl.product.model.db.ImageStore;

public interface ImageStoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImageStore record);

    int insertSelective(ImageStore record);

    ImageStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImageStore record);

    int updateByPrimaryKey(ImageStore record);
}