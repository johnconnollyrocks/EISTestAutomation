package mja;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mja.SubscriptionRenewal.CaseName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;

/**
 * Test class - TestVerifySameOppytMultipleAgreements
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifySameOppytMultipleAgreements extends MJATestBase {
	public TestVerifySameOppytMultipleAgreements() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_SameOpptyMultipleAgreements() throws Exception {
		String currentDate;
		
		CaseName caseName = getCaseName(MJAConstants.MJA_CASE_NAME_ENUM_CONSTANT_NAME);
		// Can include Selenium and WebDriver commands - but please don't!
		loginAsAutoUser();

		// NOTES TO OFFSHORE - verifications should be done in the test method,
		// not the "base" class or the SFDCObject subclass
		SubscriptionRenewal subRenewal = utilCreateSubscriptionRenewal(caseName);
		Page_ viewServiceContractPage = subRenewal.getViewServiceContractPage();
		String lastModifiedTimestamp = subRenewal.getLastModifiedTimestamp();

		Date date = new Date(lastModifiedTimestamp);

		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		currentDate = dateFormat.format(cal.getTime());
		Object lastModifiedTime = dateFormat.format(date);

		String[] currentDateList = currentDate.split(" ");
		String[] lastModifiedTimeList = ((String) lastModifiedTime).split(" ");

		assertEquals(viewServiceContractPage.getName(), "LastModifiedTime",lastModifiedTimeList[0], currentDateList[0]);
		// assertLesser(currentDateList[1]),lastModifiedTimeList[1]);

	}

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
