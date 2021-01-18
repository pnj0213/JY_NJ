<%@page import="board.Paging"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="board.BoardBean"%>
<%@ page import="java.util.Vector"%>
<%@ page import="board.BoardDAO"%>
<jsp:useBean id="boardbean" class="board.BoardBean">
	<jsp:setProperty name="boardbean" property="*" />
</jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../Header.jsp" />
<style>
.table {
	width: 700px;
	margin: 0px auto;
	background: #FFF;
	padding: 20px;
}
.button{
width: 300px;
height: 200px;
margin:0px auto;
}
</style>
</head>
<body>
	<jsp:include page="../file/fileUploadSearch.jsp"></jsp:include>
	<%
		request.setCharacterEncoding("UTF-8");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum == "0") {
			pageNum = "1";
		}
		int pageList = 10;
		String keyword = request.getParameter("keyword");
		String searchSubject = request.getParameter("search");
		int pageNum1 = Integer.parseInt(pageNum);
		BoardDAO bdao = new BoardDAO();
		Paging paging = new Paging(pageNum);
		paging.setTotalCount(bdao.getCount(searchSubject, keyword));
		Vector<BoardBean> vec = bdao.searchBoard(pageNum1, pageList, searchSubject, keyword);
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
				if (vec.size() > 0) {
					for (int i = 0; i < vec.size(); i++) {
						BoardBean bean = vec.get(i); //벡터에 저장되어 있는 빈클래스를 하나씩 추출
			%>
			<tr>
				<td><%=paging.getNumber() - i%></td>
				<td><a href="FileDown?file_id=<%=bean.getFile_id()%>"><%=bean.getFile_name()%></a></td>
				<td><%=bean.getFile_size()%></td>
				<td><%=bean.getUpload_date()%></td>
				<td><%=bean.getAuthor()%></td>
				<td><a href="fileDelete.up?file_id=<%=bean.getFile_id()%>">삭제</a></td>
			</tr>
			<%
				}
				} else {
					out.println("<tr><td colspan='6'>게시글이 없습니다.</td></tr>");
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
								href="main.jsp?page=lib/search&keyword=<%=keyword%>&search=<%=searchSubject%>&pageNum=<%=paging.getPrev()%>"
								aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								}
								for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
							%>
							<li
								<%if (i == Integer.parseInt(pageNum))
					out.print("class='active'");%>><a
								href="main.jsp?page=lib/search&keyword=<%=keyword%>&search=<%=searchSubject%>&pageNum=<%=i%>"><%=i%>
									<span class="sr-only"></span></a></li>
							<%
								}
								if (paging.getEndPage() < paging.getPageCount()) {
							%>
							<li><a
								href="main.jsp?page=lib/search&keyword=<%=keyword%>&search=<%=searchSubject%>&pageNum=<%=paging.getNext()%>"
								aria-label="next"><span aria-hidden="true">&raquo;</span></a></li>
							<%
								}
							%>
						</ul>
						<p class="text-left">
							<input type="button" value="목록" name="list" class="btn1"
								onclick="location.href='main.jsp?page=lib/libMain'"
								style="vertical-align: text-top; color: white; font-size: 12pt; width: 50px; height: 33px; background-color: #8C8C8C; border-width: 0 0 0 0;">
						</p>

						
					</nav>
				</td>
			</tr>
		</table>
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
	</div>
</body>
</html>
