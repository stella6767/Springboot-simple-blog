<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<c:forEach var="post" items="${posts.content}">
		<div class="card">
			<div class="card-body">
				<div class="d-flex justify-content-between">
					<h4 class="card-title">${post.title}</h4>
					<div class="">작성자: ${post.user.username}
						<div >${post.count}</div>
					</div>
				</div>
				<a href="/post/${post.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
		<br>
	</c:forEach>
	



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
<%@ include file="../layout/footer.jsp"%>