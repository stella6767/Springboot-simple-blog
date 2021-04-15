package com.cos.blog.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cos.blog.domain.user.User;
import com.cos.blog.handler.MyAuthenticationException;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {

			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {

				System.out.println("인터셉터 테스트");

				HttpSession session = request.getSession();
				User principal = (User) session.getAttribute("principal");

				String method = request.getMethod();
				System.out.println("메서드:  " + method);

				if (principal == null && (method.equals("PUT") || method.equals("POST") || method.equals("DELETE"))) {

					throw new MyAuthenticationException();
				} else {
					return true;
				}

			}

		}).addPathPatterns("/user/*").addPathPatterns("/post/*").addPathPatterns("/reply/*");

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) { // custom CORS 정책
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
						"Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
	}


}
