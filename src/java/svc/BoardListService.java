package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

// db.JdbcUtil 클래스 내의 static 멤버를 모두 import
import static db.JdbcUtil.*;

public class BoardListService {

	// 현재 게시물 총 갯수를 가져와서 리턴하는 작업을 요청하는 getListCount() 메서드 정의
	// => 파라미터 : 없음, 리턴타입 : int(listCount)
	public int getListCount() {
//		System.out.println("BoardListService - getListCount()");
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
//		Connection con = JdbcUtil.getConnection();
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		
		// BoardDAO 객체의 selectListCount() 메서드를 호출하여 게시물 총 갯수 구하기
		// => 파라미터 : 없음, 리턴타입 : int(listCount)
		int listCount = dao.selectListCount();
		System.out.println("listCount = " + listCount);
		
		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return listCount;
	}

	public ArrayList<BoardBean> getArticleList(int page, int limit) {
		// 공통작업-1. Connection Pool 로부터 Connection 객체 가져오기
//		Connection con = JdbcUtil.getConnection();
		Connection con = getConnection();
		
		// 공통작업-2. BoardDAO 클래스로부터 BoardDAO 객체 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);

		// 게시물 목록 조회를 수행하는 getArticleList() 호출
		// => 파라미터 : 페이지번호(page), 게시물 갯수(limit)
		//	  리턴타입 : ArrayList<BoardBean> 객체(articleList)
		ArrayList<BoardBean> articleList = dao.selectArticleList(page, limit);
		
		// 공통작업-4. Connection 객체 반환
		close(con);
		
		return articleList;
	}

}














