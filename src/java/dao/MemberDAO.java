package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.BoardBean;
import vo.MemberBean;

import static db.JdbcUtil.*;

public class MemberDAO {
	// -----------------------------------------------------
	private static final MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {}

	public static MemberDAO getInstance() {
		return instance;
	}
	// -----------------------------------------------------
	Connection con = null;

	public void setConnection(Connection con) {
		this.con = con;
	}
	// -----------------------------------------------------
	// 회원 등록 작업을 수행하는 insertMember() 메서드 정의
	public int insertMember(MemberBean member) {
		int insertCount = 0;
		
		// INSERT 작업을 통해 회원 가입 정보(이름, 성별, 나이, 이메일, 아이디, 패스워드) 추가
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO mvc_member VALUES(null,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getGender());
			pstmt.setInt(3, member.getAge());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getId());
			pstmt.setString(6, member.getPasswd());
			
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("MemberDAO - insertMember() 오류 발생!");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}

	public int selectMember(MemberBean member) {
		int insertCount = 0;
		
		// SELECT 작업을 통해 회원 아이디, 패스워드 조회 후 로그인 결과 판별
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 아이디, 패스워드를 모두 전달하여 결과가 조회되면 성공, 아니면 실패
			String sql = "SELECT id FROM mvc_member WHERE id=? AND passwd=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 조회된 id 값이 있을 경우 로그인 성공
				insertCount += 1;
			}
			
		} catch (SQLException e) {
			System.out.println("MemberDAO - selectMember() 오류 발생!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}

	public int menagementMember(String passwd) {
		int insertCount = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM mvc_member WHERE passwd=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, passwd);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				insertCount += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return insertCount;
	}

	public MemberBean selectArticle(String passwd) {
		MemberBean article = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 3단계. SQL 구문 작성 및 전달
			// => 글번호(board_num)에 해당하는 게시물 상세 정보 조회 후 BoardBean 객체에 저장
			String sql = "SELECT * FROM mvc_member WHERE passwd=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, passwd);

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			if(rs.next()) {
				// 조회된 상세 정보를 BoardBean 객체에 저장
				article = new MemberBean();
				article.setName(rs.getString("name"));
				article.setGender(rs.getString("gender"));
				article.setAge(rs.getInt("age"));
				article.setEmail(rs.getString("email"));
				article.setId(rs.getString("id"));
				article.setPasswd(rs.getString("passwd"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(rs);
			close(pstmt);
		}

		return article;
	}

	public int managementMember(MemberBean member) {
		int updateCount = 0;

		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE mvc_member SET name=?,passwd=?,gender=?,age=?,email=? WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getGender());
			pstmt.setInt(4, member.getAge());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getId());

			updateCount = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("updateArticle() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}

		return updateCount;
	}
}













