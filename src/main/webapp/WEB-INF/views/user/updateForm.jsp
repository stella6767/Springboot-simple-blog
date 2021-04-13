<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/loginForm.css">
<%@ include file="../layout/header.jsp"%>


<div class="border"  id="logreg-forms">
		
			<h3 class="font-weight-normal pt-3" style="text-align: center; border: 1px; height: 50px;">유저정보</h3>
			<div class="social-login">
	
			<form>
				<input type="hidden" id="id" value="${id}"/>
				<input type ="text" value="${principal.user.username}" class="form-control"  placeholder="Username" id="username" readonly="readonly"/><br/>
				<input type ="password" value=""  class="form-control" placeholder="Password" id="password"/> <br/>
				
				<input type ="email" class="form-control" value="${principal.user.email}" placeholder="Email" id="email"/><br/> 

				<button id="btn-update" class="btn btn-success btn-block mt-3" >
					<i class="fas fa-sign-in-alt"></i> 회원수정
				</button>
			</form>


	</div>


<script>
	$("#btn-update").on("click",(e)=>{
		e.preventDefault(); //submit 막기, type=button 줘도 submit 방지가능
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
		};
		
		console.log(data);
		
		let id = $("#id").val();
		
		$.ajax({
			type:"PUT",
			url:"/user/"+id,
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"JSON"	
		}).done((res)=>{
			console.log(res);
			if(res.statusCode === 1){
				alert("수정에 성공하였습니다.");
				location.href="/";
			}else{ //else가 의미가 없는게 어차피 실패뜨면, fail로 탐. 이거 안 탐
				alert("수정에 실패하였습니다.");
			}
		
		}).fail();
		
	});
	
	
	

</script>


