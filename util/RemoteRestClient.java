package com.lenovo.sap.api.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
@Slf4j
@Component
public class RemoteRestClient {
    // 默认最大重试次数
    private static final int DEFAULT_MAX_RETRY_COUNT = 2;
    // 默认重试间隔
    private static final int DEFAULT_RETRY_INTERVAL = 10 * 1000;
    // ip格式正则校验
    private static final String IP_REGEX = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
    // url中https://前缀
    private static final String HTTPS_PREFIX = "https://";

    @Autowired
    private RestTemplate restTemplate;

    public Map request(String jsonData, String url, HttpHeaders headers, HttpMethod httpMethod, boolean needRetry) {
        return request(jsonData, url, headers, httpMethod, Map.class, needRetry);
    }

    public Map request(String jsonData, String url, HttpHeaders headers, HttpMethod httpMethod, final HostnameVerifier hostnameVerifier) {
        return request(jsonData, url, headers, httpMethod, Map.class, false, hostnameVerifier);
    }

    public <T> T request(String jsonData, String url, HttpHeaders headers, HttpMethod httpMethod, Class<T> resultClass, boolean needRetry) {
        return request(jsonData, url, headers, httpMethod, resultClass, needRetry, null);
    }

    private <T> T request(String jsonData, String url, HttpHeaders headers, HttpMethod httpMethod, Class<T> resultClass, boolean needRetry, final HostnameVerifier hostnameVerifier) {
        HttpEntity<String> request;
        ResponseEntity<String> response = null;

        restTemplate.setRequestFactory(getRequestFactory(needRetry, hostnameVerifier));

        log.info("http request 【url】: {} 【body】: {} 【headers】: {}", url, jsonData, headers);
        try {
            if (httpMethod == HttpMethod.GET) {
                request = new HttpEntity<>(null, headers);
                if (StringUtils.isNotBlank(jsonData)) {
                    response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, JacksonUtil.readValue(jsonData, Map.class));
                } else {
                    response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
                }
            } else if (httpMethod == HttpMethod.POST) {
                request = new HttpEntity<>(jsonData, headers);
                response = restTemplate.postForEntity(url, request, String.class);
            } else if (httpMethod == HttpMethod.PUT) {
                request = new HttpEntity<>(jsonData, headers);
                response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            } else if (httpMethod == HttpMethod.DELETE) {
                request = new HttpEntity<>(jsonData, headers);
                response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            }

        } catch (HttpStatusCodeException exception) {
            exception.printStackTrace();
            log.error("http request error 【url】: {} 【body】: {} 【response】: {} 【exception】: {}", url, jsonData, checkResponse(response), exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
            String exceptionResponse = checkResponse(response, exception);
            log.error("http request error 【url】: {} 【body】: {} 【response】: {} 【exception】: {}", url, jsonData, exceptionResponse, exception.getMessage());
            return JacksonUtil.readValue(exceptionResponse, resultClass);
        }
        String body = checkResponse(response);

        log.info("http request response 【url】: {} 【body】: {} 【headers】: {} 【response】: {}", url, jsonData, headers, body);

        return JacksonUtil.readValue(body, resultClass);
    }

    /**
     * 提交文件流
     *
     * @param bytes
     * @param url
     * @param headers
     * @return
     */
    public Map postRequestForFile(byte[] bytes, String url, HttpHeaders headers) {
        log.info("http request upload file 【url】: {} 【headers】: {}", url, headers);

        HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
            String exceptionResponse = checkResponse(response, exception);
            return JacksonUtil.readValue(exceptionResponse, Map.class);
        }

        String body = checkResponse(response);
        return JacksonUtil.readValue(body, Map.class);
    }

    /**
     * post请求，默认没有重试机制
     *
     * @param jsonData api http body
     * @param url      api http url
     * @param headers  api http header
     * @return api response
     */
    public Map postRequest(String jsonData, String url, HttpHeaders headers) {
        if (isHttpsAndIpUrl(url)) {
            // 当是https请求，并且是ip关闭主机名验证:
            return postRequestNoopHostnameVerifier(jsonData, url, headers);
        }
        return request(jsonData, url, headers, HttpMethod.POST, false);
    }

    /**
     * post请求，默认没有重试机制，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map postRequestAddLog(String jsonData, String url, HttpHeaders headers, String businessKey, String activityId) {
        HttpMethod post = HttpMethod.POST;
        Map response = postRequest(jsonData, url, headers);
        addLog(jsonData, url, headers, post, response, businessKey, activityId);
        return response;
    }

    /**
     * post请求，默认没有重试机制，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map postRequestAddLog(String jsonData, String url, HttpHeaders headers, String businessKey, String activityId, String successKey) {
        HttpMethod post = HttpMethod.POST;
        Map response = postRequest(jsonData, url, headers);
        addLog(jsonData, url, headers, post, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * post请求
     *
     * @param jsonData  api http body
     * @param url       api http url
     * @param headers   api http header
     * @param needRetry 是否需要重试
     * @return api response
     */
    public Map postRequest(String jsonData, String url, HttpHeaders headers, boolean needRetry) {
        return request(jsonData, url, headers, HttpMethod.POST, needRetry);
    }

    /**
     * post请求，可重试，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map postRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId) {
        HttpMethod post = HttpMethod.POST;
        Map response = postRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, post, response, businessKey, activityId);
        return response;
    }

    /**
     * post请求，可重试，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map postRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId, String successKey) {
        HttpMethod post = HttpMethod.POST;
        Map response = postRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, post, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * post请求<br/>
     * 关闭主机名验证: 当url使用ip访问而不是域名访问时，由于颁发证书原因，需要关闭主机名验证，否则会报错javax.net.ssl.SSLPeerUnverifiedException: Certificate for <IP> doesn't match any of the subject alternative names: [domain]
     *
     * @param jsonData api http body
     * @param url      api http url
     * @param headers  api http header
     * @return api response
     */
    private Map postRequestNoopHostnameVerifier(String jsonData, String url, HttpHeaders headers) {
        return request(jsonData, url, headers, HttpMethod.POST, new NoopHostnameVerifier());
    }

    /**
     * put请求，默认没有重试机制
     *
     * @param jsonData api http body
     * @param url      api http url
     * @param headers  api http header
     * @return api response
     */
    public Map putRequest(String jsonData, String url, HttpHeaders headers) {
        return request(jsonData, url, headers, HttpMethod.PUT, false);
    }

    /**
     * put请求，默认没有重试机制，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map putRequestAddLog(String jsonData, String url, HttpHeaders headers, String businessKey, String activityId) {
        HttpMethod put = HttpMethod.PUT;
        Map response = putRequest(jsonData, url, headers);
        addLog(jsonData, url, headers, put, response, businessKey, activityId);
        return response;
    }

    /**
     * put请求，默认没有重试机制，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map putRequestAddLog(String jsonData, String url, HttpHeaders headers, String businessKey, String activityId, String successKey) {
        HttpMethod put = HttpMethod.PUT;
        Map response = putRequest(jsonData, url, headers);
        addLog(jsonData, url, headers, put, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * put请求
     *
     * @param jsonData  api http body
     * @param url       api http url
     * @param headers   api http header
     * @param needRetry 是否需要重试
     * @return api response
     */
    public Map putRequest(String jsonData, String url, HttpHeaders headers, boolean needRetry) {
        return request(jsonData, url, headers, HttpMethod.PUT, needRetry);
    }

    /**
     * put请求，可重试，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map putRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId) {
        HttpMethod put = HttpMethod.PUT;
        Map response = putRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, put, response, businessKey, activityId);
        return response;
    }

    /**
     * put请求，可重试，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map putRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId, String successKey) {
        HttpMethod put = HttpMethod.PUT;
        Map response = putRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, put, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * http DELETE request calling
     *
     * @param jsonData api http body
     * @param url      api http url
     * @param headers  api http header
     * @return api response
     */
    public Map deleteRequest(String jsonData, String url, HttpHeaders headers) {
        return request(jsonData, url, headers, HttpMethod.DELETE, false);
    }

    /**
     * delete请求，默认没有重试机制，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map deleteRequestAddLog(String jsonData, String url, HttpHeaders headers, String businessKey, String activityId) {
        HttpMethod delete = HttpMethod.DELETE;
        Map response = deleteRequest(jsonData, url, headers);
        addLog(jsonData, url, headers, delete, response, businessKey, activityId);
        return response;
    }

    /**
     * delete请求，默认没有重试机制，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map deleteRequestAddLog(String jsonData, String url, HttpHeaders headers, String businessKey, String activityId, String successKey) {
        HttpMethod delete = HttpMethod.DELETE;
        Map response = deleteRequest(jsonData, url, headers);
        addLog(jsonData, url, headers, delete, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * delete请求
     *
     * @param jsonData  api http body
     * @param url       api http url
     * @param headers   api http header
     * @param needRetry 是否需要重试
     * @return api response
     */
    public Map deleteRequest(String jsonData, String url, HttpHeaders headers, boolean needRetry) {
        return request(jsonData, url, headers, HttpMethod.DELETE, needRetry);
    }

    /**
     * delete请求，可重试，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map deleteRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId) {
        HttpMethod delete = HttpMethod.DELETE;
        Map response = deleteRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, delete, response, businessKey, activityId);
        return response;
    }

    /**
     * delete请求，可重试，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key和错误信息key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map deleteRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId, String successKey) {
        HttpMethod delete = HttpMethod.DELETE;
        Map response = deleteRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, delete, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * Get请求
     *
     * @param url     api http url
     * @param headers api http header
     * @return api response
     */
    @SuppressWarnings("unchecked")
    public <T> T getRequest(Map jsonData, String url, HttpHeaders headers, Class<T> resultClass) {
        if (jsonData != null && !jsonData.isEmpty()) {
            return request(JacksonUtil.toJSon(jsonData), url, headers, HttpMethod.GET, resultClass, false);
        }
        return request(null, url, headers, HttpMethod.GET, resultClass, false);
    }

    /**
     * get 请求
     *
     * @param jsonData api http body
     * @param url      api http url
     * @param headers  api http header
     * @return api response
     */
    public Map getRequest(Map jsonData, String url, HttpHeaders headers) {
        if (isHttpsAndIpUrl(url)) {
            // 当是https请求，并且是ip关闭主机名验证:
            if (jsonData != null && !jsonData.isEmpty()) {
                return getRequestNoopHostnameVerifier(JacksonUtil.toJSon(jsonData), url, headers);
            } else {
                return getRequestNoopHostnameVerifier(null, url, headers);
            }
        }
        return getRequest(jsonData, url, headers, Map.class);
    }

    /**
     * get请求，默认没有重试机制，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map getRequestAddLog(Map jsonData, String url, HttpHeaders headers, String businessKey, String activityId) {
        HttpMethod get = HttpMethod.GET;
        Map response = getRequest(jsonData, url, headers);
        addLog(JacksonUtil.toJSon(jsonData), url, headers, get, response, businessKey, activityId);
        return response;
    }

    /**
     * get请求，默认没有重试机制，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map getRequestAddLog(Map jsonData, String url, HttpHeaders headers, String businessKey, String activityId, String successKey) {
        HttpMethod get = HttpMethod.GET;
        Map response = getRequest(jsonData, url, headers);
        addLog(JacksonUtil.toJSon(jsonData), url, headers, get, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * get请求
     *
     * @param jsonData  api http body
     * @param url       api http url
     * @param headers   api http header
     * @param needRetry 是否需要重试
     * @return api response
     */
    public Map getRequest(String jsonData, String url, HttpHeaders headers, boolean needRetry) {
        return request(jsonData, url, headers, HttpMethod.GET, needRetry);
    }

    /**
     * get请求，可重试，并且增加返回失败的日志记录
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @return api response
     */
    public Map getRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId) {
        HttpMethod get = HttpMethod.GET;
        Map response = getRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, get, response, businessKey, activityId);
        return response;
    }

    /**
     * get请求，可重试，并且增加返回失败的日志记录，可自定义api响应json中的成功/失败标志key
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param needRetry   是否需要重试
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  http响应的map中成功/失败标志的key
     * @return api response
     */
    public Map getRequestAddLog(String jsonData, String url, HttpHeaders headers, boolean needRetry, String businessKey, String activityId, String successKey) {
        HttpMethod get = HttpMethod.GET;
        Map response = getRequest(jsonData, url, headers, needRetry);
        addLog(jsonData, url, headers, get, response, businessKey, activityId, successKey);
        return response;
    }

    /**
     * get请求<br/>
     * 关闭主机名验证: 当url使用ip访问而不是域名访问时，由于颁发证书原因，需要关闭主机名验证，否则会报错javax.net.ssl.SSLPeerUnverifiedException: Certificate for <IP> doesn't match any of the subject alternative names: [domain]
     *
     * @param jsonData api http body
     * @param url      api http url
     * @param headers  api http header
     * @return api response
     */
    private Map getRequestNoopHostnameVerifier(String jsonData, String url, HttpHeaders headers) {
        return request(jsonData, url, headers, HttpMethod.GET, new NoopHostnameVerifier());
    }

    /**
     * 使用 获取HttpComponentsClientHttpRequestFactory可忽略证书等信息
     * 默认没有重试机制，没有hostname验证
     *
     * @return
     */
    private HttpComponentsClientHttpRequestFactory getRequestFactory() {
        return getRequestFactory(false, null);
    }

    /**
     * 使用 获取HttpComponentsClientHttpRequestFactory可忽略证书等信息
     *
     * @param needRetry        是否需要重试，true: 重试，默认重试2次，重试间隔10秒
     * @param hostnameVerifier 配置主机验证
     * @return HttpComponentsClientHttpRequestFactory
     */
    private HttpComponentsClientHttpRequestFactory getRequestFactory(boolean needRetry, final HostnameVerifier hostnameVerifier) {
        HttpComponentsClientHttpRequestFactory requestFactory = null;
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = null;
            if (hostnameVerifier != null) {
                // 添加hostname验证
                csf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            } else {
                csf = new SSLConnectionSocketFactory(sslContext);
            }

            // 构建httpclient
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(csf);
            if (needRetry) {
                // 重试机制
                httpClientBuilder.setRetryHandler(getHttpRequestRetryHandler(DEFAULT_MAX_RETRY_COUNT, DEFAULT_RETRY_INTERVAL));
            }
            CloseableHttpClient httpClient = httpClientBuilder.build();

            requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Ignore SSL Exception, {}", e.getMessage());
        }
        return requestFactory;
    }

    /**
     * HttpRequestRetryHandler实现
     *
     * @param maxRetryCount 最大重试次数
     * @param retryInterval 重试间隔
     * @return HttpRequestRetryHandler
     */
    private HttpRequestRetryHandler getHttpRequestRetryHandler(int maxRetryCount, int retryInterval) {
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                Args.notNull(exception, "Exception parameter");
                Args.notNull(context, "HTTP context");
                if (executionCount > maxRetryCount) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof UnknownHostException || exception instanceof SSLException) {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    retryInterval();
                    return true;
                }
                final HttpClientContext clientContext = HttpClientContext.adapt(context);
                final HttpRequest request = clientContext.getRequest();

                HttpRequest req = request;
                if (request instanceof RequestWrapper) { // does not forward request to original
                    req = ((RequestWrapper) request).getOriginal();
                }
                if (req instanceof HttpUriRequest && ((HttpUriRequest) req).isAborted()) {
                    return false;
                }
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    // Retry if the request is considered idempotent
                    retryInterval();
                    return true;
                }

                if (!clientContext.isRequestSent()) {
                    // Retry if the request has not been sent fully or
                    // if it's OK to retry methods that have been sent
                    retryInterval();
                    return true;
                }

                // otherwise do not retry
                return false;
            }

            private void retryInterval() {
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    public Map postStRequest(String jsonData, String url, HttpHeaders headers) {
        // 构建POST form表单请求
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate().postForEntity(url, request, String.class);
        } catch (Exception exception) {
            log.error("An exception is found while accessing", exception);
            return null;
        }
        String body = checkResponse(response);
        log.debug("build form request:{}  send to :{} receive the response:{}", jsonData, url, body);
        return new Gson().fromJson(body, Map.class);

    }

    public Map getStRequest(Map params, String url, HttpHeaders headers) {
        // 构建请求
        ResponseEntity<String> response = null;
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        try {
            if (params != null) {
                response = restTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class, params);
            } else {
                response = restTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);

            }
        } catch (Exception exception) {
            log.error("An exception is found while accessing", exception);
            return null;
        }
        String body = checkResponse(response);

        return new Gson().fromJson(body, Map.class);
    }

    /**
     * 获取response  header
     *
     * @param params
     * @param url     api http url
     * @param headers api http header
     * @return
     */
    public HttpHeaders getRequestHeader(Map params, String url, HttpHeaders headers) {
        // 构建请求
        ResponseEntity<String> response = null;
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        try {
            if (params != null && !params.isEmpty()) {
                response = restTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class, params);
            } else {
                response = restTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);

            }
        } catch (Exception exception) {
            log.error("An exception is found while accessing", exception);
            return null;
        }
        return response.getHeaders();
    }

    public <T> ResponseEntity<T> getResponseEntity(String url, HttpMethod method, Object body, HttpHeaders headers, Class<T> responseType) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // 构建请求
//        log.info("http request 【url】: {} 【body】: {} 【headers】: {}", url, JacksonUtils.toJsonString(body), headers);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<T> exchange = restTemplate().exchange(url, method, httpEntity, responseType);
//        log.info("http request response 【url】: {} 【body】: {} 【headers】: {} 【response】: {}", url, JacksonUtils.toJsonString(body), headers, JacksonUtils.toJsonString(body));
        return exchange;
    }

    private String checkResponse(HttpEntity<String> response) {
        return checkResponse(response, null);
    }

    private String checkResponse(HttpEntity<String> response, Exception exception) {
        String body = null;
        if (response != null) {
            body = response.getBody();
        } else {
            log.error("The response is empty!");
        }
        return body;
    }

    /**
     * 此处可能需要补全授权信息，因此抽取出来，方便后面统一处理
     *
     * @return http header
     */
    public HttpHeaders getHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        requestHeaders.setContentType(mediaType);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return requestHeaders;
    }

    /**
     * 判断url的格式
     *
     * @param url url
     * @return true：当url为https请求，并且是用ip请求时
     * false：其他情况
     */
    private boolean isHttpsAndIpUrl(String url) {
        if (url.startsWith(HTTPS_PREFIX)) {
            // 去掉https://前缀后，从string左侧开始匹配是否是ip请求
            String subUrl = url.substring(HTTPS_PREFIX.length());
            Pattern pattern = Pattern.compile(IP_REGEX);
            Matcher matcher = pattern.matcher(subUrl);
            return matcher.lookingAt();
        }
        return false;
    }

    /**
     * 增加api调用失败时的日志，默认successKey为 success
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param httpMethod  api http method
     * @param response    api http response
     * @param businessKey 单号
     * @param activityId  流程节点id
     */
    private void addLog(String jsonData, String url, HttpHeaders headers, HttpMethod httpMethod, Map response, String businessKey, String activityId) {
        addLog(jsonData, url, headers, httpMethod, response, businessKey, activityId, "success");
    }

    /**
     * 增加api调用失败时的日志
     *
     * @param jsonData    api http body
     * @param url         api http url
     * @param headers     api http header
     * @param httpMethod  api http method
     * @param response    api http response
     * @param businessKey 单号
     * @param activityId  流程节点id
     * @param successKey  response中成功/失败标志的key
     */
    private void addLog(String jsonData, String url, HttpHeaders headers, HttpMethod httpMethod, Map response, String businessKey, String activityId, String successKey) {

    }
}
