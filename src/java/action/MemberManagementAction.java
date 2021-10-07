package action;

import svc.MemberJoinProService;
import svc.MemberManagementService;
import vo.ActionForward;
import vo.MemberBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MemberManagementAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;

        MemberBean article = new MemberBean();
        String passwd = request.getParameter("passwd");

        MemberManagementService service = new MemberManagementService();
        boolean isManagementSuccess = service.checkMember(passwd);

        if (!isManagementSuccess) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('비밀번호 확인')");
            out.println("history.back()");
            out.println("</script>");
        } else { //
            article = service.getArticle(passwd);
            request.setAttribute("article", article);
            forward = new ActionForward();
            forward.setPath("/member/member_managementPro_form.jsp");
        }

        return forward;

    }
}
