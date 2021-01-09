package cn.net.yzl.product.service.meal.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.dao.MealMapper;
import cn.net.yzl.product.dao.MealProductMapper;
import cn.net.yzl.product.model.vo.product.dto.ProductMealDTO;
import cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO;
import cn.net.yzl.product.model.vo.product.vo.ProductMealVO;
import cn.net.yzl.product.service.meal.ProductMealService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wanghuahseng
 * @version 1.0
 * @title: ProductMealServiceImpl
 * @description 商品套餐服务实现类
 * @date: 2021/1/7 10:00 下午
 */
@Service
@Slf4j
public class ProductMealServiceImpl implements ProductMealService {
    @Autowired
    private MealMapper mealMapper;
    @Autowired
    private MealProductMapper mealProductMapper;

    /**
     * @Author: wanghuasheng
     * @Description: 按照上下架查询商品套餐数量
     * @Date: 2021/1/9 09:25 上午
     * @Return: java.util.List<cn.net.yzl.product.model.vo.product.dto.ProductStatusCountDTO>
     */
    @Override
    public List<ProductStatusCountDTO> queryCountByStatus() {
        return mealMapper.queryCountByStatus();
    }

    @Override
    public ComResponse<Page<ProductMealDTO>> queryListProductMeal(ProductMealVO vo) {
        //开启分页
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<ProductMealDTO> list = mealMapper.queryListProductMeal(vo);
        for (ProductMealDTO p : list) {
            //List<ProductMealLinkDTO> pList =  productMealLinkMapper.queryProductMealLink(p.getMealNo());
        }
        //分页查询
        Page<ProductMealDTO> pageInfo = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(pageInfo);
    }

    @Override
    public ComResponse<ProductMealDTO> queryProductMealPortray(Integer mealNo) {
        try {
            ProductMealDTO productMealDTO = mealMapper.queryProductMealPortray(mealNo);
            return ComResponse.success(productMealDTO);
        } catch (Exception ex) {
            log.error("查询套餐画像信息失败,", ex);
        }
        return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "查询套餐画像信息失败");
    }

}