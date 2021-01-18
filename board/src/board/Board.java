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
		// 1.�ε�(����=�ڹٿ��� ���� �����ͺ��̽��� �� ���ڴ�.)
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} // �갡 Ŀ���� ����������
		Connection conn = null;
		while (true) { // �ݺ���
			// 2.����(connection)�����ϴ�. DriverManager.getConnection
			try {
				conn = DriverManager.getConnection(
						"jdbc:mysql://underdogb.cafe24.com:3306/underdogb?characterEncoding=utf8", "underdogb", "khacademy1!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("===== �Խ����ۼ� =====");
			System.out.println("R:��� S:�˻� D:���� U:���� L:���");
			char protocol = input.next().charAt(0); // �Ѱ��� ���ڸ� �о���δ�

			if (protocol == 'R' || protocol == 'r') { // ���
				// 01 234
				System.out.println("����|���� :");
				String titleContent = input.next();
				int indexI = titleContent.indexOf("|"); // ���� ���� �и��ؼ� |��ġ ã��
				String title = titleContent.substring(0, indexI); // ����и�
				String content = titleContent.substring(indexI + 1); // ����и�
				System.out.println("�ۼ����Է� : ");
				String author = input.next();
				System.out.println("��¥ :");
				String nal = input.next();
				System.out.println("��ȸ�� : ");
				int readcount = input.nextInt();
				// 3.�غ�(Statement)3-1 ������ �غ�
				// 3.�غ� 3-2 ���� �غ� //

				try {
					String sql = "insert into board(title,content,author,nal,readcount) values(?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql); // �̰� ����ϸ� �������� �ö󰡾���.
					pstmt.setString(1, title); // ->�������� ù��° ?�� ��
					pstmt.setString(2, content);
					pstmt.setString(3, author);
					pstmt.setString(4, nal);
					pstmt.setInt(5, readcount);
					// Statement stmt = conn.createStatement(); // �����غ�
					// String sql = "insert into board(title,content,author,nal,readcount) values('" + title + "','"
					//		+ content + "','" + author + "','" + nal + "'," + readcount + ")";
					// 4.����
					// int cnt = stmt.executeUpdate(sql); // ���������� ���ϵȴ�.
					int cnt = pstmt.executeUpdate();
					System.out.println(cnt + "�� �Խñ��� ��ϵǾ����ϴ�.");
					pstmt.close(); // ��������
					conn.close(); // ��������
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // ���
			else if (protocol == 'S' || protocol == 's') {
				System.out.print("ã�� �Խñ� ���� �Է�:");
				String searchTitle = input.next();
				System.out.print("��ȣ\t����\t����\t�۰�\t��¥\t\t��ȸ��\n");

				try {
					String sql = "SELECT NO,TITLE,CONTENT,AUTHOR,NAL,READCOUNT FROM board WHERE TITLE = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, searchTitle);
					// String sql = "SELECT NO,TITLE,CONTENT,AUTHOR,NAL,READCOUNT FROM board WHERE TITLE = '" + searchTitle + "'";
					// String sql = "select * from board where title = '"+searchTitle+"'"; //3.�غ�
					// 3-2 ������ �غ�
					ResultSet rs = pstmt.executeQuery(); // ���̺�(ǥ) �ȿ� �ִ� ù ��°, �� ��° �� ��° ... ���� ����Ų��.
					//ResultSet rs = stmt.executeQuery(sql);
					int readcount = 0;
					while (rs.next()) {// �����ͺ��̽����� ������ ������ ����ִ´�. �׷��� DB���� ���ϴ� �����͸� ����;ߵ�. �׷��� rs�� �����Ѿ� �Ѵ�.
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
			} else if (protocol == 'D' || protocol == 'd') { // ����
				System.out.println("������ ������ �Է� : ");
				String titleDelete = input.next();
				// 3. �غ�
				// 1. ������ �غ��Ѵ�.
				// 2. ������ �غ��Ѵ�.
				try {
					String sql = "delete from board where title = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, titleDelete);
					int cnt = pstmt.executeUpdate();
					//Statement stmt = conn.createStatement():
					//String sql = "delete from board where title = '"+titleDelete+"'";
					//int cnt = stmt.executeUpdate(sql);
					System.out.println(cnt + "���� �Խñ��� �����Ǿ����ϴ�.");
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // ����
			else if (protocol == 'U' || protocol == 'u') { // ����
				System.out.println("������ �Խñ� ������ �Է� : ");
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
					System.out.println("===== �����ϱ� �� �Խñ��Դϴ�. =====");
					while (rs.next()) {
						String title = rs.getString("title");
						String content = rs.getString("content");
						String author = rs.getString("author");
						String nal = rs.getString("nal");
						int readcount = rs.getInt("readcount");
						System.out.print(title + "\t" + content + "\t" + author + "\t" + nal + "\t" + readcount + "\n");
					}

					System.out.println("������ �����Ͻðڽ��ϱ�? (y/n)");
					char option = input.next().charAt(0);
					if (option == 'Y' || option == 'y') { // ������ �ϱ� ���ؼ� ��Ͽ��� �Ѱ�ó�� �ٽ� �� �о�;��Ѵ�
						System.out.println("����|���� :");
						String titleContent = input.next();
						int indexI = titleContent.indexOf("|");
						String titleUpdate = titleContent.substring(0, indexI);
						String contentUpdate = titleContent.substring(indexI + 1);
						System.out.println("�ۼ����Է� : ");
						String authorUpdate = input.next();
						System.out.println("��¥ :");
						String nalUpdate = input.next();
						System.out.println("��ȸ�� : ");
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
						System.out.println(cnt + "���� �Խñ��� �����Ǿ����ϴ�.");
						pstmt.close();
						conn.close();
						rs.close();
					} else {

						pstmt.close();
						conn.close();

						continue; // �������ҰŸ� �ٽ� �ݺ������� �ö󰡶� ..
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // ����
			else if (protocol == 'L' || protocol == 'l') { // ���(��ü���) ============================��������====================
				System.out.println("===== �Խ��� ��ü��� =====");
				System.out.print("��ȣ\t����\t����\t�ۼ���\t��¥\t��ȸ��\n");
				// 3. �����غ�
				// �����غ�
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
			} // ���(��ü���)
		} // �ݺ���
	}
}
