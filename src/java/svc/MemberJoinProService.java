package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.MemberDAO;
import vo.MemberBean;

public class MemberJoinProService {

	public boolean joinMember(MemberBean member) {
		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		dao.setConnection(con);
		
		
		int insertCount = dao.insertMember(member);
		
		// 회원 등록 작업 결과를 리턴받아 판별
		// => 0보다 클 경우 commit 작업 수행 및 isJoinSuccess 를 true 로 변경
		// => 아니면, rollback 작업 수행
		if(insertCount > 0) {
			commit(con);
			isJoinSuccess = true;
		} else {
			rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return isJoinSuccess;
	}
	
}














