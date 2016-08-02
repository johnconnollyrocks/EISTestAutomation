package bornincloud;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class ToBeDeletedTest extends BornInCloudTestBase {

	
	
	public ToBeDeletedTest() {
		super();
	}

	@Before
	public void launchBrowser() {
		System.out.println("Input from Jenkins: "+System.getProperty("deployServer"));
		System.out.println("Input from Jenkins test: "+System.getProperty("Password_jenkins"));
	}

	@Test
	public void Test_Validate_US28() throws Exception {


	}
	
	
	

	@After
	public void tearDown() throws Exception {

		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
//		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
//		finish();
	}
}
