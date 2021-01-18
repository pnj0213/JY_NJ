package file;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import board.BoardBean;
import board.BoardDAO;

@WebServlet("/Upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardBean boardBean;
	private BoardDAO boardDAO;
	private int cnt;
	private HttpSession session; // 대기

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boardBean = new BoardBean();
		try {
			boardDAO = new BoardDAO();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

		String file_id = request.getParameter("file_id"); // 대기
		String fileName = request.getParameter("file_name");
		String file_size = request.getParameter("file_size");
		String emp_no = "emp142"; // 대기
		String author = "주비두비"; // getSession으로 ID가져오기
		String dept_no = request.getParameter("dept_no");

		boardBean.setFile_id(file_id);
		boardBean.setEmp_no(emp_no);
		boardBean.setFile_name(fileName);
		boardBean.setAuthor(author);
		boardBean.setFile_size(file_size);
		
		if(dept_no==null) {
			boardDAO.fileUpload(boardBean);
			response.sendRedirect("main.jsp?page=lib/libMain");
		}
		else if(dept_no.equals("dept_no")) {
			boardDAO.fileUpload(boardBean, dept_no);
			RequestDispatcher dis = request.getRequestDispatcher("main.jsp?page=lib/libMainDept");
			request.setAttribute("dept_no", dept_no); //listPage.jsp에서 목록리스트 데이터 저장
			dis.forward(request, response);
		}
		
		
//				list = BoardDAO.uploadList();
//				RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
//				request.setAttribute("BoardBean", BoardBean);
//				dis.forward(request, response);
		
	}
//		else if(command.equals("/fileDown.up")) {
//			file_id = request.getParameter("file_name");
//			out.print(file_id +"는 이거야");
//		}

}
