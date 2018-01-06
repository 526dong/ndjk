package com.ndjk.cl.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Created by xzd on 2018/1/5.
 * @Description: 获取请求参数
 */
public class ControllerUtil {
	/**
	 * 获取请求参数
	 * @param request
	 * @return map
	 */
	public static Map<String,Object> requestMap(HttpServletRequest request){
		Enumeration emu = request.getParameterNames();
		Map<String,Object> requestMap = new HashMap<String,Object>();
		while(emu.hasMoreElements()){
			String name = (String)emu.nextElement();
			String value = request.getParameter(name);
			if(null != value && value.length() > 0 && !"".equals(value)){
				requestMap.put(name, value.trim());
			}			
		}		
		return requestMap;
	}

}
