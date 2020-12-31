package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.BrandBean;
import cn.net.yzl.product.model.BrandBeanTO;
import cn.net.yzl.product.model.ProductBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    ComResponse<PageInfo<BrandBeanTO>> getAllBrands(Integer pageNo, Integer pageSize);

    ComResponse<BrandBean> getBrandById(Integer id);

    ComResponse<List<ProductBean>> getProductByBid(Integer bid);

    ComResponse<Void> changeBrandStatus(Integer flag, Integer id);

    ComResponse<Void> insertBrand(BrandBean brand);

    ComResponse<Void> updateBrand(BrandBean brandBean);

}
