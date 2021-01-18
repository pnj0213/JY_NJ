<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
#passcheckConfirm{
position: absolute;
top:300px; left:700px;
width:400px;
text-align: center;
border:1px solid gray;
padding-bottom: 30px;
}
#checkpw{
margin-bottom: 13px;
}
</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
<%
ResultSet rs = null;
String idSearch = request.getParameter("id");
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://khgthree.cafe24.com:3306/khgthree","khgthree","wjdqhrydbrdnjs3");
String sql = "select pw from EMP where id=?";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, idSearch);
rs = pstmt.executeQuery();
String pw = null;
while(rs.next()){
	pw = rs.getString("pw");
}
%>
<div id="passcheckConfirm">
<h2>비밀번호 찾기</h2>
<div id="checkpw">
<%=pw %> 찾은 패스워드입니다.<br>
</div>
<a href="../index.jsp">로그인하기</a>
</div>
</body>
</html>