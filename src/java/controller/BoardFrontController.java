package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDeleteProAction;
import action.BoardDetailAction;
import action.BoardListAction;
import action.BoardModifyFormAction;
import action.BoardModifyProAction;
import action.BoardReplyFormAction;
import action.BoardReplyProAction;
import action.BoardWriteProAction;
import vo.ActionForward;

/*
 * URL 을 통한 요청 서블릿 주소가 XXX.bo 로 끝날 경우 톰캣(WAS)에 의해
 * 해당 서블릿 주소를 서블릿 클래스인 BoardFrontController 로 매핑하여 해당 요청을 처리
 * => 서블릿 클래스는 HttpServlet 클래스를 상속받아 정의
 */
@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	/*
	 * 서블릿 클래스 내에서 GET 방식 요청과 POST 방식 요청에 따른 doGet(), doPost() 메서드를
	 * 오버라이딩 하고, 두 방식을 공통으로 처리할 doProcess() 메서드를 정의하여
	 * doGet(), doPost() 메서드에서 doProcess() 메서드를 호출
	 */
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardFrontController");
		
		// POST 방식 요청에 대한 한글 처리(UTF-8)
		request.setCharacterEncoding("UTF-8");
		
		// 요청 URL 에 대한 작업을 구분하기 위해 서블릿 주소 추출하여 command 변수에 저장
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		// Action 클래스로부터 리턴받아 포워딩 정보를 관리하는 ActionForward 타입 변수 선언
		ActionForward forward = null;
		// 각 Action 클래스의 인스턴스를 공통으로 관리하는 Action 타입 변수 선언
		Action action = null;
		
		// 추출된 서블릿 주소 판별에 따른 작업 수행
		// - 서블릿 주소가 "/BoardWriteForm.bo" 일 경우 Presentation Logic 수행을 위한
		//   뷰페이지(board 폴더 내의 qna_board_write.jsp) 로 포워딩
		//    => 포워딩 대상이 뷰페이지(*.jsp) 일 경우 
		//       1) ActionForward 객체 생성
		//       2) 포워딩 경로(뷰페이지 위치 = "/서브폴더명/파일명") 지정
		//       3) 포워딩 방식(서블릿 주소를 그대로 유지하고 jsp 파일명을 숨김 = Dispatcher)
		if(command.equals("/BoardWriteForm.bo")) {
			// 글쓰기 작업을 위한 뷰페이지로 포워딩
			forward = new ActionForward();
			forward.setPath("/board/qna_board_write.jsp");
			forward.setRedirect(false); // Dispatcher 방식(기본값이므로 생략 가능)
		} else if(command.equals("/BoardWritePro.bo")) { 
			// 글쓰기 작업 요청을 위한 비즈니스 로직 수행 필요하므로
			// Action 클래스(Controller 역할)에 대한 접근을 위해
			// BoardWriteProAction 클래스 인스턴스 생성 후 execute() 메서드 호출
			// => 파라미터 : request, response 객체
			// => 리턴타입 : ActionForward 타입
			// => 위임받은 Exception 예외 처리(try ~ catch) 필요
//			BoardWriteProAction action = new BoardWriteProAction();

			// XXXAction 객체를 공통으로 관리하기 위해 Action 타입으로 업캐스팅
			action = new BoardWriteProAction();
			
			try {
				// 업캐스팅 후에도 공통 메서드(상속받은 메서드)는 호출이 가능하므로
				// Action 타입으로 execute() 메서드 호출 가능함
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardList.bo")) {
			// 글목록 조회 작업 요청을 위한 비즈니스 로직 수행 필요하므로
			// Action 클래스(Controller 역할)에 대한 접근을 위해
			// BoardListAction 클래스 인스턴스 생성 후 execute() 메서드 호출
			// => 파라미터 : request, response 객체
			// => 리턴타입 : ActionForward 타입
			// => 위임받은 Exception 예외 처리(try ~ catch) 필요
//			BoardListAction action = new BoardListAction();
			
			action = new BoardListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardDetail.bo")) {
			// 글 상세 정보 조회 작업 요청을 위한 비즈니스 로직 수행
			action = new BoardDetailAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardDeleteForm.bo")) {
			// 글삭제 작업을 위한 뷰페이지로 포워딩
			forward = new ActionForward();
			forward.setPath("/board/qna_board_delete.jsp");
			forward.setRedirect(false); // Dispatcher 방식(기본값이므로 생략 가능)
		} else if(command.equals("/BoardDeletePro.bo")) {
			// 글 삭제 작업 요청을 위한 비즈니스 로직 수행
			action = new BoardDeleteProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardModifyForm.bo")) {
			// 글 삭제 폼 요청을 위한 비즈니스 로직 수행
			action = new BoardModifyFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardModifyPro.bo")) {
			// 글 삭제 작업 요청을 위한 비즈니스 로직 수행
			action = new BoardModifyProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardReplyForm.bo")) {
			// 글 답변 등록 폼 요청 작업을 위한 비즈니스 로직 수행
			action = new BoardReplyFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardReplyPro.bo")) {
			// 글 답변 등록 작업 요청을 위한 비즈니스 로직 수행
			action = new BoardReplyProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * -----------------------------------------------------------------------
		 * 작업 수행 후 리턴받은 ActionForward 객체의 정보를 사용하여 
		 * 포워딩 작업을 공통으로 수행
		 * - Redirect 방식과 Dispatcher 방식을 구분하여 각기 다른 방식으로 포워딩
		 * - 필요한 정보
		 *   1) 포워딩 경로(URL)
		 *   2) 포워딩 방식(Redirect or Dispatcher)
		 */
		// 1. ActionForward 객체가 비어있지 않은지 판별
		if(forward != null) {
			// 2. ActionForward 객체 내의 isRedirect 값이 true(= Redirect 방식) 인지 판별
			if(forward.isRedirect()) { // true = Redirect 방식
				// response 객체의 sendRedirect() 메서드를 호출하여 Redirect 방식 포워딩
				// => 파라미터 : ActionForward 객체의 포워딩 경로(path)
				response.sendRedirect(forward.getPath());
			} else { // false = Dispatcher 방식
				// request 객체의 getRequestDispatcher() 메서드를 호출하여 포워딩 경로 설정
				// => 파라미터 : ActionForward 객체의 포워딩 경로(path)
				//    리턴타입 : RequestDispatcher
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				// RequestDispatcher 객체의 forward() 메서드를 호출하여 포워딩 작업 수행
				// => 파라미터 : request, response 객체
				dispatcher.forward(request, response);
			}
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
