package cn.net.yzl.product.utils;

import com.google.common.base.Supplier;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wanghuasheng
 * @version: 1.0
 * @title:
 * @description:
 * @Date: 2021/1/10 4:15 下午
 *
 */
public class BeanCopyUtil extends BeanUtils {

    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            try {
                org.springframework.beans.BeanUtils.copyProperties(source, t);
                list.add(t);
                if (callBack != null) {
                    // 回调
                    callBack.callBack(source, t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @FunctionalInterface
    public interface BeanCopyUtilCallBack <S, T> {

        /**
         * 定义默认回调方法
         * @param t
         * @param s
         */
        void callBack(S t, T s);
    }
}
