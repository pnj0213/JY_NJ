<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KHJSP</title>
</head>
<body>
	<%
		String uploadPath = request.getRealPath("/uploadedFile");
		String dept_no = request.getParameter("dept_no");
		//최대크기 설정
		int size = 10 * 1024 * 1024;

		MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "UTF-8",
				new DefaultFileRenamePolicy());

		Enumeration files = multi.getFileNames();
		String file1 = (String) files.nextElement();
		
		String file_id = multi.getFilesystemName(file1);
		String file_name = multi.getOriginalFileName(file1);
		File uploadFile = multi.getFile("file_name");
		String file_size = null;
		long filesize = uploadFile.length();
		double filesize2;
		int KB = 1024;
		long MB = KB*KB;
		long GB = MB*KB;
		if (filesize < MB && filesize >= KB) {
			filesize2 = (double)filesize/KB;
			file_size = (Math.round(filesize2*100)/100.0)+"KB";
		} else if (filesize < GB && filesize >= MB) {
			filesize2 = (double)filesize/MB;
			file_size = (Math.round(filesize2*100)/100.0)+"MB";
		} else if (filesize >= 1024 * 1024 * 1024) {
			filesize2 = (double)filesize/GB;
			file_size = (Math.round(filesize2*100)/100.0)+"GB";
		}
	%>
	<form id="filecheck" name="filecheck" action="Upload" method="post">
	<input type="hidden" name="file_id" value="<%=file_id %>">
	<input type="hidden" name="file_name" value="<%=file_name%>">
	<input type="hidden" name="file_size" value="<%=file_size %>">
	<input type="hidden" name="dept_no" value="<%=dept_no %>">
</form>
<script type="text/javascript">
	this.document.getElementById("filecheck").submit();
</script>
</body>
</html>