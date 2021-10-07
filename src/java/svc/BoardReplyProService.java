package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardReplyProService {

	// 답변글 등록 요청 작업을 위한 replyArticle() 메서드 정의
	public boolean replyArticle(BoardBean board) {
		System.out.println("BoardReplyProService - replyArticle()");
		
		boolean isReplySuccess = false;
		
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		
		// BoardDAO 객체의 insertReplyArticle() 메서드를 호출하여 답변글 등록 작업 수행
		// => 파라미터 : BoardBean 객체   리턴타입 : int(insertCount)
		int insertCount = dao.insertReplyArticle(board);
		
		// insertCount 가 0보다 크면 commit 작업 수행하고 isReplySuccess 를 true 로 변경
		// 아니면, rollback 작업 수행
		if(insertCount > 0) {
			commit(con);
			isReplySuccess = true;
		} else {
			rollback(con);
		}
		
		
		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return isReplySuccess;
	}
	
}














