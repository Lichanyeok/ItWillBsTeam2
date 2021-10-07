package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberLoginProService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberLoginProAction");
		ActionForward forward = null;
		
		// 파라미터 가져와서 MemberBean 객체에 저장
		MemberBean member = new MemberBean();
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		
		// MemberLoginProService 클래스의 loginMember() 메서드를 호출하여 로그인 작업 요청
		// => 파라미터 : MemberBean 객체    리턴타입 : boolean(isLoginSuccess)
		MemberLoginProService service = new MemberLoginProService();
		boolean isLoginSuccess = service.loginMember(member);
		
		// 로그인 요청 결과가 false 일 경우 
		// 자바스크립트를 사용하여 "아이디 또는 패스워드가 틀립니다." 출력 후 이전페이지로 돌아가기
		// 아니면, ActionForward 객체를 통해 메인페이지로 서블릿 요청
		// (Redirect 방식 요청)
		if(!isLoginSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 패스워드가 틀립니다.')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			HttpSession session = request.getSession();

			session.setAttribute("sId", member.getId());

			forward = new ActionForward();
			forward.setPath("./");
			forward.setRedirect(true);

		}
		
		return forward;
	}

}

















