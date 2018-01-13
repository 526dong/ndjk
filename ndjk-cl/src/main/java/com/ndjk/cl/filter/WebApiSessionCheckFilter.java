package com.ndjk.cl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ndjk.cl.brandservice.model.Kindergarten;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * 检查用户的session
 * 
 * @since 1.0
 */
@Component
public class WebApiSessionCheckFilter implements Filter {

    /**
	 * 
	 */
	private static final String PRODUCT_GET_PRODUCT_INFO = "/product/getProductInfo/";
	private static final String OTP_GET = "/otp/";
	private static final String ERRORRET = "{\"err\": {\"code\": -100,\"msg\": \"请重新登陆\",\"eventid\": \"\"}}";
    private static final Log LOG = LogFactory
            .getLog(WebApiSessionCheckFilter.class);
    private static final String UTF8 = "UTF-8";

    private String ignore = "";

    /**
     * Default constructor.
     */
    public WebApiSessionCheckFilter() {
        // Auto-generated method stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // place your code here
        HttpServletRequest r = (HttpServletRequest) request;

        String uri = r.getRequestURI();
        
        if (checkIgnore(uri)) {
            // ignore
            // pass the request along the filter chain
            chain.doFilter(request, response);
            return;
        }
        
        if (this.checkSession(r)) {
            chain.doFilter(request, response);
        } else {
            request.setCharacterEncoding(UTF8);
            response.setCharacterEncoding(UTF8);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println(ERRORRET);
        }

    }

	/**
	 * @param         uri
	 * @since         2016年10月12日
	 * @time          下午2:58:40
	 * @author        yanou.he
	 * @return        boolean 
	 */
	private boolean checkIgnore(String uri) {
		// 产品详情接口
		boolean isProductInfo = uri.contains(PRODUCT_GET_PRODUCT_INFO);
		
		return isProductInfo || this.getIgnore().contains(uri)||uri.contains(OTP_GET);
	}

    /**
     * 检查session
     * 
     * @param r
     * @return<br>
     * @author seven<br>
     *         add on 2016年8月12日
     */
    private boolean checkSession(HttpServletRequest r) {
        
        //TODO for test only
        HttpSession cursession = r.getSession(false);

        if (null == cursession) {
            return false;
        }
        try {
             Kindergarten kindergarten = (Kindergarten) cursession.getAttribute("kindergarten");
            if (null == kindergarten) {
                return false;
            }
            Integer userId = Integer.valueOf(r.getParameter("userId"));
            if(!userId.equals(kindergarten.getId())){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        this.setIgnore(fConfig.getInitParameter("ignore"));
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }
}
