<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../Header.jsp" />
<style>
.title{
text-align: center;
padding-bottom: 100px;
}
.code{
width: 370px;
margin: 0px auto;
}
.code2{
float: right;
}
</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
<div class="title"><h1>부서자료실</h1></div>
<div class="code">
	<form action="main.jsp?page=lib/libMainDept" method="post">
		<label for="dept_no">부서코드입력 : <input type="number" name="dept_no" style="height:20px"></label>
		<div class="code2"><input type="submit" value="입장"></div>
	</form>
</div>
</body>
</html>