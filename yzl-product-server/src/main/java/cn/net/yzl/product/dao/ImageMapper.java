package cn.net.yzl.product.dao;


import cn.net.yzl.product.model.db.Image;
import cn.net.yzl.product.model.vo.image.ImageDTO;
import cn.net.yzl.product.model.vo.image.ImageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ImageVO record);

    Image selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Image record);

    List<ImageDTO> selectByStoreId(@Param("id") Integer id,@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    int selectQuoteById(Integer id);
}