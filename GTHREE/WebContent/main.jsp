<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String pagefile=request.getParameter("page");
	if(pagefile==null){
		pagefile="center";
	}
%>    
<!DOCTYPE html>
<html>
 <head>
  <meta charset="utf-8">
  <title>Goffice</title>

</head>
<body>
<div id="top">
	<jsp:include page="top.jsp"/>
</div>

<div id="center">
	<jsp:include page='<%=pagefile+".jsp" %>'/>
</div>
</body>
</html>