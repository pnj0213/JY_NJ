package kr.or.kh.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAO {

	private Connection conn;
	private MemberDTO memberDTO;
	private ArrayList<MemberDTO> memberList;
	private ArrayList<String> memberIdcheck;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private String sql;
	private int cnt;

	public MemberDAO() {
		memberDTO = new MemberDTO();
		memberList = new ArrayList<MemberDTO>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://khgthree.cafe24.com:3306/khgthree", "khgthree",
				"wjdqhrydbrdnjs3");
		return conn;
	}

	public int memberRegister(MemberDTO memberDTO) throws SQLException {
		conn = getConnection();
		sql = "insert into JYmember(id,pw,addr,tel) values (?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberDTO.getId());
		pstmt.setString(2, memberDTO.getPw());
		pstmt.setString(3, memberDTO.getAddr());
		pstmt.setString(4, memberDTO.getTel());
		cnt = pstmt.executeUpdate();
		return cnt;
	}

	public ArrayList<MemberDTO> memberList() throws SQLException {
		conn = getConnection();
		sql = "select id,pw,addr,tel from JYmember";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		memberList = new ArrayList<MemberDTO>();
		while (rs.next()) {
			memberDTO = new MemberDTO();
			memberDTO.setId(rs.getString("id"));
			memberDTO.setPw(rs.getString("pw"));
			memberDTO.setAddr(rs.getString("addr"));
			memberDTO.setTel(rs.getString("tel"));
			memberList.add(memberDTO);
		}
		return memberList;
	}

	public int memberDelete(String deleteId, String deletePw) throws SQLException {
		conn = getConnection();
		sql = "delete from JYmember where id=? and pw=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, deleteId);
		pstmt.setString(2, deletePw);
		cnt = pstmt.executeUpdate();
		return cnt;
	}

	public MemberDTO memberLogin(String id, String pw) throws SQLException {
		conn = getConnection();
		sql = "select ID,PW from EMP where ID=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			memberDTO.setId(rs.getString("id"));
			memberDTO.setPw(rs.getString("pw"));
		}
		return memberDTO;
	}

	public ArrayList<String> memberIdcheck(String tel) throws SQLException {
		conn = getConnection();
		sql = "select id from JYmember where tel = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, tel);
		rs = pstmt.executeQuery();
		memberIdcheck = new ArrayList<String>();
		while (rs.next()) {
			memberDTO.setId(rs.getString("id"));
			String id = memberDTO.getId();
			memberIdcheck.add(id);
		}
		return memberIdcheck;
	}

	public String memberPwcheck(String id) throws SQLException {
		conn = getConnection();
		sql = "select pw from JYmember where id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		String pw = null;
		while (rs.next()) {
			pw = rs.getString("pw");
		}
		return pw;
	}

	public MemberDTO memberUpdateConfirm(String idSearch) throws SQLException {
		conn = getConnection();
		sql = "select id, pw, addr, tel from NJmember where id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, idSearch);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			memberDTO.setId(rs.getString("id"));
			memberDTO.setPw(rs.getString("pw"));
			memberDTO.setAddr(rs.getString("addr"));
			memberDTO.setTel(rs.getString("tel"));
		}
		return memberDTO;
	}
	
	public int memberUpdateFinal(MemberDTO memberDTO, String idSearch) throws SQLException {
		conn = getConnection();
		sql = "update JYmember set id=?,pw=?,addr=?,tel=? where id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberDTO.getId());
		pstmt.setString(2, memberDTO.getPw());
		pstmt.setString(3, memberDTO.getAddr());
		pstmt.setString(4, memberDTO.getTel());
		pstmt.setString(5, idSearch);
		cnt = pstmt.executeUpdate();
		return cnt;
	}
	public ResultSet memberIdCheck(String idSearch) throws SQLException {
		conn = getConnection();
		String sql = "select id from JYmember where id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, idSearch);
		rs = pstmt.executeQuery();
		return rs;
	}

}
