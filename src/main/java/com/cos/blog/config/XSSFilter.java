package com.cos.blog.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;


@Configuration//web.xml
public class XSSFilter {
 
	@Bean  //루시필터를 찾지를 못함.. 
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() { 
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
		
		//System.out.println("뭐야.... 한 번 서버 껐다가 재시작하니 작동하네..");
		filterRegistration.setFilter(new XssEscapeServletFilter());
		filterRegistration.setOrder(1);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}
}