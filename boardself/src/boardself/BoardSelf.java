package boardself;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BoardSelf {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try { // 선결조건
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // 선결조건
		Connection conn = null;

		while (true) {
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "pnj0213", "dkdlxl");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("===== 게시판작성 =====");
			System.out.println("R:등록 S:검색 D:삭제 U:수정 L:목록");
			char ch = sc.next().charAt(0);

			if (ch == 'R' || ch == 'r') { // 등록
				System.out.println("제목|내용");
				String titleContent = sc.next();
				int indexI = titleContent.indexOf("|");
				String title = titleContent.substring(0, indexI);
				String content = titleContent.substring(indexI + 1);
				System.out.println("작성자 : ");
				String author = sc.next();
				System.out.println("날짜 :");
				String nal = sc.next();
				System.out.println("조회수 : ");
				int readCount = sc.nextInt();

				try {
					Statement stmt = conn.createStatement();
					String sql = "insert into BOARDSELF(no, title, content, author, nal, readcount) values(boardself_no.nextval,'"
							+ title + "','" + content + "','" + author + "','" + nal + "'," + readCount + ")";

					int cnt = stmt.executeUpdate(sql);
					System.out.println(cnt + "건 게시글이 등록되었습니다.");
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // 등록 끝
			else if (ch == 'S' || ch == 's') { // 검색
				System.out.println("찾는 게시글 제목을 입력해주세요.");
				String titleSearch = sc.next();
				System.out.print("번호\t제목\t내용\t작성자\t날짜\t조회수\n");

				try {
					Statement stmt = conn.createStatement();
					String sql = "SELECT no, title, content, author, nal, readCount from boardself WHERE TITLE = '"
							+ titleSearch + "'";
					ResultSet rs = stmt.executeQuery(sql);
					int readCount = 0;
					while (rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						readCount = rs.getInt("readCount");

						System.out.println(
								no + "\t" + title + "\t" + content + "\t" + author + "\t" + nal + "\t" + readCount);

					}
					readCount++;
					stmt = conn.createStatement();
					sql = "UPDATE BOARDSELF SET readcount = " + readCount + "WHERE title ='" + titleSearch + "'";
					stmt.executeUpdate(sql);

					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} // 검색 끝
			else if (ch == 'D' || ch == 'd') { // 삭제
				System.out.println("삭제할 게시글 제목을 입력하세요.");
				String titleDelete = sc.next();

				try {
					Statement stmt = conn.createStatement();
					String sql = "DELETE from boardself WHERE TITLE = '" + titleDelete + "'";
					int cnt = stmt.executeUpdate(sql);
					System.out.println(cnt + "건이 삭제되었습니다.");

					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} // 삭제 끝
			else if (ch == 'U' || ch == 'u') { // 수정
				System.out.println("변경할 게시글 제목을 입력하세요.");
				String titleSearch = sc.next();

				try {
					Statement stmt = conn.createStatement();
					String sql = "SELECT no, title, content, author, nal, readCount from boardself WHERE TITLE = '"
							+ titleSearch + "'";
					ResultSet rs = stmt.executeQuery(sql);
					System.out.println("===== 변경하기 전 게시글입니다. =====");
					while (rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						int readCount = rs.getInt("readCount");

						System.out.println(
								no + "\t" + title + "\t" + content + "\t" + author + "\t" + nal + "\t" + readCount);
					}
					System.out.println("정말로 변경하시겠습니까 ? ( y / n )");
					char ch2 = sc.next().charAt(0);
					if (ch2 == 'Y' || ch2 == 'y') {
						System.out.println("제목|내용");
						String titleContent = sc.next();
						int indexI = titleContent.indexOf("|");
						String titleUpdate = titleContent.substring(0, indexI);
						String contentUpdate = titleContent.substring(indexI + 1);
						System.out.println("작성자 : ");
						String authorUpdate = sc.next();
						System.out.println("날짜 :");
						String nalUpdate = sc.next();
						System.out.println("조회수 : ");
						int readCountUpdate = sc.nextInt();

						stmt = conn.createStatement();
						sql = "UPDATE BOARDSELF SET TITLE = '" + titleUpdate + "', CONTENT = '" + contentUpdate
								+ "', AUTHOR = '" + authorUpdate + "', NAL = '" + nalUpdate + "', READCOUNT ='"
								+ readCountUpdate + "'WHERE TITLE = '" + titleSearch + "'";
						int cnt = stmt.executeUpdate(sql);
						System.out.println(cnt + "건의 게시글이 수정되었습니다.");

						stmt.close();
						conn.close();
					} else {
						stmt.close();
						conn.close();

						continue;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} // 수정 끝
			else if (ch == 'L' || ch == 'l') { // 전체목록
				System.out.print("번호\t제목\t내용\t작성자\t날짜\t조회수\n");

				try {
					Statement stmt = conn.createStatement();
					String sql = "SELECT no, title, content, author, nal, readCount from boardself";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						int readCount = rs.getInt("readCount");

						System.out.println(
								no + "\t" + title + "\t" + content + "\t" + author + "\t" + nal + "\t" + readCount);
					}
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} // 전체목록 끝
		}

	}

}
