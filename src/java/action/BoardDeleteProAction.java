package action;

import svc.BoardDeleteProService;
import vo.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class BoardDeleteProAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;

        int board_num = Integer.parseInt(request.getParameter("board_num"));
        String page = request.getParameter("page");
        String board_pass = request.getParameter("board_pass");

        BoardDeleteProService boardDeleteProService = new BoardDeleteProService();

        boolean isRightUser = boardDeleteProService.isArticleWrite(board_num, board_pass);

        if (!isRightUser) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('삭제 권한 없음'); history.back(); </script>");
        } else {
            boolean isDeleteSuccess = boardDeleteProService.removeArticle(board_num);

            if (!isDeleteSuccess) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('삭제 실패'); history.back(); </script>");
            } else {
                forward = new ActionForward();
                forward.setPath("BoardList.bo?page=" + page);
                forward.setRedirect(true);
            }
        }

        return forward;
    }
}