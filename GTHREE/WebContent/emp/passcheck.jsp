<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
ul{list-style-type: none;}
#passcheck{
position: absolute;
top:300px; left:700px;
width:400px;
text-align: center;
border:1px solid gray;
}
#checkbox{
padding: 0 0;
margin: 0px;
text-align: center;
}
.check_id{
width: 197px;
height: 20px;
margin-bottom: 4px;
}
.submit1{
width: 258px;
height: 25px;
margin-bottom: 30px;
}

</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
<div id="passcheck">
<h2>비밀번호 찾기</h2>
<form action="passcheckConfirm.jsp" method="get">
<ul id=checkbox>
<li><label for="아이디">아이디</label>
<input type="text" name="id" autofocus="autofocus" required="required" placeholder="아이디입력" class="check_id">
</li>
<li><input type="submit" value="검색" class="submit1">
</li>
</ul>
</form>
</div>
</body>
</html>