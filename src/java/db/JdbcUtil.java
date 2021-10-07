package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// DB 관련 작업을 수행하는 JdbcUtil 클래스 정의
// => DB 연결(접속), 해제(자원반환), Commit, Rollback 작업 수행
// => 모든 메서드를 인스턴스 생성 없이도 호출 가능하도록 static 메서드로 선언
public class JdbcUtil {
	// DB 연결 작업을 수행하는 getConnection() 메서드 정의
	public static Connection getConnection() {
		// context.xml 에 설정된 DBCP(커넥션풀)로부터 Connection 객체를 가져와서 리턴
		Connection con = null;
		
		try {
			// 톰캣으로부터 컨텍스트 객체 가져오기
			Context initCtx = new InitialContext(); 
			// context.xml 내의 Resource 태그에 정의된 jdbc/MySQL 커넥션풀 가져오기
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			// 가져온 커넥션풀로부터 Connection 객체 1개 가져오기
			con = ds.getConnection();
			
			// 데이터베이스 작업 단위(트랜잭션)에 대한 처리 시
			// Commit 기능과 Rollback 기능을 수동으로 동작시키기 위해
			// MySQL 의 Auto Commit 기능을 해제해야함(기본값은 오토커밋 동작함)
			// => Connection 객체의 setAutoCommit() 메서드를 호출하여 false 값 전달
			con.setAutoCommit(false);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Connection 객체 리턴
		return con;
	}
	
	// DB 자원(Connection, PreparedStatement, ResultSet) 반환(해제)을 위한 close() 메서드 정의
	// => 하나의 메서드명으로 서로 다른 파라미터를 전달받는 여러 개의 메서드를 정의하기 위해
	//    메서드 오버로딩을 활용하여 메서드 정의
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Auto Commit 기능을 해제했으므로
	// 별도로 Commit, Rollback 작업을 수행할 메서드 정의
	// => 파라미터 : Connection 객체(commit(), rollback() 메서드가 정의되어 있음)
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}












