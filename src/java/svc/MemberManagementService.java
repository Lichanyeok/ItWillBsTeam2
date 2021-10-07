package svc;

import dao.BoardDAO;
import dao.MemberDAO;
import vo.BoardBean;
import vo.MemberBean;

import java.sql.Connection;

import static db.JdbcUtil.*;

public class MemberManagementService {

	public boolean checkMember(String passwd) {
		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		dao.setConnection(con);

		int insertCount = dao.menagementMember(passwd);
		
		if(insertCount > 0) {
			commit(con);
			isJoinSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isJoinSuccess;
	}

	public MemberBean getArticle(String passwd) {
		MemberBean article = null;

		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
		Connection con = getConnection();

		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		MemberDAO dao = MemberDAO.getInstance();

		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);

		// 글 상세 정보 조회를 위해 BoardDAO 객체의 selectArticle() 메서드 호출
		// => 파라미터 : 글번호(board_num)  리턴타입 : BoardBean(article)
		article = dao.selectArticle(passwd);

		// 공통작업-4. Connection 객체 반환
		close(con);

		return article;
	}
	
}














