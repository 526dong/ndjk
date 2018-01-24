package com.ndjk.cl.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ndjk.cl.http.entity.HttpUrlParam;
import com.ndjk.cl.http.enumeration.HttpContentTypeEnum;
import com.ndjk.cl.http.enumeration.HttpHeaderEnum;
import com.ndjk.cl.http.enumeration.HttpUrlExceptionTypeEnum;
import com.ndjk.cl.http.exception.HttpUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.management.OperationsException;
import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author Allen
 * 尚未添加上传和下载 证书等功能
 */

/**
 * 使用http协议进行请求
 */
public class HttpUrlUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUrlUtil.class);

    private static final String DEFAULT_ACCEPT = "*/*";
    private static final String DEFAULT_KEEP_ALIVE = "Keep-Alive";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private HttpUrlUtil() {
    }

    /**
     * @param httpUrlParam url相关参数
     * @param params       请求基本参数
     * @param byteData     文件参数
     * @Author maochuang.li
     * @Date Create in 10:58 2017/12/7 0007
     */
    public static String httpRequestFileUpload(HttpUrlParam httpUrlParam, Map<String, String> params, HashMap<String, byte[]> byteData) throws HttpUrlException {
        HttpURLConnection httpConnection;
        try {
            httpConnection = getHttpURLConnection(httpUrlParam);
            //证书https或略
            if (httpConnection instanceof HttpsURLConnection) {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
                ((HttpsURLConnection) httpConnection).setSSLSocketFactory(sc.getSocketFactory());
                ((HttpsURLConnection) httpConnection).setHostnameVerifier(new TrustAnyHostnameVerifier());
            }
            Random random = new Random();
            String BOUNDARY = "---------------------------" + random.nextLong();
            httpConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(httpConnection.getOutputStream());
            // 封装基本参数信息
            if (params != null) {
                StringBuffer strBuf = new StringBuffer();
                Set<Map.Entry<String, String>> entries = params.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null)
                        continue;
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            //contentType非空采用filename匹配默认的图片类型
            StringBuffer strBuf = new StringBuffer();
            Set<Map.Entry<String, byte[]>> entries = byteData.entrySet();
            for (Map.Entry<String, byte[]> entry : entries) {
                String filename = "image" + System.currentTimeMillis() + ".jpg";
                strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                strBuf.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + filename + "\"\r\n");
                strBuf.append("Content-Type: application/octet-stream" + "\r\n\r\n");
                out.write(strBuf.toString().getBytes());
                out.write(entry.getValue());
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            //获取 数据响应结果
            String result;
            try {
                try {
                    result = transferInputStreamToString(httpConnection.getInputStream(), httpUrlParam.getEncode());
                } catch (Exception e) {
                    log.error(HttpUrlExceptionTypeEnum.READ_FAIL.getDesc());
                    result = transferInputStreamToString(httpConnection.getErrorStream(), httpUrlParam.getEncode());
                }
            } catch (Exception e) {
                log.error(HttpUrlExceptionTypeEnum.READ_FAIL.getDesc(), e);
                throw new HttpUrlException(HttpUrlExceptionTypeEnum.READ_FAIL);
            }
            return result;
        } catch (Exception e) {
            log.error(HttpUrlExceptionTypeEnum.INIT_FAIL.getDesc(), e);
            throw new HttpUrlException(HttpUrlExceptionTypeEnum.INIT_FAIL);
        }
    }

    //https证书忽略wl20170617
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    /**
     * 进行http发送内容
     *
     * @param httpUrlParam 请求参数
     * @param message      发送内容
     * @return 从流中取出的String
     */
    public static String httpRequest(HttpUrlParam httpUrlParam, String message) throws HttpUrlException {
        HttpURLConnection httpConnection;
        try {
            httpConnection = getHttpURLConnection(httpUrlParam);
        } catch (Exception e) {
            log.error(HttpUrlExceptionTypeEnum.INIT_FAIL.getDesc(), e);
            throw new HttpUrlException(HttpUrlExceptionTypeEnum.INIT_FAIL);
        }

        try {
            writeToResponse(httpUrlParam, message, httpConnection);
        } catch (Exception e) {
            log.error(HttpUrlExceptionTypeEnum.CONNECT_FAIL.getDesc(), e);
            throw new HttpUrlException(HttpUrlExceptionTypeEnum.CONNECT_FAIL);
        }

        String result;
        try {
            result = transferInputStreamToString(httpConnection.getInputStream(), httpUrlParam.getEncode());
        } catch (Exception e) {
            if(e instanceof SocketTimeoutException){
                log.error(HttpUrlExceptionTypeEnum.READ_TIMEOUT.getDesc(),e);
                throw new HttpUrlException(HttpUrlExceptionTypeEnum.READ_TIMEOUT);
            }
            log.error(HttpUrlExceptionTypeEnum.READ_FAIL.getDesc(),e);
            throw new HttpUrlException(HttpUrlExceptionTypeEnum.READ_FAIL);
        }
        return result;
    }

    /**
     * 进行http发送内容 以form形式
     *
     * @param httpUrlParam 请求参数
     * @param keyValueForm 发送集合
     * @return 从流中取出的String
     */
    public static String httpRequestWithForm(HttpUrlParam httpUrlParam, Map keyValueForm) throws HttpUrlException {
        httpUrlParam.setHttpContentType(HttpContentTypeEnum.FORM);
        StringBuilder message = new StringBuilder();
        Set keySet = keyValueForm.keySet();
        Iterator it = keySet.iterator();
        try {
            while (it.hasNext()) {
                String key = (String) it.next();
                message.append(key + "=" + URLEncoder.encode(keyValueForm.get(key).toString(), httpUrlParam.getEncode()) + "&");
            }
        } catch (Exception e) {
            log.error(HttpUrlExceptionTypeEnum.ASSEMBLE_PARAM_FAIL.getDesc(), e);
            throw new HttpUrlException(HttpUrlExceptionTypeEnum.ASSEMBLE_PARAM_FAIL);
        }
        if (message.length() != 0)
            message.deleteCharAt(message.length() - 1);
        return httpRequest(httpUrlParam, message + "");
    }

    /**
     * 进行http发送内容 以form形式
     *
     * @param httpUrlParam 请求参数
     * @param object       发送实体
     * @return 从流中取出的String
     */
    public static String httpRequestWithForm(HttpUrlParam httpUrlParam, Object object) throws HttpUrlException {
        Map keyValueForm = ReflectUtil.getFieldValues(object);
        return httpRequestWithForm(httpUrlParam, keyValueForm);
    }

    /**
     * 进行http发送内容 以json形式
     *
     * @param httpUrlParam 请求参数
     * @param keyValueForm 发送集合
     * @return 从流中取出的String
     */
    public static String httpRequestWithJson(HttpUrlParam httpUrlParam, Map keyValueForm) throws HttpUrlException {
        httpUrlParam.setHttpContentType(HttpContentTypeEnum.JSON);
        Gson gson = new GsonBuilder().setDateFormat(DEFAULT_DATE_FORMAT).create();
        String message = gson.toJson(keyValueForm);
        return httpRequest(httpUrlParam, message);
    }

    /**
     * 进行http发送内容 以json形式
     *
     * @param httpUrlParam 请求参数
     * @param object       发送实体
     * @return 从流中取出的String
     */
    public static String httpRequestWithJson(HttpUrlParam httpUrlParam, Object object) throws HttpUrlException {
        httpUrlParam.setHttpContentType(HttpContentTypeEnum.JSON);
        Gson gson = new GsonBuilder().setDateFormat(DEFAULT_DATE_FORMAT).create();
        String message = gson.toJson(object);
        return httpRequest(httpUrlParam, message);
    }

    /**
     * @param httpUrlParam 请求参数
     * @return 从流中取出的String
     * @throws HttpUrlException
     */
    public static String httpRequest(HttpUrlParam httpUrlParam) throws HttpUrlException {
        return httpRequest(httpUrlParam, "");
    }

    private static void writeToResponse(HttpUrlParam httpUrlParam, String message, HttpURLConnection httpConnection) throws IOException {
        OutputStream output = httpConnection.getOutputStream();
        output.write((message == null ? "" : message).getBytes(httpUrlParam.getEncode()));
        output.flush();
        output.close();
    }

    private static HttpURLConnection getHttpURLConnection(HttpUrlParam httpUrlParam) throws Exception {
        HttpURLConnection httpConnection;
        httpConnection = (HttpURLConnection) new URL(httpUrlParam.getUrl()).openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        if (httpUrlParam.getHttpMethod() != null)
            httpConnection.setRequestMethod(httpUrlParam.getHttpMethod().getKey());
        if (httpUrlParam.getReadTimeout() != null)
            httpConnection.setReadTimeout(httpUrlParam.getReadTimeout() * 1000);
        if (httpUrlParam.getConnectTimeout() != null)
            httpConnection.setConnectTimeout(httpUrlParam.getConnectTimeout() * 1000);
        httpConnection.setRequestProperty(HttpHeaderEnum.CONTENT_TYPE.getKey(), httpUrlParam.getHttpContentType().getKey());
        httpConnection.setRequestProperty(HttpHeaderEnum.ACCEPT_CHARSET.getKey(), httpUrlParam.getEncode());
        httpConnection.setRequestProperty(HttpHeaderEnum.ACCEPT.getKey(), DEFAULT_ACCEPT);
        httpConnection.setRequestProperty(HttpHeaderEnum.CONNECTION.getKey(), DEFAULT_KEEP_ALIVE);
        if (httpUrlParam.getHeaders() != null && httpUrlParam.getHeaders().size() != 0) {
            Iterator<String> it = httpUrlParam.getHeaders().keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                httpConnection.setRequestProperty(key, httpUrlParam.getHeaders().get(key));
            }
        }
        httpConnection=skipHttps(httpConnection,httpUrlParam.getUrl());
        return httpConnection;
    }

    private static HttpURLConnection skipHttps(HttpURLConnection httpConnection,String url)throws Exception{
        boolean isHttps=url.startsWith("https");
        if(!isHttps)
            return httpConnection;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        ((HttpsURLConnection) httpConnection).setSSLSocketFactory(sc.getSocketFactory());
        ((HttpsURLConnection) httpConnection).setHostnameVerifier(new TrustAnyHostnameVerifier());
        return httpConnection;
    }

    public static String transferInputStreamToString(InputStream inStream, String encode) throws OperationsException {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            outStream.close();
            return new String(outStream.toByteArray(), encode);
        } catch (Exception e) {
            log.info("transferInputStreamToString", e);
            throw new OperationsException();
        }
    }
}
