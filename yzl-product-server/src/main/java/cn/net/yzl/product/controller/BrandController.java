package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.BrandBean;
import cn.net.yzl.product.model.BrandBeanTO;
import cn.net.yzl.product.model.ProductBean;
import cn.net.yzl.product.service.BrandService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: BrandController
 * @description todo
 * @date: 2020/12/31 9:08 上午
 */
@RestController
@RequestMapping("productServer")
@Api(tags = "商品品牌管理", description = "包含：发放、锁定、使用、撤销等操作")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("getBrandById")
    public ComResponse<BrandBean> getBrandById(@RequestParam("id") Integer id) {
        return brandService.getBrandById(id);
    }

    @GetMapping("getProductByBid")
    public ComResponse<List<ProductBean>> getProductByBid(@RequestParam("bid") Integer bid) {
        return brandService.getProductByBid(bid);
    }

    @PutMapping("changeBrandStatus")
    public ComResponse<Void> changeBrandStatus(@RequestParam("flag") Integer flag, @RequestParam("id") Integer id) {
        return brandService.changeBrandStatus(flag, id);
    }

    @PostMapping("insertBrand")
    public ComResponse<Void> insertBrand(@RequestBody BrandBean brand) {
        return brandService.insertBrand(brand);
    }

    @PutMapping("updateBrand")
    public ComResponse<Void> updateBrand(@RequestBody BrandBean brandBean) {
        return brandService.updateBrand(brandBean);
    }
    @GetMapping("getAllBrands")
    public ComResponse<PageInfo<BrandBeanTO>> getAllBrands(Integer pageNo, Integer pageSize) {
        return brandService.getAllBrands(pageNo,pageSize);
    }
}
