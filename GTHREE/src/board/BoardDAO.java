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
			// "underdogb", "khacademy1!"); //cafe24 배포 이후
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

	// 모든 게시글을 리턴해주는
	public Vector<BoardBean> getAllBoard(int start, int end) {
		// 리넡할 객체 선언
		Vector<BoardBean> v = new Vector<>();
		getConnection();
		try {
			// 쿼리 준비
			String sql = "SELECT * FROM LIB ORDER BY num DESC limit ?, ?";
			// 쿼리를 실행할객체 선언
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start - 1);
			pstmt.setInt(2, end);
			// 쿼리실행 후 결과 저장
			rs = pstmt.executeQuery();
			// 데이터 개수가 몇개인지 모르기에 반복문을 이용하여 데이터를 추출
			while (rs.next()) {
				// 데이터를 패키징( 가방 = Boardbean 클래스를 이용)해줌
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt("num"));
				bean.setFile_id(rs.getString("file_id"));
				bean.setFile_name(rs.getString("file_name"));
				bean.setFile_size(rs.getString("file_size"));
				bean.setUpload_date(rs.getDate("upload_date").toString());
				bean.setAuthor(rs.getString("author"));
				// 패키징한 데이터를 벡터에 저장
				v.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반납
			closed();
		}
		return v;
	}

	public Vector<BoardBean> getDeptBoard(int start, int end, String dept_no) {
		// 리넡할 객체 선언
		Vector<BoardBean> v = new Vector<>();
		getConnection();
		try {
			// 쿼리 준비
			String sql = "SELECT * FROM LIB" + dept_no + " ORDER BY num DESC limit ?, ?";
			// 쿼리를 실행할객체 선언
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start - 1);
			pstmt.setInt(2, end);
			// 쿼리실행 후 결과 저장
			rs = pstmt.executeQuery();
			// 데이터 개수가 몇개인지 모르기에 반복문을 이용하여 데이터를 추출
			while (rs.next()) {
				// 데이터를 패키징( 가방 = Boardbean 클래스를 이용)해줌
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt("num"));
				bean.setFile_id(rs.getString("file_id"));
				bean.setFile_name(rs.getString("file_name"));
				bean.setFile_size(rs.getString("file_size"));
				bean.setUpload_date(rs.getDate("upload_date").toString());
				bean.setAuthor(rs.getString("author"));
				// 패키징한 데이터를 벡터에 저장
				v.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반납
			closed();
		}
		return v;
	}

	// 자원 반납 메소드
	private void closed() {
		try {
			// 자원 반납
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

	// 전체 글의 갯수를 리턴하는 메소드
	public int getAllCount() {
		getConnection();
		// 게시글 전체수를 저장하는 변수
		int count = 0;
		try {
			// 쿼리준비
			String sql = "select count(*) from LIB";
			// 쿼리를 실행할 객체 선언
			pstmt = conn.prepareStatement(sql);
			// 쿼리 실행 후 결과를 리턴
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

	// 검색된 게시글의 개수를 리턴하는 메소드
	public int getCount(String subjectSearch, String keyword) {
		getConnection();
		int count = 0;
		try {
			// 쿼리준비
			String sql = "select count(*) from LIB where " + keyword + " like ?";
			// 쿼리를 실행할 객체 선언
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + subjectSearch + "%");
			// 쿼리 실행 후 결과를 리턴
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

	// 검색
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
			// 데이터를 패키징( 가방 = Boardbean 클래스를 이용)해줌
			BoardBean bean = new BoardBean();
			bean.setNum(rs.getInt("num"));
			bean.setFile_id(rs.getString("file_id"));
			bean.setFile_name(rs.getString("file_name"));
			bean.setFile_size(rs.getString("file_size"));
			bean.setUpload_date(rs.getDate("upload_date").toString());
			bean.setAuthor(rs.getString("author"));
			// 패키징한 데이터를 벡터에 저장
			v.add(bean);
		}
		return v;
	}

}