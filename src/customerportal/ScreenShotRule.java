package customerportal;

/**
* @author Brijesh Chavda
* @version 1.0.0
*/

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import common.EISTestBase;
 
class ScreenShotRule implements MethodRule {
    @Override
	public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    // exception will be thrown only when a test fails.
                    captureScreenshot(frameworkMethod.getName());
                    // rethrow to allow the failure to be reported by JUnit
                    throw t;
                  }
            }
 
            public void captureScreenshot(String fileName) {
                try {
//
               File screenshot = ((TakesScreenshot)Test_VerifyCustomerPortalNavigationParallelCrossBrowser.getDriver()).getScreenshotAs(OutputType.FILE);
               FileUtils.copyFile(screenshot, new File("c:\\tmp\\screenshot.png"));
               EISTestBase.driver.quit();
                } catch (Exception e) {
                    // No need to crash the tests if the screenshot fails
                }
            }
        };
    }
}