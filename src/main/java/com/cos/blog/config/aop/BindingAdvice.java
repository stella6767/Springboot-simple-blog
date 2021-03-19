package com.cos.blog.config.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.blog.web.dto.CMRespDto;

@Component
@Aspect
public class BindingAdvice {

	@Around("execution(* com.cos.blog.web..*Controller.*(..)) && !execution(* com.cos.blog.web..AuthController.*(..))"
			+ "&& !execution(public String com.cos.blog.web..*Controller.*(..))")
	public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();

		System.out.println("type : " + type);
		System.out.println("method : " + method);

		Object[] args = proceedingJoinPoint.getArgs();

		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				
				BindingResult bindingResult = (BindingResult) arg;
				System.out.println(bindingResult);
				
				// 서비스 : 정상적인 화면 -> 사용자요청
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					System.out.println("??" +errorMap);
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						
						System.out.println("??" +errorMap);
					}
					System.out.println("??");
					return new CMRespDto<>(-1, errorMap);
				}
			}
		}
		return proceedingJoinPoint.proceed(); // 정상적이면 함수의 스택을 실행해라

	}

	@Around("execution(* com.cos.blog.web..AuthController.join(..)) || execution(* com.cos.blog.web..PostController.save(..)) ")
	public String joinCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();

		System.out.println("type : " + type);
		System.out.println("method : " + method);

		Object[] args = proceedingJoinPoint.getArgs();
		
		String url;
		
		
		if(type.contains("Auth")) {
			url ="redirect:/loginForm";	
		}else {
			url ="post/saveForm";
		}
		
		
		
		for (Object arg : args) {
			if (arg instanceof BindingResult) {

				BindingResult bindingResult = (BindingResult) arg;
				System.out.println(bindingResult);

				// 서비스 : 정상적인 화면 -> 사용자요청
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					System.out.println("??" + errorMap);
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());

						System.out.println("??" + errorMap);
					}
					System.out.println("??");
					return "error/error1";
				}
			}

		}
		
		proceedingJoinPoint.proceed();
		return url; // 정상적이면 함수의 스택을 실행해라

	}


}
