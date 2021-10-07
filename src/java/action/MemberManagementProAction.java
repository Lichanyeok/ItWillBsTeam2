package action;

import svc.MemberManagementProService;
import vo.ActionForward;
import vo.MemberBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class MemberManagementProAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;

        MemberBean member = new MemberBean();
        member.setId(request.getParameter("id"));
        member.setName(request.getParameter("name"));
        member.setPasswd(request.getParameter("passwd"));
        member.setGender(request.getParameter("gender"));
        member.setAge(Integer.parseInt(request.getParameter("age")));
        member.setEmail(request.getParameter("email1") + "@" + request.getParameter("email2"));

        MemberManagementProService service = new MemberManagementProService();
        service.ManagementMemberPro(member);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('정보 수정 완료')");
        out.println("</script>");

        HttpSession session = request.getSession();
        session.invalidate();

        forward = new ActionForward();
        forward.setPath("./");
        forward.setRedirect(true);

        return forward;
    }
}
