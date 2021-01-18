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
#file{
width: 700px;
    margin: 0px auto;
    background: #FFF;
    padding: 20px;
}
.filebox input[type="file"] {
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
}

.filebox label {
	display: inline-block;
	padding: .5em .75em;
	color: #4F4E4E;
	font-size: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #D4D0D0;
	cursor: pointer;
	border: 1px solid #BDBBBB;
	border-bottom-color: #BDBBBB;
	border-radius: .25em;
} /* named upload */
.filebox .upload-name {
	display: inline-block;
	padding: .5em .75em; /* label의 패딩값과 일치 */
	font-size: inherit;
	font-family: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	-webkit-appearance: none; /* 네이티브 외형 감추기 */
	-moz-appearance: none;
	appearance: none;
}
</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
	<div class="title"><h1>부서자료실</h1></div>
	
	<%
		String pageNum = request.getParameter("pageNum");
		String dept_no = request.getParameter("dept_no");
		if (pageNum == null) {
			pageNum = "1";
		}
		BoardDAO bdao = new BoardDAO();
		Paging paging = new Paging(pageNum);
		paging.setTotalCount(bdao.getAllCount());
		Vector<BoardBean> vec = bdao.getDeptBoard(paging.getStartRow(), paging.getEndRow(), dept_no);
	%>
	<div id="file">
	<form action="main.jsp?page=file/fileUploadDept" method="post"	enctype="multipart/form-data">
		<div class="filebox">
			<input class="upload-name" value="파일선택" disabled="disabled">
			<label for="ex_filename">업로드</label> 
			<input type="file" id="ex_filename" name="file_name" class="upload-hidden">
			<input type="hidden" name ="<%=dept_no %>">
		</div>
		<input type="submit" value="upload">
	</form>
</div>	
	<script>
		$(document).ready(
				function() {
					var fileTarget = $('.filebox .upload-hidden');
					fileTarget.on('change', function() { // 값이 변경되면 
						if (window.FileReader) { // modern browser 
							var filename = $(this)[0].files[0].name;
						} else { // old IE 
							var filename = $(this).val().split('/').pop()
									.split('\\').pop(); // 파일명만 추출 
						}
						// 추출한 파일명 삽입
						$(this).siblings('.upload-name').val(filename);
					});
				});
	</script>
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