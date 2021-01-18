package kr.or.kh.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("*.mb")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberDTO memberDTO;
	private MemberDAO memberDAO;
	private int cnt;
	private ArrayList<MemberDTO> memberList;
	private ArrayList<String> memberIdcheck;
	private HttpSession session;

	public MemberServlet() {
		memberDTO = new MemberDTO();
		memberDAO = new MemberDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		if (command.equals("/memberRegister.mb")) { // 등록
			memberDTO.setId(request.getParameter("id"));
			memberDTO.setPw(request.getParameter("pw"));
			memberDTO.setAddr(request.getParameter("addr"));
			memberDTO.setTel(request.getParameter("tel"));
			try {
				cnt = memberDAO.memberRegister(memberDTO);
				out.print(cnt + "건 회원이 등록되었습니다.");
				response.sendRedirect("memberList.mb");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (command.equals("/memberList.mb")) { // 목록
			try {
				memberList = memberDAO.memberList();
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp?page=member/memberlist");
				request.setAttribute("memberList", memberList);
				dis.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		else if(command.equals("/memberDelete.mb")) { //삭제
			String deletePw = request.getParameter("pw");
			String deleteId = (String)session.getAttribute("id");
			try {
				cnt = memberDAO.memberDelete(deleteId, deletePw);
				if(cnt == 0) {
					out.print("<script>alert('비밀번호가 틀렸습니다.'); location.href='index.jsp?page=member/memberDeleteForm'</script>");
				}
				else if(cnt == 1) {
					out.print("<script>alert('정상적으로 탈퇴되었습니다.');location.href='index.jsp'</script>");
					session.invalidate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
//		else if (command.equals("/memberDelete.mb")) { // 삭제
//			String deleteId = (String)session.getAttribute("id");
//			try {
//				cnt = memberDAO.memberDelete(deleteId);
//				session.invalidate();
//				response.sendRedirect("memberList.mb");
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} 
		else if (command.equals("/memberLogin.mb")) { // 로그인
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			try {
				memberDTO = memberDAO.memberLogin(id, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			if (!id.equals(memberDTO.getId())) {
//				out.print("아이디를 확인헤주세요");
//			} else if (!pw.equals(memberDTO.getPw())) {
//				out.print("패스워드가 틀립니다.");
//			} else {
//				out.print(id + "님 환영합니다. 로그인되었습니다.");
//				session = request.getSession();
//				session.setAttribute("id", id);
//			}
			response.sendRedirect("index.jsp");
		} else if (command.equals("/memberLogout.mb")) { // 로그아웃
			session.invalidate();
			response.sendRedirect("index.jsp");
		} else if (command.equals("/idcheckconfirm.mb")) { // 아이디찾기
			String tel = request.getParameter("tel");
			try {
				memberIdcheck = memberDAO.memberIdcheck(tel);
				out.print("<br>입력하신 전화번호로 검색된 아이디입니다.<br>");
				for (int i = 0; i < memberIdcheck.size(); i++) {
					out.print("<br>" + memberIdcheck.get(i) + "<br>");
				}
				out.print("<br><a href=index.jsp?page=member/loginForm>로그인하기</a><br>");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (command.equals("/pwcheckconfirm.mb")) { // 비밀번호찾기
			String id = request.getParameter("id");
			try {
				String pw = memberDAO.memberPwcheck(id);
				out.print("<br>회원님의 비밀번호는 '" + pw + "'입니다<br>");
				out.print("<br><a href=index.jsp?page=member/loginForm>로그인하기</a><br>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (command.equals("/memberUpdate.mb")) { //수정확인
			String idSearch = request.getParameter("id");
				try {
					memberDTO = memberDAO.memberUpdateConfirm(idSearch);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp?page=member/memberUpdateConfirm");
				request.setAttribute("memberDTO", memberDTO);
				dis.forward(request, response);
		} else if(command.equals("/memberUpdateFinal.mb")) { //수정최종
			memberDTO.setId(request.getParameter("id"));
			memberDTO.setPw(request.getParameter("pw"));
			memberDTO.setAddr(request.getParameter("addr"));
			memberDTO.setTel(request.getParameter("tel"));
			String idSearch = request.getParameter("idSearch");
			try {
				cnt = memberDAO.memberUpdateFinal(memberDTO,idSearch);
				session.setAttribute("id", memberDTO.getId());
				response.sendRedirect("memberList.mb");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(command.equals("/idcheck.mb")) {
			String idSearch = request.getParameter("id");
			try {
				ResultSet rs = memberDAO.memberIdCheck(idSearch);
				String id = null;
				while(rs.next()){
					id = rs.getString("id");
				}
				if(idSearch.equals(id)){
					out.print("중복된 아이디입니다.");	
				} 
				else {
					out.print("사용가능한 아이디입니다.");
				}
				out.print("<input type='button' value='닫기' onClick='self.close()'>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/selectedList.mb")) {
			String searchOption = request.getParameter("searchOption");
			String word = '%'+request.getParameter("word")+'%';
			out.print(searchOption+" "+word);
		}
	}

}
