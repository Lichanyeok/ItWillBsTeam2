package action;

import svc.MemberJoinProService;
import vo.ActionForward;
import vo.MemberBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MemberJoinProAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;

// 파라미터 가져와서 MemberBean 객체에 저장
        MemberBean member = new MemberBean();
        member.setName(request.getParameter("name"));
        member.setGender(request.getParameter("gender"));
        member.setAge(Integer.parseInt(request.getParameter("age")));
        // 이메일 주소는 아이디(email1)와 도메인(email2) 결합 필요
        member.setEmail(request.getParameter("email1") + "@" + request.getParameter("email2"));
        member.setId(request.getParameter("id"));
        member.setPasswd(request.getParameter("passwd"));

//        System.out.println(member.toString());

        // MemberJoinProService 클래스의 joinMember() 메서드를 호출하여 회원 가입 작업 요청
        // => 파라미터 : MemberBean 객체  리턴타입 : boolean(isJoinSuccess)

        MemberJoinProService service = new MemberJoinProService();
        boolean isJoinSuccess = service.joinMember(member);

        // 회원 가입 요청 결과가 false 일 경우
        // 자바 스크립트를 사용하여 "회원 가입 실패" 출력 후 이전페이지로 돌아가기
        // 아니면, ActionForward 객체를 통해 루트의 MemberJoinResult.me 서블릿 요청
        // (Redirect 방식 요청)

        if (!isJoinSuccess) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('회원 가입 실패')");
            out.println("history.back()");
            out.println("</script>");
        } else { //
            forward = new ActionForward();
            forward.setPath("MemberJoinResult.md");
            forward.setRedirect(true);
        }

        return forward;

    }
}
