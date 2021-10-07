package svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

// Action 클래스로부터 요청을 받아 DAO 클래스와 상호작용을 통해
// 실제 DB 작업(비즈니스 로직)을 수행하는 클래스
public class BoardWriteProService {
	// Action 클래스로부터 글쓰기 데이터를 전달받아 작업을 수행할 registArticle() 메서드 정의
	// => 파라미터 : BoardBean, 리턴타입 : boolean(isWriteSuccess)
	public boolean registArticle(BoardBean board) {
		// 1. 글쓰기 작업 요청 처리 결과를 저장할 boolean 타입 변수 선언
		boolean isWriteSuccess = false;
		
		// 2. JdbcUtil 객체로부터 Connection Pool 에 저장된 Connection 객체 가져오기(공통) 
		Connection con = getConnection();
		
		// 3. BoardDAO 클래스로부터 생성된 BoardDAO 객체 가져오기(공통)
		BoardDAO dao = BoardDAO.getInstance();
		
		// 4. BoardDAO 객체에서 DB 작업을 수행하기 위해 Connection 객체를 전달하기(공통)
		dao.setConnection(con);
		
		// 5. BoardDAO 객체의 메서드를 호출하여 XXX 작업 수행 및 결과 리턴받기
		// 글쓰기 작업을 수행하기 위해 insertArticle() 메서드 호출
		// => 파라미터 : BoardBean 객체, 리턴타입 : int(insertCount)
		int insertCount = dao.insertArticle(board);
		
		// 6. 작업 수행 결과를 리턴받아 판별 후 결과 처리
		// 1) 작업 성공 시(= insertCount > 0 일 경우) 
		//    => JdbcUtil 객체를 통해 commit 작업 수행, isWriteSuccess 를 true 로 변경
		// 2) 작업 실패 시(= insertCount == 0 일 경우)
		//    => rollback 작업 수행, isWriteSuccess 를 false 로 변경(생략 가능)
		if(insertCount > 0) { // 작업 성공 시
			commit(con);
			isWriteSuccess = true;
		} else { // 작업 실패 시
			rollback(con);
		}
		
		// 7. JdbcUtil 객체로부터 가져온 Connection 객체 반환(공통)
		close(con);
		
		// 8. Action 클래스로 글쓰기 작업 결과 리턴
		return isWriteSuccess;
	}
	
}















