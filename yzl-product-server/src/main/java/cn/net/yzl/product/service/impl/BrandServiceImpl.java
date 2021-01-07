package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.dao.BrandBeanMapper;
import cn.net.yzl.product.model.db.BrandBean;
import cn.net.yzl.product.model.vo.brand.BrandBeanTO;
import cn.net.yzl.product.model.vo.brand.BrandDelVO;
import cn.net.yzl.product.model.vo.brand.BrandVO;
import cn.net.yzl.product.service.BrandService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandBeanMapper brandBeanMapper;
    /**
     * @author lichanghong
     * @description 查询列表
     * @date: 2020/12/31 10:15 下午
     * @param keyWord:  关键词
     * @param pageNo: 页码
     * @param pageSize:每页限制数量
     * @return: null
     */
    @Override
    public ComResponse<Page<BrandBeanTO>> getAllBrands(Integer pageNo, Integer pageSize, String keyWord) {
        //开启分页
        PageHelper.startPage(pageNo,pageSize);
        //分页查询
        Page<BrandBeanTO> pageInfo = AssemblerResultUtil.resultAssembler(brandBeanMapper.selectList(keyWord));
//       if(!CollectionUtils.isEmpty(list)){
//           list.stream().forEach(brandBeanTO -> {
//               brandBeanTO.setProductCount(brandBeanMapper.selectCountByBid(brandBeanTO.getId()));
//           });
//       }
        //提取列表数据，遍历并更改数据类型
        return ComResponse.success(pageInfo);
    }
    /**
     * @author lichanghong
     * @description 根据主键查询
     * @date: 2020/12/31 11:01 下午
     * @param id:
     * @return: null
     */
    @Override
    public ComResponse<BrandBean> getBrandById(Integer id) {
        BrandBean brandBean = brandBeanMapper.selectByPrimaryKey(id);
        if (brandBean == null) {
            return ComResponse.nodata();
        }
        return ComResponse.success(brandBean);
    }
    /**
     * @author lichanghong
     * @description 修改品牌上下架状态
     * @date: 2020/12/31 11:01 下午
     * @param flag: 状态
     * @param id:主键
     * @return: null
     */
    @Override
    public ComResponse<Void> changeBrandStatus(Integer flag, Integer id) {
        brandBeanMapper.changeBrandStatus(flag,id);
        return ComResponse.success();
    }
    /**
     * @author lichanghong
     * @description 编辑品牌
     * @date: 2020/12/31 11:01 下午
     * @param brandVo: 实体
     * @return: null
     */
    @Override
    public ComResponse<Void> editBrand(BrandVO brandVo) {
        Boolean flag = this.checkUnique(brandVo.getName(), brandVo.getId()==null?0:brandVo.getId()).getData();
        if (flag){
            if(brandVo.getId()!=null&&brandVo.getId()>0){
                brandBeanMapper.updateByPrimaryKeySelective(brandVo);
            }else{
                brandBeanMapper.insertSelective(brandVo);
            }
        }else {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE,"品牌名称存在重复！");
        }
        return ComResponse.success();
    }
    /**
     * @author lichanghong
     * @description 根据主键进线逻辑删除
     * @date: 2020/12/31 10:43 下午
     * @param brandDelVo: 删除
     * @return: null
     */
    @Override
    public ComResponse<Void> deleteBrandById(BrandDelVO brandDelVo) {
        if(brandBeanMapper.selectCountByBid(brandDelVo.getId())>0){
            return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(),"存在关联商品，无法删除!");
        }
        brandBeanMapper.deleteByPrimaryKey(brandDelVo);
        return ComResponse.success();
    }

    
    /**
     * @description 
     * @author Majinbao
     * @date 2021/1/7 9:05
     * @param name, type
     * @return ComResponse<Boolean>
     */
    @Override
    public ComResponse<Boolean> checkUnique(String name, int id) {
        Integer i = brandBeanMapper.selectByName(name);
        if (i == null) {
            return ComResponse.success(true);
        }
        if(i.equals(id)){
            return ComResponse.success(true);
        }
        return ComResponse.success(false);
    }


}
