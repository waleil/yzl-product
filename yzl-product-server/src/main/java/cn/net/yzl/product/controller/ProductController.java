package cn.net.yzl.product.controller;


import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.model.vo.product.dto.*;
import cn.net.yzl.product.model.vo.product.vo.ProductSelectVO;
import cn.net.yzl.product.model.vo.product.vo.ProductUpdateStatusVO;
import cn.net.yzl.product.model.vo.product.vo.ProductUpdateTimeVO;
import cn.net.yzl.product.model.vo.product.vo.ProductVO;
import cn.net.yzl.product.service.product.ProductService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
        if (!StringUtils.isEmpty(vo.getKeyword())) {
            String str = vo.getKeyword();
            vo.setKeyword(str.replace("%", "\\%"));
        }
        return productService.queryListProduct(vo);
    }

    @GetMapping(value = "v1/queryProducts")
    @ApiOperation("提供给其他服务查询商品列表")
    public ComResponse<Page<ProductListDTO>> queryProducts(ProductSelectVO vo) {
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
        if (!StringUtils.isEmpty(vo.getKeyword())) {
            String str = vo.getKeyword();
            vo.setKeyword(str.replace("%", "\\%"));
        }
        return productService.queryProducts(vo);
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
        String str = checkParams(vo);
        if (!StringUtils.isEmpty(str)) {
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
        if (CollectionUtils.isEmpty(vo.getProductCodeList())) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), "商品code不能为空");
        }
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
        if (vo.getUpdateTime() == null) {
            return "最后修改时间不能为空!";
        }
        if (vo.getUpdateNo() == null) {
            return "编辑员工编码不能为空!";
        }
        if (vo.getDiseaseId() != null) {
            if (vo.getDiseasePid() == null) {
                return "主治病症上级编号不能为空!";
            }
        }
        return null;
    }


    @ApiOperation("查询商品图谱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "商品名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "病症id", dataType = "Int", paramType = "query"),
            @ApiImplicitParam(name = "pid", value = "病症pid", dataType = "Int", paramType = "query")
    })
    @GetMapping("v1/queryProductListAtlas")
    public ComResponse<List<ProductAtlasDTO>> queryProductListAtlas(@RequestParam(value = "productName",required = false) String productName, @RequestParam(value = "id",required = false) Integer id,@RequestParam(value = "pid",required = false) Integer pid) {
        return productService.queryProductListAtlas(productName, id,pid);
    }

    /**
     * @param vo
     * @Author: wanghuasheng
     * @Description: 修改商品售卖时间
     * @Date: 2021/1/9 13:00 下午
     * @Return: cn.net.yzl.common.entity.ComResponse
     */
    @PostMapping(value = "v1/updateTime")
    @ApiOperation("修改商品售卖时间")
    ComResponse updateTimeByProductCode(@RequestBody @Valid ProductUpdateTimeVO vo, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                sb.append(error.getDefaultMessage() + ",");
            }
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), sb.toString());
        }
        if (CollectionUtils.isEmpty(vo.getProductCodeList())) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_EMPTY_ERROR_CODE.getCode(), "商品code不能为空");
        }
        if (vo.getSaleEndTime().compareTo(vo.getSaleStartTime()) < 0) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), "日期错误");
        }
        return productService.updateTimeByProductCode(vo);
    }

    @GetMapping(value = "v1/queryProductDetail")
    @ApiOperation("查询商品详情")
    public ComResponse<ProductDetailVO> queryProductDetail(@RequestParam("productCode") String productCode) {
        return productService.queryProductDetail(productCode);
    }

    @GetMapping(value = "v1/queryProductPortrait")
    @ApiOperation("查询商品画像")
    public ComResponse<ProductPortraitDTO> queryProductPortrait(@RequestParam("productCode") String productCode) {
        return ComResponse.success(productService.queryProductPortrait(productCode));
    }

    /**
     * @param productCode
     * @Author: lichanghong
     * @Description: 根据商品编号查询病症
     * @Date: 2021/1/10 4:03 下午
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductDiseaseDTO>
     */
    @GetMapping(value = "v1/queryDiseaseByProductCode")
    @ApiOperation("根据商品编号查询病症")
    public ComResponse<List<ProductDiseaseDTO>> queryDiseaseByProductCode(@RequestParam("productCode") String productCode) {
        List<ProductDiseaseDTO> diseaseDTOS = productService.queryDiseaseByProductCode(productCode);
        return ComResponse.success(diseaseDTOS);
    }
    /**
     * @param codes
     * @Author: lichanghong
     * @Description: 根据商品编号查询病症
     * @Date: 2021/1/10 4:03 下午
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductDiseaseDTO>
     */
    @GetMapping(value = "v1/queryByCodes")
    @ApiOperation("根据商品编号查询病症")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codes", paramType="query",required = true,value = "商品code，以逗号分隔"),
    })
    public ComResponse<List<ProductDTO>> queryByProductCodes(@RequestParam("codes") String codes){
        if(StringUtils.isEmpty(codes)){
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(),ResponseCodeEnums.PARAMS_ERROR_CODE.getMessage());
        }
        List<String> list = new ArrayList<>(StringUtils.commaDelimitedListToSet(codes));
        return ComResponse.success(productService.queryByProductCodes(list));
    }
}

