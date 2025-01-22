package functionalLibrary;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import util.BaseUtil;
import util.HTLMReporter;
import util.SetProperties;

import java.util.Properties;

public class NavigateToAmazon {
    WebDriver driver = BaseUtil.getDriver();
    Properties properties = SetProperties.getProperties();

    String url = properties.getProperty("Amazon_URL");

    public void navigateToURL(){
        try {
            driver.get(url);
            HTLMReporter.reportLog(driver,"Navigation to URL : OK","["+url+"] --> OK", Status.INFO);
        } catch (Exception e) {
            HTLMReporter.reportLog(driver,"Navigation to URL : NOK","["+url+"] --> NOK", Status.FAIL);
        }

    }
}
