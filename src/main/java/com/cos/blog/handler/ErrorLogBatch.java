package com.cos.blog.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.blog.domain.errorlog.ErrorLog;
import com.cos.blog.domain.errorlog.ErrorLogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ErrorLogBatch {

	public final ExceptionList exceptionList;
	private final ErrorLogRepository errorLogRepository;
	
	
	//@Scheduled(fixedDelay = 1000*60*10)
	@Scheduled(fixedDelay = 10*10*10) //Cron 정기적 실행
	public void excute() {
		List<String> exList = exceptionList.getExList();
		//DB에 insert 하기, 
		
		List<ErrorLog> errorLogs = new ArrayList<>();
		for (String error : exList) {
			errorLogs.add(new ErrorLog(null, error, null));
		}
		
		errorLogRepository.saveAll(errorLogs);
		
		exList.clear();
	}	
	
}
