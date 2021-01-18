package file;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardBean;
import board.BoardDAO;

@WebServlet("*.up")
public class FileManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardBean bean;
	private BoardDAO boardDAO;
	private HttpSession session; // ´ë±â

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		bean = new BoardBean();
		try {
			boardDAO = new BoardDAO();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		if(command.equals("/fileDelete.up")) {
			String file_id = request.getParameter("file_id");
			try {
				boardDAO.fileDelete(request, file_id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("main.jsp?page=lib/libMain");
		}
		else if(command.equals("/libDept.up")) {
			String dept_no = request.getParameter("dept_no");
			out.print(dept_no);
		}
	}

}
