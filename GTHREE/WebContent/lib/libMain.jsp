<%@page import="board.BoardBean"%>
<%@page import="java.util.Vector"%>
<%@page import="board.Paging"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../Header.jsp" />
<style>
.table {
	width: 700px; 
	margin : 0px auto;
	background: #FFF;
	padding: 20px;
}
.title{
text-align: center;
margin: 0px auto;
}
.button{
width: 300px;
height: 200px;
margin:0px auto;
}
</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
	<div class="title"><h1>전사자료실</h1></div>
	<jsp:include page="../file/fileUploadSearch.jsp"></jsp:include>
	<%
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		BoardDAO bdao = new BoardDAO();
		Paging paging = new Paging(pageNum);
		paging.setTotalCount(bdao.getAllCount());
		Vector<BoardBean> vec = bdao.getAllBoard(paging.getStartRow(), paging.getEndRow());
	%>
	<div class="table">
		<table class="table">
			<tr>
				<th>번호</th>
				<th>파일명</th>
				<th>파일크기</th>
				<th>등록날짜</th>
				<th>작성자</th>
				<th>파일삭제</th>
			</tr>

			<%
				for (int i = 0; i < vec.size(); i++) {
					BoardBean bean = vec.get(i); //벡터에 저장되어 있는 빈클래스를 하나씩 추출
			%>
			<tr>
				<td><%=paging.getNumber() - i%></td>
				<td><a
					href="FileDown?file_id=<%=bean.getFile_id()%>&&<%=bean.getFile_name()%>"><%=bean.getFile_name()%></a></td>
				<td><%=bean.getFile_size()%></td>
				<td><%=bean.getUpload_date()%></td>
				<td><%=bean.getAuthor()%></td>
				<td><a href="fileDelete.up?file_id=<%=bean.getFile_id()%>">삭제</a></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td colspan="5" class="text-center">
					<nav>
						<ul class="pagination">
							<%
								if (paging.getStartPage() > 10) {
							%>
							<li><a
								href="main.jsp?page=lib/libMain&&pageNum=<%=paging.getPrev()%>"
								aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								}
								for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
							%>
							<li
								<%if (i == Integer.parseInt(pageNum))
									out.print("class='active'");%>><a
								href="main.jsp?page=lib/libMain&&pageNum=<%=i%>"><%=i%> </a></li>
							<%
								}
								if (paging.getEndPage() < paging.getPageCount()) {
							%>
							<li><a
								href="main.jsp?page=lib/libMain&&pageNum=<%=paging.getNext()%>"
								aria-label="next"><span aria-hidden="true">&raquo;</span></a></li>
							<%
								}
							%>
						</ul>
						<p class="text-left">
					</nav>
				</td>
			</tr>
		</table>
	</div>
<form action="main.jsp?page=lib/search" method="post">
			<div class="button">
				<select name="keyword"
					style="vertical-align: text-top; font-size: 12pt; text-align-last: center; width: 70px; height: 30px; background-color: #8C8C8C; color: white; border: 1px solid gray;">
					<option value="file_id">파일명</option>
					<option value="author">작성자</option>
				</select> <input type="text" name="search" autocomplete="off"
					style="vertical-align: sub; border: 1px solid #888ca5;  width: 150px; height: 30px; overflow: visible;">
				<input type="submit" value="검색" name="searchSubject" class="btn1"
					style="vertical-align: text-top; color: white; font-size: 12pt; width: 50px; height: 30px; background-color: #8C8C8C; border-width: 0 0 0 0;">
			</div>
		</form>
</body>
</html>