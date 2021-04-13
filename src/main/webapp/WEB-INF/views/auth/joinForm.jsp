<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/loginForm.css">
</head>

<%@ include file="../layout/header.jsp"%>


<title>Register Form</title>
</head>
<body>
	<div class="border"  id="logreg-forms">
		
			<h3 class="font-weight-normal pt-3" style="text-align: center; border: 1px; height: 50px;">회원가입</h3>
			<div class="social-login">
	
			<form action="/join" method="POST">
				<input type="text" name="username" class="form-control" placeholder="username" required="" autofocus="">
				<input type="password" name="password" class="form-control mt-2" placeholder="Password" required="">
				<input type="email" name="email" class="form-control mt-2" placeholder="Email" required="">

				<button class="btn btn-success btn-block mt-3" >
					<i class="fas fa-sign-in-alt"></i> 회원가입
				</button>
			</form>
			<hr>


			<div class="d-flex justify-content-between" id="btn-signup" >
				<p class="ml-2 mt-2">이미 회원가입이 되셨나요?</p>
				<a class="text-decoration-none mb-2 pb-1 mr-3" href="/loginForm" > 로그인
				</a>
			</div>
	



	</div>
	
</body>
</html>