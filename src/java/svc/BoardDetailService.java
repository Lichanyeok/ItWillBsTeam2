package svc;

import vo.BoardBean;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardDetailService {

	// 게시물 1개 상세 정보 조회를 요청하는 getArticle() 메서드 정의
	public BoardBean getArticle(int board_num) {
		BoardBean article = null;
		
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// 글 상세 정보 조회를 위해 BoardDAO 객체의 selectArticle() 메서드 호출
		// => 파라미터 : 글번호(board_num)  리턴타입 : BoardBean(article)
		article = dao.selectArticle(board_num);

		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return article;
	}

	// 조회수 증가 작업 요청을 위한 increaseReadcount() 메서드 정의
	public void increaseReadcount(int board_num) {
//		System.out.println("BoardDetailService - increaseReadcount()");
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// 글번호에 대한 조회수 증가를 위해 BoardDAO 객체의 updateReadcount() 메서드 호출
		// => 파라미터 : 글번호(board_num)  리턴타입 : void
		dao.updateReadcount(board_num);

		// 공통작업-4. Connection 객체 반환
		close(con);
		
	}

	
}













