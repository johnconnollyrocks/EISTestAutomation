package mja;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mja.SubscriptionRenewal.CaseName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;

/**
 * Test class - TestVerifySameOppytNumMultipleAgreements
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifySameOppytNumMultipleAgreements extends MJATestBase {
	public TestVerifySameOppytNumMultipleAgreements() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_SameOpptyNumMultipleAgreements() throws Exception {

		String currentDate = null;
		String lastModifiedTimestamp = null;
		Date date;
		Calendar cal;
		DateFormat dateFormat;
		Object lastModifiedTime = null;
		String[] currentDateList = null;
		String[] lastModifiedTimeList = null;
		List opportunityNumberArray;
		String masterOppurtunity = null;
		String childOpportunity = null;
		String additionalAgreementNumbersInMasterAgreement = null;
		String additionalAgreementNumbersInChildAgreement = null;
		List additionalAgreementNumbersInMasterAgreementList = new ArrayList();
		List additionalAgreementNumbersInChildAgreementList = new ArrayList();
		boolean masterAgreementExistsInMasterAgreementList;
		boolean masterAgreementExistsInChildAgreementList;
		boolean childAgreementExistsInMasterAgreementList;
		boolean childAgreementExistsInChildAgreementList;
		String masterAgreement = null;
		String childAgreement = null;
		
		CaseName caseName = getCaseName(MJAConstants.MJA_CASE_NAME_ENUM_CONSTANT_NAME);
		// Can include Selenium and WebDriver commands - but please don't!
		loginAsAutoUser();

		// NOTES TO OFFSHORE - verifications should be done in the test method,
		// not the "base" class or the SFDCObject subclass
		SubscriptionRenewal subRenewal = utilCreateSubscriptionRenewal(caseName);
		Page_ createSubscriptionRenewalPage = subRenewal
				.getCreateSubscriptionRenewalPage();
		Page_ viewOpptyPage = subRenewal.getViewOpptyPage();
		Page_ viewServiceContractPage = subRenewal.getViewServiceContractPage();
		lastModifiedTimestamp = subRenewal.getLastModifiedTimestamp();

		date = new Date(lastModifiedTimestamp);

		cal = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		currentDate = dateFormat.format(cal.getTime());
		lastModifiedTime = dateFormat.format(date);

		currentDateList = currentDate.split(" ");
		lastModifiedTimeList = ((String) lastModifiedTime).split(" ");

		// Last modified date assertion
		assertEquals(viewServiceContractPage.getName(), "LastModifiedTime", lastModifiedTimeList[0], currentDateList[0]);
		// assertLesser(currentDateList[1]),lastModifiedTimeList[1]);

		opportunityNumberArray = subRenewal.verifyOpptyNumMultipleAgreements();
		masterOppurtunity = (String) opportunityNumberArray.get(0);
		childOpportunity = (String) opportunityNumberArray.get(1);

		// Opportunity number assertion
		assertEquals(viewServiceContractPage.getName(), "Opportunity Number", masterOppurtunity, childOpportunity);

		// Agreement Number assertion
		assertEquals(viewOpptyPage.getName(), "AgreementNumber in Master Agreement", subRenewal.getAgreementNumberInMasterAgreement(),
				subRenewal.getMasterAgreement());
		assertEquals(viewOpptyPage.getName(), "AgreementNumber in Child Agreement", subRenewal.getAgreementNumberInChildAgreement(),
				subRenewal.getMasterAgreement());

		masterAgreement = subRenewal.getMasterAgreement();
		childAgreement = subRenewal.getChildAgreement();

		additionalAgreementNumbersInMasterAgreement = subRenewal
				.getAdditionalAgreementNumbersInMasterAgreement();
		String[] additionalAgreementNumbersInMaster = additionalAgreementNumbersInMasterAgreement
				.split(", ");
		for (int i = 0; i < additionalAgreementNumbersInMaster.length; i++) {
			additionalAgreementNumbersInMasterAgreementList
					.add(additionalAgreementNumbersInMaster[i]);
		}
		// Count assertion in Master Agreement List
		assertEquals(viewOpptyPage.getName(), "Additional Agreement Count in Master Agreement", additionalAgreementNumbersInMasterAgreementList.size(), 2);

		// assert if masterAgreementExistsInMasterAgreementList
		masterAgreementExistsInMasterAgreementList = additionalAgreementNumbersInMasterAgreementList
				.contains(masterAgreement);
		assertEquals(viewOpptyPage.getName(), "Master Agreement Exists in Master Additional Agreement Numbers List", masterAgreementExistsInMasterAgreementList, true);

		// assert if childAgreementExistsInMasterAgreementList
		childAgreementExistsInMasterAgreementList = additionalAgreementNumbersInMasterAgreementList
				.contains(childAgreement);
		assertEquals(viewOpptyPage.getName(), "Child Agreement Exists in Master Additional Agreement Numbers List", childAgreementExistsInMasterAgreementList, true);

		additionalAgreementNumbersInChildAgreement = subRenewal
				.getAdditionalAgreementNumbersInChildAgreement();
		String[] additionalAgreementNumbersInChild = additionalAgreementNumbersInChildAgreement
				.split(", ");
		for (int i = 0; i < additionalAgreementNumbersInChild.length; i++) {
			additionalAgreementNumbersInChildAgreementList
					.add(additionalAgreementNumbersInChild[i]);
		}
		// Count assertion in Child Agreement List
		assertEquals(viewOpptyPage.getName(), "Additional Agreement Count in Child Agreement", additionalAgreementNumbersInChildAgreementList.size(), 2);

		// assert if masterAgreementExistsInChildAgreementList
		masterAgreementExistsInChildAgreementList = additionalAgreementNumbersInChildAgreementList
				.contains(masterAgreement);
		assertEquals(viewOpptyPage.getName(), "Master Agreement Exists in Child Additional Agreement Numbers List", masterAgreementExistsInChildAgreementList, true);

		// assert if childAgreementExistsInChildAgreementList
		childAgreementExistsInChildAgreementList = additionalAgreementNumbersInChildAgreementList
				.contains(childAgreement);
		assertEquals(viewOpptyPage.getName(), "Child Agreement Exists in Child Additional Agreement Numbers List", childAgreementExistsInChildAgreementList, true);

		
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
