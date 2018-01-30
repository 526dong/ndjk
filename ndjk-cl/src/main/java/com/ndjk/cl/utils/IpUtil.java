package com.ndjk.cl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: wl
 * @Description: ip获取工具类
 * @Date: 2018/1/30  17:36
 * @Version: 2.0
 *
 */
public class IpUtil {

    public static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	/*private static final int IPV6Length = 8; // IPV6地址的分段  
    private static final int IPV4Length = 4; // IPV6地址分段  
    private static final int IPV4ParmLength = 2; // 一个IPV4分段占的长度  
    private static final int IPV6ParmLength = 4; // 一个IPV6分段占的长 
	 */

    @Resource
    private WebServiceContext wsContext;

    /**
     * 获取请求IP
     *
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        //这里主要是获取本机的ip,可有可无
        if ("127.0.0.1".equals(ip) || ip.endsWith("0:0:0:0:0:0:1")) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                logger.error(e.getMessage(), e);
            }
            if (inet != null) {
                ip = inet.getHostAddress();
            }
            return ip;
        }
        if (ip.length() > 0) {
            String[] ipArray = ip.split(",");
            if (ipArray != null && ipArray.length > 1) {
                return ipArray[0];
            }
            return ip;
        }

        return "";
    }

    /**
     * @Description:获取外网ip
     * @Author:xiang.li
     * @Date:4:46 2017/9/4
     */
    public static String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
            //System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
        }
        return ip;
    }

}
