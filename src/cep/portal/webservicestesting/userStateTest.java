package cep.portal.webservicestesting;

import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @Description: This is used to test Portal web services. 
 * 
 * @author t_marus
 *
 */
public class userStateTest extends EISWebService{

	/**
	 * Get the below info from JVM args
	 */
	//Always ensure that properties file name should be name of the java class file.
	// This way we can avoid giving too many JVM args at runtime.
	// I feel giving a rule her,e will be good instead of giving whole bunch of jvm agrs on fly
	private static String propertiesFileName = Thread.currentThread().getClass().getName()+".properties";
	private Properties testProperties = readPropertiesFile(propertiesFileName);
	
	private static String environment= System.getProperty("environment");
	private static String passWord	=null;
	private static String userName= null;
	
	//Static block to get the user name and password
	//The suer name and password are hard coded here as the 
	{
		userName=testProperties.getProperty("userName");
		passWord=testProperties.getProperty("passWord");
	}

	public userStateTest() {
		super(environment, userName, passWord);
	}
	
	@BeforeClass
	public void setupBaseState() throws Exception {
		loginToCustomerPortal(environment);
		
		
		
	}

	@Test
	public void testIgnoreProductUpdatesService()  throws Exception{
		
		
		
	}
	

	

}
