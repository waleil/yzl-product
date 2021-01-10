package cn.net.yzl.product.init;

import cn.net.yzl.product.context.BeanUtils;
import cn.net.yzl.product.dao.CategoryBeanMapper;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.dao.MealMapper;
import cn.net.yzl.product.dao.ProductMapper;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author lichanghong
 * @version 1.0
 * @title: InitEnv
 * @description 启动初始化加载
 * @date: 2021/1/7 3:14 下午
 */
@Component
@Slf4j
public class InitEnv implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("启动加载...");
        initProductCode();
        initDiseaseMax();
        initCategoryMax();
        initMealCode();
    }

    /**
     * @Author: lichanghong
     * @Description: 初始化商品编号
     * @Date: 2021/1/7 3:28 下午
     * @Return: void
     */
    private void initProductCode() {
        ProductMapper productMapper = BeanUtils.getBean(ProductMapper.class);
        String maxProductCodeStr = productMapper.queryMaxProductCode();
        int maxProductCode = maxProductCodeStr == null ? 0 : Integer.parseInt(maxProductCodeStr);
        //先从redis中获取缓存的数据
        RedisUtil redisUtil = BeanUtils.getBean(RedisUtil.class);
        String cacheKey = CacheKeyUtil.maxProductCacheKey();
        String maxCode = redisUtil.getStr(cacheKey);
        //判断是否从redis中获取到数据
        if (StringUtils.isNotEmpty(maxCode)) {
            //从redis中获取到数据，判断它与数据库中获取的编号大小
            int redisCode = Integer.parseInt(maxCode);
            if (redisCode < maxProductCode) {
                redisUtil.set(cacheKey, redisCode);
            }
        } else {
            //从redis中没有获取到值
            if (maxProductCode < 10000000) {
                maxProductCode = 10000000;
            }
            redisUtil.set(cacheKey, maxProductCode);
        }
    }

    /**
     * @Author: lichanghong
     * @Description: 初始化病症最大编号
     * @Date: 2021/1/7 6:52 下午
     * @Return:
     */
    private void initDiseaseMax() {
        DiseaseBeanMapper diseaseBeanMapper = BeanUtils.getBean(DiseaseBeanMapper.class);
        int maxId = diseaseBeanMapper.queryMaxId();
        RedisUtil redisUtil = BeanUtils.getBean(RedisUtil.class);
        String cacheKey = CacheKeyUtil.maxDiseaseCacheKey();
        String maxCode = redisUtil.getStr(cacheKey);
        if (maxId > 0 && StringUtils.isNotBlank(maxCode)) {
            if (maxId > Integer.parseInt(maxCode)) {
                redisUtil.set(cacheKey, maxId);
            }
        } else {
            redisUtil.set(cacheKey, maxId);
        }

    }
    /**
     * @Author: lichanghong
     * @Description: 初始化病症最大编号
     * @Date: 2021/1/7 6:52 下午
     * @Return:
     */
    private void initCategoryMax() {
        CategoryBeanMapper categoryBeanMapper = BeanUtils.getBean(CategoryBeanMapper.class);
        int maxId = categoryBeanMapper.queryMaxId();
        RedisUtil redisUtil = BeanUtils.getBean(RedisUtil.class);
        String cacheKey = CacheKeyUtil.maxCategoryCacheKey();
        String maxCode = redisUtil.getStr(cacheKey);
        if (maxId > 0 && StringUtils.isNotBlank(maxCode)) {
            if (maxId > Integer.parseInt(maxCode)) {
                redisUtil.set(cacheKey, maxId);
            }
        } else {
            redisUtil.set(cacheKey, maxId);
        }

    }

    /**
     * @Author: wanghuasheng
     * @Description: 初始化套餐编号
     * @Date: 2021/1/7 6:52 下午
     * @Return:
     */
    private void initMealCode() {
        MealMapper mealMapper = BeanUtils.getBean(MealMapper.class);
        int maxId = mealMapper.queryMaxId();
        RedisUtil redisUtil = BeanUtils.getBean(RedisUtil.class);
        String cacheKey = CacheKeyUtil.maxMealCacheKey();
        String maxCode = redisUtil.getStr(cacheKey);
        if (maxId > 0 && StringUtils.isNotBlank(maxCode)) {
            if (maxId > Integer.parseInt(maxCode)) {
                redisUtil.set(cacheKey, maxId);
            }
        } else {
            redisUtil.set(cacheKey, maxId);
        }

    }
}
