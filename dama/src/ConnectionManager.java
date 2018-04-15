
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chadia
 */
public class ConnectionManager {
    
    static Logger log = Logger.getLogger(ConnectionManager.class);
    static Connection con  = null;
    
    public static Connection getConnection() throws SQLException{
        
        try{  	
                GetPropertyValues properties = new GetPropertyValues();
		String dburl = properties.getPropValues("dburl");
                String dbuser = properties.getPropValues("dbuser");
                String dbpassword = properties.getPropValues("dbpassword");

                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection (dburl,dbuser,dbpassword);
        } catch ( IOException e){
            
            log.error("Error establishing DB connection. Encounter error in reading DB properties from config file");
            throw new SQLException("Error reading DB properties from config file",e);
        }
        catch (ClassNotFoundException e){
            log.error("Error loading JDBC driver");
            throw new SQLException("Error loading JDBC driver",e);
        }
         
        return con ;
        
        
    }
    
        public static void closeConnection() {
            
            if ( !isClosed() ) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("Error closing DB connection");
                }
                
            }
        }
    
    public static boolean isClosed() {
        boolean closed = true;
        if (con == null)
            closed = true;
        else{
            try{ 
                closed = con.isClosed();
            }
            catch(Exception e){
                log.debug("Eception while checking if DB connections is closed");
            }
        }
        
        return closed;
    }
 
}
