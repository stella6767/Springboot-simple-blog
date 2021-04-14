<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>



<div class="container" >


<form>
  <div class="input-group">
    <input  type="text" class="form-control" id ="search" placeholder="이메일을 입력해주세요">
    <div class="input-group-btn">
      <button class="btn btn-default btn-search" type="button">
      	검색
      </button>
    </div>
  </div>
</form>


	<table class="table">
		<thead>

			<tr>

				<th>이름</th>
				<th>이메일</th>
				<th>생성날짜</th>
				<th>권한</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach var="user" items="${users.content}">

				<tr>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>${user.createDate}</td>
					<td><div class="form-group">
							<select class="${user.id} form-control roles">
								<option selected disabled hidden>${user.role}</option>
								<option value="ROLE_USER">ROLE_USER</option>
								<option value="ROLE_ADMIN">ROLE_ADMIN</option>
							</select>
						</div></td>
					<td><button class="${user.id } btn btn-danger btn-delete">삭제</button></td>
				</tr>

			</c:forEach>
		</tbody>
	</table>
	
	
	
	
	<ul class="pagination justify-content-center">


    <c:set var="boardsSize" value="${fn:length(pageInfo.pageList)}" /> 

		<c:choose>
			<c:when test="${!pageInfo.isPrevExist}">
				<li class="page-item disabled"><a class="page-link">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${pageInfo.pageList[0]-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:forEach varStatus="status" begin="${pageInfo.blockFirstPageNum}" end="${pageInfo.blockLastPageNum}">
			<!-- 여기서 페이징 10개씩 나눠서 처리하는 로직을 추가시켜줄려고 했지만 귀찮아서 생략 -->

			<c:choose>
				<c:when test="${pageInfo.currentPageNum eq status.current}">
					<li class=" page-item active"><a class="page-link" href="?page=${status.current}"> ${status.current}  </a></li>
				</c:when>

				<c:otherwise>
					<li class=" page-item"><a class="page-link" href="?page=${status.current}"> ${status.current} </a></li>
				</c:otherwise>
			</c:choose>


		</c:forEach>

		<c:choose>
			<c:when test="${!pageInfo.isNextExist}">
				<li class="page-item disabled"><a class="page-link">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${pageInfo.pageList[0] + 3}">Next</a></li>
			</c:otherwise>
		</c:choose>


	</ul>
	
	
	
	
	
	
	
	
	
	
</div>


<script>
let index = {
		init : function() {
			
			$(".form-control").on("keypress",()=>{
				if(event.keyCode == 13){
					this.searchUser();
					return false;
				}
			});
			
			$(".btn-search").on("click",()=>{
				this.searchUser();
			});
			
			//유저 권한
			$(".roles").on("change", ()=>{
				this.updateRole();
			});
			
			//유저 삭제하기
			$(".btn-delete").on("click", ()=>{
				this.deleteById();
			});
			
			
		},
		
	
		searchUser: function(){
			console.log(event.target);
			
			let data = {
			 email : $("#search").val()
			}
			
		//	alert(data.email);
			
			$.ajax({
				type : "post",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				url : "/admin/user",
				dataType : "json"
				
			}).done(function(result){
				$("#tbody").empty();
				
				for(var user of result){
					
					var user = "  <tr>\r\n" + 
					"        <td>"+user.username+"</td>\r\n" + 
					"        <td>"+user.email+"</td>\r\n" + 
					"        <td>"+user.createDate+"</td>\r\n" + 
					"        <td><div class=\"form-group\">\r\n" + 
					" 			 <select class=\""+user.id+"form-control roles\" >\r\n" + 
					"			 <option selected disabled hidden>"+user.role+"</option>\r\n" + 
					"   			 <option value=\"ROLE_USER\">ROLE_USER</option>\r\n" + 
					"  			 <option value=\"ROLE_ADMIN\">ROLE_ADMIN</option>\r\n" + 
					" 			 </select>\r\n" + 
					"		</div>\r\n" + 
					"	   </td>\r\n" + 
					"        <td><button class=\""+user.id+ " btn btn-danger btn-delete\">삭제</button></td>\r\n" + 
					"      </tr>\r\n" + 
					"    "
					
					$("#tbody").append(user);
					
				}
			
				index.init();
				console.log(result);
		
			}).fail((error)=>{
				console.log(error);
			});
		},
		
		//유저 권한
		updateRole: function(){
			
			console.log(event.target.value);
			
			let data = {
					role: event.target.value
			}
			
			$.ajax({
				type:"put",
				url: "/admin/user/"+event.target.className.split(" ")[0],
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify(data),
				dataType: "text"
			}).done((resp)=>{
				alert("변경하였습니다.");
				location.href = "/admin/user";  //다시 컨트롤러를 때리게한다.
			}).fail((error)=>{
				alert("변경에 실패하셨습니다");
				console.log(error);
			});
		},
		
		//유저삭제 로직 
		deleteById: function() {
			let id = event.target.className.split(" ")[0]; // 공백으로 나누어서 제일 첫번째에 있는것
			console.log(id);
			
			$.ajax({
				type:"delete",
				url: "/admin/user/"+id,
				dataType: "text"
			}).done((resp)=>{
				alert("삭제에 성공하셨습니다.");
				location.href = "/admin/user";
			}).fail((error)=>{
				alert("삭제실패");
				console.log(error);
			});
		}
		
}
index.init();

</script>



<%@ include file="../layout/footer.jsp"%>