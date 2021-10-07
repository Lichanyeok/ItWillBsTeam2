package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardModifyFormAction");
		ActionForward forward = null;
		
		// request 객체를 통해 전달받은 파라미터(board_num, page) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page"); 
		// => page 는 현재 불필요하며, 다음페이지로 전달만 하므로 문자열 그대로 사용 가능
		
		// BoardDetailService 클래스 인스턴스 생성 후
		// getArticle() 메서드 호출하여 board_num 에 대한 게시물 상세 정보 조회 요청
		// => 파라미터 : 글번호(board_num)   리턴타입 : BoardBean(article)
		BoardDetailService service = new BoardDetailService();
		BoardBean article = service.getArticle(board_num);
		
		
		// 다음 페이지로 전달하기 위한 객체(article, page)를 request 객체에 저장
		request.setAttribute("article", article);
		request.setAttribute("page", page);
		
		
		// ActionForward 객체를 생성하여 /board/qna_board_modify.jsp 페이지로 포워딩
		// => request 객체를 유지해야하므로 Dispatcher 방식 포워딩
		forward = new ActionForward();
		forward.setPath("/board/qna_board_modify.jsp");
		forward.setRedirect(false); // 생략 가능
		
		return forward;
	}

}
