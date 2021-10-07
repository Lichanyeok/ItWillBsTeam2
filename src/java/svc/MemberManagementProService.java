package svc;

import dao.MemberDAO;
import vo.MemberBean;

import java.sql.Connection;

import static db.JdbcUtil.*;

public class MemberManagementProService {

	public void ManagementMemberPro(MemberBean member) {

		Connection con = getConnection();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		dao.setConnection(con);
		
		int updateCount = dao.managementMember(member);
		
		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);

	}
	
}














