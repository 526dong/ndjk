/**
 * COPYRIGHT 北京微梦创新科技发展有限公司 2016
 * chetu - XSSSecurityConfig.java
 * 2016年9月20日
 * seven
 */
package com.ndjk.cl.filter;

/**
 * @author seven
 *
 */
public class XSSSecurityConfig {

    public static boolean IS_CHECK_HEADER = false;

    public static boolean IS_CHECK_PARAMETER = true;

    public static boolean IS_LOG = true;

    public static boolean IS_CHAIN = true;

    public static boolean REPLACE = true;

    public static boolean IS_FILTER_REFERER = false;

    public static String REPLACEMENT = "";

    public static String FILTER_ERROR_PAGE = "/error/404.jsp";

    /**
     * 
     */
    private XSSSecurityConfig() {

    }

}
