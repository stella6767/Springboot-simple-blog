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

import com.cos.blog.handler.ExceptionList;
import com.cos.blog.utils.Script;
import com.cos.blog.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Aspect
public class BindingAdvice {
	
	
	public final ExceptionList exceptionList;

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
						
					}
					exceptionList.addExceptionList("Validation 통과실패");
					return "error/error1";
				}
			}
		}
		return proceedingJoinPoint.proceed(); // 정상적이면 함수의 스택을 실행해라

	}

	@Around("execution(* com.cos.blog.web..AuthController.join(..)) || execution(* com.cos.blog.web..PostController.save(..)) ")
	public Object joinCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();

		System.out.println("type : " + type);
		System.out.println("method : " + method);

		Object[] args = proceedingJoinPoint.getArgs();
		
		String errorurl;
		
		
		if(type.contains("Auth")) {
			errorurl ="error/error1";	
		}else {
			//url ="redirect:/"; //redirect 여기선 안 먹네..
			errorurl = "post/saveForm";
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

					}
					
					//return Script.alert("validation에 통과못했습니다.");  안 되네..
					exceptionList.addExceptionList("Validation 통과실패");
					return errorurl;
				}
			}

		}
		
		return proceedingJoinPoint.proceed();// 정상적이면 함수의 스택을 실행해라
		//return url; // 이것때문이네.. 제일 마지막에 실행하는 것이.. 여기인데 redirect가 안 먹히네.. 여기서 처리하면,

	
	}


}
