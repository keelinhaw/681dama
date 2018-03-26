import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validate {
	public static boolean checkUser(String username, String password_candidate) throws ClassNotFoundException, SQLException {
		boolean valid = false;
		try {
/*			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@dama.cc0rojk8d4jm.us-east-1.rds.amazonaws.com:1521:dama","swe681","SWEpass123");
			String query = "SELECT USERNAME,PASS FROM USERS WHERE USERNAME=? AND PASS=?";
*/
            Class.forName("org.postgresql.Driver");

		GetPropertyValues properties = new GetPropertyValues();
		String dburl = properties.getPropValues("dburl");
                String dbuser = properties.getPropValues("dbuser");
                String dbpassword = properties.getPropValues("dbpassword");
                Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);            

//            String query = "SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME=? AND PASSWORD=?";
              String query = "SELECT USERNAME,PASSWORD_HASH FROM damauser WHERE USERNAME=?";


            
            
			PreparedStatement ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setString(1, username);
//			ps.setString(2, password_candidate);
	        ResultSet rs = ps.executeQuery();
	        valid = rs.next();
                
                String stored_hash = rs.getString("password_hash");
                PasswordUtil.checkPassword(password_candidate, stored_hash);

	        
	        con.close();
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
		
		return valid;
	}
}
