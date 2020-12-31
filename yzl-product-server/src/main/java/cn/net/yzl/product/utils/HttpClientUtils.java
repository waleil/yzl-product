package cn.net.yzl.product.utils;

import cn.net.yzl.common.util.UUIDGenerator;
import cn.net.yzl.logger.annotate.SysAccessLog;
import cn.net.yzl.logger.enums.DefaultDataEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: HttpClientUtils
 * @date: 2020/12/10 6:38 下午
 */
@Slf4j
public class HttpClientUtils {

    private static final String CHARSET = "UTF-8";

    public static String sendGet(String url, Map<String, Object> params, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        // 请求结果
        String resultJson = null;
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            // 封装参数
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key).toString());
                }
            }
            URI uri = builder.build();
            log.info("请求地址：" + uri);
            // 设置请求地址
            httpGet.setURI(uri);
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应数据
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
                log.error("响应失败，状态码：" + status);
            }
        } catch (Exception e) {
            log.error("发送get请求失败", e);
        } finally {
            httpGet.releaseConnection();
        }
        return resultJson;
    }


    @SysAccessLog(logKeyParamName = {"url"},
            source = DefaultDataEnums.Source.THIRD_API,
            action = DefaultDataEnums.Action.QUERY,
            requestMetod = "GET"
    )
    public static String get(String url) {
        // 请求结果
        String resultJson = null;
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet(url);
        try {
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应数据
                resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                log.error("响应失败，状态码：" + status);
            }
        } catch (Exception e) {
            log.error("发送get请求失败", e);
        } finally {
            httpGet.releaseConnection();
        }
        return resultJson;
    }

    /**
     * http post json
     */
    public static String post(String url, String params, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        String cspanid = UUIDGenerator.getUUID();
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                if (key.equals("spanid")) {
                    httpPost.addHeader(key, cspanid);
                }else{
                    httpPost.addHeader(key, headers.get(key));
                }
            }
        }
        StringEntity requestentity = new StringEntity(params, CHARSET);
        httpPost.setEntity(requestentity);
        log.info("链路:traceId:{},spanid:{},cspanid:{},请求操作:{}", headers.get("traceId"), headers.get("spanid"), cspanid, url);
        return send(httpPost);
    }


    /**
     * 执行请求
     *
     * @param base
     * @return
     */
    private static String send(HttpRequestBase base) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(base);
            return outBody(response.getEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            close(httpClient, response);
        }
        return null;
    }

    /**
     * 输出body
     *
     * @param httpEntity
     * @return
     * @throws IOException
     */
    private static String outBody(HttpEntity httpEntity) throws IOException {
        if (httpEntity != null) {
            String body = EntityUtils.toString(httpEntity, CHARSET);
            log.info("返回结果body:{}", body);
            return body;
        }
        return null;
    }


    /**
     * 关闭流
     */
    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

}
