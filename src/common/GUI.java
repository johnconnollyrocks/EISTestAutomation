package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Sets;
import common.exception.GUIException;
import common.exception.MetadataException;
import common.exception.TestDataException;

/**
 * Manages direct interaction with the GUI - all Selenium code resides here.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class GUI {
	//setSeleniumLocator sets both of these
	private String seleniumLocator;
	private WebElement element;
	private List<WebElement> elementList;

	//Used primarily by waitForPageToLoad() and setSeleniumLocator() as the time to
	//  wait for a page to load or a field to be present.  We provide a protected
	//  setter to enable the caller to modify it in certain circumstances
	private int windowWaitTimeout = EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;

//	private EventFiringWebDriver driver;
	private WebDriver driver;

//	protected GUI(EventFiringWebDriver driver, WebDriver event) {
//		this.driver = driver;
//		this.event=event;
//
//	}

	protected GUI(WebDriver driver) {        
		this.driver=driver;

	}

	protected void setWindowWaitTimeout(int timeInMillis) {
		windowWaitTimeout = timeInMillis;
	}

	protected String getSeleniumLocator() {
		return seleniumLocator;
	}
	protected List<WebElement> getWebElementList(){
		return elementList;
	}
	/**
	 * @Description this is needed to parse the webelement for further verification purposes
	 * @return current Webelement
	 */
	protected WebElement getWebElement(){
		return element;
	}

	protected void populateHoverField(Field field) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}

		element.clear();	//production code
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		Util.sleep(5000);
	}


	protected void populateTextField(Field field, String value) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}
		try {		
			//NOTE: ELEMENT.CLEAR IS APPLICABLE ONLY FOR TEXT FIELDS. AND THIS BEING CALLED EVEN THOUGH IT IS NOT TEXT FIELD AND RESULTING IN EXCEPTION
			///SO TO AVOID THE BRITTLENESS OF THIS METHOD KEEPING THIS INSIDE A TRY BLOCK
			element.clear(); //production code
		} catch (Exception e) {//Dont do anything
		}

		try {
			element.sendKeys(value); //production code
			String elementName= EISTestBase.getWebElementName(element);
			EISTestBase.reportStep("Enter value in edit field '"+elementName+"'", value);

		} catch (StaleElementReferenceException staleExp) {
			Util.printWarning("StaleReferenceElement occurred. Hence reidentifying the element");
			element=driver.findElement(By.xpath(field.getLocators().get(0)));
			element.click(); ///to enable the focus
			element.sendKeys(value);

		}


	}
	protected void populateTextFieldSlowly(Field field, String value) throws Exception{
		try {    		

			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}
		Util.sleep(2000);
		element.clear();
		char[] cValue=value.toCharArray();
		for(int i=0;i<cValue.length;i++){
			Util.sleep(100);
			element.sendKeys(Character.toString(cValue[i]));			
		}

	}

	protected void populateHiddenTextField(Field field, String value) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}
		Util.sleep(2000);
		element.clear();	//production code
		element.sendKeys(value);	//production code
		if(field.getType().equals(EISConstants.FieldType.HIDDENTEXT))
			element.sendKeys(Keys.RETURN);		
	}
	protected void populateDateField(Field field, String value) throws MetadataException, TestDataException {
		String formattedDate;

		formattedDate = Util.formatDate(value, EISConstants.DEFAULT_DATE_FORMAT);
		if (formattedDate.equalsIgnoreCase(EISConstants.INVALID_INPUT)) {
			throw new TestDataException("The value '" + value + "' does not represent a valid date");
		}

		//We will allow a blank date - the user may want to enter one when testing for errors
		if (formattedDate.isEmpty()) {
			Util.printWarning("The value '" + value + "' is a blank date - it will be used anyway");
		}

		populateTextField(field, formattedDate);
	}

	protected void selectFromPicklist(Field field, String inValue) throws MetadataException, TestDataException {
		selectFromList(field, inValue, field.getType());
	}

	protected void selectFromMultiSelectList(Field field, String inValue) throws MetadataException, TestDataException {
		selectFromList(field, inValue, field.getType());
	}

	/*	private void selectFromList(Field field, String inValue, EISConstants.FieldType listType) throws MetadataException, TestDataException {
		//Because there are bugs in the way Selenium handles expressions (using the regexp and regexpi patterns),
		//  and because the glob pattern is case-sensitive (and also buggy), it is simplest to just grab the
		//  contents of the list, do a Java regex search, and then select using the index
		//We will make a number of attempts to find the element in the list (all case-insensitive).  After trimming both inValue and the
		//  contents of the list, we try the following until we find the element:
		//  1) no change to the value (which may already contain wild card characters)
		//  2) after changing trailing "*" (if any) to ".*"
		//  3) after appending ".*" (if not already present)
		//  4) steps 1 - 3 are tried again, but after removing ALL whitespace from the value and list elements
		//  5) steps 1 - 3 are tried again, but after removing ALL whitespace from the value and list elements, and removing
		//     leading non-alphabetic characters from the value and list elements

		List<String> contents;
		List<String> savedContents;
		int index;
		String value;
		boolean foundIt;

		value = inValue.trim();

		//We need to check for this here because further down we would end up checking for
		//  ".*", which would result in our selecting the first value in the list
		if (value.isEmpty()) {
			throw new TestDataException("The value '" + value + "' is an empty string");
		}

		setSeleniumLocator(field.getLocators());

		if (!isEditable(seleniumLocator)) {
			throw new TestDataException("The input field is not editable");
		}

		//Save the contents of the list
		//savedContents = new ArrayList<String>(Arrays.asList(selenium.getSelectOptions(seleniumLocator)));
		savedContents = new ArrayList<String>(Arrays.asList(getContentsOfList(field)));

		//Create a working copy of the contents, and trim its elements
		contents = Util.listOfStringTrim(new ArrayList<String>(savedContents));

		foundIt	= false;

		for (int tryNum = 1; tryNum <= 3; tryNum++) {
			if (tryNum > 1) {
				value = inValue.replaceAll("\\s", "");

				if (tryNum == 2) {
					ListIterator<String> itr = contents.listIterator();
					while (itr.hasNext()) {
						String element = itr.next();
						itr.set(element.replaceAll("\\s", ""));
					}
				}

				if (tryNum == 3) {
					value = value.replaceFirst("^[^a-zA-Z]+", "");

					ListIterator<String> itr2 = contents.listIterator();
					while (itr2.hasNext()) {
						String element = itr2.next();
						itr2.set(element.replaceFirst("^[^a-zA-Z]+", ""));
					}
				}
			}

			//Search contents without changing the value or the list contents.  This will do a
			//  case-insensitive search, and assumes that regex terms in value are well-formed
			index = Util.listOfStringMatch(contents, value, true);

			if (index < 0) {
				//We didn't find the value.  Modify value to make it regex terms compliant (take
				//  into account that some terms may already be compliant) and try again
				value = value.replace("*", ".*");
				value = value.replace("..*", ".*");

				index = Util.listOfStringMatch(contents, value, true);
			}

			if (index < 0) {
				//We didn't find the value.  Try tacking a .* on the end and trying again
				if (!value.endsWith("*")) {
					value += ".*";

					index = Util.listOfStringMatch(contents, value, true);
				}
			}

			if (index >= 0) {
				if (listType == EISConstants.FieldType.PICKLIST) {
					selenium.select(seleniumLocator, savedContents.get(index));
				} else {
					selenium.addSelection(seleniumLocator, savedContents.get(index));
				}

				foundIt = true;
				break;
			}
		}

		if (!foundIt) {
			throw new TestDataException("The value '" + inValue + "' cannot be resolved to a valid element in the " + listType.toString());
		}
	}
	 */
	/*	private void selectFromList(Field field, String inValue, EISConstants.FieldType listType) throws MetadataException, TestDataException {
		List<String> contents;
		List<String> savedContents;
		int index;
		String value;

		value = inValue.trim();

		//We need to check for this here because further down we would end up checking for
		//  ".*", which would result in our selecting the first value in the list
		if (value.isEmpty()) {
			throw new TestDataException("The value '" + value + "' is an empty string");
		}

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		if (!isEditable(seleniumLocator)) {
			throw new TestDataException("The input field is not editable");
		}

		//Save the contents of the list
		//savedContents = new ArrayList<String>(Arrays.asList(selenium.getSelectOptions(seleniumLocator)));
		//savedContents = new ArrayList<String>(Arrays.asList(getContentsOfList(field)));
		savedContents = new ArrayList<String>(getListContents(field));

		//Create a working copy of the contents, and trim its elements
		contents = Util.listOfStringTrim(new ArrayList<String>(savedContents));

		index = findValueInListContents(contents, value);

		if (index >= 0) {
			if (listType == EISConstants.FieldType.PICKLIST) {
				selenium.select(seleniumLocator, savedContents.get(index));
			} else {
				selenium.addSelection(seleniumLocator, savedContents.get(index));
			}
		} else {
			throw new TestDataException("The value '" + inValue + "' cannot be resolved to a valid element in the " + listType.toString());		
		}
	}
	 */
	private void selectFromList(Field field, String inValue, EISConstants.FieldType listType) throws MetadataException, TestDataException {
		List<String> contents;
		List<String> savedContents;
		int index;
		String value;
		Select select;

		value = inValue.trim();

		//We need to check for this here because further down we would end up checking for
		//  ".*", which would result in our selecting the first value in the list
		if (value.isEmpty()) {
			//			throw new TestDataException("The value '" + value + "' is an empty string");
		}

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}

		//TODO  We could try to save some time here (and in WebDriver this code does take a long time!) by first calling
		//  Select(driver.findElement(By.xpath(seleniumLocator))).selectByVisibleText(value).  If it is found, we can
		//  skip everything else.
		//The problem is that that call can take a long time as well!  For example, finding the 29th value in a list of
		//  31 took about five seconds.  And what if it had not been found?  We would then have to execute the following
		//  slow code.  Given that the aim is to not require the user to supply an exact match, we should assume that
		//  indeed the user will not supply exact matches; therefore, we should NOT make the selectByVisibleText() call.

		//Instead of calling getListContents, which makes a setSeleniumLocator call before calling Select(element).getOptions(),
		//  call Select(element).getOptions() on the WebElement that was previously set in setSeleniumLocator
		//  (and then make the getWebElementListText call that getListContents also calls) 
		//savedContents = new ArrayList<String>(getListContents(field));
		select = new Select(element);

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		//We'll try to select on value first (see comments above about timings)
		try {

			EISTestBase.reportSelectOption(element,value);
			select.selectByVisibleText(value);

			Util.printDebug("Got an exact match on '" + value + "'");
			//} catch (NoSuchElementException e) {E
		} catch (Exception e) {
			Util.printDebug("Did not get an exact match on '" + value + "'");

			List<WebElement> elements = new ArrayList<WebElement>(select.getOptions());
			savedContents = new ArrayList<String>(getWebElementListText(elements));

			//Create a working copy of the contents, and trim its elements
			contents = Util.listOfStringTrim(new ArrayList<String>(savedContents));

			index = findValueInListContents(contents, value);

			if (index >= 0) {
				//Not sure if this will work on a MULTISELECT field
				//new Select(driver.findElement(By.xpath(seleniumLocator))).selectByIndex(index);
				//new Select(element).selectByIndex(index);
				select.selectByIndex(index);
			} else {
				//Set the implicit wait time back to the default
				driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

				throw new TestDataException("The value '" + inValue + "' cannot be resolved to a valid element in the " + listType.toString());		
			}
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
	}

	/*	protected int findValueInListContents(List<String> contents, String inValue) throws TestDataException {
		//Because there are bugs in the way Selenium handles expressions (using the regexp and regexpi patterns),
		//  and because the glob pattern is case-sensitive (and also buggy), it is simplest to just grab the
		//  contents of the list, do a Java regex search, and then select using the index
		//We will make a number of attempts to find the element in the list (all case-insensitive).  After trimming both inValue and the
		//  contents of the list, we try the following until we find the element:
		//  1) no change to the value (which may already contain wild card characters)
		//  2) after changing trailing "*" (if any) to ".*"
		//  3) after appending ".*" (if not already present)
		//  4) steps 1 - 3 are tried again, but after removing ALL whitespace from the value and list elements
		//  5) steps 1 - 3 are tried again, but after removing ALL whitespace from the value and list elements, and removing
		//     leading non-alphabetic characters from the value and list elements

		int index = -1;
		String value;

		value = inValue.trim();

		//We need to check for this here; otherwise, further down we might end up checking for
		//  ".*", which would result in our selecting the first value in the list
		if (value.isEmpty()) {
			throw new TestDataException("The value '" + value + "' is an empty string");
		}

		for (int tryNum = 1; tryNum <= 3; tryNum++) {
			if (tryNum > 1) {
				value = inValue.replaceAll("\\s", "");

				if (tryNum == 2) {
					ListIterator<String> itr = contents.listIterator();
					while (itr.hasNext()) {
						String element = itr.next();
						itr.set(element.replaceAll("\\s", ""));
					}
				}

				if (tryNum == 3) {
					value = value.replaceFirst("^[^a-zA-Z]+", "");

					ListIterator<String> itr2 = contents.listIterator();
					while (itr2.hasNext()) {
						String element = itr2.next();
						itr2.set(element.replaceFirst("^[^a-zA-Z]+", ""));
					}
				}
			}

			//Do a case-insensitive search, assuming that any regex characters in the string
			//  (namely     [\^$.|?*+()     ) are escaped
			index = Util.listOfStringMatch(contents, value, true);

			if (index < 0) {
				//We didn't find the value.  Modify value to make it regex terms compliant (take
				//  into account that some terms may already be compliant) and try again
				value = value.replace("*", ".*");
				value = value.replace("..*", ".*");

				index = Util.listOfStringMatch(contents, value, true);
			}

			if (index < 0) {
				//We didn't find the value.  Try tacking a .* on the end and trying again
				if (!value.endsWith("*")) {
					value += ".*";

					index = Util.listOfStringMatch(contents, value, true);
				}
			}

			if (index >= 0) {
				break;
			}
		}

		return index;
	}
	 */
	protected int findValueInListContents(List<String> contents, String inValue) throws TestDataException {
		//Because there are bugs in the way Selenium handles expressions (using the regexp and regexpi patterns),
		//  and because the glob pattern is case-sensitive (and also buggy), it is simplest to just grab the
		//  contents of the list, do a Java regex search, and then select using the index
		//We will make a number of attempts to find the element in the list (all case-insensitive).  After trimming
		//   both inValue and the contents of the list, we try the following until we find the element:
		//  1) the trimmed value
		//  2) after changing all occurrences (if any) in value of "*" to ".*"
		//  3) after appending ".*" to value (if not already present)
		//  4) steps 1 - 3 are tried again, but after:
		//     removing ALL whitespace from the value and list elements
		//  5) steps 1 - 3 are tried again, but after:
		//     removing ALL whitespace from the value and list elements
		//     removing leading non-alphabetic characters from the value and list elements
		//
		//  Note that most of the searches will be performed using both regex-capable and regex-incapable versions
		//    of value 

		int index = -1;
		String value;
		String quotedValue;

		value = inValue.trim();

		//We need to check for this here; otherwise, further down we might end up checking for
		//  ".*", which would result in our selecting the first value in the list
		if (value.isEmpty()) {
			throw new TestDataException("The value '" + value + "' is an empty string");
		}

		for (int tryNum = 1; tryNum <= 3; tryNum++) {
			if (tryNum > 1) {
				value = inValue.replaceAll("\\s", "");

				if (tryNum == 2) {
					ListIterator<String> itr = contents.listIterator();
					while (itr.hasNext()) {
						String element = itr.next();
						itr.set(element.replaceAll("\\s", ""));
					}
				}

				if (tryNum == 3) {
					value = value.replaceFirst("^[^a-zA-Z]+", "");

					ListIterator<String> itr2 = contents.listIterator();
					while (itr2.hasNext()) {
						String element = itr2.next();
						itr2.set(element.replaceFirst("^[^a-zA-Z]+", ""));
					}
				}
			}

			quotedValue = java.util.regex.Pattern.quote(value);

			//Do a case-insensitive search, trying both the natural and quoted versions
			index = Util.listOfStringMatch(contents, value, true);
			if (index < 0) {
				index = Util.listOfStringMatch(contents, quotedValue, true);
			}

			if (index < 0) {
				//We didn't find the value.  Modify value to make internal wild card terms regex
				//  compliant (take into account that some terms may already be compliant) and
				//  try again.
				//  Note that we will NOT perform this search using quotedValue, because any
				//  internal wild card terms that value may have contained were neutralized
				//  when it was quoted 
				value = value.replace("*", ".*");
				value = value.replace("..*", ".*");

				index = Util.listOfStringMatch(contents, value, true);
			}

			if (index < 0) {
				//We didn't find the value.  Try tacking a .* on the end and trying again
				if (!value.endsWith("*")) {
					value += ".*";

					//Do a case-insensitive search, trying both the natural and quoted versions
					//  (note that any regex terms that are appended or prepended to quotedValue
					//  ARE seen as regex terms by String.matches())
					index = Util.listOfStringMatch(contents, value, true);
					if (index < 0) {
						quotedValue += ".*";
						index = Util.listOfStringMatch(contents, quotedValue, true);
					}
				}
			}

			if (index >= 0) {
				break;
			}
		}

		return index;
	}

	protected int findValueInListContents(String[] contents, String inValue) throws TestDataException {
		return findValueInListContents(Arrays.asList(contents), inValue);
	}

	/*	protected void clickCheckbox(Field field, String value) throws MetadataException, TestDataException {
		if (!clickToggleField(field, value)) {
			throw new TestDataException("The value '" + value + "' cannot be resolved to a valid action for a checkbox");
		}
	}
	 */
	protected void clickCheckbox(Field field, String value) throws MetadataException, TestDataException {
		clickToggleField(field, value);
	}

	//Selenium provides direct support for radio buttons, but not radio lists (let's wait to implement radio lists until we have to)
	/*	protected void clickRadioButton(Field field, String value) throws MetadataException, TestDataException {
		if (!clickToggleField(field, value)) {
			throw new TestDataException("The value '" + value + "' cannot be resolved to a valid action for a radio button");
		}
	}
	 */
	protected void clickRadioButton(Field field, String value) throws MetadataException, TestDataException {
		clickToggleField(field, value);
	}

	//!!! DO NOT delete this MASTER commented version, as it contains notes (in painful detail) on all of the failed
	//  attempts to work around the WebDriver click() bug!!!
	/*	protected void clickButtonOrLink(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//selenium.click(seleniumLocator);

		//WebDriver's click method does not always work on buttons or links, so we need to try a different approach
		//The options are:
		//  1) use click, then detect whether it has worked (but how?); if not, use sendKeys
		//  2) figure out how to set focus first, and then use sendKeys
		//  3) get the element's Point object, and then click on that point
		//  4) use Javascript (but beware the possible side effects, like actions not firing)
		//  5) create a new EISConstants.FieldType member, something like SPECIAL_CLICK, and follow
		//     different code paths here based on the type
		//  6) try HtmlUnitDriver (perhaps we could switch to it here, and then switch back?)

		//Changed from this to the sendKeys call (below) on 03/08/2012, in an attempt to get around the
		//  issue I am having with waiting for pages to load.  However, I had to revert back to this.

		//***** This is my original (followed by a variation).  The problem is that it never return
		//  when a modal dialog appears
		//((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
		//((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);

		//Non-Javascript attempt
		//Works only if executed twice (same as with the plain old click()
		//new Actions(driver).moveToElement(element).moveByOffset(5,5).click().perform();
		//new Actions(driver).moveToElement(element).moveByOffset(5,5).clickAndHold().release().perform();

		//**** I can't get this to work reliably, as with all other attempts outside of Javascript -
		//  it sometimes has to be repeated twice
		//new Actions(driver).moveToElement(element).clickAndHold().moveByOffset(1,1).release().perform();
		//Actions actions = new Actions(driver);
		//actions.moveToElement(element).clickAndHold().perform();
		//actions.moveToElement(element).moveByOffset(1,1).release().perform();

		//This is the 03/08/2012 change
		//This doesn't work on some fields, for example the PageLookupPopUp.firstFoundObject field when the
		//  lookup window is opened from PageCreateLead.contact
		//element.sendKeys("\n");

		//***** Below are some other solutions I tried before settling on the above - do not delete!!!
			// Sometimes WebElement.click() doesn't have any effect.  sendKeys(Keys.ENTER) seems to be more reliable.
			//  However, sometimes sendKeys(Keys.ENTER) throws an exception and prints the message "We don't have focus on
			//  element."  Let's trap that, and try WebElement.click() afterward

		//***** The straight WebDriver call that is causing all the problems.  It is a known issue that sometimes click has
		//  no effect on buttons and links.  So I replaced it with a Javascript call (above), but had a problem with that
		//  not returning when it triggered a modal dialog.  So, I experimented A LOT MORE and settled on the sendKeys call.
		//!!!!! HOWEVER, I have other comments near here about how sendKeys sometimes does not work, and indeed I was using
		//  it for a while!  So be careful!!!
		//driver.findElement(By.xpath(seleniumLocator)).click();
		//driver.findElement(By.xpath(seleniumLocator)).sendKeys(Keys.ENTER);

		//SURE ENOUGH!!!  When using sendKeys I got a stale reference exception (using v2.17 and v2.21 jars) when clicking 
		//  PageCreateCase.supportedAssetSerialNum!!!!!  But sometimes the click acts pretty much like a no-op, and the
		//  message "We don't have focus on element" is printed to the console
		//try {
		//	element.sendKeys(Keys.ENTER);
		//} catch (org.openqa.selenium.StaleElementReferenceException e) {
		//	//Lets hope that a modal dialog is not triggered, because the following call will not return
		//	//!!!!!  sendKeys does not work on PageCreateCase.supportedAssetSerialNum (and probably all other
		//	//  img elements) because of the focus problem.  But how do we set focus without actually
		//	//  clicking it again?
		//	((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
		//}


		//Problem:  sendKeys may return StaleElementReferenceException
		//          so, we try to trap that, and work with the element some other way
		//            however, that involves "clicking" the element until is is no longer there,
		//            which is a problem because sometimes the element SHOULD persist
		//          we can use driver.click(), which often does not have an effect, so we would
		//            still need to execute the loop, leading to the same problem with
		//            elements that SHOULD persist
		//          (of course, the reason we are in this mess is because the Javascript call hangs
		//            if the click triggers a modal dialog)

		try {
			element.sendKeys(Keys.ENTER);
			//driver.findElement(By.xpath(seleniumLocator)).click();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			Actions actions = new Actions(driver);
			while (true)
			{
				try {
					//actions.moveToElement(element).clickAndHold().moveByOffset(1,1).release();
					actions.moveToElement(element).clickAndHold().moveByOffset(1,1).release().perform();
					//actions.build().perform();

					//The problem with breaking out of this loop only when the element is no longer
					//  present is that there are some elements that SHOULD persist, such as tabs,
					//  headings, etc.
					setSeleniumLocator(field.getLocators(), 500);
				} catch (Exception e2) {
					break;
				}
			}
		}


		String onClickContent = null;
		try {
			onClickContent = element.getAttribute("onclick");
			//For some reason we are still getting this exception sometimes (e.g., PageCreateCase.supportedAssetSerialNum)
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			try {
				setSeleniumLocator(field.getLocators());
			} catch (MetadataException me) {
				throw me;
			}

			onClickContent = element.getAttribute("onclick");
		}

		String deleteUrlInfo = "";
		if (onClickContent != null) {
			if (onClickContent.toUpperCase().indexOf("CONFIRMDELETE") >= 0) {
				deleteUrlInfo = element.getAttribute("href");
				if (deleteUrlInfo != null) {
					open(element.getAttribute("href"));
				} else {
					throw new MetadataException("The 'href' attribute of the GUI element was not found");
				}
			} else if ((onClickContent.toUpperCase().indexOf("DELETEREDIRECT") >= 0) || (onClickContent.toUpperCase().indexOf("UNDELETEPAGE") >= 0)) {
				deleteUrlInfo = onClickContent.substring(onClickContent.toUpperCase().indexOf("NAVIGATETOURL"));
				((JavascriptExecutor) driver).executeScript(deleteUrlInfo);
			} else {
				((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
			}
		} else {
			deleteUrlInfo = element.getAttribute("href");
			if (deleteUrlInfo != null) {
				if (deleteUrlInfo.toUpperCase().indexOf("DELETEACCOUNT") >= 0) {
					deleteUrlInfo = deleteUrlInfo.substring(deleteUrlInfo.toUpperCase().indexOf("DELETEACCOUNT"));
					((JavascriptExecutor) driver).executeScript(deleteUrlInfo);
				} else {
					((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
				}
			} else {
				((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
			}
		}

			//driver.findElement(By.xpath(seleniumLocator)).sendKeys(Keys.ENTER);

			//I can't get this working for some elements, in particular the firstFoundObject fields on the
			//  PageLookupPopUp page - but only when it is invoked using the product field on the PageCreateLead
			//  page!  Until I can get it working, I will use Selenium.click()
			//try {
			//	driver.findElement(By.xpath(seleniumLocator)).sendKeys(Keys.ENTER);
			//} catch (Exception e) {
			//	driver.findElement(By.xpath(seleniumLocator)).click();
			//}

			//The problem with the Selenium.click() solution is that the call throws an uncatchable exception
			//  when it is used to click on the product field (a lookup) on the PageCreateLead page
			//Selenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
	    	//selenium.click(seleniumLocator);
	    	//driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();

			//I read somewhere that clicking it to set focus, and then sending keys should work.  I'll try that,
			//  but trap the possibility that the first click worked.... Tried it: this also seems to throw an uncatchable
			//  exception when it is used to click on the product field (a lookup) on the PageCreateLead page
			//BUT it does work in Firefox!
			//NOTE that we can't use this code, because of all the checks for elements still existing after
			//  clicking or sending keys to them.  In many cases, the element SHOULD persist - tabs, app chooser, etc.

			//driver.findElement(By.xpath(seleniumLocator)).click();
			//Util.sleep(1000);
			//try {
		    //	driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		    //	WebElement we = driver.findElement(By.xpath(seleniumLocator));
		    //	Util.printInfo("WebElement.click() did not work on '" + seleniumLocator + "'; will try WebElement.sendKeys()");
		    //	try {
		    //		we.sendKeys(Keys.ENTER);
		    //	} catch (Exception e) {
			//    	Util.printInfo("WebElement.sendKeys did not work on '" + seleniumLocator + "'; it threw an exception");
		    //	} finally {
			//    	driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
			//	}
			//} catch (Exception e) {
			//	Util.printInfo("WebElement.click() worked on '" + seleniumLocator + "'");
			//} finally {
			//	//DEBUG !!!!!  Problem is that when this line is executed after a pop-up has closed, it crashes
		    //	driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
			//}
	}
	 */
	/*	protected void clickButtonOrLink(Field field) throws MetadataException {
		//!!! SEE THE MASTER COMMENTED VERSION of this method (above) for notes (in painful detail) on all of the failed
		//  attempts to work around the WebDriver click() bug!!!

		String onClickContent = null;
		String deleteInfo = null;
		String message = "'Clicked' the Field '" + field.getFieldName() + "' by ";

		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		try {
			onClickContent = element.getAttribute("onclick");
			//For some reason we are still getting this exception sometimes (e.g., PageCreateCase.supportedAssetSerialNum)
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			try {
				setSeleniumLocator(field.getLocators());
			} catch (MetadataException me) {
				throw me;
			}

			onClickContent = element.getAttribute("onclick");
		}

		try {
			doClick(onClickContent, field.getFieldName());
		} catch (MetadataException me) {
			throw me;
		}

		if (onClickContent != null) {
			if (onClickContent.toUpperCase().indexOf("CONFIRMDELETE") >= 0) {
				deleteInfo = element.getAttribute("href");
				if (deleteInfo != null) {
					open(element.getAttribute("href"));
					Util.printDebug(message + "navigating to the URL specified in the href attribute in the JavaScript onclick() event");
				} else {
					throw new MetadataException("The 'href' attribute of the GUI element was not found");
				}
			} else if ((onClickContent.toUpperCase().indexOf("DELETEREDIRECT") >= 0) || (onClickContent.toUpperCase().indexOf("UNDELETEPAGE") >= 0)) {
				deleteInfo = onClickContent.substring(onClickContent.toUpperCase().indexOf("NAVIGATETOURL"));
				((JavascriptExecutor) driver).executeScript(deleteInfo);
				Util.printDebug(message + "executing the navigateToUrl function in the JavaScript onclick() event");
			} else {
				((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
				Util.printDebug(message + "calling the JavaScript click() method");
			}
		} else {
			deleteInfo = element.getAttribute("href");
			if (deleteInfo != null) {
				if (deleteInfo.toUpperCase().indexOf("DELETEACCOUNT") >= 0) {
					deleteInfo = deleteInfo.substring(deleteInfo.toUpperCase().indexOf("DELETEACCOUNT"));
					((JavascriptExecutor) driver).executeScript(deleteInfo);
					Util.printDebug(message + "executing the JavaScript deleteAccount() function in the href attribute");
				} else {
					((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
					Util.printDebug(message + "calling the JavaScript click() method");
				}
			} else {
				((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
				Util.printDebug(message + "calling the JavaScript click() method");
			}
		}
	}
	 */

	//NOTE THAT THIS IS THE MOST RECENT "VALID" VERSION (06/07/2012)
	protected void clickButtonOrLink(Field field) throws MetadataException {
		//!!! SEE THE MASTER COMMENTED VERSION of this method (above) for notes (in painful detail) on all of the failed
		//  attempts to work around the WebDriver click() bug!!!

		String onClickContent = null;

		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		try {
			onClickContent = element.getAttribute("onclick");

			Util.printDebug("onClickContent = '" + onClickContent + "'");

			//For some reason we are still getting this exception sometimes (e.g., PageCreateCase.supportedAssetSerialNum)
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			Util.printDebug("Got StaleElementReferenceException while calling element.getAttribute('onclick')");

			try {
				setSeleniumLocator(field.getLocators());
			} catch (MetadataException me) {
				throw me;
			}

			onClickContent = element.getAttribute("onclick");

			Util.printDebug("onClickContent = '" + onClickContent + "'");
		}

		try {
			doClick(element, onClickContent);
		} catch (MetadataException me) {
			throw me;
		}
	}


	//This is a super light-weight wrapper that will be called when clicks need to be made on fields for which we
	//  have not declared Field metadata.  An example is the "Show more" link at the bottom of related lists (see
	//  Page.expandRelatedList for details)
	//NOTE that it is assumed that the element referenced by locator exists!!!
	/*	protected void click(String locator) {
		WebElement element;
		//selenium.click(locator);

    	//We will control how long we wait for an element
    	driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

    	element = driver.findElement(By.xpath(locator));

    	//Doing this because of problems in WebDriver clicking links and buttons
    	//!!!CAUTION  See comments in clickButtonOrLink()
		((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);

		//Set the implicit wait time back to the default
    	driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
	}
	 */
	/*	protected void click(String locator) throws MetadataException {
		//!!! SEE THE MASTER COMMENTED VERSION of the clickButtonOrLink method (above) for notes (in painful detail) on all of the failed
		//  attempts to work around the WebDriver click() bug!!!

		String onClickContent = null;
		WebElement element;

    	//We will control how long we wait for an element
    	driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

    	element = driver.findElement(By.xpath(locator));

		onClickContent = element.getAttribute("onclick");

		String deleteUrlInfo = "";
		if (onClickContent != null) {
			if (onClickContent.toUpperCase().indexOf("CONFIRMDELETE") >= 0) {
				deleteUrlInfo = element.getAttribute("href");
				if (deleteUrlInfo != null) {
					open(element.getAttribute("href"));
				} else {
					throw new MetadataException("The 'href' attribute of the GUI element referenced by the locator '" + locator + "' was not found");
				}
			} else if ((onClickContent.toUpperCase().indexOf("DELETEREDIRECT") >= 0) || (onClickContent.toUpperCase().indexOf("UNDELETEPAGE") >= 0)) {
				deleteUrlInfo = onClickContent.substring(onClickContent.toUpperCase().indexOf("NAVIGATETOURL"));
				((JavascriptExecutor) driver).executeScript(deleteUrlInfo);
			} else {
				((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
			}
		} else {
			deleteUrlInfo = element.getAttribute("href");
			if (deleteUrlInfo != null) {
				if (deleteUrlInfo.toUpperCase().indexOf("DELETEACCOUNT") >= 0) {
					deleteUrlInfo = deleteUrlInfo.substring(deleteUrlInfo.toUpperCase().indexOf("DELETEACCOUNT"));
					((JavascriptExecutor) driver).executeScript(deleteUrlInfo);
				} else {
					((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
				}
			} else {
				((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
			}
		}

		//Set the implicit wait time back to the default
    	driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
	}
	 */
	protected void click(String locator) throws MetadataException {
		//!!! SEE THE MASTER COMMENTED VERSION of the clickButtonOrLink method (above) for notes (in painful detail) on all of the failed
		//  attempts to work around the WebDriver click() bug!!!

		String onClickContent = null;
		WebElement element;

		//We will control how long we wait for an element
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		element = driver.findElement(By.xpath(locator));

		onClickContent = element.getAttribute("onclick");

		try {
			doClick(element, onClickContent);
		} catch (MetadataException me) {
			throw me;
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
	}



	private void doClick(WebElement element, String onClickContent) throws MetadataException {
		//!!! SEE THE MASTER COMMENTED VERSION of the clickButtonOrLink method (above) for notes (in painful detail) on all of the failed
		//  attempts to work around the WebDriver click() bug!!!

		//!!!!! *************************************************
		//TODO  See about using AutoIT to take care of this stuff
		//!!!!! *************************************************

		EISTestBase.reportButtonClick(element);
		String deleteInfo = null;
		String message = "Attempting to 'click' the GUI element by ";

		if (onClickContent != null) {
			Util.printDebug("onClickContent != null");

			if (onClickContent.toUpperCase().indexOf("CONFIRMDELETE") >= 0) {
				Util.printDebug("'CONFIRMDELETE' found in onClickContent");

				deleteInfo = element.getAttribute("href");
				if (deleteInfo != null) {
					Util.printDebug(message + "navigating to the URL specified in the href attribute in the JavaScript onclick() event");
					open(element.getAttribute("href"));
				} else {
					throw new MetadataException("The 'href' attribute of the GUI element was not found");
				}
			} else if ((onClickContent.toUpperCase().indexOf("DELETEREDIRECT") >= 0) || (onClickContent.toUpperCase().indexOf("UNDELETEPAGE") >= 0)) {
				Util.printDebug("'DELETEREDIRECT' or 'UNDELETEPAGE' found in onClickContent");

				deleteInfo = onClickContent.substring(onClickContent.toUpperCase().indexOf("NAVIGATETOURL"));

				Util.printDebug(message + "executing the navigateToUrl function in the JavaScript onclick() event");
				((JavascriptExecutor) driver).executeScript(deleteInfo);
			} else {
				Util.printDebug("No delete-related code found in onClickContent; will try something else...");

				try {
					//if (onClickContent.indexOf("openNew") >= 0) {
					if (onClickContent.toUpperCase().indexOf("OPENNEW") >= 0) {
						Util.printDebug("'OPENNEW' found in onClickContent");

						String openNewInfo = onClickContent.substring(onClickContent.toUpperCase().indexOf("OPENNEW"));

						Util.printDebug(message + "executing the openNew function in the JavaScript onclick() event");
						((JavascriptExecutor) driver).executeScript(openNewInfo);
					} else if (onClickContent.toUpperCase().indexOf("A4J.AJAX.SUBMIT") >= 0) {
						Util.printDebug("'A4J.AJAX.SUBMIT' found in onClickContent");

						String submitInfo = onClickContent.substring(onClickContent.toUpperCase().indexOf("A4J.AJAX.SUBMIT"));

						Util.printDebug(message + "executing the A4J.AJAX.Submit function in the JavaScript onclick() event");
						((JavascriptExecutor) driver).executeScript(submitInfo);
					} else {
						Util.printDebug(message + "calling the JavaScript click() method (element has an onclick attribute, but we are not using it)");
						((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
					}
				} catch (Exception e) {
					//It seems that we get the "UnreachableBrowserException: Error communicating with the remote browser. It may have died"
					//  error whether we execute Javascript click() or WebElement.click()
					Util.printDebug("Got exception while calling the JavaScript or WebElement click() method, or executing the Javascript openNew method: " + e.getMessage());
					Util.printDebug("*************** BEGIN STACK TRACE ***************");
					e.printStackTrace();
					Util.printDebug("**************** END STACK TRACE ****************");
				}
			}
		} else {
			Util.printDebug("onClickContent = null");

			deleteInfo = element.getAttribute("href");
			if (deleteInfo != null) {
				Util.printDebug("href attribute != null");

				if (deleteInfo.toUpperCase().indexOf("DELETEACCOUNT") >= 0) {
					deleteInfo = deleteInfo.substring(deleteInfo.toUpperCase().indexOf("DELETEACCOUNT"));

					Util.printDebug(message + "executing the JavaScript deleteAccount() function in the href attribute");
					((JavascriptExecutor) driver).executeScript(deleteInfo);
				} else {

					Util.printDebug(message + "calling the JavaScript click() method (element does not have an onclick attribute; has an href attribute, but we are not using it)");

					Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
					String browserName = capabilities.getBrowserName();
					//if block
					if(!browserName.equalsIgnoreCase("safari")) {
						((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
					}
					else{
						Util.sleep(2000);
						element.click();
						Util.sleep(2000);
					}




				}
			} else {
				Util.printDebug("href attribute = null");

				Util.printDebug(message + "calling the JavaScript click() method (element has neither an onclick nor an href attribute)");
				Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
				String browserName = capabilities.getBrowserName();
				//else Block
				if(!browserName.equalsIgnoreCase("safari")) {
					((JavascriptExecutor) driver).executeScript("return arguments[0].click()", element);
				}
				else{
					element.click();
				}
			}
		}
	}


	//Called by clickCheckbox() and clickRadioButton()
	/*	private void clickToggleField(Field field, String value) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		if (!isEditable(seleniumLocator)) {
			throw new TestDataException("The input field is not editable");
		}

		if (Util.isCheckValue(value)) {
			if (!selenium.isChecked(seleniumLocator)) {
				selenium.click(seleniumLocator);
			}
		} else {
			if (selenium.isChecked(seleniumLocator)) {
				selenium.click(seleniumLocator);
			}
		}
	}
	 */
	/*	private void clickToggleField(Field field, String value) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		if (!isEditable(seleniumLocator)) {
			throw new TestDataException("The input field is not editable");
		}

		WebElement element = driver.findElement(By.xpath(seleniumLocator));

		if (Util.isCheckValue(value)) {
			if (!element.isSelected()) {
				element.click();
			}
		} else {
			if (element.isSelected()) {
				element.click();
			}
		}
	}
	 */
	/*	private void clickToggleField(Field field, String value) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}

		//Sometimes the click doesn't "take" in WebDriver, so do it twice
		if (Util.isCheckValue(value) != element.isSelected()) {
			element.click();

			if (Util.isCheckValue(value) != element.isSelected()) {
				element.click();
			}
		}
	}
	 */
	private void clickToggleField(Field field, String value) throws MetadataException, TestDataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//Instead of calling isEditable, which makes a driver.findElement call on an element we already know exists,
		//  call WebElement.isEnabled on the WebElement that was previously set in setSeleniumLocator.
		//if (!element.isEnabled()) {
		//	throw new TestDataException("The input field is not editable");
		//}
		if (!waitForEnabled(element)) {
			throw new TestDataException("The input field is not editable");
		}

		//Sometimes the click doesn't "take" in WebDriver, so we'll repeat it a few times.
		//!!!!!  Actually, the click() call did work when running locally, but when running from Jenkins (IE8 or IE9),
		//  the click() didn't take.  The sendKeys call works for radio buttons,
		//  !!!!! BUT HAS NOT BEEN TESTED ON CHECK BOXES !!!!!
		int tries = 0;
		Util.sleep(2000);
		EISTestBase.reportStep("Clicking on CheckBox/Radio button '"+EISTestBase.getWebElementName(element)+"'", "");
		//if you get StaleELement or Element not visible then reidentify the webelement again
		try {

			element.click();
		} catch (Exception e) {
			Util.printWarning("Found StaleElement/Element not visible exception occuured.Hence reidentifying the webelement");
			List<String> lsFields=field.getLocators();
			for(String myLoc:lsFields){
				elementList=driver.findElements(By.xpath(myLoc));
				if(elementList.size()>0 && elementList!=null){
					element=elementList.get(0);
					break;
				}
			}
		}
		if (Util.isCheckValue(value) != element.isSelected()) {
			element.click();
			//			element.sendKeys(Keys.SPACE);
			tries++;

			if (Util.isCheckValue(value) != element.isSelected()) {
				element.click();
				//				element.sendKeys(Keys.SPACE);
				tries++;

				if (Util.isCheckValue(value) != element.isSelected()) {
					element.click();
					//					element.sendKeys(Keys.SPACE);
					tries++;

					if (Util.isCheckValue(value) != element.isSelected()) {
						element.click();
						//						element.sendKeys(Keys.SPACE);
						tries++;

						if (Util.isCheckValue(value) != element.isSelected()) {
							element.click();
							//							element.sendKeys(Keys.SPACE);
							tries++;
						}
					}
				}
			}
		}

		if (Util.isCheckValue(value) == element.isSelected()) {
			Util.printDebug("Successfully toggled field after " + tries + " tries");
		} else {
			Util.printDebug("Unable to successfully toggle field after " + tries + " tries");
		}
	}

	protected String getValueFromTextField(Field field) throws MetadataException {
		String value;

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//return selenium.getValue(seleniumLocator);
		//return driver.findElement(By.xpath(seleniumLocator)).getText();
		//return element.getText();
		value = element.getAttribute("value");
		if (value == null) {
			value = element.getText();
		}

		return value;
	}

	protected String getValueFromDateField(Field field) throws MetadataException {
		return Util.formatDate(getValueFromTextField(field));
	}

	protected String getValueFromPicklist(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//return selenium.getSelectedLabel(seleniumLocator);
		//return new Select(driver.findElement(By.xpath(seleniumLocator))).getFirstSelectedOption().getText();
		return new Select(element).getFirstSelectedOption().getText();
	}

	protected String getValueFromMultiSelect(Field field) throws MetadataException {
		String [] values = getValuesFromMultiSelect(field).toArray(new String[0]);

		return Util.buildDelimitedString(values, EISConstants.VALUE_DELIM);
	}

	/*	private String[] getValuesFromMultiSelect(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		return selenium.getSelectedLabels(seleniumLocator);
		return new Select(driver.findElement(By.xpath(seleniumLocator))).getAllSelectedOptions();
	}
	 */
	private List<String> getValuesFromMultiSelect(Field field) throws MetadataException {
		List<WebElement> elements = new ArrayList<WebElement>();

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//elements = new Select(driver.findElement(By.xpath(seleniumLocator))).getAllSelectedOptions();
		elements = new Select(element).getAllSelectedOptions();

		return getWebElementListText(elements);
	}

	protected String getValueFromCheckbox(Field field) throws MetadataException {
		return new Boolean(isChecked(field)).toString();
	}

	protected String getValueFromRadioButton(Field field) throws MetadataException {
		return new Boolean(isChecked(field)).toString();
	}

	protected boolean isChecked(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//return selenium.isChecked(seleniumLocator);
		//return driver.findElement(By.xpath(seleniumLocator)).isSelected();
		return element.isSelected();
	}

	protected String getValueFromButton(Field field) throws MetadataException {
		String value;

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//return selenium.getValue(seleniumLocator);
		//Not sure if this call is the right one...
		//return driver.findElement(By.xpath(seleniumLocator)).getText();
		//return element.getText();
		value = element.getAttribute("value");
		//handle multiple situation like value could be empty 
		if (value == null || value.isEmpty() || value=="") {
			value = element.getText();
		}

		return value;
	}

	/*	protected String getTextFromLink(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		return selenium.getText(seleniumLocator);
	}
	 */

	protected String getTextFromLink(Field field) throws MetadataException {
		return getTextFromReadOnlyField(field);
	}

	protected String getTextFromReadOnlyField(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//getText() is not consistent across browsers; textContent works in many; innerText works in most but not in FF and is more similar to getText
		/*Added by Sai:
		 * For elements matching more than 1 then return the text as text1,text2*/
		List<WebElement> lsElements= getWebElementList();
		String elementText="";
		if (lsElements!=null && lsElements.size()>1){
			for(WebElement wEle:lsElements){
				elementText=wEle.getText().trim()+","+elementText;
			}
			elementText=elementText.substring(0,elementText.length()-1);	//remove ','
			return elementText.trim();
		}else{

			String text="";
			try {
				text = element.getText();
				if (text.isEmpty()) {
					text = element.getAttribute("textContent");
					if (text.isEmpty()) {
						text = element.getAttribute("innerText");
					}
				}
			} catch (StaleElementReferenceException staleEx) {
				Util.printInfo("Stale Element exception found hence reidentify the webelement");
				setSeleniumLocator(field.getLocators());
				text = element.getText();
			}
			return text;
		}
		//		String text = element.getText();
		//		if (text.isEmpty()) {
		//			text = element.getAttribute("textContent");
		//			if (text.isEmpty()) {
		//				text = element.getAttribute("innerText");
		//			}
		//		}
		//		return text;
	}

	protected String getTextFromReadOnlyDateField(Field field) throws MetadataException {
		return Util.formatDate(getTextFromReadOnlyField(field));
	}

	//This can be used for grabbing the text from a non-input object, such as error messages
	//  and other textual data used in verification
	//
	//BUT  It is advised to use getTextFromReadOnlyElement() or getTextFromReadOnlyDateElement()
	//  instead!
	protected String getText(List<String> locators) throws GUIException {
		String text;

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		//setSeleniumLocator(locators);
		try {
			setSeleniumLocator(locators);
		} catch (MetadataException me) {
			throw me;
		}

		//The locator for the text object can be found, but the text may not be visible.  This
		//  is often the case when there is no error on the page, but an error message object is
		//  present - just not visible.  In such a case, we will return the text only if it is
		//  visible
		//if (selenium.isVisible(seleniumLocator)) {
		//text = selenium.getText(seleniumLocator);
		//WebElement element = driver.findElement(By.xpath(seleniumLocator));
		if (element.isDisplayed()) {
			text = element.getText();
		} else {
			throw new GUIException("The GUI element '" + seleniumLocator + "' is not visible");
		}

		return text;
	}

	protected String getText(Field field) throws GUIException {
		return getText(field.getLocators());
	}

	protected String getText(String locator) throws GUIException {
		List<String> locators = new ArrayList<String>();

		locators.add(locator);

		return getText(locators);
	}

	/*	protected String[] getContentsOfList(Field field) throws MetadataException {
		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		return selenium.getSelectOptions(seleniumLocator);
	}
	 */

	/*	protected String[] getContentsOfList(Field field) throws MetadataException {
		setSeleniumLocator(field.getLocators());

		return selenium.getSelectOptions(seleniumLocator);
	}
	 */	
	protected List<String> getListContents(Field field) throws MetadataException {
		List<WebElement> elements = new ArrayList<WebElement>();

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//elements = new Select(driver.findElement(By.xpath(seleniumLocator))).getOptions();
		elements = new Select(element).getOptions();

		return getWebElementListText(elements);
	}

	private List<String> getWebElementListText(List<WebElement> elements) {
		List<String> contents = new ArrayList<String>(); 

		for (WebElement element : elements) {
			contents.add(element.getText());
		}

		return contents;
	}

	//Search for text anywhere on the page
	/*	protected boolean isTextPresent(String text) {
		return selenium.isTextPresent(text);
	}
	 */	
	protected boolean isTextPresent(String text) {
		boolean found = false;

		try {
			WebElement bodyElement = driver.findElement(By.tagName("body"));
			found = bodyElement.getText().contains(text);
			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
			//} catch (NoSuchElementException e) {}
		} catch (Exception e) {}

		return found;
	}

	//As part of the migration to WebDriver, it appears that we no longer need to make this call.
	//  If it turns out that we do, we can use SOME of the logic in getTableAsListOfList
	//TODO  Optimize all table access code to use variants of getTableAsListOfList.  Note that often
	//      we don't need to execute all of the code in getTableAsListOfList because we just need 
	//      a row count, the contents of a row, etc.
	/*	protected String getTable(String locator, int rowNumber, int columnNumber) {
		//Locator is assumed to point to a table, and contain .rowNumber and .columnNumber, e.g.:
		//  myTable.2.3
		return selenium.getTable(locator);
	}
	 */

	/*	protected List<List<String>> getTableAsListOfList(String locator, boolean includeHeaders) throws MetadataException {
		List<List<String>> tableContents = new ArrayList<List<String>>();
		List<String> rowContents = new ArrayList<String>();
		List<WebElement> rows = new ArrayList<WebElement>();
		List<WebElement> cells = new ArrayList<WebElement>();
		List<WebElement> chunks = new ArrayList<WebElement>();
		WebElement table = null;
		WebElement row;
		boolean checkedForHeaders = false;
		boolean isDataColumn;
		String text;

		//TODO  This needs to be tested -  ensure that it works for all table types
		//  (see Page methods that call this)

		//NOTE that the locator MUST end with tbody!!!
        try {
			table = driver.findElement(By.xpath(locator));
		//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
		//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
		//} catch (NoSuchElementException e) {
		} catch (Exception e) {
			throw new MetadataException("Could not find a table using the locator '" + locator + "'");
		}

        //Now that we have found the table, the following findElements calls do not need to wait
	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

        //get all the rows (tr elements) from the table
        rows = table.findElements(By.xpath("tr"));

        //iterate over the rows, getting the text from the cells
		ListIterator<WebElement> itr = rows.listIterator();
		while (itr.hasNext()) {
			row = itr.next();
        	if ((includeHeaders) && (!checkedForHeaders)) {
        		cells = row.findElements(By.tagName("th"));
            	checkedForHeaders = true;
        	} else {
        		//cells = row.findElements(By.xpath("*[normalize-space(@class)='dataCell']"));
        		cells = row.findElements(By.tagName("td"));
        	}

        	rowContents.clear();


            //Some cells contain two bits of data.  By default, we will grab the first one.  But
        	//  we only care about that if the cell is in a data column, not a label column
        	isDataColumn = false;
        	for (WebElement cell : cells) {
        		if (isDataColumn) {
            		chunks = cell.findElements(By.tagName("*"));
            		if (chunks.size() > 0) {
            			text = chunks.get(0).getText();
            		} else {
            			text = cell.getText();
            		}
        		} else {
            		text = cell.getText();
        		}

            	rowContents.add(text.trim());

            	isDataColumn = !isDataColumn;
        	}

        	tableContents.add(new ArrayList<String>(rowContents));
        }

		//Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        return tableContents;
	}
	 */
	/*	protected List<List<String>> getTableAsListOfList(String locator, boolean includeHeaders) throws MetadataException {
		List<List<String>> tableContents = new ArrayList<List<String>>();
		List<String> rowContents = new ArrayList<String>();
		List<WebElement> rows = new ArrayList<WebElement>();
		List<WebElement> cells = new ArrayList<WebElement>();
		List<WebElement> chunks = new ArrayList<WebElement>();
		WebElement table = null;
		WebElement row;
		boolean checkedForHeaders = false;
		boolean isDataColumn;
		String text;

		//TODO  This needs to be tested -  ensure that it works for all table types
		//  (see Page methods that call this)

		//NOTE that the locator MUST end with tbody!!!
        try {
			table = driver.findElement(By.xpath(locator));
		//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
		//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
		//} catch (NoSuchElementException e) {
		} catch (Exception e) {
			throw new MetadataException("Could not find a table using the locator '" + locator + "'");
		}

        //Now that we have found the table, the following findElements calls do not need to wait
	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

        //get all the rows (tr elements) from the table
        rows = table.findElements(By.xpath("tr"));

        //iterate over the rows, getting the text from the cells
		ListIterator<WebElement> itr = rows.listIterator();
		while (itr.hasNext()) {
			row = itr.next();
        	if ((includeHeaders) && (!checkedForHeaders)) {
        		cells = row.findElements(By.tagName("th"));
            	checkedForHeaders = true;
        	} else {
        		//cells = row.findElements(By.xpath("*[normalize-space(@class)='dataCell']"));
        		cells = row.findElements(By.tagName("td"));
        	}

        	rowContents.clear(); 		

            //Some cells contain more than one bit of data, and sometimes empty strings are included.  By default, we will grab
        	//  the first non-empty string, if there is one.  (But we only care about this if the cell is in a data column, not
        	//  a label column)
        	isDataColumn = false;
        	text = "";
        	for (WebElement cell : cells) {
        		if (isDataColumn) {
            		chunks = cell.findElements(By.tagName("*"));
            		if (chunks.size() > 0) {
            			for (WebElement chunk : chunks) {
            				text = chunk.getText().trim();
            				if (!text.isEmpty()) {
            					break;
            				}
            			}
            		} else {
            			text = cell.getText().trim();
            		}
        		} else {
            		text = cell.getText().trim();
        		}

            	rowContents.add(text);

            	isDataColumn = !isDataColumn;
        	}

        	tableContents.add(new ArrayList<String>(rowContents));
        }

		//Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        return tableContents;
	}
	 */

	//The logic for returning/not returning header rows is very difficult to implement, given that even in some data rows the th
	//  tag is used.  That makes it very difficult to grab cells from a data row without inadvertently grabbing cells from the
	//  header row.  Let's abandon the handling of header rows for now.
	//!!!!! BUT DO SAVE THIS CODE !!!!!
	/*	protected List<List<String>> getTableAsListOfList(String locator, boolean includeHeaders) throws MetadataException {
		List<List<String>> tableContents = new ArrayList<List<String>>();
		List<String> rowContents = new ArrayList<String>();
		List<WebElement> rows = new ArrayList<WebElement>();
		List<WebElement> cells = new ArrayList<WebElement>();
		List<WebElement> chunks = new ArrayList<WebElement>();
		WebElement table = null;
		WebElement row;
		WebElement chunk;
		int chunkIndex;
		boolean checkedForHeaders = false;
		boolean isDataColumn;
		String text;

		//TODO  This needs to be tested -  ensure that it works for all table types
		//  (see Page methods that call this)

		//NOTE IN MOST CASES the locator must end with tbody!!!  But not always - PageViewDiscountApprovalRequest.approvalChainTable is an example.
		//  In Firefox it is rendered as:
		//		table
		//			tbody
		//				tr
		//				tr
		//				tr
		//				etc.
		//	But in IE it is rendered as:
		//		table
		//			label
		//				tbody
		//					tr  (just one of these)
		//			tbody
		//				label
		//					tr
		//				label
		//					tr
		//				label
		//					tr
		//					etc.
		//  Leaving the tbody off of the locator works (at least for IE - I don't know about FF)

        try {
			table = driver.findElement(By.xpath(locator));
		//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
		//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
		//} catch (NoSuchElementException e) {
		} catch (Exception e) {
			throw new MetadataException("Could not find a table using the locator '" + locator + "'");
		}

        //Now that we have found the table, the following findElements calls do not need to wait
	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

        //get all the rows (tr elements) from the table
        //rows = table.findElements(By.xpath("tr"));
        rows = table.findElements(By.tagName("tr"));

        //iterate over the rows, getting the text from the cells
		ListIterator<WebElement> itr = rows.listIterator();
		while (itr.hasNext()) {
			row = itr.next();
        	if ((includeHeaders) && (!checkedForHeaders)) {
        		cells = row.findElements(By.tagName("th"));
            	checkedForHeaders = true;
        	} else {
        		//cells = row.findElements(By.xpath("*[normalize-space(@class)='dataCell']"));
        		cells = row.findElements(By.tagName("td"));
        	}

        	rowContents.clear(); 		

            //Some cells contain more than one bit of data, and sometimes empty strings are included.
        	//  This typically occurs when the column contains a link (usually we want its text), and
        	//  a "Change" link, and/or an image, and/or some other stuff.  Our plan used to be:
        	//    grab the first non-empty string, if there is one
        	//  Now it is:
        	//    grab the next to last non-empty element
        	//  (But we only care about all this if the cell is in a data column, not a label column)
        	isDataColumn = false;
        	text = "";
        	for (WebElement cell : cells) {
        		if (isDataColumn) {
            		chunks = cell.findElements(By.tagName("*"));
            		if (chunks.size() > 1) {
            			Iterator<WebElement> chunkItr = chunks.iterator();
            			while (chunkItr.hasNext()) {
            				chunk = chunkItr.next();

            				text = chunk.getText().trim();
            				if (text.isEmpty()) {
            					chunkItr.remove();
            				}
            			}
            			chunkIndex = chunks.size() - 2;
            			if (chunkIndex >= 0) {
            				text = chunks.get(chunkIndex).getText().trim();
            			} else {
            				text = "";
            			}
            		} else {
            			text = cell.getText().trim();
            		}
        		} else {
            		text = cell.getText().trim();
        		}

            	rowContents.add(text);

            	isDataColumn = !isDataColumn;
        	}

        	tableContents.add(new ArrayList<String>(rowContents));
        }

		//Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        return tableContents;
	}
	 */

	//Better findElement calls in this version make dealing with header rows much easier, so header rows
	//  are now supported.  HOWEVER, we are now referring to the "header row" as the "first row", because while
	//  a related list has headers an 'info panel' does not.  (Regular tables sometimes do, and sometimes don't...)
	protected List<List<String>> getTableAsListOfList(String locator, boolean returnFirstRow) throws MetadataException {
		List<List<String>> tableContents = new ArrayList<List<String>>();
		List<String> rowContents = new ArrayList<String>();
		List<WebElement> rows = new ArrayList<WebElement>();
		List<WebElement> cells = new ArrayList<WebElement>();
		List<WebElement> chunks = new ArrayList<WebElement>();
		boolean isFirstRow = true;
		WebElement table = null;
		WebElement row;
		WebElement chunk;
		int chunkIndex;
		boolean isDataColumn;
		String text;

		//TODO  This needs to be tested -  ensure that it works for all table types
		//  (see Page methods that call this)

		//NOTE IN MOST CASES the locator must end with tbody!!!  But not always - PageViewDiscountApprovalRequest.approvalChainTable is an example.
		//  In Firefox it is rendered as:
		//		table
		//			tbody
		//				tr
		//				tr
		//				tr
		//				etc.
		//	But in IE it is rendered as:
		//		table
		//			label
		//				tbody
		//					tr  (just one of these)
		//			tbody
		//				label
		//					tr
		//				label
		//					tr
		//				label
		//					tr
		//					etc.
		//  Leaving the tbody off of the locator works (at least for IE - I don't know about FF)

		try {
			//wait is so that we don't accidentally get empty table element
			synchronized (driver){driver.wait(2500);}
			table = driver.findElement(By.xpath(locator));
			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
			//} catch (NoSuchElementException e) {
		} catch (Exception e) {
			throw new MetadataException("Could not find a table using the locator '" + locator + "'");
		}

		//Now that we have found the table, the following findElements calls do not need to wait
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		//get all the rows (tr elements) from the table
		//rows = table.findElements(By.xpath("tr"));
		rows = table.findElements(By.tagName("tr"));

		//iterate over the rows, getting the text from the cells
		ListIterator<WebElement> itr = rows.listIterator();
		while (itr.hasNext()) {
			row = itr.next();

			//Note that the first row will be headers for a related list, but data in an 'info panel'
			//  (I'm not sure about regular tables)
			if ((!returnFirstRow) && (isFirstRow)) {
				isFirstRow = false;

				continue;
			} else {
				//cells = row.findElements(By.xpath("*[normalize-space(@class)='dataCell']"));
				//cells = row.findElements(By.tagName("td"));
				//Sometimes DATA cells use the tag th instead of td!  So we can't count on
				//  the presence of th's as a way of determining whether a table has a header
				//  row!
				cells = row.findElements(By.xpath("child::*[self::td or self::th]"));

				rowContents.clear(); 		

				//Some cells contain more than one bit of data, and sometimes empty strings are included.
				//  This typically occurs when the column contains a link (usually we want its text), and
				//  a "Change" link, and/or an image, and/or some other stuff.  Our plan used to be:
				//    grab the first non-empty string, if there is one
				//  Now it is:
				//    grab the next to last non-empty element
				//  (But we only care about all this if the cell is in a data column, not a label column)
				isDataColumn = false;
				text = "";
				for (WebElement cell : cells) {
					if (isDataColumn) {
						chunks = cell.findElements(By.tagName("*"));
						if (chunks.size() > 1) {
							Iterator<WebElement> chunkItr = chunks.iterator();
							while (chunkItr.hasNext()) {
								chunk = chunkItr.next();

								text = chunk.getText().trim();
								if (text.isEmpty()) {
									chunkItr.remove();
								}
							}
							chunkIndex = chunks.size() - 2;
							if (chunkIndex >= 0) {
								text = chunks.get(chunkIndex).getText().trim();
							} else {
								text = "";
							}
						} else {
							text = cell.getText().trim();
						}
					} else {
						text = cell.getText().trim();
					}

					rowContents.add(text);

					isDataColumn = !isDataColumn;
				}

				tableContents.add(new ArrayList<String>(rowContents));
			}
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return tableContents;
	}

	protected List<List<String>> getTableAsListOfList(String locator) throws MetadataException {
		return getTableAsListOfList(locator, false);
	}

	//Better findElement calls in this version make dealing with header rows much easier, so header rows
	//  are now supported.  HOWEVER, we are now referring to the "header row" as the "first row", because while
	//  a related list has headers an 'info panel' does not.  (I'm not sure about regular tables)
	protected int getTableSize(String locator, boolean returnFirstRow) throws MetadataException {
		int size;
		WebElement table = null;

		//TODO  This needs to be tested -  ensure that it works for all table types
		//  (see Page methods that call this)

		//NOTE IN MOST CASES the locator must end with tbody!!!  But not always - PageViewDiscountApprovalRequest.approvalChainTable is an example.
		//  In Firefox it is rendered as:
		//		table
		//			tbody
		//				tr
		//				tr
		//				tr
		//				etc.
		//	But in IE it is rendered as:
		//		table
		//			label
		//				tbody
		//					tr  (just one of these)
		//			tbody
		//				label
		//					tr
		//				label
		//					tr
		//				label
		//					tr
		//					etc.
		//  Leaving the tbody off of the locator works (at least for IE - I don't know about FF)

		try {
			table = driver.findElement(By.xpath(locator));
			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
			//} catch (NoSuchElementException e) {
		} catch (Exception e) {
			throw new MetadataException("Could not find a table using the locator '" + locator + "'");
		}

		size = table.findElements(By.tagName("tr")).size();
		if (!returnFirstRow) {
			size--;
		}

		return size;
	}

	protected int getTableSize(String locator) throws MetadataException {
		return getTableSize(locator, false);
	}

	protected int getTableWidth(String locator) throws MetadataException {
		int width = -1;
		List<WebElement> rows = new ArrayList<WebElement>();
		WebElement table = null;

		try {
			table = driver.findElement(By.xpath(locator));
			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
			//} catch (NoSuchElementException e) {
		} catch (Exception e) {
			throw new MetadataException("Could not find a table using the locator '" + locator + "'");
		}

		rows = table.findElements(By.tagName("tr"));

		if (rows.size() > 0) {
			width = rows.get(0).findElements(By.xpath("child::*[self::td or self::th]")).size();
		}

		return width;
	}

	//WebDriver does not differentiate between alerts and confirmations
	/*    protected void chooseCancelOnNextConfirmation() {
    	selenium.chooseCancelOnNextConfirmation();
    }
	 */

	//WebDriver does not differentiate between alerts and confirmations
	/*    protected boolean isConfirmationPresent() {
    	return selenium.isConfirmationPresent();
    }
	 */

	//WebDriver does not differentiate between alerts and confirmations
	/*    protected String getConfirmation() {
    	return selenium.getConfirmation();
    }
	 */

	/*    protected boolean isAlertPresent() {
    	return selenium.isAlertPresent();
    }
	 */

	protected String handleAlert(EISConstants.AlertResponseType alertResponse) {
		//NOTE that the caller needs to point the driver to the main window
		//  by calling mainWindow.select()!

		String alertText = "[no alert appeared]";

		if (isAlertPresent()) {
			alertText = getAlert();
			respondToAlert(alertResponse);
		}

		return alertText;
	}

	protected boolean isAlertPresent() {
		//NOTE that the caller needs to point the driver to the main window
		//  by calling mainWindow.select()!

		boolean found = false;

		//We will control how long we wait for the alert (although the setting doesn't seem to make
		//  much difference)
		//driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		//   driver.manage().timeouts().implicitlyWait(50, TimeUnit.MILLISECONDS);

		try {
			//If we don't get an exception here, we know we have an alert.
			driver.switchTo().alert();

			found = true;
		} catch (NoAlertPresentException e) {}

		//Set the implicit wait time back to the default
		//    	driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return found;
	}

	protected String getAlert() {
		//NOTE that the caller needs to point the driver to the main window
		//  by calling mainWindow.select()!

		return driver.switchTo().alert().getText();
	}

	protected void respondToAlert(EISConstants.AlertResponseType alertResponse) {
		//NOTE that the caller needs to point the driver to the main window
		//  by calling mainWindow.select()!

		Alert alert = driver.switchTo().alert();

		switch (alertResponse) {
		case CANCEL:	{	
			alert.dismiss();
			break;
		}
		case OK:
		default:	{
			alert.accept();
			break;
		}
		case IAgree:	{	
			alert.accept();
			break;
		}


		}
	}

	/*    protected void waitForPageToLoad(int waitTime) throws GUIException {
		Util.printDebug("NOTE that gui.waitForPageToLoad() is deprecated!  ...AndWait methods should model their waiting strategy after that in Page.clickToSubmit");


		//HOWEVER, we may need to do something, because WebDriver is known to not wait for a page to load after a click...
		//AND WHAT WE SHOULD DO IS write a Page.waitForPageToLoad method that calls Page.waitForElementPresent.  By default it
		//  would wait on a Field that we can almost always expect to be present (like EISConstants.DEFAULT_FIELD_TO_WAIT_FOR
		//  on commonPage).  But it could also be passed another Field.  doPopulate would not call it; the "andWait" and
		//  "toSubmit" methods would call it (any method that sets Page.waitTime).
		//!!!NOTE that I have already started doing this, in Page.clickToSubmit


    	//Sometimes we get a Selenium exception here (socket timeout).  I can trap it, but I don't know how to
    	//  recover from it.  We can also trap the waitForPageToLoad exception here (the one it throws if a
    	//  Selenium call has been made in the interim since the page load was initiated) 

		try {
			selenium.waitForPageToLoad(Integer.toString(waitTime));
		//TODO  Address exception trapping - is SeleniumException what we should be trapping?
		} catch (SeleniumException se) {
			throw new GUIException(se.getMessage());
		}

    }
	 */

	/*    protected void waitForPageToLoad() throws GUIException {
    	//waitForPageToLoad(EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
    	waitForPageToLoad(windowWaitTimeout);
    }
	 */

	/*    protected void waitForCondition(String script, int waitTime) throws GUIException {
		//We can get an uncatchable exception here if the window has been closed!  We can try to deal with it by
		//  calling Page.clearPageLoadErrorFlag(), but we then need to reselect the window that contains this
		//  page.  Unfortunately, at present Page instances do not contain a reference to the containing Window
    	//  instance, so we can't do that.
    	try {
    		selenium.waitForCondition(script, Integer.toString(waitTime));
		//TODO  Address exception trapping - is SeleniumException what we should be trapping?
    	//} catch (SeleniumException se) {
		//	throw new GUIException(se.getMessage());
    	//}
		} catch (Exception e) {
			throw new GUIException("Error executing Javascript '" + script + "': " + e.getMessage());
		}
    }
	 */
	/*    protected void waitForCondition(final String script, int timeToWaitMillis) throws GUIException {
    	//It is assumed that script represents a Javascript snippet that returns a boolean
    	boolean success = false;
    	int seconds = timeToWaitMillis / 1000;
    	Wait<WebDriver> wait = new WebDriverWait(driver, seconds);
    	//Wait wait = getWebDriverWait(waitTime);

    	try {
			success = wait.until
			    (
			        new ExpectedCondition<Boolean>() {
			        	public Boolean apply(WebDriver driver) {
			        		boolean success = false;
							try {
								success = (Boolean)getEval(script);
							} catch (GUIException e) {
								//I tried throwing a GUIException here, but it would not compile.  I hope
								//  the trapped exception propagates to the outer catch block
							}

			        		return success;
			        	}
			        }
			    );
		} catch (Exception e) {
			throw new GUIException("Error in waitForCondition(): " + e.getMessage());
		}

    	if (!success) {
			throw new GUIException("Error in waitForCondition(): the condition '" + script + "' did not pass within " + seconds + " seconds");
    	}
    }
	 */


	//Methods that wait for locator element present/absent, without refreshing
	protected boolean waitForElementPresent(String locator, int timeToWaitMillis) {
		return waitForElement(locator, true, timeToWaitMillis);
	}

	protected boolean waitForElementAbsent(String locator, int timeToWaitMillis) {
		return waitForElement(locator, false, timeToWaitMillis);
	}

	protected boolean waitForElement(String locator, boolean waitForPresent, int timeToWaitMillis) {
		boolean success = false;

		if (waitForPresent) {
			//We will control how long we wait for an element
			driver.manage().timeouts().implicitlyWait(timeToWaitMillis, TimeUnit.MILLISECONDS);

			try {
				driver.findElement(By.xpath(locator));

				success = true;
				//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
				//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
				//} catch (NoSuchElementException e) {}
			} catch (Exception e) {}
		} else {
			final int interval = 500;

			//We will control how long we wait for an element
			driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS);

			long startTime = System.currentTimeMillis();
			long endTime = startTime + timeToWaitMillis;

			while (System.currentTimeMillis() < endTime) {
				try {
					driver.findElement(By.xpath(locator));
					//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
					//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
					//} catch (NoSuchElementException e) {
				} catch (Exception e) {
					success = true;
					break;
				}
			}

		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return success;
	}


	//Methods that wait for Field element present/absent, without refreshing
	protected boolean waitForElementPresent(Field field, int timeToWaitMillis) {
		boolean success;

		try {
			//If setSeleniumLocator() does NOT throw an exception, it's there; otherwise it isn't there
			setSeleniumLocator(field.getLocators(), timeToWaitMillis);

			success = true;
		} catch (MetadataException me) {
			success = false;
		}

		return success;
	}

	protected boolean waitForElementAbsent(Field field, int timeToWaitMillis) {
		boolean success;
		List<String> locators = new ArrayList<String>(field.getLocators());

		//First see if it is already gone - if so, we're outta here
		//  CAUTION - it may be seen to be absent because the locator is incorrect,
		//  but there's nothing we can do about that
		if (isElementPresent(locators)) {
			try {
				//It's there, so let's wait for it to go away.
				//  If setSeleniumLocator() throws an exception, it's already gone, so the catch block
				//    will set success to true
				//  If setSeleniumLocator() does NOT throw an exception, it's still there, and
				//    waitForElement() will be called
				setSeleniumLocator(locators, timeToWaitMillis);

				//TODO  Change the timeout that we pass to waitForElement() here to reflect the
				//  amount of time spent in setSeleniumLocator()) (although most likely it wasn't
				//  long, because if we made it here it means that setSeleniumLocator() did not
				//  throw an exception, meaning the element is present; in that case, it would
				//  have found it almost immediately)
				success = waitForElement(seleniumLocator, false, timeToWaitMillis);
			} catch (MetadataException me) {
				success = true;
			}
		} else {
			success = true;

			Util.printDebug("In GUI.waitForElementAbsent to wait for the Field '" + field.getName() + "' to be absent, but it was already gone");
		}

		return success;
	}

	protected boolean waitForField(Field field, boolean waitForPresent, int timeToWaitMillis) {
		boolean success;

		if (waitForPresent) {
			success = waitForElementPresent(field, timeToWaitMillis);
		} else {
			success = waitForElementAbsent(field, timeToWaitMillis);			
		}

		return success;
	}


	//Method that waits for Field element present and visible (or invisible) without refreshing
	/*	protected boolean waitForElementVisible(Field field, int timeToWaitMillis) {
    	final int MIN_TIME_SLICE = 100;
    	final int MAX_TIME_SLICE = 500;

    	WebElement element;

    	List<String> locators = field.getLocators();
    	boolean success = false;

	    long interval = Math.min(timeToWaitMillis / locators.size(), MAX_TIME_SLICE);
	    interval = Math.max(interval, MIN_TIME_SLICE);

	    long endTime = System.currentTimeMillis() + timeToWaitMillis;

    	//We will control how long we wait for an element  
	    driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS); //and no sleep below

	    while (System.currentTimeMillis() < endTime) {
        	for (String locator : locators) {
        		try {
        			//element = driver.findElement(By.xpath(locator));
        		    element = (new WebDriverWait(driver, 1)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))); 
        		    element = (new WebDriverWait(driver, 1)).until(ExpectedConditions.elementToBeClickable(By.xpath(locator))); 

        			Util.printDebug("Found the locator '" + locator + "'");

        			success = element.isDisplayed();

        			if (success) {
            			Util.printDebug("Found the visible locator '" + locator + "'");

        				break;
        			}
    			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
    			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
    			//} catch (NoSuchElementException e) {
    			} catch (Exception e) {
        			Util.printDebug("Didn't find the locator '" + locator + "'");
        		}
        	}

    		if (success) {
    			break;
    		}
        }

	    //Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        return success;
	}
	 */	
	/*	protected boolean waitForElementVisible(Field field, int timeToWaitMillis) {
    	final int MIN_TIME_SLICE = 100;
    	final int MAX_TIME_SLICE = 500;

    	WebElement element = null;

    	List<String> locators = field.getLocators();
    	boolean success = false;

	    long interval = Math.min(timeToWaitMillis / locators.size(), MAX_TIME_SLICE);
	    interval = Math.max(interval, MIN_TIME_SLICE);

	    long endTime = System.currentTimeMillis() + timeToWaitMillis;

    	//We will control how long we wait for an element  
	    driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS); //and no sleep below

	    while (System.currentTimeMillis() < endTime) {
        	for (String locator : locators) {
        		try {
        			//element = driver.findElement(By.xpath(locator));
        		    element = (new WebDriverWait(driver, 1)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))); 
        		    element = (new WebDriverWait(driver, 1)).until(ExpectedConditions.elementToBeClickable(By.xpath(locator))); 

        			Util.printDebug("Found the locator '" + locator + "'");

        			if (element != null) {
            			success = element.isDisplayed();
         			}

        			if (success) {
            			Util.printDebug("Found the visible locator '" + locator + "'");

        				break;
        			}
    			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
    			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
    			//} catch (NoSuchElementException e) {
    			} catch (Exception e) {
        			Util.printDebug("Didn't find the locator '" + locator + "'");
        		}
        	}

    		if (success) {
    			break;
    		}
        }

	    //Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        return success;
	}
	 */	

	//NOTE that I rewrote this code because something in the prior version (the "until" calls?) were causing
	//  the JVM to crash (but only when run from Jenkins)
	protected boolean waitForElementVisibility(Field field, boolean bWaitForVisibility, int timeToWaitMillis) {
		boolean isPresent;
		boolean isVisible = false;
		long elapsedTime = 0;

		try {
			//If setSeleniumLocator() does NOT throw an exception, it's there; otherwise it isn't there
			elapsedTime = setSeleniumLocator(field.getLocators(), timeToWaitMillis);

			isPresent = true;
		} catch (MetadataException me) {
			isPresent = false;
		}

		Util.printDebug("In GUI.waitForElementVisibility to wait for the Field '" + field.getName() + "' to be " + (bWaitForVisibility? "" : "not ") + "visible");

		if (isPresent) {
			final int interval = 500;

			//We will control how long we wait for an element
			driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS);

			long startTime = System.currentTimeMillis();
			long endTime = startTime + timeToWaitMillis - elapsedTime;

			while (System.currentTimeMillis() < endTime) {
				try {
					//Do I really need to find the element again before checking?
					//isVisible = element.isDisplayed();
					isVisible = driver.findElement(By.xpath(seleniumLocator)).isDisplayed();

					if (isVisible == bWaitForVisibility) {
						Util.printDebug("Locator '" + seleniumLocator + "' is " + (bWaitForVisibility? "" : "not ") + "visible");

						break;
					}

					Util.printDebug("Locator '" + seleniumLocator + "' is " + (bWaitForVisibility? "not " : "") + "visible");

					//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
					//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
					//} catch (NoSuchElementException e) {
				} catch (Exception e) {
					//We should never get here, since seleniumLocator is known to be valid (isPresent == true)
				}
			}			

			//Set the implicit wait time back to the default
			driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
		}

		return isVisible;
	}

	protected boolean waitForFieldVisible(Field field, int timeToWaitMillis) {
		return waitForElementVisibility(field, true, timeToWaitMillis);
	}

	protected boolean waitForFieldNotVisible(Field field, int timeToWaitMillis) {
		return waitForElementVisibility(field, false, timeToWaitMillis);
	}

	protected boolean waitForFieldEnabled(Field field, int timeToWaitMillis) {
		boolean isPresent;
		boolean isEnabled = false;
		long elapsedTime = 0;

		try {
			//If setSeleniumLocator() does NOT throw an exception, it's there; otherwise it isn't there
			elapsedTime = setSeleniumLocator(field.getLocators(), timeToWaitMillis);

			isPresent = true;
		} catch (MetadataException me) {
			isPresent = false;
		}

		Util.printDebug("In GUI.waitForElementEnabled to wait for the Field '" + field.getName() + "' to be enabled");

		if (isPresent) {
			final int interval = 500;

			//We will control how long we wait for an element
			driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS);

			long startTime = System.currentTimeMillis();
			long endTime = startTime + timeToWaitMillis - elapsedTime;

			while (System.currentTimeMillis() < endTime) {
				try {
					//Do I really need to find the element again before checking?
					isEnabled = driver.findElement(By.xpath(seleniumLocator)).isEnabled();

					if (isEnabled) {
						Util.printDebug("Locator '" + seleniumLocator + "' is enabled");

						break;
					}

					Util.printDebug("Locator '" + seleniumLocator + "' is not enabled");

					//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
					//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
					//} catch (NoSuchElementException e) {
				} catch (Exception e) {
					//We should never get here, since seleniumLocator is known to be valid (isPresent == true)
				}
			}			

			//Set the implicit wait time back to the default
			driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
		}

		return isEnabled;
	}


	//Methods that wait for locator element present/absent, while refreshing
	protected boolean waitForElementPresentWhileRefreshing(String locator, int timeToWaitMillis) {
		return waitForElementWhileRefreshing(locator, true, timeToWaitMillis);
	}

	protected boolean waitForElementAbsentWhileRefreshing(String locator, int timeToWaitMillis) {
		return waitForElementWhileRefreshing(locator, false, timeToWaitMillis);
	}

	/*	protected boolean waitForElementWhileRefreshing(String locator, boolean waitForPresent, int timeToWaitMillis) {
		boolean success = false;
		int interval = EISConstants.DEFAULT_PAGE_REFRESH_INTERVAL;

		try {
			//seleniumTest.waitForElementWhileRefreshing does not return a boolean - if the condition is met within
			//  the timeout threshold, it does not throw an exception; otherwise it does.
			//a first pass... (just to get it to compile)
			//  BE SURE that the exceptions we are catching are appropriate to whatever replaces the kludge below 
			//  We will create our own waitForElement class, which will use findElement to wait.
			//  see http://groups.google.com/group/webdriver/browse_thread/thread/6e705242cc6d75ed
			//seleniumTest.waitForElementWhileRefreshing("", locator, waitForPresent, timeToWaitMillis, interval);
			Util.sleep(timeToWaitMillis); //A kludge, just to get it to compile
			driver.navigate().refresh();

			success = true;
		} catch (Exception e) {
			Util.printDebug("An exception (" + e.getMessage() + ") occurred during waitForElementWhileRefreshing call (locator = '" + locator + "'");

			success = false;
		}

		return success;
	}
	 */

	protected boolean waitForElementWhileRefreshing(String locator, boolean waitForPresent, int timeToWaitMillis) {
		boolean success = false;
		String expectedState = waitForPresent ? "present" : "absent";
		int interval = EISConstants.DEFAULT_PAGE_REFRESH_INTERVAL;

		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeToWaitMillis;

		while (System.currentTimeMillis() < endTime) {
			if (success = waitForElement(locator, waitForPresent, interval)) {
				break;
			}

			try {
				driver.navigate().refresh();
			} catch (Exception e) {
				Util.printDebug("An exception (" + e.getMessage() + ") occurred during page refresh while waiting for the locator '" + locator + "' to be " + expectedState);
			}
		}

		return success;
	}


	//Methods that wait for Field element present/absent, while refreshing
	/*	protected boolean waitForElementPresentWhileRefreshing(Field field, int timeToWaitMillis) {
		boolean success = false;
		List<String> locators = new ArrayList<String>(field.getLocators());
		long endTime;

	    endTime = System.currentTimeMillis() + timeToWaitMillis;

        while (System.currentTimeMillis() < endTime) {        	
			try {
				//If setSeleniumLocator() does NOT throw an exception, it's there; otherwise it isn't there
				setSeleniumLocator(locators, (EISConstants.DEFAULT_PAGE_REFRESH_INTERVAL / 2));

				success = true;
			} catch (MetadataException me) {}

			if (success) {
				break;
			}

			refresh(EISConstants.DEFAULT_PAGE_REFRESH_INTERVAL / 2);
        }

		return success;
	}
	 */

	protected boolean waitForElementPresentWhileRefreshing(Field field, int timeToWaitMillis) {
		return waitForFieldWhileRefreshing(field, true, timeToWaitMillis);
	}

	/*	protected boolean waitForElementAbsentWhileRefreshing(Field field, int timeToWaitMillis) {
		boolean success;
		List<String> locators = new ArrayList<String>(field.getLocators());

		//First see if it is already gone - if so, we're outta here
		//  CAUTION - it may be seen to be absent because the locator is incorrect,
		//  but there's nothing we can do about that
		if (isElementPresent(locators)) {
			try {
				//It's there, so let's wait for it to go away.
				//  If setSeleniumLocator() throws an exception, it's gone, so the catch block
				//    will set success to true
				//  If setSeleniumLocator() does NOT throw an exception, it's still there, and
				//    waitForElementWhileRefreshing() will be called
				setSeleniumLocator(locators, timeToWaitMillis);

				//TODO  Change the timeout that we pass to waitForElementWhileRefreshing() here to reflect the
				//  amount of time spent in setSeleniumLocator() (although most likely it wasn't long, because
				//  if we made it here setSeleniumLocator() did not throw an exception, meaning the element is
				//  present; in that case, it would have found it almost immediately)
				success = waitForElementWhileRefreshing(seleniumLocator, false, timeToWaitMillis);
			} catch (MetadataException me) {
				success = true;
			}
		} else {
			success = true;
		}

		return success;
	}
	 */

	protected boolean waitForElementAbsentWhileRefreshing(Field field, int timeToWaitMillis) {
		return waitForFieldWhileRefreshing(field, false, timeToWaitMillis);
	}

	/*	protected boolean waitForElementWhileRefreshing(Field field, boolean waitForPresent, int timeToWaitMillis) {
		boolean success;

		if (waitForPresent) {
			success = waitForElementPresentWhileRefreshing(field, timeToWaitMillis);
		} else {
			success = waitForElementAbsentWhileRefreshing(field, timeToWaitMillis);			
		}

		return success;
	}
	 */

	protected boolean waitForFieldWhileRefreshing(Field field, boolean waitForPresent, int timeToWaitMillis) {
		boolean success = false;
		String expectedState = waitForPresent ? "present" : "absent";
		int interval = EISConstants.DEFAULT_PAGE_REFRESH_INTERVAL;

		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeToWaitMillis;

		while (System.currentTimeMillis() < endTime) {
			if (success = waitForField(field, waitForPresent, interval)) {
				break;
			}

			try {
				driver.navigate().refresh();
			} catch (Exception e) {
				Util.printDebug("An exception (" + e.getMessage() + ") occurred during page refresh while waiting for the Field '" + field.getName() + "' to be " + expectedState);
			}
		}

		return success;
	}


	//These methods:
	//  isElementPresent
	//  isVisible
	//  isEnabled
	//  isEditable
	//  isDisplayed
	//  findSeleniumLocator
	//  searchLocators

	//  are similar to setSeleniumLocator() in that they loop through a list of locators,
	//  but they do not wait between tries.  That is by design!  They are used when the page is known
	//  to be "settled", and are not meant to fail when a locator is not found.  They do not wait for
	//  the target element.  AND they do not set seleniumLocator!  (They do, however, trap the exception
	//  that is thrown when isElementPresent is called with a malformed locator.)
	//They are used primarily for accessing a GUI element using something other than a locator associated
	//  with a Field object.  Examples include accessing related lists and tables using xpaths defined in
	//  EISConstants.  These accesses are usually for verification purposes (e.g., existence, visibility),
	//  not for form populating.
	protected boolean isElementPresent(List<String> locators) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		return findSeleniumLocator(locators);
	}

	protected boolean isElementPresent(String locator) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		List<String> locators = new ArrayList<String>();

		locators.add(locator);

		return isElementPresent(locators);
	}

	protected boolean isVisible(List<String> locators) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		return isDisplayed(locators);
	}

	protected boolean isVisible(String locator) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		List<String> locators = new ArrayList<String>();

		locators.add(locator);

		return isVisible(locators);
	}

	protected boolean isDisplayed(List<String> locators) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		boolean displayed = false;

		//We are checking for the presence of the field by searching for the locator instead of setting
		//  seleniumLocator.  This is in keeping with the paradigm (outlined in the comments above the
		//  isElementPresent method) of not setting the locator when all we care about is getting the
		//  state of a GUI element, not populating or scraping its contents
		int locatorNum = searchLocators(locators);

		if (locatorNum >= 0) {
			//push the WebElement to WebElement list here as well
			elementList=driver.findElements(By.xpath(locators.get(locatorNum)));
			if(elementList.size()>0 && elementList!=null){
				element=elementList.get(0);	//the very first one
				displayed=element.isDisplayed();
			}
			//			displayed = driver.findElement(By.xpath(locators.get(locatorNum))).isDisplayed();
		}

		return displayed;
	}

	protected boolean isDisplayed(String locator) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		List<String> locators = new ArrayList<String>();

		locators.add(locator);

		return isDisplayed(locators);
	}

	private boolean findSeleniumLocator(List<String> locators) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		//DO NOT add a timeout value to this method!  It is used by methods (such as waitForElementAbsent)
		//  that expect an element to be present (or not) at a given time, so that its state can then be
		//  polled
		boolean foundLocator = false;		
		//We will control how long we wait for an element
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		for (String locator : locators) {
			try {
				/*driver.findElement(By.xpath(locator));*/
				/*foundLocator = true;*/
				List<WebElement> lsElements=driver.findElements(By.xpath(locator));
				if (lsElements.size()>0 && lsElements!=null){
					element=lsElements.get(0);		//the very first one
					foundLocator = true;
					elementList=lsElements;
				}
				//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
				//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
				//} catch (NoSuchElementException e) {}
			} catch (Exception e) {}

			if (foundLocator) {
				break;
			}
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return foundLocator;
	}

	@SuppressWarnings("unused")
	private boolean findSeleniumLocator(String locator) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for the target element.  AND it does not set seleniumLocator!
		List<String> locators = new ArrayList<String>();

		locators.add(locator);

		return findSeleniumLocator(locators);
	}

	/*	protected int searchLocators(List<String> locators) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for an element.  AND it does not set seleniumLocator!
		boolean foundLocator = false;
		int locatorNum = -1;

    	//We are not waiting for the target element to appear
    	driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		for (int index = 0; index < locators.size(); index++) {
			try {
				driver.findElement(By.xpath(locators.get(index)));

				foundLocator = true;
			//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
			//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
			//} catch (NoSuchElementException e) {}
			} catch (Exception e) {}

			if (foundLocator) {
				locatorNum = index;

				break;
			}
		}

    	//Set the implicit wait time back to the default
    	driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return locatorNum;
	}
	 */
	protected int searchLocators(List<String> locators) {
		//This is used when the page is known to be "settled", and is not meant to fail when a locator
		//  is not found.  It does not wait for an element.  AND it does not set seleniumLocator!
		int locatorNum = -1;

		//We are not waiting for the target element to appear
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		for (int index = 0; index < locators.size(); index++) {
			try {
				driver.findElement(By.xpath(locators.get(index)));

				locatorNum = index;
				break;
				//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
				//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
				//} catch (NoSuchElementException e) {}
			} catch (Exception e) {}
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return locatorNum;
	}

	//This represents an attempt to override WebDriver.findElement, with the intent of finding
	//  elements by ALL "by" types (xpath, id, cssSelector, etc.) instead of just one.  However,
	//  it turned out to be more trouble than it is worth, because of issues involved with setting
	//  and resetting implicit wait times; sometimes expecting the element to appear (or go away)
	//  within a period of time but other times expecting that to happen immediately; duplicating
	//  to a significant degree what is done in GUI.setSeleniumLocator, etc.
	/*    private WebElement findElement(By by) {
    	//NOTE that it is up to the caller to manage the driver's implicit wait time.  Here we set it to 0 before searching,
    	//  and set it back to EISTestBase.getDefaultPageWaitTimeout() before returning
    	final String[] searchContexts = new String[] {"ByXPath", "ById", "ByLinkText", "ByCssSelector", "ByName", "ByClassName", "ByTagName", "ByPartialLinkText"};
		WebElement element = null;
		boolean found = false;
		String specifiedByName = by.getClass().getSimpleName();				//"ByXPath"
		String toString = by.toString();									//"By.xpath: this one is preferred"
		String searchTerm = by.toString().split(":")[1].trim();				//"this one is preferred"

    	//We will control how long we wait for an element  
	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

	    //First try using the SearchContext that was passed in 
		try {
			element = driver.findElement(by);
			found = true;
		} catch (Exception e) {}

		if (!found) {
			for (String byName : searchContexts) {
				try {
					switch (byName) {
						case "ByXPath": 			element = driver.findElement(By.xpath(searchTerm));
						case "ById": 				element = driver.findElement(By.id(searchTerm));
						case "ByLinkText": 			element = driver.findElement(By.linkText(searchTerm));
						case "ByCssSelector": 		element = driver.findElement(By.cssSelector(searchTerm));
						case "ByName": 				element = driver.findElement(By.name(searchTerm));
						case "ByClassName": 		element = driver.findElement(By.className(searchTerm));
						case "ByTagName": 			element = driver.findElement(By.tagName(searchTerm));
						case "ByPartialLinkText":	element = driver.findElement(By.partialLinkText(searchTerm));
						//We should never get here...
						default:					element = driver.findElement(By.xpath(searchTerm));
					}

					found = true;
					break;
				} catch (Exception e) {}
			}
		}

		//Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        if (!found) {
			throw new NoSuchElementException();
		}

		return element;
	}
	 */	

	protected void selectWindow(String locator) {
		//selenium.selectWindow(locator);
		driver.switchTo().window(locator);
	}

	protected void closeWindow(String locator) {
		//selenium.close();
		selectWindow(locator);
		driver.close();
	}

	protected boolean closeAllWindowsBut(String... windowsToLeaveOpenLocators) {
		boolean closedPopUp = false;
		String locator;
		Set<String> openWindows = getWindowHandles();
		Set<String> windowsToLeaveOpen = new HashSet<String>(Arrays.asList(windowsToLeaveOpenLocators));
		Set<String> windowsToClose = Sets.difference(openWindows, windowsToLeaveOpen);

		Iterator<String> itr = windowsToClose.iterator();
		while (itr.hasNext()) {
			locator = itr.next();

			closeWindow(locator);
			closedPopUp = true;
		}

		return closedPopUp;
	}

	protected String getClosedWindowLocator(Set<String> existingWindows) {
		String locator = "";
		Set<String> openWindows = getWindowHandles();
		Set<String> closedWindows = Sets.difference(existingWindows, openWindows);

		//It's always possible that more than one window closed, but I'm not sure how to handle that...
		if (closedWindows.size() > 0) {
			locator = closedWindows.iterator().next();
		}

		return locator;
	}

	protected String getOpenedWindowLocator(Set<String> existingWindows) {
		String locator = "";
		Set<String> openWindows = getWindowHandles();
		Set<String> openedWindows = Sets.difference(openWindows, existingWindows);

		//It's always possible that more than one window opened, but I'm not sure how to handle that...
		if (openedWindows.size() > 0) {
			locator = openedWindows.iterator().next();
		}

		return locator;
	}

	protected String getWindowHandle() {
		return driver.getWindowHandle();
	}

	protected Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	//Since migration to WebDriver, this method is no longer relevant
	/*	protected String[] getAllWindowNames() {
		return selenium.getAllWindowNames();
	}
	 */	    

	/*	protected void open(String url) {
		selenium.open(url);
	}
	 */	
	protected void open(String url) {
		driver.get(url);

		//Just in case...
		Util.sleep(500);
	}

	protected String getLocation() {
		//If the page hasn't settled, getLocation can return an unexpected value - perhaps like a URL that
		//  appears for an instant during redirection.  So let's sleep just a bit. This problem seems to
		//  occur mostly when cloning SFDC objects - it's probably due to a bug in Selenium.waitForPagetoLoad
		//  (which we often call before calling this method) wherein it thinks the page has loaded
		//  because some part of the URL is the same as that of the previous page.  (When cloning, the URL
		//  of the new page is indeed the same as the URL of the base object, with some stuff added to the
		//  end. This URL is present in the location box for a second or so when the new object loads up, while
		//  redirection occurs.  Most likely, waitForPageToLoad cannot differentiate between the URL of the
		//  base object and the pre-redirected URL that appears when the page loads.)
		//Changed from 1000 to 500 on 03/08/2012 (after WebDriver port)
		//Util.sleep(1000);
		Util.sleep(500);

		//return selenium.getLocation();
		return driver.getCurrentUrl();
	}

	protected String getTitle() {
		//return selenium.getTitle();
		return driver.getTitle();
	}

	//NOTE that there are some differences in Javascript implementation between Selenium and WebDriver
	/*	protected String getEval(String command) throws GUIException {
		try {
			return selenium.getEval(command);
		} catch (Exception e) {
			throw new GUIException(e.getMessage());
		}
	}
	 */	
	protected Object getEval(String script, Object... args) throws GUIException {
		Object returnVal = null;
		JavascriptExecutor js = (JavascriptExecutor)driver;

		//In WebDriver, the Javascript code must contain a "return" call, and should not reference browserbot,
		//  which is not supported
		try {
			returnVal = js.executeScript(script, args);
		} catch (Exception e) {
			throw new GUIException("Error executing Javascript '" + script + "': " + e.getMessage());
		}

		return returnVal;
	}

	/*	protected String getAttribute(String locator, String attributeName) {
		String attribute = "";

		if (findSeleniumLocator(locator)) {
			attribute = selenium.getAttribute(locator + "@" + attributeName);
		}

		return attribute;
	}
	 */
	protected String getAttribute(Field field, String attributeName) throws MetadataException {
		String attribute = "";

		//Catch exception thrown by setSeleniumLocator, so that the caller can potentially fail the test gracefully,
		//  rather than have a following Selenium call fail ungracefully because it was passed a bad locator
		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}

		//attribute = selenium.getAttribute(seleniumLocator + "@" + attributeName);
		//attribute = driver.findElement(By.xpath(seleniumLocator)).getAttribute(attributeName);
		attribute = element.getAttribute(attributeName);

		return attribute;
	}

	/*    private void setSeleniumLocator(List<String> locators, int timeout) throws MetadataException {
    	final int MIN_TIME_SLICE = 100;
    	final int MAX_TIME_SLICE = 500;

    	boolean foundLocator = false;
    	int numTries = 0;

	    long interval = Math.min(timeout / locators.size(), MAX_TIME_SLICE);
	    interval = Math.max(interval, MIN_TIME_SLICE);

	    long endTime = System.currentTimeMillis() + timeout;

    	//We will control how long we wait for an element  
	    driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS); //and no sleep below

		seleniumLocator = "";
        while (System.currentTimeMillis() < endTime) {
        	for (String locator : locators) {
        		try {
        			numTries++;

        			//seleniumTest.waitForElement("", locator, true, interval);
        			//Util.sleep(interval);
        			driver.findElement(By.xpath(locator));
        			seleniumLocator = locator;

        			foundLocator = true;
				//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
				//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
				//} catch (NoSuchElementException e) {
				} catch (Exception e) {
        			Util.printDebug("Didn't find the locator '" + locator + "'");

        			continue;
        		}

        		if (foundLocator) {
        			Util.printDebug("Found the locator '" + locator + "'");

        			break;
        		}
        	}

    		if (foundLocator) {
    			break;
    		}
        }

    	//Set the implicit wait time back to the default
        driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

        if (!foundLocator) {
			throw new MetadataException("A valid Selenium locator was not found; tried the locator(s) " + locators.toString() + " " + numTries + " times over a period of " + (timeout / 1000) + " seconds");
		}
	}
	 */
	private long setSeleniumLocator(List<String> locators, int timeout) throws MetadataException {
		final int MIN_TIME_SLICE = 100;
		//final int MAX_TIME_SLICE = 500;
		final int MAX_TIME_SLICE = 250;

		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;
		long elapsedTime;

		boolean foundLocator = false;
		int numTries = 0;

		if (locators.size() == 0) {
			throw new MetadataException("While searching for a Selenium locator, no locators were specified to search for.  Perhaps the Field object has a parametrized locator type (like " + EISConstants.ParameterizedLocatorType.values() + "), in which case it would have no 'regular' locators");
		}

		long interval = Math.min(timeout / locators.size(), MAX_TIME_SLICE);
		interval = Math.max(interval, MIN_TIME_SLICE);

		//We will control how long we wait for an element  
		driver.manage().timeouts().implicitlyWait(interval, TimeUnit.MILLISECONDS); //and no sleep below

		seleniumLocator = "";
		while (System.currentTimeMillis() < endTime) {
			for (String locator : locators) {
				try {
					numTries++;        			
					//        			element=driver.findElement(By.xpath(locator));
					elementList=driver.findElements(By.xpath(locator));
					if (elementList!=null && elementList.size()!=0){
						//return the very first one, even findElement method does the same returns the first encountered element
						element = elementList.get(0);	
						seleniumLocator = locator;        			
						foundLocator = true;
						break;
					}
					/*seleniumLocator = locator;        			
    				foundLocator = true;
    				break;*/
					//Even though findElement is documented to throw NoSuchElementException (and indeed DOES THROW IT!!)
					//  catching it does not work.  We fail with java.lang.reflect.InvocationTargetException
					//} catch (NoSuchElementException e) {
				} catch (Exception e) {
					Util.printDebug("Didn't find the locator '" + locator + "'");
				}
			}

			if (foundLocator) {
				break;
			}
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		if (!foundLocator) {
			throw new MetadataException("A valid Selenium locator was not found; tried the locator(s) " + locators.toString() + " " + numTries + " times over a period of " + (timeout / 1000) + " seconds");
		}

		elapsedTime = System.currentTimeMillis() - startTime;

		Util.printDebug("Found the locator '" + seleniumLocator + "' after " + elapsedTime + " milliseconds");

		return elapsedTime;
	}

	private long setSeleniumLocator(List<String> locators) throws MetadataException {
		//setSeleniumLocator(locators, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		return setSeleniumLocator(locators, windowWaitTimeout);
	}

	@SuppressWarnings("unused")
	private boolean isEditable(String locator) {
		return isEnabled(locator);
	}

	private boolean isEnabled(String locator) {
		//We are assuming here that the element referenced by locator exists
		//NOTE that if we want to allow test code to check for field "enabledness", we will need
		//  to add a wrapper to Page, and make this method more robust by calling searchLocators 
		//  (see Page.isFieldVisible and gui.isVisible and gui.isDisplayed)
		boolean isEnabled;

		//We will control how long we wait for an element
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		WebElement element = driver.findElement(By.xpath(locator));

		isEnabled = element.isEnabled();

		if (!isEnabled) {
			Util.printDebug("The locator '" + locator + "' was not enabled; will sleep and try again");

			Util.sleep(1000);

			isEnabled = element.isEnabled();
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return isEnabled;
	}
	public boolean isFieldEnable(String locator) {

		boolean isEnabled=false;
		//Iterate throught lsit of elements
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
			try {
			
				element=driver.findElement(By.xpath(locator));
				isEnabled=element.isEnabled();
			} catch (Exception e) {}

		if (isEnabled) {
			Util.printDebug("Found the element as enabled:"+ locator);
			
		}else {
			Util.printDebug("Found the element as disabled. Expected to be enabled. Please check the element locator value:"+ locator);
		}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);

		return isEnabled;
	}

	protected void refresh() {
		//selenium.refresh();
		driver.navigate().refresh();
	}

	protected void refresh(int waitTime) {
		refresh();

		Util.sleep(waitTime);
	}

	private boolean waitForEnabled(WebElement element) {
		boolean isEnabled = element.isEnabled();

		if (!isEnabled) {
			Util.sleep(1000);

			isEnabled = element.isEnabled();
		}

		return isEnabled;
	}

	//DEBUG this code is not yet ready for prime time
	protected List<String> getMultipleTextValues(Field field) {
		List<String> locators = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		List<WebElement> elements = new ArrayList<WebElement>();
		int locatorNum;

		locators = field.getLocators();
		locatorNum = searchLocators(locators);

		if (locatorNum >= 0) {
			elements = driver.findElements(By.xpath(locators.get(locatorNum)));

			ListIterator<WebElement> itr = elements.listIterator();
			while (itr.hasNext()) {
				WebElement element = itr.next();

				values.add(element.getText());
			}
		}

		return values;
	}


	protected List<WebElement> getMultipleTextValuesfromField(Field field) throws MetadataException {    	
		setSeleniumLocator(field.getLocators());   	
		return elementList;
	}

	/**
	 * @Description: Wait for the element to disappear from the page THis uses explicit wait concepts
	 * @param field
	 * @param iTimeout
	 * @return
	 */
	public boolean waitForElementToDisappear(Field field, int iTimeout) {

		List<String> locators=field.getLocators();
		String newLocator="";
		boolean locatorDisappeared=false;	//set the initial val as false
		try {
			for(String loc: locators){
				newLocator=loc;
				WebDriverWait explicitWait= new WebDriverWait(driver, iTimeout, 1000);	//poll every sec and check if exists
				explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loc)));  //if it is find return true;
				locatorDisappeared=true;	
			}

		}
		catch (Exception ex) {
			//intentioanlly left blank
		}
		return locatorDisappeared;
	}
	/**
	 * @Description: Wait for the element to disappear from the page THis uses explicit wait concepts
	 * @param field
	 * @param iTimeout
	 * @return
	 */
	public boolean verifyIfElementIsPresentOnDOM(Field field, int iTimeout) {

		List<String> locators=field.getLocators();
		String newLocator="";
		boolean elementFound=false;	//set the initial val as false
		try {
			for(String loc: locators){
				newLocator=loc;
				WebDriverWait explicitWait= new WebDriverWait(driver, iTimeout, 100);	//poll every sec and check if exists
				explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loc)));  //if it is find return true;
				elementFound=true;	
			}

		}
		catch (Exception ex) {
			//intentioanlly left blank
		}
		return elementFound;
	}
	public void clickUsingLowLevelActions(Field field) throws MetadataException{

		WebElement element;

		//We will control how long we wait for an element
		//wait for a 2 sec'

		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

		Actions myAction= new Actions(driver);
		try {
			for(String locator:field.getLocators()){				
				element = driver.findElement(By.xpath(locator));
				if (element!=null){					
					Action action=myAction.moveToElement(element).click(element).build();
					//need to wait for 0.5 sec sometimes this is breaking.
					Util.sleep(500);
					action.perform();
					break;
				}
			}

		} catch (Exception me) {}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
	}
	
	public void clickUsingLowLevelActions(WebElement elementToClick) throws MetadataException{
		//We will control how long we wait for an element
		//wait for a 2 sec'

		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

		Actions myAction= new Actions(driver);
		try {
						
					Action action=myAction.moveToElement(elementToClick).click(elementToClick).build();
					//need to wait for 0.5 sec sometimes this is breaking.
					Util.sleep(500);
					action.perform();
											

		} catch (Exception me) {}

		//Set the implicit wait time back to the default
		driver.manage().timeouts().implicitlyWait(EISTestBase.getDefaultPageWaitTimeout(), TimeUnit.MILLISECONDS);
	}

	protected String getCssPropertyValue(Field field, String cssPropertyName) throws MetadataException {
		String attribute = "";

		//Get the css property value

		try {
			setSeleniumLocator(field.getLocators());
		} catch (MetadataException me) {
			throw me;
		}		
		attribute = element.getCssValue(cssPropertyName);		
		return attribute;
	}

}
