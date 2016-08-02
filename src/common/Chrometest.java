package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class Chrometest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		WebDriver driver1 = new    //ChromeDriver("http://www.google.com");
		
		System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");    
		WebDriver driver = new ChromeDriver();   
		driver.get("http://www.google.com/xhtml");
		
		

	}

}
