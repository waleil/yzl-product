package cn.net.yzl.product.service.meal;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.pojo.product.Meal;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductMealListDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.*;
import cn.net.yzl.product.model.vo.product.vo.ProductMealVO;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: ProductService
 * @description: 商品套餐服务接口
 * @date: 2021/1/9 9:20 上午
 */
public interface ProductMealService {
    /**
     * @Author: wanghuasheng
     * @Description: 查询上下架商品套餐总数
     * @Date: 2021/1/9 9:20 上午
     * @Return:
     */
    List<ProductStatusCountDTO> queryCountByStatus();

    ComResponse<ProductMealDTO> queryProductMealPortray(Integer mealNo);

    /**
     * @Description: 修改套餐上下架状态
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:18 下午
     * @param vo:
     * @return: cn.net.yzl.common.entity.ComResponse
     **/

    ComResponse updateStatusByMealCode(ProductMealUpdateStatusVO vo);

    /**
     * @Description: 修改套餐售卖时间
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:26
     * @param vo :
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    ComResponse updateTimeByMealCode(ProductMealUpdateTimeVO vo);


    /**
     * @Description: 编辑套餐
     * @Author: dongjunmei
     * @Date: 2021-01-09 13:48
     * @param vo:
     * @return: cn.net.yzl.common.entity.ComResponse<java.lang.Void>
     **/
    ComResponse editProductMeal(ProductMealVO vo);


    /**
     * @Description: 查看套餐
     * @Author: dongjunmei
     * @Date: 2021-01-09 15:41
     * @param:
     * @return: cn.net.yzl.common.entity.ComResponse
     **/
    ComResponse<ProductMealDetailVO> queryMealDetail(Meal meal);

    /**
     * @Author: wanghuasheng
     * @Description: 查询商品套餐列表
     * @Date: 2021/1/10 12:07 上午
     * @Return:
     */
    ComResponse<Page<ProductMealListDTO>> queryProductMealList(ProductMealSelectVO vo);
}