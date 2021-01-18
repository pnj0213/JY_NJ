package board;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class BoardDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public BoardDAO() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	public void getConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://khgthree.cafe24.com:3306/khgthree?characterEncoding=utf8",
					"khgthree", "wjdqhrydbrdnjs3");
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/underdogb?characterEncoding=utf8",
			// "underdogb", "khacademy1!"); //cafe24 ���� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fileUpload(BoardBean bean) {
		getConnection();
		String sql = "insert into LIB(file_id, emp_no, file_name, file_size, upload_date, author) value(?,?,?,?,now(),?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getFile_id());
			pstmt.setString(2, bean.getEmp_no());
			pstmt.setString(3, bean.getFile_name());
			pstmt.setString(4, bean.getFile_size());
			pstmt.setString(5, bean.getAuthor());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closed();
		}
	}

	public void fileUpload(BoardBean bean, String dept_no) {
		getConnection();
		String sql = "insert into LIB"+dept_no+"(file_id, emp_no, file_name, file_size, upload_date, author) value(?,?,?,?,now(),?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getFile_id());
			pstmt.setString(2, bean.getEmp_no());
			pstmt.setString(3, bean.getFile_name());
			pstmt.setString(4, bean.getFile_size());
			pstmt.setString(5, bean.getAuthor());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closed();
		}
	}

	public void fileDelete(HttpServletRequest request, String file_id) throws SQLException {
		getConnection();
		String sql = "delete from LIB where file_id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, file_id);
		pstmt.executeUpdate();
		String filePath = request.getRealPath("uploadedFile");
		File file = new File(filePath + "/" + file_id);
		file.delete();
	}

	// ��� �Խñ��� �������ִ�
	public Vector<BoardBean> getAllBoard(int start, int end) {
		// ������ ��ü ����
		Vector<BoardBean> v = new Vector<>();
		getConnection();
		try {
			// ���� �غ�
			String sql = "SELECT * FROM LIB ORDER BY num DESC limit ?, ?";
			// ������ �����Ұ�ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start - 1);
			pstmt.setInt(2, end);
			// �������� �� ��� ����
			rs = pstmt.executeQuery();
			// ������ ������ ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ����
			while (rs.next()) {
				// �����͸� ��Ű¡( ���� = Boardbean Ŭ������ �̿�)����
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt("num"));
				bean.setFile_id(rs.getString("file_id"));
				bean.setFile_name(rs.getString("file_name"));
				bean.setFile_size(rs.getString("file_size"));
				bean.setUpload_date(rs.getDate("upload_date").toString());
				bean.setAuthor(rs.getString("author"));
				// ��Ű¡�� �����͸� ���Ϳ� ����
				v.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ڿ� �ݳ�
			closed();
		}
		return v;
	}

	public Vector<BoardBean> getDeptBoard(int start, int end, String dept_no) {
		// ������ ��ü ����
		Vector<BoardBean> v = new Vector<>();
		getConnection();
		try {
			// ���� �غ�
			String sql = "SELECT * FROM LIB" + dept_no + " ORDER BY num DESC limit ?, ?";
			// ������ �����Ұ�ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start - 1);
			pstmt.setInt(2, end);
			// �������� �� ��� ����
			rs = pstmt.executeQuery();
			// ������ ������ ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ����
			while (rs.next()) {
				// �����͸� ��Ű¡( ���� = Boardbean Ŭ������ �̿�)����
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt("num"));
				bean.setFile_id(rs.getString("file_id"));
				bean.setFile_name(rs.getString("file_name"));
				bean.setFile_size(rs.getString("file_size"));
				bean.setUpload_date(rs.getDate("upload_date").toString());
				bean.setAuthor(rs.getString("author"));
				// ��Ű¡�� �����͸� ���Ϳ� ����
				v.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ڿ� �ݳ�
			closed();
		}
		return v;
	}

	// �ڿ� �ݳ� �޼ҵ�
	private void closed() {
		try {
			// �ڿ� �ݳ�
			if (rs != null)
				conn.close();
			if (pstmt != null)
				conn.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ü ���� ������ �����ϴ� �޼ҵ�
	public int getAllCount() {
		getConnection();
		// �Խñ� ��ü���� �����ϴ� ����
		int count = 0;
		try {
			// �����غ�
			String sql = "select count(*) from LIB";
			// ������ ������ ��ü ����
			pstmt = conn.prepareStatement(sql);
			// ���� ���� �� ����� ����
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// �˻��� �Խñ��� ������ �����ϴ� �޼ҵ�
	public int getCount(String subjectSearch, String keyword) {
		getConnection();
		int count = 0;
		try {
			// �����غ�
			String sql = "select count(*) from LIB where " + keyword + " like ?";
			// ������ ������ ��ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + subjectSearch + "%");
			// ���� ���� �� ����� ����
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// �˻�
	public Vector<BoardBean> searchBoard(int pageNum, int pageList, String subjectSearch, String keyword)
			throws SQLException {
		Vector<BoardBean> v = new Vector<>();
		getConnection();
		String sql = "SELECT * FROM LIB where " + keyword + " like ?";
		sql += "ORDER BY num DESC LIMIT ?, ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + subjectSearch + "%");
		pstmt.setInt(2, pageList * (pageNum - 1));
		pstmt.setInt(3, pageList);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			// �����͸� ��Ű¡( ���� = Boardbean Ŭ������ �̿�)����
			BoardBean bean = new BoardBean();
			bean.setNum(rs.getInt("num"));
			bean.setFile_id(rs.getString("file_id"));
			bean.setFile_name(rs.getString("file_name"));
			bean.setFile_size(rs.getString("file_size"));
			bean.setUpload_date(rs.getDate("upload_date").toString());
			bean.setAuthor(rs.getString("author"));
			// ��Ű¡�� �����͸� ���Ϳ� ����
			v.add(bean);
		}
		return v;
	}

}