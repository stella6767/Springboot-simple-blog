<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/loginForm.css">
</head>

<%@ include file="../layout/header.jsp"%>


<title>Bootstrap 4 Login/Register Form</title>
</head>
<body>
	<div id="logreg-forms">
		
			<h3 class="h3 mb-3 font-weight-normal pt-3" style="text-align: center; height: 50px;">로그인</h3>
			<div class="social-login">
			<div class="d-flex">
				<a href="/oauth2/authorization/facebook">
				<button class="btn facebook-btn social-btn mr-1" type="button">
					<span><i class="fab fa-facebook-f"></i> Sign in with Facebook</span>
				</button>
				</a>
				<a href="/oauth2/authorization/google">
				<button class="btn google-btn social-btn" type="button">
					<span><i class="fab fa-google-plus-g"></i> Sign in with Google+</span>
				</button>
				</a>
				</div>
				<div class="mt-2 d-flex">

					<a href="/oauth2/authorization/kakao"> <img alt="" src="/image/kakao_login.png" />
					</a>
				<a href="/oauth2/authorization/naver"> <img alt="" class="ml-2" src="/image/naver_login.PNG" style="height: 45px; width: 195px" />
					</a>

				</div>

			</div>
			<p style="text-align: center">OR</p>

			<form action="/login" method="POST">
				<input type="text" name="username" class="form-control" placeholder="username" required="" autofocus="">
				<input type="password" name="password" class="form-control mt-2" placeholder="Password" required="">

				<button class="btn btn-success btn-block mt-3" >
					<i class="fas fa-sign-in-alt"></i> 로그인
				</button>
			</form>
			<hr>


			<button class="btn btn-primary btn-block" type="button" id="btn-signup">
				<a class="text-decoration-none pb-2" href="/joinForm" style="color: white; text-decoration: none; font-style: normal;">회원가입하러가기</a>
			</button>
	



	</div>
	<p style="text-align: center">
</body>
</html>