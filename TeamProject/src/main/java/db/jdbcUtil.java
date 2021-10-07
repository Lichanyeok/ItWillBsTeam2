package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


	public class jdbcUtil {
	
		public static Connection getConnection() {
			Connection con = null;
			try {

				Context initCtx = new InitialContext(); 
				DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
				// context.xml È®ÀÎ
				con = ds.getConnection();
				
				con.setAutoCommit(false);//AutoCommit ²û
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return con;
		}
		
		
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

