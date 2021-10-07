package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardListAction");
		
		// 포워딩 정보 관리를 위한 ActionForward 타입 변수 선언
		ActionForward forward = null;
		
		// 페이징 처리를 위한 변수 선언
		int page = 1; // 현재 페이지 번호를 저장할 변수(기본값 1)
		int limit = 10; // 한 페이지에 표시할 게시물 수를 저장할 변수(최대 10개 설정)
		
		// 만약, page 파라미터가 존재할 경우 해당 파라미터의 값으로 page 변수값 교체
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page")); // 정수화 필요
		}
		
		// BoardListService 클래스 인스턴스 생성 후
		// getListCount() 메서드를 호출하여 전체 게시물 수 조회 작업 요청
		// => 파라미터 : 없음, 리턴타입 : int(listCount)
		BoardListService service = new BoardListService();
		int listCount = service.getListCount();
		
		// BoardListService 클래스의 getArticleList() 메서드를 호출하여 전체 게시물 조회 요청
		// => 파라미터 : page, limit   리턴타입 : ArrayList<BoardBean>(articleList)
		ArrayList<BoardBean> articleList = service.getArticleList(page, limit);
		
		
		// 페이지 계산 작업 수행
		// 1) 전체 페이지 수 계산(총 게시물 수 / 페이지 당 게시물 수 + 0.95 결과를 정수화시킴)
		int maxPage = (int)((double)listCount / limit + 0.95); // 0.95 는 올림처리를 위한 덧셈
		
		// 2) 현재 페이지에서 보여줄 시작 페이지 수(1, 11, 21 페이지 등)
		int startPage = ((int)((double)page / 10 + 0.9) - 1) * 10 + 1;
		
		// 3) 현재 페이지에서 보여줄 마지막 페이지 수(10, 20, 30 페이지 등)
		int endPage = startPage + 10 - 1;
		
		// 4) 마지막 페이지가 현재 페이지에서 표시할 최대 페이지(전체 페이지 수)보다 클 경우
		//    마지막 페이지 번호를 전체 페이지 번호로 대체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 계산된 페이지 정보를 PageInfo 객체에 저장
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		// request 객체에 PageInfo 객체와 ArrayList<BoardBean> 객체 저장
		// => setAttribute() 메서드 사용
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		// ActionForward 객체를 생성하여 board 폴더내의 qna_board_list.jsp 페이지로 포워딩
		// => 게시물 정보를 request 객체에 담아 전달해야하므로 request 객체가 유지되어야하며
		//    요청받은 URL(BoardList.bo)이 유지되어야 함(qna_board_list.jsp 주소 숨김)
		// => 따라서, Dispatcher 방식 포워딩
		forward = new ActionForward();
		forward.setPath("/board/qna_board_list.jsp");
		forward.setRedirect(false);
		
		// ActionForward 객체 리턴
		return forward;
	}

}
















