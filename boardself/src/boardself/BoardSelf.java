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

		try { // ��������
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // ��������
		Connection conn = null;

		while (true) {
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "pnj0213", "dkdlxl");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("===== �Խ����ۼ� =====");
			System.out.println("R:��� S:�˻� D:���� U:���� L:���");
			char ch = sc.next().charAt(0);

			if (ch == 'R' || ch == 'r') { // ���
				System.out.println("����|����");
				String titleContent = sc.next();
				int indexI = titleContent.indexOf("|");
				String title = titleContent.substring(0, indexI);
				String content = titleContent.substring(indexI + 1);
				System.out.println("�ۼ��� : ");
				String author = sc.next();
				System.out.println("��¥ :");
				String nal = sc.next();
				System.out.println("��ȸ�� : ");
				int readCount = sc.nextInt();

				try {
					Statement stmt = conn.createStatement();
					String sql = "insert into BOARDSELF(no, title, content, author, nal, readcount) values(boardself_no.nextval,'"
							+ title + "','" + content + "','" + author + "','" + nal + "'," + readCount + ")";

					int cnt = stmt.executeUpdate(sql);
					System.out.println(cnt + "�� �Խñ��� ��ϵǾ����ϴ�.");
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // ��� ��
			else if (ch == 'S' || ch == 's') { // �˻�
				System.out.println("ã�� �Խñ� ������ �Է����ּ���.");
				String titleSearch = sc.next();
				System.out.print("��ȣ\t����\t����\t�ۼ���\t��¥\t��ȸ��\n");

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

			} // �˻� ��
			else if (ch == 'D' || ch == 'd') { // ����
				System.out.println("������ �Խñ� ������ �Է��ϼ���.");
				String titleDelete = sc.next();

				try {
					Statement stmt = conn.createStatement();
					String sql = "DELETE from boardself WHERE TITLE = '" + titleDelete + "'";
					int cnt = stmt.executeUpdate(sql);
					System.out.println(cnt + "���� �����Ǿ����ϴ�.");

					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} // ���� ��
			else if (ch == 'U' || ch == 'u') { // ����
				System.out.println("������ �Խñ� ������ �Է��ϼ���.");
				String titleSearch = sc.next();

				try {
					Statement stmt = conn.createStatement();
					String sql = "SELECT no, title, content, author, nal, readCount from boardself WHERE TITLE = '"
							+ titleSearch + "'";
					ResultSet rs = stmt.executeQuery(sql);
					System.out.println("===== �����ϱ� �� �Խñ��Դϴ�. =====");
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
					System.out.println("������ �����Ͻðڽ��ϱ� ? ( y / n )");
					char ch2 = sc.next().charAt(0);
					if (ch2 == 'Y' || ch2 == 'y') {
						System.out.println("����|����");
						String titleContent = sc.next();
						int indexI = titleContent.indexOf("|");
						String titleUpdate = titleContent.substring(0, indexI);
						String contentUpdate = titleContent.substring(indexI + 1);
						System.out.println("�ۼ��� : ");
						String authorUpdate = sc.next();
						System.out.println("��¥ :");
						String nalUpdate = sc.next();
						System.out.println("��ȸ�� : ");
						int readCountUpdate = sc.nextInt();

						stmt = conn.createStatement();
						sql = "UPDATE BOARDSELF SET TITLE = '" + titleUpdate + "', CONTENT = '" + contentUpdate
								+ "', AUTHOR = '" + authorUpdate + "', NAL = '" + nalUpdate + "', READCOUNT ='"
								+ readCountUpdate + "'WHERE TITLE = '" + titleSearch + "'";
						int cnt = stmt.executeUpdate(sql);
						System.out.println(cnt + "���� �Խñ��� �����Ǿ����ϴ�.");

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

			} // ���� ��
			else if (ch == 'L' || ch == 'l') { // ��ü���
				System.out.print("��ȣ\t����\t����\t�ۼ���\t��¥\t��ȸ��\n");

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

			} // ��ü��� ��
		}

	}

}
