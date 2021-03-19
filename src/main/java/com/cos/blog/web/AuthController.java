package com.cos.blog.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.service.AuthService;
import com.cos.blog.web.auth.dto.AuthJoinReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {
	
	private final AuthService authService;
	
	// 주소: 인증안되어있을 때 /user, /post,  인증이 되든 말든 무조건 온다. /loginForm
	@GetMapping("/loginForm")
	public String loginForm() {
		return "auth/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "auth/joinForm";
	}
	
	
	@PostMapping("/join")
	public String join(@Valid AuthJoinReqDto authJoinReqDto, BindingResult bindingResult) {
		
		System.out.println(authJoinReqDto);
		authService.회원가입(authJoinReqDto.toEntity());
		return "redirect:/loginForm"; //  /auth  재활용
	}
	

	
	
}
