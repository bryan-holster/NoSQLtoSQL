package umw.dahlgren.twitter.snapshot;

import java.util.*;
import java.sql.*;

public class User {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://localhost/";

        static String USER;
        static String PASS;
	
	private Connection conn = null;

	public User(String u, String p) {
		USER = u;
		PASS = p;
	}


	public int login() {
		try{
                        Class.forName("com.mysql.jdbc.Driver");

                        System.out.println("Validating MySQL user...");
                        conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.close();
			System.out.println("Finished validating user...");
		}catch(SQLException se){
			System.out.println("Invalid username or password");
                        return 0;
                }catch(Exception e){
                        return 0;
                }
		return 1;
	}   		
}
