/**
 * COPYRIGHT 北京微梦创新科技发展有限公司 2016
 * chetu - XSSFilter.java
 * 2016年9月20日
 * seven
 */
package com.ndjk.cl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author seven
 *
 */
public class XSSFilter implements Filter {

    private Log log = LogFactory.getLog(XSSFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(request);
        String pageLink = xssRequest.getServletPath();
        this.log.info(pageLink);

        if (pageLink != null) {
            if ((!pageLink.equals(XSSSecurityConfig.FILTER_ERROR_PAGE))
                    && (xssRequest.validateParameter(response))) {
                if (XSSSecurityConfig.IS_LOG) {
                    this.log.error(request.getRemoteAddr() + " " + pageLink
                            + " " + xssRequest.getQueryString());
                }
                if (XSSSecurityConfig.IS_CHAIN) {
                    request.getRequestDispatcher(
                            XSSSecurityConfig.FILTER_ERROR_PAGE).forward(
                            request, response);
                    return;
                }
                chain.doFilter(xssRequest, response);
            } else {
                chain.doFilter(xssRequest, response);
            }
        } else
            chain.doFilter(xssRequest, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        XSSSecurityManager.init();
    }

}
