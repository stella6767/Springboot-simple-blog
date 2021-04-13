package com.cos.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cos.blog.config.auth.PrincipalDetails;


public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        
        final Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 120; //단위는 초, 2시간 간격으로 세션만료

        request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS); //세션만료시간.
       
        
        PrincipalDetails principalDetails = (PrincipalDetails)userDetails;
        

    	response.sendRedirect("/");
        
    
	}

	

}
