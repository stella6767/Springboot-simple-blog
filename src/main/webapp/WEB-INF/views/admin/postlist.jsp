<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>



<div class="container">


	<form>
		<div class="input-group">
			<input type="text" class="form-control" id="search" placeholder="게시글 제목이나 내용을 입력해주세요">
			<div class="input-group-btn">
				<button class="btn btn-default btn-search" type="button">검색</button>
			</div>
		</div>
	</form>


	<table class="table">
		<thead>
			<tr>
				<th>제목</th>
				<th>내용</th>
				<th>작성자</th>
				<th>작성시간</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach var="post" items="${posts.content}">

				<tr>
					<td>${post.title}</td>
					<td>${post.content}</td>
					<td>${post.user.username}</td>
					<td>${post.createDate }</td>
					<td><button class="${post.id } btn btn-danger btn-delete">삭제</button></td>
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
					<li class=" page-item active"><a class="page-link" href="?page=${status.current}"> ${status.current} </a></li>
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
					this.searchPost();
					return false;
				}
			});
			
			$(".btn-search").on("click",()=>{
				this.searchPost();
			});
			
			
			$(".btn-delete").on("click", ()=>{
				this.deleteById();
			});
			
			
		},
		
	
		searchPost: function(){
			console.log(event.target);
			
			let data = {
			 keyword : $("#search").val()
			}
			
		//	alert(data.email);
			
			$.ajax({
				type : "post",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				url : "/admin/post",
				dataType : "json"
				
			}).done(function(result){
				$("#tbody").empty();
				
			for(var post of result){
					
					var post = 	" <tr>\r\n" + 
					"    \r\n" + 
					"        <td>"+post.title+"</td>\r\n" + 
					"        <td>"+post.content+"</td>\r\n" + 
					"        <td>"+post.user.username+"</td>\r\n" + 
					"        <td>"+post.createDate+"</td>\r\n" + 
					"        <td><button class=\""+post.id+" btn btn-danger btn-delete\">삭제</button></td>\r\n" + 
					"      </tr>\r\n" + 
					"   ";
					
					$("#tbody").append(post);
					
				}
			
				index.init();
				console.log(result);
		
			}).fail((error)=>{
				console.log(error);
			});
		},
		
		
		
		deleteById: function() {
			let id = event.target.className.split(" ")[0]; // 공백으로 나누어서 제일 첫번째에 있는것
			console.log(id);
			
			$.ajax({
				type:"delete",
				url: "/admin/post/"+id,
				dataType: "text"
			}).done((resp)=>{
				alert("삭제에 성공하셨습니다.");
				location.href = "/admin/post";
			}).fail((error)=>{
				alert("삭제실패");
				console.log(error);
			});
		}
		
}
index.init();

</script>



<%@ include file="../layout/footer.jsp"%>