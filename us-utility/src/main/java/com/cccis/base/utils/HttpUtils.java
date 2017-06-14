package com.cccis.base.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.cccis.base.common.BaseConstant;

/**
 * HTTP请求工具类.
 */
public class HttpUtils {

    private final static Logger LOG = Logger.getLogger(HttpUtils.class);

    private final static int HTTP_TIMEOUT = ConstantUtils.getValue(30000);

    private final static String ENCODING = BaseConstant.ENCODING;

    /**
     * A Common http Get sender to handler the URL request. The URL should be with parameters if necessary.
     *
     * @param url url
     * @return String result
     */
    public static String httpGet(final String url) {
        String responseText = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);
            final CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            httpGet.setConfig(getRequestConfig());
            response = httpclient.execute(httpGet);
            final HttpEntity entity = response.getEntity();
            responseText = EntityUtils.toString(entity, ENCODING);
        } catch (final Exception e) {
            LOG.error("Can not send GET message to " + url);
        } finally {
            try {
                httpGet.releaseConnection();
                response.close();
            } catch (final Exception e) {
                LOG.error("Close response error within " + url + " , " + e.getMessage());
            }
        }
        return responseText;
    }

    /**
     * A Common http POST sender to handler the URL parameter. The URL should be with parameters if necessary.
     *
     * @param url       url
     * @param jsonParam param
     * @return String result
     */
    public static String httpPost(final String url, final String jsonParam) {
        String responseText = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            final CloseableHttpClient httpclient = HttpClientBuilder.create().build();

            httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(jsonParam, ENCODING));
            httpPost.setConfig(getRequestConfig());

            response = httpclient.execute(httpPost);
            final HttpEntity entity = response.getEntity();
            responseText = EntityUtils.toString(entity, ENCODING);
            httpPost.releaseConnection();
        } catch (final Exception e) {
            LOG.error("Can not send POST message to " + url + " , " + e.getMessage());
        } finally {
            try {
                httpPost.releaseConnection();
                response.close();
            } catch (final Exception e) {
                LOG.error("Close response error within " + url + " , " + e.getMessage());
            }
        }
        return responseText;
    }

    /**
     * A http post function by the url and post params.
     *
     * @param url       url
     * @param paramsMap param
     * @return String result
     */
    public static String httpPost(final String url, final Map<String, String> paramsMap) {
        final CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        final HttpPost httpPost = new HttpPost(url);
        try {
            if (paramsMap != null) {
                final List<NameValuePair> paramList = Lists.newArrayList();
                for (final Map.Entry<String, String> param : paramsMap.entrySet()) {
                    final NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                httpPost.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            httpPost.setConfig(getRequestConfig());
            response = client.execute(httpPost);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (final Exception e) {
            LOG.error("Can not send POST message to " + url + " , " + e.getMessage());
        } finally {
            try {
                httpPost.releaseConnection();
                response.close();
            } catch (final Exception e) {
                LOG.error("Close response error within " + url + " , " + e.getMessage());
            }
        }
        return responseText;
    }

    /**
     * Http timeout config setting.
     *
     * @return RequestConfig
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(HTTP_TIMEOUT).setConnectTimeout(HTTP_TIMEOUT).setSocketTimeout(HTTP_TIMEOUT).build();
    }
}
