package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Board {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// 1.로드(적재=자바에게 내가 데이터베이스를 뭘 쓰겠다.)
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} // 얘가 커넥터 실행파일임
		Connection conn = null;
		while (true) { // 반복문
			// 2.연결(connection)연결하다. DriverManager.getConnection
			try {
				conn = DriverManager.getConnection(
						"jdbc:mysql://underdogb.cafe24.com:3306/underdogb?characterEncoding=utf8", "underdogb", "khacademy1!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("===== 게시판작성 =====");
			System.out.println("R:등록 S:검색 D:삭제 U:수정 L:목록");
			char protocol = input.next().charAt(0); // 한개의 문자를 읽어들인다

			if (protocol == 'R' || protocol == 'r') { // 등록
				// 01 234
				System.out.println("제목|내용 :");
				String titleContent = input.next();
				int indexI = titleContent.indexOf("|"); // 제목 내용 분리해서 |위치 찾음
				String title = titleContent.substring(0, indexI); // 제목분리
				String content = titleContent.substring(indexI + 1); // 내용분리
				System.out.println("작성자입력 : ");
				String author = input.next();
				System.out.println("날짜 :");
				String nal = input.next();
				System.out.println("조회수 : ");
				int readcount = input.nextInt();
				// 3.준비(Statement)3-1 공간을 준비
				// 3.준비 3-2 쿼리 준비 //

				try {
					String sql = "insert into board(title,content,author,nal,readcount) values(?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql); // 이거 사용하면 쿼리문이 올라가야함.
					pstmt.setString(1, title); // ->쿼리문의 첫번째 ?에 들어감
					pstmt.setString(2, content);
					pstmt.setString(3, author);
					pstmt.setString(4, nal);
					pstmt.setInt(5, readcount);
					// Statement stmt = conn.createStatement(); // 공간준비
					// String sql = "insert into board(title,content,author,nal,readcount) values('" + title + "','"
					//		+ content + "','" + author + "','" + nal + "'," + readcount + ")";
					// 4.실행
					// int cnt = stmt.executeUpdate(sql); // 정수형으로 리턴된다.
					int cnt = pstmt.executeUpdate();
					System.out.println(cnt + "건 게시글이 등록되었습니다.");
					pstmt.close(); // 문장종료
					conn.close(); // 연결종료
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // 등록
			else if (protocol == 'S' || protocol == 's') {
				System.out.print("찾는 게시글 제목 입력:");
				String searchTitle = input.next();
				System.out.print("번호\t제목\t내용\t작가\t날짜\t\t조회수\n");

				try {
					String sql = "SELECT NO,TITLE,CONTENT,AUTHOR,NAL,READCOUNT FROM board WHERE TITLE = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, searchTitle);
					// String sql = "SELECT NO,TITLE,CONTENT,AUTHOR,NAL,READCOUNT FROM board WHERE TITLE = '" + searchTitle + "'";
					// String sql = "select * from board where title = '"+searchTitle+"'"; //3.준비
					// 3-2 쿼리를 준비
					ResultSet rs = pstmt.executeQuery(); // 테이블(표) 안에 있는 첫 번째, 두 번째 세 번째 ... 열을 가리킨다.
					//ResultSet rs = stmt.executeQuery(sql);
					int readcount = 0;
					while (rs.next()) {// 데이터베이스에서 꺼내서 변수로 집어넣는다. 그래서 DB에서 원하는 데이터를 갖고와야됨. 그래서 rs로 가리켜야 한다.
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						readcount = rs.getInt("readcount");
						System.out.println(no + "\t" + title + "\t" + content + "\t" + author + "\t" + nal + "\t" + readcount);
						readcount++;
						System.out.println();
					}
					sql = "UPDATE board SET readcount = ? WHERE TITLE = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, readcount);
					pstmt.setString(2, searchTitle);
					int cnt = pstmt.executeUpdate();
					//stmt = conn.createStatement();
					//sql = "UPDATE board SET readcount = " + readcount + " WHERE TITLE = '"+searchTitle+"'";
					//int cnt = stmt.executeUpdate(sql);
					pstmt.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (protocol == 'D' || protocol == 'd') { // 삭제
				System.out.println("삭제할 제목을 입력 : ");
				String titleDelete = input.next();
				// 3. 준비
				// 1. 공간을 준비한다.
				// 2. 쿼리를 준비한다.
				try {
					String sql = "delete from board where title = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, titleDelete);
					int cnt = pstmt.executeUpdate();
					//Statement stmt = conn.createStatement():
					//String sql = "delete from board where title = '"+titleDelete+"'";
					//int cnt = stmt.executeUpdate(sql);
					System.out.println(cnt + "건의 게시글이 삭제되었습니다.");
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // 삭제
			else if (protocol == 'U' || protocol == 'u') { // 수정
				System.out.println("변경할 게시글 제목을 입력 : ");
				String titleSearch = input.next();
				ResultSet rs = null;
				PreparedStatement pstmt = null;
				try {
					String sql = "SELECT TITLE, CONTENT, AUTHOR, NAL, READCOUNT FROM board WHERE TITLE = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, titleSearch);
					//Statement stmt = conn.CreateStatement();
					// String sql = "SELECT TITLE, CONTENT, AUTHOR, NAL, READCOUNT FROM board WHERE TITLE = '"+ titleSearch + "'";
					rs = pstmt.executeQuery();
					//ResultSet rs = stmt.executeQuery(sql);
					System.out.println("===== 변경하기 전 게시글입니다. =====");
					while (rs.next()) {
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						int readcount = rs.getInt("readcount");
						System.out.print(title + "\t" + content + "\t" + author + "\t" + nal + "\t" + readcount + "\n");
					}

					System.out.println("정말로 변경하시겠습니까? (y/n)");
					char option = input.next().charAt(0);
					if (option == 'Y' || option == 'y') { // 수정을 하기 위해서 등록에서 한것처럼 다시 다 읽어와야한다
						System.out.println("제목|내용 :");
						String titleContent = input.next();
						int indexI = titleContent.indexOf("|");
						String titleUpdate = titleContent.substring(0, indexI);
						String contentUpdate = titleContent.substring(indexI + 1);
						System.out.println("작성자입력 : ");
						String authorUpdate = input.next();
						System.out.println("날짜 :");
						String nalUpdate = input.next();
						System.out.println("조회수 : ");
						int readcountUpdate = input.nextInt();
						sql = "UPDATE board SET TITLE = ?, CONTENT = ? , AUTHOR =?, NAL = ?, READCOUNT = ? WHERE TITLE = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, titleUpdate);
						pstmt.setString(2, contentUpdate);
						pstmt.setString(3, authorUpdate);
						pstmt.setString(4, nalUpdate);
						pstmt.setInt(5, readcountUpdate);
						pstmt.setString(6, titleSearch);
						
						// stmt = conn.createStatement();
						// sql = "UPDATE board SET TITLE = '" + titleUpdate + "', CONTENT = '" + contentUpdate
						//		+ "', AUTHOR = '" + authorUpdate + "', NAL = '" + nalUpdate + "', READCOUNT ='"
						//		+ readcountUpdate + "'WHERE TITLE = '" + titleSearch + "'";
						int cnt = pstmt.executeUpdate();
						//int cnt = stmt.executeUpdate(sql);
						System.out.println(cnt + "건의 게시글이 수정되었습니다.");
						pstmt.close();
						conn.close();
						rs.close();
					} else {

						pstmt.close();
						conn.close();

						continue; // 수정안할거면 다시 반복문으로 올라가라 ..
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // 수정
			else if (protocol == 'L' || protocol == 'l') { // 목록(전체출력) ============================시험참고====================
				System.out.println("===== 게시판 전체출력 =====");
				System.out.print("번호\t제목\t내용\t작성자\t날짜\t조회수\n");
				// 3. 공간준비
				// 쿼리준비
				try { 
					String sql = "SELECT NO, TITLE, CONTENT, AUTHOR, NAL, READCOUNT FROM board";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					// Statement stmt = conn.createStatement();
					// String sql = "SELECT NO, TITLE, CONTENT, AUTHOR, NAL, READCOUNT FROM board";
					// ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						int readcount = rs.getInt("readcount");
						System.out.print(no + "\t" + title + "\t" + content + "\t" + author + "\t" + nal + "\t"
								+ readcount + "\n");

					}
					pstmt.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // 목록(전체출력)
		} // 반복문
	}
}
