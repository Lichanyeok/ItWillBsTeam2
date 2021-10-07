package action;

import vo.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;

        HttpSession session = request.getSession();
        session.invalidate();

        forward = new ActionForward();
        forward.setPath("./");
        forward.setRedirect(true);

        return forward;
    }
}
