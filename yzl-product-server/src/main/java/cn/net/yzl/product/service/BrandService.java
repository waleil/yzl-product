package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.BrandBean;
import cn.net.yzl.product.model.vo.bread.BrandBeanTO;
import cn.net.yzl.product.model.vo.bread.BrandDelVO;
import cn.net.yzl.product.model.vo.bread.BrandVO;
import com.github.pagehelper.PageInfo;

public interface BrandService {
    /**
     * @author lichanghong
     * @description 查询列表
     * @date: 2020/12/31 10:15 下午
     * @param keyWord:  关键词
     * @param pageNo: 页码
     * @param pageSize:每页限制数量
     * @return: null
     */
    ComResponse<PageInfo<BrandBeanTO>> getAllBrands(Integer pageNo, Integer pageSize,String keyWord);

    /**
     * @author lichanghong
     * @description 根据主键查询
     * @date: 2020/12/31 11:01 下午
     * @param id:
     * @return: null
     */
    ComResponse<BrandBean> getBrandById(Integer id);
    /**
     * @author lichanghong
     * @description 修改品牌上下架状态
     * @date: 2020/12/31 11:01 下午
     * @param flag: 状态
     * @param id:主键
     * @return: null
     */
    ComResponse<Void> changeBrandStatus(Integer flag, Integer id);
    /**
     * @author lichanghong
     * @description 新增
     * @date: 2020/12/31 11:01 下午
     * @param brandVo:
     * @return: null
     */
    ComResponse<Void> editBrand(BrandVO brandVo);
    /**
     * @author lichanghong
     * @description 删除品牌，逻辑删除
     * @date: 2020/12/31 10:54 下午
     * @param brandDelVo:删除实体
     * @return: null
     */
    ComResponse<Void> deleteBrandById(BrandDelVO brandDelVo);
}
