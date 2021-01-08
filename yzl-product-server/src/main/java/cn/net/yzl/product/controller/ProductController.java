package cn.net.yzl.product.controller;


import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.model.vo.product.dto.ProductListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductSelectVO;
import cn.net.yzl.product.model.vo.product.vo.ProductUpdateStatusVO;
import cn.net.yzl.product.model.vo.product.vo.ProductVO;
import cn.net.yzl.product.service.product.ProductService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lichanghong
 */
@RestController
@Api(tags = "商品服务")
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * @param
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/7 10:39 下午
     * @Return: cn.net.yzl.common.entity.ComResponse<java.util.List < cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO>>
     */
    @GetMapping(value = "v1/queryCountByStatus")
    @ApiOperation("按照上下架状态查询商品数量")
    public ComResponse<List<ProductStatusCountDTO>> queryCountByStatus() {
        List<ProductStatusCountDTO> list = productService.queryCountByStatus();
        return ComResponse.success(list);
    }

    @GetMapping(value = "v1/queryPageProduct")
    @ApiOperation("分页查询商品列表")
    public ComResponse<Page<ProductListDTO>> queryListProduct(ProductSelectVO vo) {
        //价格必须成对出现
        if ((vo.getPriceUp() != null && vo.getPriceDown() == null)
                || (vo.getPriceUp() == null && vo.getPriceDown() != null)) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
        }
        if (vo.getPriceUp() != null) {
            vo.setUpPrice((int) (vo.getPriceUp() * 100));
        }
        if (vo.getPriceDown() != null) {
            vo.setDownPrice((int) (vo.getPriceDown() * 100));
        }
        if (vo.getPageNo() == null) {
            vo.setPageNo(1);
        }
        if (vo.getPageSize() == null) {
            vo.setPageSize(15);
        }
        if (vo.getPageSize() > 50) {
            vo.setPageSize(50);
        }
        if (StringUtils.isNotBlank(vo.getKeyword())) {
            String str = vo.getKeyword();
            vo.setKeyword(str.replace("%", "\\%"));
        }
        return productService.queryListProduct(vo);
    }

    /**
     * @param vo
     * @Author: lichanghong
     * @Description: 编辑商品
     * @Date: 2021/1/8 10:45 上午
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @PostMapping(value = "v1/edit")
    @ApiOperation("编辑商品")
    public ComResponse<Void> editProduct(@RequestBody @Valid ProductVO vo) {
        String str=checkParams(vo);
        if(StringUtils.isNotBlank(str)){
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), str);
        }
        return productService.editProduct(vo);
    }

    /**
     * @param vo
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/8 9:46 下午
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @PostMapping(value = "v1/updateStatus")
    @ApiOperation("修改商品上下架状态")
    ComResponse updateStatusByProductCode(@RequestBody @Valid ProductUpdateStatusVO vo) {
        return productService.updateStatusByProductCode(vo);
    }

    /**
     * @param vo
     * @Author: lichanghong
     * @Description: 参数校验
     * @Date: 2021/1/8 11:15 上午
     * @Return: java.lang.String
     */
    public String checkParams(ProductVO vo) {
        if (vo.getCostPriceD() == null) {
            return "市场价价格不能为空";
        }
        if (vo.getSalePriceD() == null) {
            return "市场价价格不能为空";
        }
        if(vo.getUpdateTime()==null){
            return "最后修改时间不能为空!";
        }
        if(vo.getUpdateNo()==null){
            return "编辑员工编码不能为空!";
        }
        return null;
    }
}

