package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Properties;


public class BaseUtil {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
    // Setup Driver
    public void setUpDriver(){
        Properties properties = SetProperties.getProperties();
        String browser =  properties.getProperty("Browser");
        switch (browser){
            case "Chrome":
                System.setProperty("webdriver.chrome.driver",properties.getProperty("ChromeDriverPath"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                break;
            default:
                throw new RuntimeException("Unhandled browser!");
        }
        setDriver(driver);
    }

    public void endTest(){
        if(null!=driver){
            driver.close();
            driver.quit();
        }

    }
}
