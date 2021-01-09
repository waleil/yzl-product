package cn.net.yzl.product.service.meal;

import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;

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

}