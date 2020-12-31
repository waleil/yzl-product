package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.BrandBeanMapper;
import cn.net.yzl.product.dao.ProductBeanMapper;
import cn.net.yzl.product.model.BrandBean;
import cn.net.yzl.product.model.BrandBeanTO;
import cn.net.yzl.product.model.ProductBean;
import cn.net.yzl.product.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandBeanMapper brandBeanMapper;

    @Autowired
    private ProductBeanMapper productBeanMapper;

    public ComResponse<PageInfo<BrandBeanTO>> getAllBrands(Integer pageNo, Integer pageSize) {
        //开启分页
        PageHelper.startPage(pageNo,pageSize);
        //分页查询
        Page<BrandBean> pageInfo = (Page<BrandBean>)brandBeanMapper.selectAll();
        //提取列表数据，遍历并更改数据类型
        List<BrandBean> brandBeans = pageInfo.getResult();
            if(brandBeans==null||brandBeans.size() == 0){
                return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), ResponseCodeEnums.NO_DATA_CODE.getMessage());
            }else {
                List<BrandBeanTO> brandBeanTOS = brandBeans.stream().map(brand->{
                    BrandBeanTO brandBeanTO = new BrandBeanTO();
                    brandBeanTO.setBrandBean(brand);
                    brandBeanTO.setProductCount(brandBeanMapper.selectCountByBid(brand.getId()));
                    return brandBeanTO;
                }).collect(Collectors.toList());
                return ComResponse.success(new PageInfo<BrandBeanTO>(brandBeanTOS));
            }
    }

    public ComResponse<BrandBean> getBrandById(Integer id) {
        BrandBean brandBean = brandBeanMapper.selectByPrimaryKey(id);
        if (brandBean == null) {
            return ComResponse.nodata();
        }
        return ComResponse.success(brandBean);
    }


    public ComResponse<List<ProductBean>> getProductByBid(Integer bid) {
        List<ProductBean> list = productBeanMapper.getProductByBid(bid);
        return ComResponse.success(list);
    }

    public ComResponse<Void> changeBrandStatus(Integer flag, Integer id) {
        brandBeanMapper.changeBrandStatus(flag,id);
        return ComResponse.success();
    }

    public ComResponse<Void> insertBrand(BrandBean brand) {
        brandBeanMapper.insertSelective(brand);
        return ComResponse.success();
    }

    public ComResponse<Void> updateBrand(BrandBean brandBean) {
        brandBeanMapper.updateByPrimaryKeySelective(brandBean);
        return ComResponse.success();
    }


}
