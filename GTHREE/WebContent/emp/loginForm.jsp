<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">

#loginForm{
position: absolute;
top:300px; left:700px;
width:400px;
text-align: center;
border:1px solid gray;
}
.logo1{
padding: 30px 0;
margin-bottom: 0px;
text-align: center;
}
#loginbox{
padding: 0 0;
margin: 0px;
text-align: center;
}
.login_id, .login_pw{
width: 250px;
height: 20px;
margin-bottom: 4px;
}
.submit1{
width: 258px;
height: 25px;
}
</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>

<body>
<div id="loginForm">
<div class="logo1">
<img src="images/daouLogo.png">
</div>
<form action="emp/login.jsp" method="get">

<ul id=loginbox>
<li><label for="아이디"></label>
<input type="text" name="id" maxlength="10" size="20" autofocus="autofocus" required="required" placeholder="아이디" class="login_id">
</li>
<li><label for="비밀번호"></label>
<input type="password" name="pw" maxlength="10" size="20" placeholder="비밀번호" class="login_pw">
</li>
<li><input type="submit" value="로그인" class="submit1"><p>
<a href="emp/passcheck.jsp">비밀번호찾기</a>
</li>
</ul>

</form>
</div>
</body>
</html>