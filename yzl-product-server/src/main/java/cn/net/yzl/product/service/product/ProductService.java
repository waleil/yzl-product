package cn.net.yzl.product.service.product;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.product.model.pojo.product.ProductDisease;
import cn.net.yzl.product.model.vo.product.dto.*;
import cn.net.yzl.product.model.vo.product.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductDao
 * @date: 2021/1/7 9:34 下午
 */
public interface ProductService {
    /**
     * @Author: lichanghong
     * @Description: 查询上下架商品总数
     * @Date: 2021/1/7 9:59 下午
     * @Return:
     */
    List<ProductStatusCountDTO> queryCountByStatus();
    /**
     * @Author: lichanghong
     * @Description: CRM查询商品列表
     * @Date: 2021/1/10 11:33 上午
     * @Return:
     */
    ComResponse<Page<ProductListDTO>> queryListProduct(ProductSelectVO vo);
    /**
     * @Author: lichanghong
     * @Description: 提供给其他服务查询商品列表
     * @Date: 2021/1/7 9:59 下午
     * @Return:
     */
    ComResponse<Page<ProductListDTO>> queryProducts(ProductSelectVO vo);
    /**
     * @Author: lichanghong
     * @Description: 编辑商品
     * @Date: 2021/1/7 9:59 下午
     * @Return:
     */
     ComResponse editProduct(ProductVO vo);
    /**
     * @Author: lichanghong
     * @Description: 修改商品上下架状态
     * @Date: 2021/1/8 9:25 下午
     * @param vo
     * @Return:
     */
    ComResponse updateStatusByProductCode(ProductUpdateStatusVO vo);

    /**
     * 查询商品图谱
     * 商品图谱
     * @param productName 商品名称(模糊查询)
     * @param id 病症id
     * @return
     */
    ComResponse<List<ProductAtlasDTO>> queryProductListAtlas(String productName, Integer id,Integer pid);

    /**
     * @Author: wanghuasheng
     * @Description: 修改商品售卖时间
     * @Date: 2021/1/9 13:00 下午
     * @param vo
     * @Return:
     */
    ComResponse updateTimeByProductCode(ProductUpdateTimeVO vo);

    /**
     * @Author: wanghuasheng
     * @Description: 查询商品详情信息
     * @Date: 2021/1/9 13:00 下午
     * @param productCode
     * @Return:
     */
    ComResponse<ProductDetailVO> queryProductDetail(String productCode);
    /**
     * @Author: lichanghong
     * @Description: 查询商品画像
     * @Date: 2021/1/10 12:29 下午
     * @param productCode 商品编号
     * @Return: cn.net.yzl.product.model.vo.product.dto.ProductPortraitDTO
     */
    ProductPortraitDTO queryProductPortrait(String productCode);

    /**
     * @Author: lichanghong
     * @Description: 根据商品编号查询病症
     * @Date: 2021/1/10 4:03 下午
     * @param productCode
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductDiseaseDTO>
     */
    List<ProductDiseaseDTO> queryDiseaseByProductCode(String productCode);
    /**
     * @Author: lichanghong
     * @Description: 根据病症一级主键查询商品信息
     * @Date: 2021/1/10 6:28 下午
     * @param list 主键编号
     * @Return:
     */
    List<ProductDTO> queryByProductCodes(List<String> list);
    /**
     * @Author: lichanghong
     * @Description: 扣减库存
     * @Date: 2021/1/11 11:30 下午
     * @param orderProductVO
     * @Return:
     */
    ComResponse productReduce(OrderProductVO orderProductVO);
    /**
     * @Author: lichanghong
     * @Description:    增加库存
     * @Date: 2021/1/11 11:30 下午
     * @param orderProductVO
     * @Return:
     */
    ComResponse increaseStock(OrderProductVO orderProductVO);


}
