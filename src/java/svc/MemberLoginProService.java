package svc;

import dao.MemberDAO;
import vo.MemberBean;

import java.sql.Connection;

import static db.JdbcUtil.*;

public class MemberLoginProService {
    public boolean loginMember(MemberBean member) {
        boolean isLoginSuccess = false;

        Connection con = getConnection();

        MemberDAO dao = MemberDAO.getInstance();

        dao.setConnection(con);

        int insertCount = dao.selectMember(member);

        if (insertCount > 0) {
            commit(con);
            isLoginSuccess = true;
        } else {
            rollback(con);
        }

        return isLoginSuccess;
    }
}
