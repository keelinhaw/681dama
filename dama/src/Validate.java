import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validate {
	public static boolean checkUser(String username, String password) throws ClassNotFoundException, SQLException {
		boolean valid = false;
		try {
/*			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@dama.cc0rojk8d4jm.us-east-1.rds.amazonaws.com:1521:dama","swe681","SWEpass123");
			String query = "SELECT USERNAME,PASS FROM USERS WHERE USERNAME=? AND PASS=?";
*/
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/dama","postgres","ISA681DamaP!");
            String query = "SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME=? AND PASSWORD=?";
//TODO:
// remove hardcoded credentials above
            
            
			PreparedStatement ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setString(1, username);
			ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();
	        valid = rs.next();
	        
	        con.close();
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
		
		return valid;
	}
}
