package cep.portal.webservicestesting;

import org.testng.Assert;

import common.Util;

public class cepAssert extends EISWebService{
	
	
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
        Assert.assertTrue(condition, message);
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
        Assert.assertEquals(actual, expected, message);
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
     * Asserts that two Strings are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual
     *            the actual value
     * @param expected
     *            the expected value
     * @param message
     *            the assertion error message
     */
     public static void assertEquals(String actual, String expected, String message)
    {
        try {
        	Assert.assertEquals(actual, expected, message);
			System.out.println("*****ASSERTING \n"+ message+" \n ********ASSERTING PASSED ");
		} catch (AssertionError e) {
			Util.printInfo("*****ASSERTING \n "+message+" \n *****ASSERTING FAILED ");
			throw new AssertionError();
			
		}
    }

    /**
     * Asserts that two Strings are equal. If they are not throw assertion error
     *
     * @param actual
     *            the actual value
     * @param expected
     *            the expected value
     */
     public static void assertEquals(String actual, String expected)
    {
        Assert.assertEquals(actual, expected, null);
    }

	
	
}

