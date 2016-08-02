package epartner;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.EISTestBase;
import common.Oppty;
import common.Page_;
import common.Util;

/**
 * Test class - TestCreateOpptyInSFDC
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class TestCreateOpptyInSFDC extends EpartnerTestBase {
	public TestCreateOpptyInSFDC() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce(getAppServerBaseURL());
	}

	@Test
	public void Test_CreateOpptyInSFDC() throws Exception {
		
		loginAsAutoUser(true);
		
		Oppty oppty = utilCreateOppty();
		Page_ viewOpptyPage = oppty.getViewOpptyPage();
		
		String opptyName = viewOpptyPage.getValueFromGUI("opptyName");
		String opptyNumber = viewOpptyPage.getValueFromGUI("opptyNumber");
		Util.printInfo("Oppty Number is:"+opptyNumber);
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("OpptyNumber", opptyNumber);
		Util.writeToExcel(testProperties.getConstant("OUTPUT_FILENAME"), map);
	}

	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
}
