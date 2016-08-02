package mja;

import java.util.List;
import java.util.ListIterator;

import mja.SubscriptionRenewal.CaseName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;
import common.Util;

/**
 * Test class - TestVerifyActiveExpiredSeatCountOnAgreements
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyActiveExpiredSeatCountOnAgreements extends
		MJATestBase {
	public TestVerifyActiveExpiredSeatCountOnAgreements() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_VerifyActiveExpiredSeatCountOnAgreements()
			throws Exception {

		final String TARGET_ASSET_STATUS = "Registered";
		String contractTableAgreementNumRow = null;
		String agreementActiveSeatCountRow = null;
		String agreementExpiredSeatCountRow = null;
		String agreementActiveSeatCountValue = null;
		String agreementExpiredSeatCountValue = null;
		int statusColumnNum = 0;
		int seatsColumnNum = 0;
		int assetStatusColumnNum = 0;

		// NOTE TO OFFSHORE - I changed a number of these variable names for
		// clarity. I was especially confused by the
		// "arrayOf..." variable names and the use of the name
		// "statusinRelatedList" to refer to an entire row from
		// the contract line related list
		List<List<String>> contractLines = null;
		List<String> contractLine = null;
		
		CaseName caseName = getCaseName(MJAConstants.MJA_CASE_NAME_ENUM_CONSTANT_NAME);
		// Can include Selenium and WebDriver commands - but please don't!
		loginAsAutoUser();

		SubscriptionRenewal subRenewal = utilCreateSubscriptionRenewal(caseName);
		Page_ createSubscriptionRenewalPage = subRenewal
				.getCreateSubscriptionRenewalPage();
		
		Page_ viewServiceContractPage = subRenewal.getViewServiceContractPage();

		String contractTableAgreementNum = "contractTableAgreementNumber";
		String agreementActiveSeatCount = "agreementActiveSeatCount";
		String agreementExpiredSeatCount = "agreementExpiredSeatCount";
		
		// looping 10 agreements to validate the active and expired seat counts
		for (int i = 1; i <= 10; i++) {

			int activeSeatCount = 0;
			int expiredSeatCount = 0;
			int totalActiveSeatCount = 0;
			int totalExpiredSeatCount = 0;
			String totalActiveSeat = null;
			String totalExpiredSeat = null;
			String agreementNumber;

			mainWindow.select();

			contractTableAgreementNumRow = contractTableAgreementNum + i;
			agreementActiveSeatCountRow = agreementActiveSeatCount + i;
			agreementExpiredSeatCountRow = agreementExpiredSeatCount + i;

			agreementNumber=createSubscriptionRenewalPage.getValueFromGUI(contractTableAgreementNumRow);
			
			Util.printInfo("Agreement Number "+i+" is "+ agreementNumber);
			agreementActiveSeatCountValue = createSubscriptionRenewalPage
					.getValueFromGUI(agreementActiveSeatCountRow);
			agreementExpiredSeatCountValue = createSubscriptionRenewalPage
					.getValueFromGUI(agreementExpiredSeatCountRow);

			String locator = createSubscriptionRenewalPage
					.clickAndWaitForPopUpToOpen(contractTableAgreementNumRow);
			viewServiceContractPage.selectWindow(locator);

			contractLines = viewServiceContractPage
					.getRelatedList("contractLineItemsRelatedList");
			statusColumnNum = viewServiceContractPage
					.getRelatedListColumnNum("statusInContractLineItemsRelatedList");
			seatsColumnNum = viewServiceContractPage
					.getRelatedListColumnNum("assetSeatsInContractLineItemsRelatedList");
			assetStatusColumnNum = viewServiceContractPage
					.getRelatedListColumnNum("assetStatusInContractLineItemsRelatedList");
			ListIterator<List<String>> itr = contractLines.listIterator();
			while (itr.hasNext()) {
				contractLine = itr.next();

				if (contractLine.get(statusColumnNum)
						.equalsIgnoreCase("Active")
						&& contractLine.get(assetStatusColumnNum)
								.equalsIgnoreCase(TARGET_ASSET_STATUS)) {
					activeSeatCount = Integer.parseInt(contractLine
							.get(seatsColumnNum));
					totalActiveSeatCount = totalActiveSeatCount
							+ activeSeatCount;

					totalActiveSeat = Integer.toString(totalActiveSeatCount);
					
				}
				if (totalActiveSeat == null) {
					totalActiveSeat = "0";
				}
				if (contractLine.get(statusColumnNum).equalsIgnoreCase(
						"Expired")
						&& contractLine.get(assetStatusColumnNum)
								.equalsIgnoreCase(TARGET_ASSET_STATUS)) {
					expiredSeatCount = Integer.parseInt(contractLine
							.get(seatsColumnNum));
					totalExpiredSeatCount = totalExpiredSeatCount
							+ expiredSeatCount;
					totalExpiredSeat = Integer.toString(totalExpiredSeatCount);
					
				}
				if (totalExpiredSeat == null) {
					totalExpiredSeat = "0";
				}
			}
			
			Util.printDebug("totalActiveSeat in sales force page -------------"
							+ agreementActiveSeatCountValue);
			Util.printDebug("totalExpiredSeat in sales force page-------------"
							+ agreementExpiredSeatCountValue);
			Util.printDebug("totalActiveSeat in Service contract page -------------"
							+ totalActiveSeat);
			Util.printDebug("totalExpiredSeatin Service contract page-------------"
							+ totalExpiredSeat);

			String mainWindowLocator = mainWindow.getLocator();
			viewServiceContractPage.closeAllPopUps(mainWindowLocator);

			assertEquals(createSubscriptionRenewalPage.getName(), "Active Seat count of agreement on row "+i,agreementActiveSeatCountValue, totalActiveSeat);
			assertEquals(createSubscriptionRenewalPage.getName(), "Expired Seat count of agreement on row "+i,agreementExpiredSeatCountValue, totalExpiredSeat);
		}

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
