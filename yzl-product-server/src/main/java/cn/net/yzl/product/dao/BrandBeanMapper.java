package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.BrandBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BrandBeanMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(BrandBean record);

    int insertSelective(BrandBean record);

    BrandBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrandBean record);

    int updateByPrimaryKey(BrandBean record);

    List<BrandBean> selectAll();

    Integer selectCountByBid(@Param("bid") Integer bid);

    void changeBrandStatus(@Param("flag") Integer flag, @Param("id") Integer id);
}