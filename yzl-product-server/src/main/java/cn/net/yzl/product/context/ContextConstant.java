package cn.net.yzl.product.context;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: ContextConstant
 * @Author wanghuashegn
 * @Date: 2020/12/16 18:32 下午
 * @Version 1.0
 */
public class ContextConstant {
    public static Map<String, String> HEADERS = new ConcurrentHashMap<>(10);

    public static Map<String, String> getHeaders() {
        return new HashMap<>(HEADERS);
    }

    public static Map<String, String> setHeaders(Map<String, String> header) {
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                HEADERS.put(key, header.get(key));
            }
        }
        return HEADERS;
    }
}
