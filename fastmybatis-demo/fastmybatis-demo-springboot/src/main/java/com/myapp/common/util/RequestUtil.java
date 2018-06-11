package com.myapp.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	/**
	 * 判断请求是否为Ajax请求. <br/>
	 * 
	 * @param request
	 *            请求对象
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(header);
	}
	
	/**
	 * 获取客户端真实IP
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
				}
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	
	}
	
	/**
	 * 获取请求路径,即contextPath后面部分. 如http://www.aaa.com/rms/sys/index.jsp<br>
	 * 返回/sys/index.jsp
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String result = request.getServletPath();
		String pathInfo = request.getPathInfo();

		if (pathInfo != null) {
			result = result + pathInfo;
		}

		// getServletPath() returns null unless the mapping corresponds to a
		// servlet
		if (result == null) {
			String requestURI = request.getRequestURI();
			if (request.getPathInfo() != null) {
				// strip the pathInfo from the requestURI
				return requestURI.substring(0,
						requestURI.indexOf(request.getPathInfo()));
			} else {
				return requestURI;
			}
		} else if ("".equals(result)) {
			// in servlet 2.4, if a request is mapped to '/*', getServletPath
			// returns null (SIM-130)
			return request.getPathInfo();
		} else {
			return result;
		}
	}

}
