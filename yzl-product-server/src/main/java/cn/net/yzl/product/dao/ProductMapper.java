package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.ProductAtlasBean;
import cn.net.yzl.product.model.pojo.product.Product;
import cn.net.yzl.product.model.pojo.product.ProductDisease;
import cn.net.yzl.product.model.pojo.product.ProductStatus;
import cn.net.yzl.product.model.pojo.product.ProductStockDO;
import cn.net.yzl.product.model.vo.product.dto.ProductDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductDetailVO;
import cn.net.yzl.product.model.vo.product.dto.ProductListDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductSelectVO;
import cn.net.yzl.product.model.vo.product.vo.ProductUpdateStatusVO;
import cn.net.yzl.product.model.vo.product.vo.ProductUpdateTimeVO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {

    int insertSelective(Product product);


    ProductDetailVO selectByProductCode(String productCode);


    int updateByPrimaryKeySelective(Product product);

    /**
     * @Author: lichanghong
     * @Description: 查询最大商品编号
     * @Date: 2021/1/7 3:12 下午
     * @Return: java.util.String
     */
    String queryMaxProductCode();

    List<ProductStatusCountDTO> queryCountByStatus();

    List<ProductListDTO> queryListProduct(ProductSelectVO vo);

    /**
     * @param productCode
     * @Author: lichanghong
     * @Description: 根据主键查询商品状态
     * @Date: 2021/1/8 9:25 下午
     * @Return:
     */
    ProductStatus queryProductStatusByProductCode(String productCode);

    List<ProductAtlasBean> queryProductListAtlas(@Param("productName") String productName, @Param("diseaseId") Integer diseaseId, @Param("diseasePid") Integer  diseasePid);

    /**
     * @param vo
     * @Author: lichanghong
     * @Description: 修改商品上下架状态
     * @Date: 2021/1/8 9:25 下午
     * @Return:
     */
    int updateStatusByProductCode(ProductUpdateStatusVO vo);

    /**
     * @param vo
     * @Author: wanghuasheng
     * @Description: 修改商品售卖时间
     * @Date: 2021/1/9 13:00 下午
     * @Return:
     */
    void updateTimeByProductCode(ProductUpdateTimeVO vo);
    /**
     * @Author: lichanghong
     * @Description:  根据商品编号查询主治病症
     * @Date: 2021/1/10 4:00 下午
     * @param productCode
     * @Return:
     */
    ProductDisease queryDiseaseByProductCode(String productCode);

    Integer queryCountByDiseaseIdAndPID(@Param("diseasePid") Integer diseasePid, @Param("diseaseId") Integer diseaseId);
    /**
     * @Author: lichanghong
     * @Description: 根据病症一级主键查询商品信息
     * @Date: 2021/1/10 6:28 下午
     * @param list
     * @Return:
     */
    List<ProductDTO> queryByDiseasePid(@Param("list") List<Integer> list);
    /**
     * @Author: lichanghong
     * @Description: 根据病症一级主键查询商品信息
     * @Date: 2021/1/10 6:28 下午
     * @param list 主键编号
     * @Return:
     */
    List<ProductDTO> queryByProductCodes(@Param("list") List<String> list);
    /**
     * @Author: lichanghong
     * @Description: 查询商品库存
     * @Date: 2021/1/10 6:28 下午
     * @param list 主键编号
     * @Return:
     */
    List<ProductStockDO> queryStockByCodes(@Param("list") List<String> list);

    List<ProductDTO> selectByDiseaseId(int id);

    List<String> querySaleEndTimeByCodes(@Param("params") Map<String,Object> map);
}