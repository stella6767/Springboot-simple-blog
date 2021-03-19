package com.cos.blog.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.web.dto.CMRespDto;


@RestController //데이터를 리턴하기 위해서
@ControllerAdvice //모든 익셉션을 낚아챔.
public class GlobalExceptionHandler {
	
	//그 중 IllegalArgumentException 이 발생하면 해당 함수 실행됨
		@ExceptionHandler(value = DataIntegrityViolationException.class)
		public CMRespDto<?> dataIntegrityViolationException(Exception e){
			return new CMRespDto<>(-1, null);
		}
		
		@ExceptionHandler(value = IllegalArgumentException.class)
		public CMRespDto<?> illegalArgumentException(Exception e){
			return new CMRespDto<>(-1,null);
		}
		
		@ExceptionHandler(value = EmptyResultDataAccessException.class)
		public CMRespDto<?> emptyResultDataAccessException(Exception e){
			return new CMRespDto<>(-1,null);
		}

		@ExceptionHandler(value = MyAuthenticationException.class)
		public CMRespDto<?> myAuthenticationException(Exception e){
			return new CMRespDto<>(-1,null);
		}
		

}