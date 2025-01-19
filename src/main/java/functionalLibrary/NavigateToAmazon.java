package functionalLibrary;

import org.openqa.selenium.WebDriver;
import util.BaseUtil;
import util.SetProperties;

import java.util.Properties;

public class NavigateToAmazon {
    WebDriver driver = BaseUtil.getDriver();
    Properties properties = SetProperties.getProperties();

    String url = properties.getProperty("Amazon_URL");

    public void navigateToURL(){
         driver.get(url);
    }
}
