package sfdc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;
import common.Util;

/**
 * Test class - TestAuthentication
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestContactValidation extends SFDCTestBase {
	public TestContactValidation() {
		super();
	}

	@Before
	public void setUp() throws Exception {

		launchSFDC(SFDCConstants.BASE_URL);
	}

	@Test
	public void TEST_ViewContact() throws Exception {
		Page_ viewContactsPage;
		List<String> buttons;
		String buttonLabel = null;
		int productCount = 0;
		int buttonCount = 0;
		int buttonVersion = 0;
		int checkYears = 0;

		// Method used to login to the application
		loginAsSFDCUser();

		// NOTES TO OFFSHORE - verifications should be done in the test method,
		// not the "base" class or the SFDCObject subclass
		Contacts contact = utilSearchContacts();

		viewContactsPage = contact.getViewContactsPage();

		ArrayList<String> products = new ArrayList<String>(Arrays.asList(
				"autoCadTable", "autoCadArchitectureTable",
				"autoCadCivilTable", "autoCadCivil3DTable",
				"autoCadElectricalTable",
				"autoCadInventorProfessionalSuiteTable",
				"autoCadInventorSuiteTable", "autoCadLTTable",
				"autoCadMap3DTable", "autoCadMechanicalTable",
				"autoCadMEPTable", "autoCadRasterDesignTable",
				"autoCadRevitArchitectureSuiteTable",
				"autoCadRevitArchitectureVisualizationSuiteTable",
				"autoCadRevitMEPSuiteTable", "autoCadRevitStructureSuiteTable",
				"autoDesk3dsMaxTable", "autoDesk3DSMaxDesignTable",
				"autoDeskBuildingDesignSuitePremiumTable",
				"autoDeskBuildingDesignSuiteUltimateTable",
				"autoDeskInfrastructureMapServerTable",
				"autoDeskNavisworksManageTable",
				"autoDeskNavisworksSimulateTable",
				"autoDeskProductDesignSuitePremiumTable",
				"autoDeskProductDesignSuiteStandardTable",
				"autoDeskProductDesignSuiteUltimateTable",
				"autoDeskRevitArchitectureTable",
				"autoDeskRevitStructureTable",
				"autoDeskRobotStructuralAnalysisProfessionalTable",
				"autoDeskWaultWorkgroupTable", "revitArchitectureTable",
				"revitStructureTable"));

		// Various product Validation
		if (products.size() > 0) {
			for (productCount = 0; productCount < products.size(); productCount++) {
				String product = products.get(productCount);
				buttons = viewContactsPage.getTableRow(product, 0, true);
				buttonCount = buttons.size();

				for (checkYears = 0; checkYears < buttonCount; checkYears++) {
					buttonLabel = buttons.get(checkYears);
					buttonVersion = Integer.parseInt(buttonLabel);
					
					// This is to validate the product availability for current
					// version + 3 consecutive versions and the cut off version
					// being 2010
					if (checkYears == 4 || buttonVersion < 2010) {
						Util.printInfo("No Validation required as the Product year is less than 2010 for " +product);
						break;
					} else {
						Util.printInfo("Product version " + buttonVersion
								+ " available for " + product);
					}
				}
			}
		} else
			Util.printInfo("No Product found for the user");
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
