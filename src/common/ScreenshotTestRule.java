package common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import customerportal.CustomerPortalTestBase;
import customerportal.Test_VerifyCustomerPortalNavigationParallelCrossBrowser;

public class ScreenshotTestRule extends CustomerPortalTestBase implements MethodRule {
    public ScreenshotTestRule() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();

                } catch (Throwable t) {
                    captureScreenshot(frameworkMethod.getName());
                    throw t; // rethrow to allow the failure to be reported to JUnit                     
                } finally {
//                	captureScreenshot(frameworkMethod.getName());
                    tearDown();
                }
            }

            public void tearDown() {
            	System.out.println("driver quit");
            	driver.quit();
            	finish();
                //logout to the system;
            }


            public void captureScreenshot(String fileName) {
                try {
                    File file  = new File("EISTestAutomation/target/screenshots");
                    file.mkdirs(); // Insure directory is there
                    
                    System.out.println("dire : "+file.isDirectory());
                    SimpleDateFormat sdf = new SimpleDateFormat("MMDDYYYY-HHMMSS");
                    String date = sdf.format(new Date());
                    
                    System.out.println("sdf : "+date);
//                    File newFile  = new File("target/screenshots/screenshot-" +fileName +"-"+date+".png");
//                    File screenshot = null;
                    try{
//                     screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                     FileOutputStream out = new FileOutputStream("EISTestAutomation/target/screenshots/screenshot-" +fileName +"-"+date+".png");
                     System.out.println("file name : "+out.getFD());
                     out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                     System.out.println("done screenshot capture");
//                     out.close();
                    }
                    catch(Exception e){
                    	e.printStackTrace();
                    }
//                    FileUtils.copyFile(screenshot, newFile);
//                    System.out.println("date : "+date);
//                    FileOutputStream out = new FileOutputStream("target/screenshots/screenshot-" +fileName +"-"+date+".png");
//                    System.out.println("file name : "+newFile.getName());
//                    out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                    System.out.println("done screenshot capture");
//                    out.close();
                } catch (Exception e) {
                    // No need to crash the tests if the screenshot fails
                }
            }
        };
    }
}

