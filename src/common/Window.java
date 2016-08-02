package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import common.Window_;
import common.exception.MetadataException;

import java.util.*;

/**
 * Representation of a window in an SFDC application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class Window implements Window_ {
	//Used to find page-level metadata entries in the properties object
	private static final String WINDOW_NAME			= "windowName";

	//NOTE:  Predefined locators were deprecated when the framework was migrated to WebDriver;
	//  it generates all window handles dynamically
	//private static final String LOCATOR			= "locator";

	private static final String LOAD_TIMEOUT		= "loadTimeout";
	private static final String PAGE_REDRAW_DELAY	= "pageRedrawDelay";

	//NOTE that predefined locators were deprecated when the framework was migrated to WebDriver;
	//  it generates all window handles dynamically.  As of 02/2012, the only window for which we
	//  are storing a locator is the main window (the value is set in EISTestBase.launchSalesforce),
	//  because its value will persist throughout the test, as long as we don't close the window and
	//  re-open it.  Locators for all other windows are transient, and should not be saved in the
	//  locator field!
	
	//Metadata, from the window properties file
	private String name;														//required (no default)
	
	//private String locator	= EISConstants.DEFAULT_MAIN_WINDOW_LOCATOR;		//optional (defaults to EISConstants.DEFAULT_MAIN_WINDOW_LOCATOR)
	private String locator		= "";

	private int loadTimeout		= EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;		//optional (defaults to EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT)
	private int pageRedrawDelay	= EISConstants.DEFAULT_PAGE_REDRAW_DELAY;		//optional (defaults to EISConstants.DEFAULT_PAGE_REDRAW_DELAY)

	private String propertiesFilename;

	private final GUI gui;

	//Staging area for window properties (we can use a Properties object because
	//  duplicates are not allowed)
	private Properties windowProperties = new Properties();

	public Window(WebDriver driver, String windowPropertiesFilename) {
		propertiesFilename = windowPropertiesFilename;

		gui = new GUI(driver);

		windowProperties = Util.loadPropertiesFile(propertiesFilename);

		try {
			setWindowMetadata();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public String getLocator() {
		return locator;
	}

	@Override
	public void setLocator(String locator) {
		this.locator = locator;
	}

	@Override
	public void setLocator() {
		locator = gui.getWindowHandle();
	}

	@Override
	public int getLoadTimeout() {
		return loadTimeout;
	}

	@Override
	public int getPageRedrawDelay() {
		return pageRedrawDelay;
	}

	@Override
	public String getMetadata() {
		return	"Window name:       " + name + "\n" +
		"Locator:           " + locator + "\n" +
		"Load timeout:      " + loadTimeout + "\n" +
		"Page redraw delay:	" + pageRedrawDelay;
	}

	@Override
	public String toString() {
		return	getMetadata() + "\n" +
		"Properties file:   " + propertiesFilename;
	}

	private void setWindowMetadata() throws MetadataException {
		String temp;

		name = windowProperties.getProperty(WINDOW_NAME, "");
		if (name.isEmpty()) {
			throw new MetadataException("The '" + WINDOW_NAME + "' property was not found in the window properties file '" + propertiesFilename + "'");
		}

		temp = windowProperties.getProperty(LOAD_TIMEOUT, "");
		if (!temp.isEmpty()) {
			loadTimeout = Integer.parseInt(temp);
		} else {
			loadTimeout = EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;
		}

		temp = windowProperties.getProperty(PAGE_REDRAW_DELAY, "");
		if (!temp.isEmpty()) {
			pageRedrawDelay = Integer.parseInt(temp);
		} else {
			pageRedrawDelay = EISConstants.DEFAULT_PAGE_REDRAW_DELAY;
		}
	}

	@Override
	public void close() {
		//We may add code here to verify that the window closes in the alloted amount of time
		select();

		//selenium.close();
		//driver.close();
		//gui.closeWindow(driver.getWindowHandle());
		gui.closeWindow(locator);
	}

	@Override
	public void select() {
		//String seleniumLocator = !locator.equalsIgnoreCase(EISConstants.DEFAULT_MAIN_WINDOW_LOCATOR) ? locator : null;
		//gui.selectWindow(seleniumLocator);
		//gui.selectWindow(driver.getWindowHandle());
		gui.selectWindow(locator);
	}

	@Override
	public void selectWindow() {
		//Convenience method for those who are used to calling the Selenium selectWindow method
		select();
	}

	@Override
	public String getTitle() {
		return gui.getTitle();
	}
}
