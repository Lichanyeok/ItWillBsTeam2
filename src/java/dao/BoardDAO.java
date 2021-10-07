package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardDAO {
	/*
	 * BoardDAO 클래스는 다른 인스턴스에서 공통으로 처리할 DB 작업이 기술되는 클래스이므로
	 * 매번 인스턴스를 생성하기 보다 하나의 인스턴스만 생성한 뒤
	 * 각 요청 객체에서 BoardDAO 객체(인스턴스)를 리턴받아 사용하도록 제한하면 메모리 낭비 적음 
	 * => 단 하나의 유일한 인스턴스를 공유하기 위한 프로그래밍 패턴
	 *    = 싱글톤 디자인 패턴(Singleton Design Pattern)
	 * -------------- 싱글톤 디자인 패턴을 활용한 BoardDAO 인스턴스 작업 ---------------
	 * 1. 외부에서 인스턴스 생성이 불가능하도록 생성자를 private 접근제한자로 선언
	 * 2. 자신의 클래스 내에서 생성하는 인스턴스를 저장하기 위한 멤버변수 선언
	 *    => private 접근제한자로 선언하여 외부로부터 숨김
	 * 3. 인스턴스 생성 후 외부로 리턴하기 위한 Getter 메서드 정의
	 *    => 단, 인스턴스가 생성되지 않았을 경우 인스턴스 생성 후 리턴
	 * 4. 3번 메서드는 인스턴스 생성 후 호출 가능한 메서드이므로
	 *    인스턴스 생성 없이도 호출이 되도록 static 메서드로 선언
	 * 5. static 메서드인 Getter 에서 멤버변수에 접근하기 위해서는
	 *    해당 멤버변수(instance)도 static 변수로 선언해야한다! 
	 */
	private BoardDAO() {}
	
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
		// 기존의 인스턴스가 존재하지 않을 경우에만 인스턴스를 생성
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		// 인스턴스 리턴
		return instance;
	}
	// ========================================================================
	// DB 작업을 수행하기 위해 사용하는 Connection 타입 멤버변수 선언
	Connection con;

	// Connection 객체를 외부로부터 전달받아 저장하기 위한 Setter 메서드 정의
	public void setConnection(Connection con) {
		this.con = con;
	}
	// ========================================================================
	// Service 클래스로부터 비즈니스 로직을 요청받아 처리하는 메서드 정의
	// 글등록 작업을 위한 insertArticle() 메서드 정의
	// => 파라미터 : BoardBean 객체, 리턴타입 : int(insertCount)
	public int insertArticle(BoardBean board) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int num = 1; // 새 글 번호를 저장할 변수 선언
		
		try {
			// 현재 MVC_Board 테이블의 게시물 최대 번호를 조회하여
			// 조회된 결과 값에 + 1 값을 새 글 번호로 지정
			// => 만약, 조회된 게시물이 하나도 없을 경우 새 글 번호는 1번 그대로 사용
			String sql = "SELECT MAX(board_num) FROM mvc_board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 조회된 글 번호가 하나라도 존재할 경우
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			}
			
			// 다음 작업을 위해 PreparedStatement 객체 반환
			// 하나의 메서드에서 복수개의 PreparedStatement 가 생성되는 것을 방지
			close(pstmt);
			
			// 글 등록 작업을 위한 INSERT 작업 수행
			// => 등록일(board_date)은 now() 함수 활용
			sql = "INSERT INTO mvc_board VALUES (?,?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); // 계산된 새 글 번호
			pstmt.setString(2, board.getBoard_name());
			pstmt.setString(3, board.getBoard_pass());
			pstmt.setString(4, board.getBoard_subject());
			pstmt.setString(5, board.getBoard_content());
			pstmt.setString(6, board.getBoard_file());
			pstmt.setString(7, board.getBoard_original_file());
			pstmt.setInt(8, num); // board_re_ref = 참조글 번호 = 새글 번호와 동일하게 설정
			pstmt.setInt(9, 0); // board_re_lev = 들여쓰기 레벨 = 0으로 설정
			pstmt.setInt(10, 0); // board_re_seq = 목록 순서 번호 = 0으로 설정
			pstmt.setInt(11, 0); // board_readcount = 조회수 = 0으로 설정
			
			// INSERT 구문 실행 및 결과 리턴받기 => insertCount 에 저장
			insertCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("insertArticle() 오류 - " + e.getMessage());
		} finally {
			// 자원 반환(주의! Connection 객체는 DAO 에서 반환하지 않도록 해야한다!)
//			if(rs != null) try { rs.close(); } catch(Exception e) {}
//			if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}
	
	
	// 전체 게시물 총 갯수 조회하여 리턴하는 selectListCount() 메서드 정의
	public int selectListCount() {
//		System.out.println("BoardDAO - selectListCount()");
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// => 전체 레코드 갯수를 조회하기 위해 COUNT(*) 함수 사용(또는 COUNT(num))
			String sql = "SELECT COUNT(*) FROM mvc_board";
			pstmt = con.prepareStatement(sql);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1); // 또는 "COUNT(*)" 지정
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}
	
	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		ArrayList<BoardBean> articleList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 조회 시작 게시물(레코드) 번호 계산(= 행 번호 계산)
		int startRow = (page - 1) * limit;
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// => mvc_board 테이블의 모든 레코드 조회
			//    (참조글번호를 기준으로 내림차순, 순서번호를 기준으로 오름차순 정렬)
			// => 단, 시작행번호부터 페이지당 게시물수 만큼만 조회
			//    LIMIT 시작행번호,페이지당게시물수
			String sql = "SELECT * FROM mvc_board "
					+ "ORDER BY board_re_ref DESC,board_re_seq ASC "
					+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow); // 시작행번호
			pstmt.setInt(2, limit); // 페이지당 게시물 수
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// 모든 레코드를 저장할 List 객체(ArrayList) 생성
			articleList = new ArrayList<BoardBean>();
			
			// while 문을 사용하여 ResultSet 객체의 모든 레코드 접근
			while(rs.next()) {
				// BoardBean 객체를 생성하여 1개 레코드 정보를 BoardBean 객체에 저장
				// => 글번호, 작성자, 제목, 날짜, 조회수만 필요
				//    (답글에 대한 들여쓰기를 위해 board_re_lev 값도 추가)
				BoardBean board = new BoardBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_re_lev(rs.getInt("board_re_lev"));
				board.setBoard_date(rs.getDate("board_date"));
				board.setBoard_readcount(rs.getInt("board_readcount"));
				
				// 1개 레코드가 저장된 BoardBean 객체를 List 객체에 추가
				articleList.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
 		} finally {
			// 자원 반환
 			close(rs);
 			close(pstmt);
		}
		
		return articleList;
	}
	
	// 글 상세 정보 조회를 위한 selectArticle() 메서드 정의
	public BoardBean selectArticle(int board_num) {
		BoardBean article = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// => 글번호(board_num)에 해당하는 게시물 상세 정보 조회 후 BoardBean 객체에 저장
			String sql = "SELECT * FROM mvc_board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 조회된 상세 정보를 BoardBean 객체에 저장
				article = new BoardBean();
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_file(rs.getString("board_original_file"));
				article.setBoard_re_ref(rs.getInt("board_re_ref"));
				article.setBoard_re_lev(rs.getInt("board_re_lev"));
				article.setBoard_re_seq(rs.getInt("board_re_seq"));
				article.setBoard_date(rs.getDate("board_date"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
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
	
	// 게시물 조회 수 증가 작업을 수행하는 updateReadcount() 메서드 정의
	public void updateReadcount(int board_num) {
		PreparedStatement pstmt = null;
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// => mvc_board 테이블의 레코드 중 전달받은 글번호(board_num)에 해당하는 레코드의
			//    board_readcount 값을 1 만큼 증가시키기
			String sql = "UPDATE mvc_board SET board_readcount=board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			// 4단계. SQL 구문 실행
			pstmt.executeUpdate();
			
			commit(con); // 무조건 commit 작업 수행
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(pstmt);
		}
		
	}
	
	
	// 글 삭제 작업을 위한 패스워드 확인을 수행하는 isBoardArticleWriter() 메서드 정의
	public boolean isBoardArticleWriter(int board_num, String board_pass) {
		boolean isArticleWriter = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// => board_num 에 해당하는 레코드의 board_pass 조회
			String sql = "SELECT board_pass FROM mvc_board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 조회된 패스워드와 입력받은 패스워드가 일치할 경우
				// isArticleWriter 를 true 로 변경
				if(board_pass.equals(rs.getString("board_pass"))) {
					isArticleWriter = true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(rs);
			close(pstmt);
		}
		
		return isArticleWriter;
	}
	
	// 글 삭제 작업을 수행하는 deleteArticle() 메서드 정의
	public int deleteArticle(int board_num) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// => board_num 에 해당하는 레코드 삭제
			String sql = "DELETE FROM mvc_board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			deleteCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(pstmt);
		}
		
		return deleteCount;
	}
	
	// 글 수정 작업을 수행하는 updateArticle() 메서드 정의
	public int updateArticle(BoardBean board) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 글번호(board_num)에 해당하는 레코드의 작성자, 제목, 내용 수정(UPDATE)
			String sql = "UPDATE mvc_board "
					+ "SET board_name=?,board_subject=?,board_content=? "
					+ "WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getBoard_name());
			pstmt.setString(2, board.getBoard_subject());
			pstmt.setString(3, board.getBoard_content());
			pstmt.setInt(4, board.getBoard_num());
			
			// UPDATE 구문 실행 및 결과 리턴받기 => updateCount 에 저장
			updateCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("updateArticle() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	// 답변글 등록 작업을 위한 insertReplyArticle() 메서드 정의
	public int insertReplyArticle(BoardBean board) {
		System.out.println("BoardDAO - insertReplyArticle()");
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int num = 1; // 새 글 번호를 저장할 변수 선언
		
		// 게시물 순서 지정에 사용될 값들 변수에 미리 저장오기
		int board_re_ref = board.getBoard_re_ref();
		int board_re_lev = board.getBoard_re_lev();
		int board_re_seq = board.getBoard_re_seq();
		
		System.out.println("board_re_ref : " + board_re_ref);
		System.out.println("board_re_lev : " + board_re_lev);
		System.out.println("board_re_seq : " + board_re_seq);
		
		try {
			// 현재 MVC_Board 테이블의 게시물 최대 번호를 조회하여
			// 조회된 결과 값에 + 1 값을 새 글 번호로 지정
			// => 만약, 조회된 게시물이 하나도 없을 경우 새 글 번호는 1번 그대로 사용
			String sql = "SELECT MAX(board_num) FROM mvc_board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 조회된 글 번호가 하나라도 존재할 경우
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			}
			
			// 다음 작업을 위해 PreparedStatement 객체 반환
			// 하나의 메서드에서 복수개의 PreparedStatement 가 생성되는 것을 방지
			close(pstmt);
			
			
			// ------------------------------------------------------------------
			// 새 글(답글) 등록 전 기존 게시물에 대한 순서번호 증가 처리 작업
			// => 원본 게시물의 참조글번호(board_re_ref)와 같은 게시물들 중
			//    원본 게시물의 순서번호(board_re_seq)보다 큰 순서번호를 갖는 
			//    게시물들의 순서번호를 모두 1씩 증가시킴(+1)
			sql = "UPDATE mvc_board SET board_re_seq=board_re_seq+1 "
					+ "WHERE board_re_ref=? AND board_re_seq>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_re_ref); // 원본글의 참조글번호
			pstmt.setInt(2, board_re_seq); // 원본글의 순서번호
			
			int updateCount = pstmt.executeUpdate();
			if(updateCount > 0) {
				commit(con);
			} else {
				rollback(con);
			}
			
			// 새 글(답글)에 지정할 들여쓰기 레벨과 순서 번호를 원본글 값 + 1 수행
//			board_re_lev++;
//			board_re_seq++;
			// => 구문 파라미터 채울 때 직접 + 1 값을 전달해도 무관함
			
			close(pstmt); // 다음 작업을 위한 PreparedStatement 객체 반환
			// ------------------------------------------------------------------
			// 글 등록 작업을 위한 INSERT 작업 수행
			// => 등록일(board_date)은 now() 함수 활용
			sql = "INSERT INTO mvc_board VALUES (?,?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); // 계산된 새 글 번호
			pstmt.setString(2, board.getBoard_name());
			pstmt.setString(3, board.getBoard_pass());
			pstmt.setString(4, board.getBoard_subject());
			pstmt.setString(5, board.getBoard_content());
			// 답글에서 파일 업로드 기능은 제외시켰으므로 파일명은 널스트링("")으로 설정
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setInt(8, board_re_ref); // 참조글 번호 = 원본 글의 참조글번호와 동일
			pstmt.setInt(9, board_re_lev + 1); // 들여쓰기 레벨 = 원본 글의 레벨 + 1 로 설정한 값 사용
			pstmt.setInt(10, board_re_seq + 1); // 목록 순서 번호 = 원본 글의 순서번호 + 1 로 설정한 값 사용
			pstmt.setInt(11, 0); // board_readcount = 조회수 = 0으로 설정
			
			// INSERT 구문 실행 및 결과 리턴받기 => insertCount 에 저장
			insertCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("insertArticle() 오류 - " + e.getMessage());
		} finally {
			// 자원 반환(주의! Connection 객체는 DAO 에서 반환하지 않도록 해야한다!)
//			if(rs != null) try { rs.close(); } catch(Exception e) {}
//			if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}

}












