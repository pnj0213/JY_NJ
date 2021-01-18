<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
  <script src="js/jquery.innerfade.js"></script>
  <style>
   body {
    font-family: Arial,sans-serif;
    font-size: 14px;
    line-height: 1.6;}
   #ALL {
    width: 700px;
    margin: 30px auto;
    background: #FFF;
    padding: 20px;}  
   .menu { 
    height: 45px;
    background: #000;}
   .menu ul {
    list-style: none;
    padding: 0;
    margin: 0;}
   .menu ul li {
    float: left;
    overflow: hidden;
    text-align: center;    
    line-height: 45px;}
   .menu ul li a {
    position: relative;
    display: block;
    width: 110px;
    height: 45px;
    color: #FFF; 
    font-family: Arial;
    font-size: 11px;
    font-weight: bold;
    letter-spacing: 1px;
    text-transform: uppercase;
    text-decoration: none;
    cursor: pointer;}

   .menu ul li a span {
    position: absolute;
    top: 0px;
    left: 0;
    width: 110px; } 
   .menu ul li a span.over{
    top: -45px; } 
   .menu ul li a span.over {
    background: #FFF;
    color: #000;}
.logo2{
    width: 170px;
    height: 60px;
}
#homeregister{
position: absolute;
right:0px; top:0px;
width:300px; hegith:100px;
/*border:1px solid red;*/
font-size:20px;
}
a{text-decoration: none;}
a:hover {
	text-decoration: underline;
}
#idlogin{
position: absolute;
top: 50px;
font-size: 15px;

}
  </style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>

  <div id="ALL">
   <h1><img src="images/daouLogo.png" class="logo2"></h1>
   
   <div class="menu">
    <ul>
     <li><%String id = (String)session.getAttribute("id"); 
     if(id!=null){
     out.print("<a href=#>학사소개ro</a>");
     }else{
    	 out.print("<a href=#>학사소개</a>");
     }
     %></li>
     <li><%if(id!=null){
    	 out.print("<a href=#>커뮤니티</a>");
     }else{
    	 out.print("<a href=#>커뮤니티</a>");
     }
     %>
     </li>
     <li><a href="main.jsp?page=lib/libMain">자료실</a></li>
     <li><a href="main.jsp?page=lib/DeptSelect">부서자료실</a></li>
     <li><a href="#">포트폴리오</a></li> 
    </ul>
   </div>
   <div id="homeregister">
      <% id = (String)session.getAttribute("id"); 
      if(id==null){%>
      <a href="index.jsp">로그인</a>
      <%} %>
      <div id="idlogin">
      <%
         id = (String)session.getAttribute("id");
         if(id!=null){
        	 out.print(id+"님 환영합니다.<br>");     	 
        //	 out.print("<a href=emp/myProfile.jsp>개인정보수정</a><br>");
        	 out.print("<a href=emp/empList.jsp>관리자페이지</a><br>");
        	 out.print("<a href=emp/logout.jsp>로그아웃</a><br>");
   
         }
      %>
    
      </div>
   </div>
  </div>
  <script type="text/javascript">
   $(function(){
    $(".menu li a").wrapInner( '<span class="out"></span>' );

    $(".menu li a").each(function() {
     $( '<span class="over">' +  $(this).text() + '</span>' ).appendTo( this );
    });
    $(".menu li a").hover(function() {
     $(".out", this).stop().animate({'top': '45px'}, 200); 
     $(".over", this).stop().animate({'top': '0px'}, 200);

    }, function() {
     $(".out", this).stop().animate({'top': '0px'},  200); 
     $(".over", this).stop().animate({'top': '-45px'}, 200);
    });
   });
  </script>
</body>
</html>