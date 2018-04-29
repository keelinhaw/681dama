package com.Dama;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;

public class Validate {
//        static Logger log = Logger.getLogger(Validate.class);
     private static final Logger log = LogManager.getLogger(ConnectionManager.class);
        static Connection con = null;
    
	public static boolean checkUser(String username, String password_candidate) throws ClassNotFoundException, SQLException {
		boolean valid = false;
                String stored_hash = "";
		try {
/*			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@dama.cc0rojk8d4jm.us-east-1.rds.amazonaws.com:1521:dama","swe681","SWEpass123");
			String query = "SELECT USERNAME,PASS FROM USERS WHERE USERNAME=? AND PASS=?";
*/
 /*           Class.forName("org.postgresql.Driver");

		GetPropertyValues properties = new GetPropertyValues();
		String dburl = properties.getPropValues("dburl");
                String dbuser = properties.getPropValues("dbuser");
                String dbpassword = properties.getPropValues("dbpassword");
                Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);            
*/
              con = ConnectionManager.getConnection();
        
//            String query = "SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME=? AND PASSWORD=?";
              String query = "SELECT USERNAME,PASSWORD_HASH FROM dama_user WHERE USERNAME=? ";


            
            
			PreparedStatement ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setString(1, username);
//			ps.setString(2, password_candidate);
	        ResultSet rs = ps.executeQuery();
                
                if(rs.next()){                                   
                    stored_hash = rs.getString("password_hash");            
                    valid = PasswordUtil.checkPassword(password_candidate, stored_hash);
                }
	        
	        con.close();
		}
		catch(SQLException e) { 
			log.error("Encountered Exception: " + e);
		}
		
		return valid;
	}
}
