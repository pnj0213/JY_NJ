<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<style type="text/css">
table{
border: 1px solid gray; border-collapse: collapse;
text-align: center;
margin-bottom: 10px;
}
th{background-color: gray; color: white;}
#empList{
position: absolute;
top: 300px; left: 600px;

}
</style>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
<div id ="empList">
<h2>멤버통합관리</h2>
<table border="1" cellspacing="0" cellpadding="5">
<tr>
<th><input type="checkbox" name="chk" onclick="selectAll(this)"></th>
<th>번호</th>
<th>이름</th>
<th>사번(ID)</th>
<th>비밀번호</th>
<th>주소</th>
<th>핸드폰번호</th>
<th>이메일</th>
<th>입사일</th>
<th>직급번호</th>
<th>직급이름</th>
<th>부서번호</th>
<th>부서이름</th>
<th>부서주소</th>

</tr>
<%
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://khgthree.cafe24.com:3306/khgthree","khgthree","wjdqhrydbrdnjs3");
String sql = "SELECT E.EMP_NO,E.EMP_NAME,E.PW,E.ID,E.TEL,E.ADDR,E.EMAIL,E.JOIN_DATE,J.JOB_NO,J.JOB_NAME,D.DEPT_NO,D.DEPT_NAME,D.DEPT_LOC FROM EMP E, JOB J, DEPT D WHERE E.JOB_NO=J.JOB_NO AND E.DEPT_NO=D.DEPT_NO;";
PreparedStatement pstmt = conn.prepareStatement(sql);
ResultSet rs = null;
int emp_no = 0;
String emp_name = null;
String id = null;
String pw = null;
String addr = null;
String tel = null;
String email = null;
String join_date = null;
int job_no = 0;
String job_name = null;
int dept_no = 0;
String dept_name = null;
String dept_loc = null;

rs = pstmt.executeQuery();
while(rs.next()){
	emp_no = rs.getInt("E.EMP_NO");
	emp_name = rs.getString("E.EMP_NAME");
	id = rs.getString("E.ID");
	pw = rs.getString("E.PW");
	addr = rs.getString("E.ADDR");
	tel = rs.getString("E.TEL");
	email = rs.getString("E.EMAIL");
	join_date = rs.getString("E.JOIN_DATE");
	job_no = rs.getInt("J.JOB_NO");
	job_name = rs.getString("J.JOB_NAME");
	dept_no = rs.getInt("D.DEPT_NO");
	dept_name = rs.getString("D.DEPT_NAME");
	dept_loc = rs.getString("D.DEPT_LOC");
	out.print("<tr><td><input type='checkbox' name='chk'></td><td>"+emp_no+"</td><td>"+emp_name+"</td><td>"+id+"</td><td>"+pw+"</td><td>"+addr+"</td><td>"+tel+"</td><td>"+email+"</td><td>"+join_date+"</td><td>"+job_no+"</td><td>"+job_name+"</td><td>"+dept_no+"</td><td>"+dept_name+"</td><td>"+dept_loc+"</td></tr>");	
}
%>
</table>
<a href = "empForm.jsp">멤버등록</a>
<a href = "empUpdateForm.jsp">멤버수정</a>
<a href = "empOut.jsp">멤버삭제</a>
<a href = "../dept/deptList.jsp">부서관리</a>
<a href = "../job/jobList.jsp">직급관리</a>
<a href = "../top.jsp">메인페이지</a>
</div>
<script>
//체크박스 전체선택 및 전체해제
function selectAll(selectAll)  {
  const checkboxes 
       = document.getElementsByName('chk');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked;
  })
}


</script>
</body>
</html>