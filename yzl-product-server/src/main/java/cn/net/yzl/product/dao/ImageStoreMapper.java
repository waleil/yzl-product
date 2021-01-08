package cn.net.yzl.product.dao;


import cn.net.yzl.product.model.db.ImageStore;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreDTO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageStoreMapper {
    int deleteByPrimaryKey(@Param("id") Integer id,@Param("userId") String userId);

    int insertSelective(ImageStoreVO record);

    ImageStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImageStore record);

    Integer selectTypeById(Integer id);

    List<ImageStoreDTO> selectByType(Integer type);
}