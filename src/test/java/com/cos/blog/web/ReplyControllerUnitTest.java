package com.cos.blog.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cos.blog.config.SecurityConfig;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.User;
import com.cos.blog.handler.GlobalExceptionHandler;
import com.cos.blog.service.ReplyService;
import com.cos.blog.web.dto.CMRespDto;
import com.cos.blog.web.reply.dto.ReplySaveReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = ReplyController.class,
excludeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, GlobalExceptionHandler.class})
}
)
public class ReplyControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean // loc 환경에 bean 등록됨
	private ReplyService replyService;

	// BDDMockito 패턴 given, when, then
	@WithMockUser(roles="USER")
	@Test
	public void save_테스트() throws Exception { // 그냥 함수를 실행하면 오류가 뜬다. 왜냐, 컨트롤러는 서비스가 메모리에 떠야 작동이 되는데
		log.info("save_테스트() 시작 =========================================================="); // 현재 서비스가 없기 때문에 그래서
																								// @MockBean으로 가짜서비스 객체를
																								// 만들어줌
		
		//security OAuth2DetailsService' 을 JUNIT 테스트 환경에서 정의해줘야 되는디..
		// given(테스트를 하기 위한 준비)
		ReplySaveReqDto replySaveReqDto = new ReplySaveReqDto(1, "댓글1");
		
		String content = new ObjectMapper().writeValueAsString(replySaveReqDto); // json으로 변환시켜줌
		log.info(content);
		when(replyService.댓글쓰기(replySaveReqDto, new User())).thenReturn(new Reply("댓글1"));// 가짜로 결과값을 집어넣어줌, 스텁
		// 컨트롤러 관련로직만 테스트하기 때문에 실제 DB와 아무 상관이 없이 그냥 로직만 테스트

		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/reply")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then(검증)
		resultAction.andExpect(jsonPath("$.statusCode")
				.value(1))// 나는 이 결과를 기대한다.만약 틀리면 에런 																										
		.andDo(MockMvcResultHandlers.print());
	}

	
	
	
}
