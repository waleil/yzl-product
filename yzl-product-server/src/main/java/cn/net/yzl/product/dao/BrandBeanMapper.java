package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.BrandBean;
import cn.net.yzl.product.model.vo.brand.BrandBeanTO;
import cn.net.yzl.product.model.vo.brand.BrandDelVO;
import cn.net.yzl.product.model.vo.brand.BrandVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BrandBeanMapper {
    /**
     * @author lichanghong
     * @description 根据主键进线逻辑删除
     * @date: 2020/12/31 10:43 下午 
     * @param brandDelVo: 删除实体
     * @return: null 
     */
    int deleteByPrimaryKey(BrandDelVO brandDelVo);

    /**
     * @author lichanghong
     * @description 新增
     * @date: 2020/12/31 11:01 下午
     * @param brandVo:
     * @return: null
     */
    int insertSelective(BrandVO brandVo);
    /**
     * @author lichanghong
     * @description 根据主键查询
     * @date: 2020/12/31 11:01 下午
     * @param id:
     * @return: null
     */
    BrandBean selectByPrimaryKey(@Param("id") Integer id);
    /**
     * @author lichanghong
     * @description 修改
     * @date: 2020/12/31 11:01 下午
     * @param brandVo:
     * @return: null
     */
    int updateByPrimaryKeySelective(BrandVO brandVo);
    /**
     * @author lichanghong
     * @description 条件查询
     * @date: 2020/12/31 11:01 下午
     * @param keyWord:
     * @return: null
     */
    List<BrandBeanTO> selectList(@Param("keyWord")String keyWord);
    /**
     * @author lichanghong
     * @description 根据品牌主键查询关联主键
     * @date: 2020/12/31 11:01 下午
     * @param bid:
     * @return: null
     */
    Integer selectCountByBid(@Param("bid") int bid);
    /**
     * @author lichanghong
     * @description 根据品牌主键查询关联主键
     * @date: 2020/12/31 11:01 下午
     * @param flag: 状态
     * @param id:主键
     * @return: null
     */
    void changeBrandStatus(@Param("flag") int flag, @Param("id") int id);
}