package com.yida.core.webxml.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName="EncodingFilter", urlPatterns={"/*"},initParams={@WebInitParam(name="encoding", value="UTF-8")})
public class EncodingFilter implements Filter {
	
	protected FilterConfig config;
	protected String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.encoding = config.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Cache-Control", "no-cache");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setHeader("Expires", "0");
		
		response.setContentType("text/html; charset=" + encoding);
		if (null == request.getCharacterEncoding()) {
			if (null != encoding) {
				request.setCharacterEncoding(encoding);
				response.setCharacterEncoding(encoding);
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
