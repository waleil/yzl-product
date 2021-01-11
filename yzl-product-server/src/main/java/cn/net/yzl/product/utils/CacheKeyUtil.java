package cn.net.yzl.product.utils;

/**
 * @author lichanghong
 * @version 1.0
 * @title: CacheKeyUtil
 * @description 缓存生成key
 * @date: 2021/1/7 3:20 下午
 */
public class CacheKeyUtil {
    final static String serverName ="product";
    /**
     * @Author: lichanghong
     * @Description: 生成最大缓存key
     * @Date: 2021/1/7 3:24 下午
     * @param
     * @Return: java.lang.String
     */
    public static String maxProductCacheKey(){

        return new StringBuilder(serverName).append("-").append("maxProductCode").toString();
    }
    /**
     * @Author: lichanghong
     * @Description: 生成品类字典表自增主键
     * @Date: 2021/1/7 3:24 下午
     * @param
     * @Return: java.lang.String
     */
    public static String maxCategoryCacheKey(){
        return new StringBuilder(serverName).append("-").append("maxCategoryId").toString();
    }
    /**
     * @Author: lichanghong
     * @Description: 生成病症字典表自增主键
     * @Date: 2021/1/7 3:24 下午
     * @param
     * @Return: java.lang.String
     */
    public static String maxDiseaseCacheKey(){
        return new StringBuilder(serverName).append("-").append("maxDiseaseId").toString();
    }
    /**
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/9 9:30 下午
     * @param id 病症主键
     * @Return: java.lang.String
     */
    public static String diseaseCacheKey(Integer id,Integer pid){
        return new StringBuilder(serverName).append("-")
                .append("disease:").append(id).append("-").append(pid).toString();
    }
    /**
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/9 9:30 下午
     * @param id 病症主键
     * @Return: java.lang.String
     */
    public static String categoryCacheKey(Integer id){
        return new StringBuilder(serverName).append("-")
                .append("category:").append(id).toString();
    }

    /**
     * @Author: wanghuasheng
     * @Description: 生成商品套餐自增主键
     * @Date: 2021/1/10 3:10 下午
     * @param
     * @Return: java.lang.String
     */
    public static String maxMealCacheKey(){
        return new StringBuilder(serverName).append("-").append("maxMealNo").toString();
    }
}
