/**
 * COPYRIGHT 北京微梦创新科技发展有限公司 2016
 * chetu - XSSHttpRequestWrapper.java
 * 2016年9月20日
 * seven
 */
package com.ndjk.cl.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * @author seven
 *
 */
public class XSSHttpRequestWrapper extends HttpServletRequestWrapper {

    private static Logger log = Logger.getLogger(XSSHttpRequestWrapper.class);

    private static String[][] FilterChars = { { "-", "" }, { "<", "《" },
            { ">", "》" }, { " ", "" }, { "'", "" }, { "\r", " " },
            { "\n", " " }, { "\r\n", " " }, { "''", "" }, { "&", "" },
            { "/", "" }, { "\\", "/" }, { "\n", "<br>" } };

    private static String[][] FilterScriptChars = { { "\n", "'+'\\n'+'" },
            { "\r", " " }, { "\\", "'+'\\\\'+'" }, { "'", "'+'\\''+'" } };

    public XSSHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);

        return value;
    }

    public String getParameter(String name) {
        String value = super.getParameter(name);

        if ((XSSSecurityConfig.REPLACE)
                && (!XSSSecurityManager.isNullStr(value))) {
            return stringFilter(value);
        }
        return value;
    }

    private boolean checkHeader() {
        Enumeration headerParams = getHeaderNames();
        while (headerParams.hasMoreElements()) {
            String headerName = (String) headerParams.nextElement();
            String headerValue = getHeader(headerName);
            if (XSSSecurityManager.matches(headerValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkParameter() {
        Map submitParams = getParameterMap();
        Set<String> submitNames = submitParams.keySet();
        for (String submitName : submitNames) {
            String[] submitValues = (String[]) submitParams.get(submitName);
            for (String submitValue : submitValues) {
                try {
                    log.info(submitName + ":" + submitValue + "----"
                            + XSSSecurityManager.matches(submitValue));
                    if (XSSSecurityManager.matches(submitValue))
                        return true;
                } catch (Exception e) {
                    log.info("地址解码异常:" + submitValue);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 没有违规的数据，就返回false; 若存在违规数据，根据配置信息判断是否跳转到错误页面
     * 
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public boolean validateParameter(HttpServletResponse response)
            throws ServletException, IOException {
        if ((XSSSecurityConfig.IS_CHECK_HEADER) && (checkHeader())) {
            return true;
        }

        if ((XSSSecurityConfig.IS_CHECK_PARAMETER) && (checkParameter())) {
            return true;
        }

        return false;
    }

    public static String stringConnect(String[] strings, String spilit_sign) {
        String str = "";
        for (int i = 0; i < strings.length; i++) {
            str = str + strings[i] + spilit_sign;
        }
        return str;
    }

    public static String stringFilter(String str) {
        String temp = StringUtils.replace(str, "%27", "");
        temp = StringUtils.replace(temp, "*", "");
        temp = StringUtils.replace(temp, "\"", "&quot;");
        temp = StringUtils.replace(temp, "'", "");
        temp = StringUtils.replace(temp, "\\\"", "");
        temp = StringUtils.replace(temp, ";", "");
        temp = StringUtils.replace(temp, "<", "&lt;");
        temp = StringUtils.replace(temp, ">", "&gt;");
        temp = StringUtils.replace(temp, "(", "");
        temp = StringUtils.replace(temp, ")", "");
        temp = StringUtils.replace(temp, "{", "");
        temp = StringUtils.replace(temp, "}", "");
        return temp.trim();
    }

    public static String stringFilterScriptChar(String str) {
        String[] str_arr = stringSpilit(str, "");
        for (int i = 0; i < str_arr.length; i++) {
            for (int j = 0; j < FilterScriptChars.length; j++) {
                if (FilterScriptChars[j][0].equals(str_arr[i]))
                    str_arr[i] = FilterScriptChars[j][1];
            }
        }
        return stringConnect(str_arr, "").trim();
    }

    public static String[] stringSpilit(String str, String spilit_sign) {
        String[] spilit_string = str.split(spilit_sign);
        if (spilit_string[0].equals("")) {
            String[] new_string = new String[spilit_string.length - 1];
            for (int i = 1; i < spilit_string.length; i++)
                new_string[(i - 1)] = spilit_string[i];
            return new_string;
        }

        return spilit_string;
    }

    public static void main(String[] args) {
        System.out
                .println(stringFilter("'sjhdfkj;lqwhj82dkjs%6982%^&28121//sdh\\%"));
    }
}
