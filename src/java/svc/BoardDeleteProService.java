package svc;

import dao.BoardDAO;

import java.sql.Connection;

import static db.JdbcUtil.*;

public class BoardDeleteProService {

    public boolean removeArticle(int board_num) {
        boolean isDeleteSuccess = false;

        Connection con = getConnection();

        BoardDAO dao = BoardDAO.getInstance();

        dao.setConnection(con);

        int deleteCount = dao.deleteArticle(board_num);

        if (deleteCount > 0) {
            isDeleteSuccess = true;
            commit(con);
        } else {
            rollback(con);
        }

        close(con);

        return isDeleteSuccess;
    }

    public boolean isArticleWrite(int board_num, String board_pass) {
        boolean isArticleWrite = false;

        Connection con = getConnection();

        BoardDAO dao = BoardDAO.getInstance();

        dao.setConnection(con);

        isArticleWrite = dao.isBoardArticleWriter(board_num, board_pass);

        close(con);

        return isArticleWrite;
    }
}