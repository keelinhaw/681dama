package com.Dama;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Validate {

     private static final Logger log = LogManager.getLogger(ConnectionManager.class);
        static Connection con = null;
    
	public static boolean checkUser(String username, String password_candidate) throws ClassNotFoundException, SQLException {
		boolean valid = false;
                String stored_hash = "";
		try {

              con = ConnectionManager.getConnection();
        
//            String query = "SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME=? AND PASSWORD=?";
              String query = "SELECT USERNAME,PASSWORD_HASH FROM dama_user WHERE USERNAME=? ";

    
			PreparedStatement ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setString(1, username);
//			ps.setString(2, password_candidate);
	        ResultSet rs = ps.executeQuery();
                
                if(rs.next()){               
                    // if user exists, check the password
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
