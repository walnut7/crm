package com.kpleasing.crm.filter;

import com.kpleasing.crm.ejb.pojo.UserProfile;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SecurityFilter implements Filter {
	private static Logger logger = Logger.getLogger(SecurityFilter.class);
	public FilterConfig config;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	private boolean isLoginPage(HttpServletRequest request) {
		try {
			return request.getRequestURI().split("/")[2].toLowerCase().indexOf("login") != -1;
		} catch (Exception localException) {
		}
		return false;
	}

	private boolean isIgnore(String uri, String[] argc) {
		try {
			if (uri.indexOf("/") != -1) {
				String module = uri.split("/")[2].toLowerCase();
				for (int i = 0; i < argc.length; i++) {
					if (module.equals(argc[i])) {
						return true;
					}
				}
			}
		} catch (Exception localException) {
		}
		return false;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponseWrapper wrapperResponse = new HttpServletResponseWrapper((HttpServletResponse) response);

		String ignores = this.config.getInitParameter("ignorePath");
		String redirectPath = httpRequest.getContextPath() + this.config.getInitParameter("redirectPath");
		String defaultPath = httpRequest.getContextPath() + this.config.getInitParameter("defaultPath");
		if (isIgnore(httpRequest.getRequestURI(), ignores.split(";"))) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = httpRequest.getSession();
		UserProfile user = (UserProfile) session.getAttribute("LOGIN_USER");
		if (null == user) {
			if (!isLoginPage(httpRequest)) {
				wrapperResponse.sendRedirect(redirectPath);
			}
		} else if (isLoginPage(httpRequest)) {
			wrapperResponse.sendRedirect(defaultPath);
			return;
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		this.config = null;
	}
}
