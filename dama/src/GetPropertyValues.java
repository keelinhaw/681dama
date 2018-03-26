/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chadia
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class GetPropertyValues {
    
    	String result = "";
	InputStream inputStream;
 
	public String getPropValues(String myproperty) throws IOException {
 
            String result="";
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			result = prop.getProperty(myproperty);
                        inputStream.close();
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
/*                finally {
			inputStream.close();
		}
*/
            return result;
	}
    
}
