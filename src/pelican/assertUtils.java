package pelican;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import common.EISConstants;
import common.Util;

public class assertUtils {
	
	
	private static boolean verifyCaseSensitive	= true;
	private static boolean verifyMatch			= true;
	private static boolean verifyUnMatch        =false;
	private static boolean ignoreFailedAsserts	= false;
	private static boolean failOnSubmitError	= true;	

	private static String assertMessage			= "";
	private static String errorReason			= "";
	
	private static int numAsserts				= 0;
	
	 /**
     * Asserts that a condition is true. If it isn't, an AssertionError, with
     * the given message, is thrown.
     *
     * @param condition
     *            the condition to evaluate
     * @param message
     *            
     */
     public static void assertTrue(boolean condition, String message)
    {
    	
  		message += "' should ";
  		message += verifyMatch ? "match " : "not match ";
  		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
  		
  		Util.printAssertingMessage(message);
  		
  		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
  		message += verifyMatch ? ", " : ", but do";
  		
  		try {
  			Assert.assertTrue(condition, message);
  			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
			
		} 
    	 catch (AssertionError e) {
  			
    		 message+= ".Failed, Expected to be true";
  			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
  			
  			if (!ignoreFailedAsserts) {
  				fail(e.getMessage());
  			}
  			
  			assertMessage = e.getMessage();
  		}
        
    }

    /**
     * Asserts that a condition is true. If it isn't, an AssertionError is
     * thrown.
     *
     * @param condition
     *            
     */
     public static void assertTrue(boolean condition) throws Exception
    {
        Assert.assertTrue(condition, null);
    }

    /**
     * Asserts that a condition is false. If it isn't, throw assertion error with provided messagge
     *
     * @param condition
     *            the condition to evaluate
     * @param message
     *            the assertion error message
     */
    public static void assertFalse(boolean condition, String message)
    {
        Assert.assertFalse(condition, message);
    }

    /**
     * Asserts that a condition is false. If it isn't throw Assettion error
     *
     * @param condition
     *           
     */
    public static void assertFalse(boolean condition)
    {
        Assert.assertFalse(condition, null);
    }

    /**
     * Fails a test with the given message catching the  exception.
     *
     * @param message
     *            the assertion error message
     * @param realCause
     *            the original exception
     */
     public static void fail(String message, Throwable realExceptionCause)
    {
        Assert.fail(message, realExceptionCause);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message
     *            the assertion error message
     */
     public static void fail(String message)
    {
        Assert.fail(message);
    }

    /**
     * Fails a test without any message
     */
     public static void fail()
    {
        Assert.fail(null);
    }

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual
     *            the actual value
     * @param expected
     *            the expected value
     * @param message
     *            the assertion error message
     */
     public static void assertEquals(Object actual, Object expected, String message)
    {
        
    	 try {
         	Assert.assertEquals(actual, expected, message);
         	Util.printInfo("ASSERTING \n"+ message+" \n ASSERTING PASSED ");        	
 		} catch (AssertionError e) {
 			Util.printInfo("ASSERTING \n The step failed, found the actual as :"+actual+ "  expected to be :"+expected+"\n ASSERTING FAILED "); 			
 			throw new AssertionError("** FAILED found the actual as :"+actual+ "  expected to be :"+expected);
 			
 		}
        
    }

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError is
     * thrown.
     *
     * @param actual
     *            the actual value
     * @param expected
     *            the expected value
     */
     public static void assertEquals(Object actual, Object expected)
    {
        Assert.assertEquals(actual, expected, null);
    }

 

    /**
     * Asserts that two Strings are equal. If they are not throw assertion error
     *
     * @param actual
     *            the actual value
     * @param expected
     *            the expected value
     */
  /*   public static void assertEquals(String actual, String expected)
    {
        Assert.assertEquals(actual, expected, null);
    }*/
     
     protected static boolean assertEqualsWithFlags(String APIName, String scenarioName, String actual, String expected) {
     	boolean result = true;
 		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values of the scenario '" + scenarioName;
 		if (!APIName.isEmpty()) {
 			message += "' on the API '" + APIName;
 		}
 		message += "' should ";
 		message += verifyMatch ? "match " : "not match ";
 		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";
 		
 		try {
 			if (verifyCaseSensitive) {
 				org.testng.Assert.assertTrue((actual.equals(expected) == verifyMatch),message);
 			} else {
 				org.testng.Assert.assertTrue((actual.equalsIgnoreCase(expected) == verifyMatch),message);
 			}
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			message= "Found the actual value as "+actual+ " but the expected to be "+expected;
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }

 	/**
 	 * Asserts the equality of two strings (actual and expected), using assertion flags without pageName
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(String APIName, String scenarioName String actual, String expected)
 	 */
     protected static boolean assertEquals(String scenario, String actual, String expected) {
 		return assertEqualsWithFlags("", scenario, actual, expected);
     }
     
 	/**
 	 * Asserts the equality of two strings (actual and expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(String APIName, String scenarioName String actual, String expected)
 	 */
     protected static boolean assertEquals(String APIName, String scenario, String actual, String expected) {
 		return assertEqualsWithFlags(APIName, scenario, actual, expected);
     }

 	/**
 	 * Asserts the equality of two strings (actual and expected), using assertion flags.
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyCaseSensitive()
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
     protected static boolean assertEqualsWithFlags(String actual, String expected) {
     	boolean result = true;
 		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values should ";
 		
 		message += verifyMatch ? "match " : "not match ";
 		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";
 		
 		try {
 			if (verifyCaseSensitive) {
 				org.testng.Assert.assertTrue((actual.equals(expected) == verifyMatch),message);
 			} else {
 				org.testng.Assert.assertTrue((actual.equalsIgnoreCase(expected) == verifyMatch),message);
 			}
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }

 	
 	/**
 	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyCaseSensitive()
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
     protected static boolean assertEqualsWithFlags(String APIName, String scenarioName, String actual, String... expecteds) {
     	boolean found = false;
     	boolean result = false;
     	boolean ignoreFailedAssertsSave;
     	boolean verifyMatchSave;
 		String message = "The actual ('" + actual + "') value of the Field '" + scenarioName + "' on the APi '" + APIName + "' should ";
 		
 		message += verifyMatch ? "be found" : "not be found";
 		message += " in the list of expected values (" + Arrays.toString(expecteds) + ") ";
 		message += (verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)");
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but is not" : ", but is";
 		
 		//Save the value of ignoreFailedAsserts.  We will set it to true temporarily, so that when we call assertEqualsWithFlags(pageName, fieldName, actual, expected)
 		//  in the loop it won't fail the test
 		ignoreFailedAssertsSave = ignoreFailedAsserts;
 		ignoreFailedAsserts = true;
 		
 		//Save the value of verifyMatch.  We will set it to true temporarily, because it's simpler to have assertEqualsWithFlags(pageName, fieldName, actual, expected)'s
 		//  return value specify whether the string was actually found, and then deal with the verifyMatch logic here
 		verifyMatchSave = verifyMatch;
 		verifyMatch = true;
 		
 		for (String expected : expecteds) {
 			found = assertEqualsWithFlags(APIName, scenarioName, actual, expected);

 			result = result || found;
 			
 			//Break when the string is found
 			if (found) {
 				break;
 			}
 		}
 		
 		//Get the original settings back, then use them to assess success or failure and whether to fail the test
 		ignoreFailedAsserts = ignoreFailedAssertsSave;
 		verifyMatch = verifyMatchSave;
 		
 		result = (result == verifyMatch);
 		if (result) {
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} else {
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);

 			if (!ignoreFailedAsserts) {
 				fail(message);
 			}
 			
 			assertMessage = message;
 		}

 		return result;
     }

 	/**
 	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(String APIName, String scenarioName String actual, String... expecteds)
 	 */
     protected static boolean assertEquals(String APIName, String scenarioName, String actual, String... expecteds) {
 		return assertEqualsWithFlags(APIName, scenarioName, actual, expecteds);
     }
     
 	/**
 	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyCaseSensitive()
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
     protected static boolean assertEqualsWithFlags(String actual, String... expecteds) {
     	boolean found = false;
     	boolean result = false;
     	boolean ignoreFailedAssertsSave;
     	boolean verifyMatchSave;
 		String message = "The actual ('" + actual + "') value should ";
 		
 		message += verifyMatch ? "be found" : "not be found";
 		message += " in the list of expected values (" + Arrays.toString(expecteds) + ") ";
 		message += (verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)");
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but is not" : ", but is";
 		
 		//Save the value of ignoreFailedAsserts.  We will set it to true temporarily, so that when we call assertEqualsWithFlags(pageName, fieldName, actual, expected)
 		//  in the loop it won't fail the test
 		ignoreFailedAssertsSave = ignoreFailedAsserts;
 		ignoreFailedAsserts = true;
 		
 		//Save the value of verifyMatch.  We will set it to true temporarily, because it's simpler to have assertEqualsWithFlags(actual, expected)'s
 		//  return value specify whether the string was actually found, and then deal with the verifyMatch logic here
 		verifyMatchSave = verifyMatch;
 		verifyMatch = true;
 		
 		for (String expected : expecteds) {
 			found = assertEqualsWithFlags(actual, expected);

 			result = result || found;
 			
 			//Break when the string is found
 			if (found) {
 				break;
 			}
 		}
 		
 		//Get the original settings back, then use them to assess success or failure and whether to fail the test
 		ignoreFailedAsserts = ignoreFailedAssertsSave;
 		verifyMatch = verifyMatchSave;
 		
 		result = (result == verifyMatch);
 		if (result) {
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} else {
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);

 			if (!ignoreFailedAsserts) {
 				fail(message);
 			}
 			
 			assertMessage = message;
 		}

 		return result;
     }
    
 	/**
 	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
 	 * @param actual the actual value, typically "scraped" from the field on the page
 	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(String actual, String... expecteds)
 	 */
     protected static boolean assertEquals(String actual, String... expecteds) {
 		return assertEqualsWithFlags(actual, expecteds);
     }
     
 	/**
 	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actuals a list of actual values, typically "scraped" from the field on the page
 	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyCaseSensitive()
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
     protected static boolean assertEqualsWithFlags(String APIName, String scenarioName, ArrayList<String> actuals, ArrayList<String> expecteds) {
     	boolean result = true;
 		String message = "The actual ('" + actuals.toString() + "') and expected ('" + expecteds.toString() + "') lists of values of the scenario '" + scenarioName + "' on the API '" + APIName + "' should ";
 		
 		message += verifyMatch ? "match " : "not match ";
 		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";

 		try {
 			if (verifyCaseSensitive) {
 				org.testng.Assert.assertTrue((actuals.equals(expecteds) == verifyMatch),message);
 			} else {
 				org.testng.Assert.assertTrue((Util.listOfStringToUpperCase(actuals).equals(Util.listOfStringToUpperCase(expecteds)) == verifyMatch),message);
 			}
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }
     
 	
     
 	/**
 	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
 	 * @param actuals a list of actual values, typically "scraped" from the field on the page
 	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyCaseSensitive()
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
      protected static boolean assertEqualsWithFlags(List<String> actuals, List<String> expecteds) {
     	boolean result = true;
 		String message = "The actual ('" + actuals.toString() + "') and expected ('" + expecteds.toString() + "') lists of values should ";
 		
 		message += verifyMatch ? "match " : "not match ";
 		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";

 		try {
 			if (verifyCaseSensitive) {
 				org.testng.Assert.assertTrue((actuals.equals(expecteds) == verifyMatch),message);
 			} else {
 				org.testng.Assert.assertTrue((Util.listOfStringToUpperCase(actuals).equals(Util.listOfStringToUpperCase(expecteds)) == verifyMatch),message);
 			}
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }
     
 	/**
 	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
 	 * @param actuals a list of actual values, typically "scraped" from the field on the page
 	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(List actuals, List expecteds)
 	 */
     protected static boolean assertEquals(List<String> actuals, List<String> expecteds) {
 		return assertEqualsWithFlags(actuals, expecteds);
     }
        
 	/**
 	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertEqualsWithFlags(String APIName, String scenarioName, boolean actual, boolean expected) {
     	boolean result = true;
 		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values of the scenario '" + scenarioName + "' on the API '" + APIName + "' should ";
 		
 		message += verifyMatch ? "match" : "not match";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == expected) == verifyMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }
 	
 
     
 	/**
 	 * Asserts the equality of two ints (actual and expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertEqualsWithFlags(String APIName, String scenarioName,int actual, int expected) {
     	boolean result = true;
 		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values of the scenario '" + scenarioName + "' on the APi '" + APIName + "' should ";
 		
 		message += verifyMatch ? "match" : "not match";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == expected) == verifyMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }
 	
 	/**
 	 * Asserts the equality of two ints (actual and expected), using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(String APIName, String scenarioName int actual, int expected)
 	 */
     protected static boolean assertEquals(String APIName, String scenarioName, int actual, int expected) {
 		return assertEqualsWithFlags(APIName, scenarioName, actual, expected);
     }
     
 	/**
 	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertEqualsWithFlags(boolean actual, boolean expected) {
     	boolean result = true;
 		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values should ";
 		
 		message += verifyMatch ? "match" : "not match";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == expected) == verifyMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }
 	
 	/**
 	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(boolean actual, boolean expected)
 	 */
     protected static boolean assertEquals(boolean actual, boolean expected) {
 		return assertEqualsWithFlags(actual, expected);
     }

 	/**
 	 * Asserts the equality of two ints (actual and expected), using assertion flags.
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertEqualsWithFlags(int actual, int expected) {
     	boolean result = true;
 		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values should ";
 		
 		message += verifyMatch ? "match" : "not match";
 		
 		Util.printAssertingMessage(message);
 		
 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += verifyMatch ? ", but do not" : ", but do";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == expected) == verifyMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
     }
 	
 	/**
 	 * Asserts the equality of two ints (actual and expected), using assertion flags.
 	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
 	 * @param expected the expected value, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertEqualsWithFlags(int actual, int expected)
 	 */
     protected static boolean assertEquals(int actual, int expected) {
 		return assertEqualsWithFlags(actual, expected);
     }
 	    
 	/**
 	 * Asserts the existence of a GUI element, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual existence of the field
 	 * @param expected the expected existence of the field, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertFieldExistenceWithFlags(String APIName, String scenarioName, boolean actual, boolean expected) {
     	boolean result = true;
 		boolean contextExpected = (expected == verifyMatch);
 		String message = "The scenario '" + scenarioName + "' on the API '" + APIName + "' should ";

 		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
 		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
 		//  so that we can tailor the error message
 		
 		message += contextExpected ? "exist" : "not exist";

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but does not" : ", but does";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == contextExpected),message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
 	
 	/**
 	 * Asserts the existence of a GUI element, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual existence of the field
 	 * @param expected the expected existence of the field, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertFieldExistenceWithFlags(String APIName, String scenarioName boolean actual, boolean expected)
 	 */
     protected static boolean assertFieldExistence(String APIName, String scenarioName, boolean actual, boolean expected) {
 		return assertFieldExistenceWithFlags(APIName, scenarioName, actual, expected);
     }
 	
 	/**
 	 * Asserts the visibility of a GUI element, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual visibility of the field
 	 * @param expected the expected visibility of the field, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertFieldVisibilityWithFlags(String APIName, String scenarioName, boolean actual, boolean expected) {
     	boolean result = true;
 		boolean contextExpected = (expected == verifyMatch);
 		String message = "The scenario '" + scenarioName + "' on the API '" + APIName + "' should ";

 		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
 		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
 		//  so that we can tailor the error message
 		
 		message += contextExpected ? "be visible" : "not be visible";

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but is not" : ", but is";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == contextExpected),message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
 	
 	/**
 	 * Asserts the visibility of a GUI element, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual visibility of the field
 	 * @param expected the expected visibility of the field, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertFieldVisibilityWithFlags(String APIName, String scenarioName boolean actual, boolean expected)
 	 */
     public static boolean assertFieldVisibility(String APIName, String scenarioName, boolean actual, boolean expected) {
 		return assertFieldVisibilityWithFlags(APIName, scenarioName, actual, expected);
     }

 	/**
 	 * Asserts the 'nullness' of a GUI element, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual 'nullness' of the field
 	 * @param expected the expected 'nullness' of the field, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertFieldNullnessWithFlags(String APIName, String scenarioName, boolean actual, boolean expected) {
     	boolean result = true;
 		boolean contextExpected = (expected == verifyMatch);
 		String message = "The scenario '" + scenarioName + "' on the API '" + APIName + "' should ";

 		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
 		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
 		//  so that we can tailor the error message
 		
 		message += contextExpected ? "be null" : "not be null";

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but is not" : ", but is";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == contextExpected),message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
 	
 	/**
 	 * Asserts the 'nullness' of a GUI element, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param actual the actual 'nullness' of the field
 	 * @param expected the expected 'nullness' of the field, as specified in the test properties file or set programmatically
 	 * @return The assertion result
 	 * @see #assertFieldNullnessWithFlags(String APIName, String scenarioName boolean actual, boolean expected)
 	 */
     protected static boolean assertFieldNullness(String APIName, String scenarioName, boolean actual, boolean expected) {
 		return assertFieldNullnessWithFlags(APIName, scenarioName, actual, expected);
     }
        
 	/**
 	 * Asserts the presence of errors at the field and/or page level on a page, using assertion flags.
 	 * @param checkedElements a string containing the name(s) of the field(s) and/or the name of the page
 	 * that were checked for errors 
 	 * @param actual the actual absence of errors
 	 * @param expected the expected absence of errors
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertErrorCheckWithFlags(String checkedElements, boolean actual, boolean expected) {
     	boolean result = true;
 		boolean contextExpected = (expected == verifyMatch);
 		String message = "The " + checkedElements + " should ";

 		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
 		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
 		//  so that we can tailor the error message

 		message += contextExpected ? "not contain errors" : "contain one or more errors";

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but does" : ", but does not";
 		
 		try {
 			org.testng.Assert.assertTrue((actual == contextExpected),message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
 	
 	/**
 	 * Asserts the presence of errors at the field and/or page level on a page, using assertion flags.
 	 * @param checkedElements a string containing the name(s) of the field(s) and/or the name of the page
 	 * that were checked for errors 
 	 * @param actual the actual absence of errors
 	 * @param expected the expected absence of errors
 	 * @return The assertion result
 	 * @see #assertErrorCheckWithFlags(String checkedElements, boolean actual, boolean expected)
 	 */
     protected static boolean assertErrorCheck(String checkedElements, boolean actual, boolean expected) {
 		return assertErrorCheckWithFlags(checkedElements, actual, expected);
     }
 		
 	/**
 	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param assertingMessage a description of how the actual value was arrived at
 	 * @param actual the actual value, typically set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertTrueWithFlags(String APIName, String scenarioName, String assertingMessage, boolean actual) {
     	boolean result = true;
 		boolean contextExpected = (true == verifyMatch);
 		String message = "The test '" + assertingMessage + "' for the scenario '" + scenarioName + "' of the API '" + APIName + "' should be ";

 		message += contextExpected ? "true" : "false";

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but is false" : ", but is true";
 		
 		try {
 			org.testng.Assert.assertTrue(actual == verifyMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
 	
 	/**
 	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
 	 * @param pageName the name of the relevant page
 	 * @param fieldName the name of the field on the page
 	 * @param assertingMessage a description of how the actual value was arrived at
 	 * @param actual the actual value, typically set programmatically
 	 * @return The assertion result
 	 * @see #assertTrueWithFlags(String APIName, String scenarioName String assertingMessage, boolean actual)
 	 */
     protected static boolean assertTrue(String APIName, String scenarioName, String assertingMessage, boolean actual) {
 		return assertTrueWithFlags(APIName, scenarioName, assertingMessage, actual);
     }

     /**
 	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
 	 * @param assertingMessage a description of how the actual value was arrived at
 	 * @param actual the actual value, typically set programmatically
 	 * @return The assertion result
 	 * @see #isVerifyMatch()
 	 * @see #isIgnoreFailedAsserts()
 	 */
 	protected static boolean assertTrueWithFlags(String assertingMessage, boolean actual) {
     	boolean result = true;
 		boolean contextExpected = (true == verifyMatch);
 		String message = "The test '" + assertingMessage + "' should be ";

 		message += contextExpected ? "true" : "false";

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but is false" : ", but is true";
 		
 		try {
 			org.testng.Assert.assertTrue(actual == verifyMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
 	
 	
 	protected static boolean assertFalseWithFlags(String assertingMessage, boolean actual) {
     	boolean result = true;
 		boolean contextExpected = (true == verifyUnMatch);
 		String message = "The test '" + assertingMessage + "' should be ";

 		message += contextExpected ? "true" : "false";
 		

 		Util.printAssertingMessage(message);

 		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
 		message += contextExpected ? ", but is false" : ", but is true";
 		
 		try {
 			org.testng.Assert.assertTrue(actual == verifyUnMatch,message);
 			
 			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
 		} catch (AssertionError e) {
 			result = false;
 			
 			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
 			//  indeed fail!
 			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
 			
 			if (!ignoreFailedAsserts) {
 				fail(e.getMessage());
 			}
 			
 			assertMessage = e.getMessage();
 		}
 		
 		return result;
 	}
	/**
	 * Description : This is used to verify the list of expected values with actuals. checkEachExpValue is used to verify each of the values in exp against the actual.
	 * Otherwise call regular method assertEqualsWithFlags(actuals,expecteds); which does the straight checks of both lists
	 * @param actuals
	 * @param expecteds
	 * @param checkEachExpectedVal
	 * @return
	 */
	
	public static boolean  assertEqualsWithFlags(String assertingMsg,String APIName, String scenarioName, ArrayList<String> actuals, ArrayList<String> expecteds,boolean checkEachExpectedVal) {			
    	boolean result = false;
    	if (!checkEachExpectedVal){
    		assertEqualsWithFlags(actuals,expecteds);
    	}
    	String message = assertingMsg+" the test ' "  + scenarioName + "' of the API '" + APIName ;
    	message += " should contain list of values actual ('" + actuals.toString() + "') and expected ('" + expecteds.toString() + "')";		
		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";

		try {
			
				//do the 1 to 1 check with expected and actual list
				int i=0;
				boolean foundMatch=false;
				for(String myexpStr: expecteds){						
					for (String myactStr:actuals){
						if (myexpStr.equalsIgnoreCase(myactStr)){
							foundMatch=true;
							i++;	//The increment is used to check how many matched with expected list
							break;
						}
					}
				}
					if (foundMatch && i==expecteds.size()){
						reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);								
						return true;
					}
			
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
 	private static void reportAssertResult(String message) {
    	Util.printAssertResultMessage(message);
    	
    	numAsserts++;
    }
	
	
}

