package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardModifyProService {

	public boolean isArticleWriter(int board_num, String board_pass) {

		boolean isArticleWriter = false;
		
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		
		// BoardDAO 객체의 isBoardArticleWriter() 메서드를 호출하여 패스워드 확인 작업 요청
		// => 파라미터 : 글번호, 패스워드     리턴타입 : boolean(isArticleWriter)
		isArticleWriter = dao.isBoardArticleWriter(board_num, board_pass);
//		System.out.println("isArticleWriter = " + isArticleWriter);
		

		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return isArticleWriter;
	}

	public boolean modifyArticle(BoardBean board) {
//		System.out.println("BoardModifyProService - modifyArticle()");
		
		boolean isModifySuccess = false;
		
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		
		// BoardDAO 객체의 updateArticle() 메서드를 호출하여 글 수정 작업 요청
		// => 파라미터 : BoardBean 객체     리턴타입 : int(updateCount)
		int updateCount = dao.updateArticle(board);
		
		// 삭제 결과 판별
		// updateCount 가 0보다 클 경우 commit 수행 및 isModifySuccess 를 true 로 변경
		if(updateCount > 0) { // 삭제 성공 시
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return isModifySuccess;
	}

}
