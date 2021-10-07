package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardModifyProAction");
		ActionForward forward = null;
		
		// 파라미터로 전달받은 데이터 가져오기
		// => 현재페이지번호는 변수에 저장하고
		//    글번호, 작성자, 패스워드, 제목, 내용은 BoardBean 객체에 저장
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page");
		
		// BoardBean 객체에 전달받은 파라미터 저장
		BoardBean board = new BoardBean();
		board.setBoard_num(board_num);
		board.setBoard_name(request.getParameter("board_name"));
		board.setBoard_pass(request.getParameter("board_pass")); // 패스워드는 비교에만 사용
		board.setBoard_subject(request.getParameter("board_subject"));
		board.setBoard_content(request.getParameter("board_content"));
		
		// BoardModifyProService 클래스 인스턴스 생성 후
		// isArticleWrite() 메서드를 호출하여 패스워드 일치 여부 판별(본인 확인)
		// => 파라미터 : 글번호, 패스워드   리턴타입 : boolean(isRightUser)
		BoardModifyProService service = new BoardModifyProService();
		boolean isRightUser = service.isArticleWriter(board_num, board.getBoard_pass());
		
		// 만약, 게시물 삭제 권한이 없는 경우(패스워드가 틀린 경우)
		// => 자바스크립트를 통해 "삭제 권한 없음" 출력하고 이전페이지로 돌아가기
		// 아니면 removeArticle() 메서드를 호출하여 글 삭제 작업 요청
		// => 파라미터 : 글번호    리턴타입 : boolean(isDeleteSuccess)
		if(!isRightUser) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 권한 없음')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			boolean isModifySuccess = service.modifyArticle(board);
			
			// 수정 작업 요청 후 결과를 리턴받아 판별 작업 수행
			// => 삭제 실패 시 자바스크립트를 사용하여 "수정 실패" 출력하고 이전페이지로 돌아가기
			// 아니면, ActionForward 객체를 생성하여 "BoardDetail.bo" 서블릿 주소 요청
			// => 주소 파라미터 : 글번호(board_num), 현재페이지(page), 포워딩 방식 : Redirect 방식
			if(!isModifySuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 실패')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setPath("BoardDetail.bo?board_num=" + board_num + "&page=" + page);
				forward.setRedirect(true);
			}
		}
		
		return forward;
	}

}













