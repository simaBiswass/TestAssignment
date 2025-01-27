package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Properties;

/**
 * Utility class for managing the WebDriver instance and the HTML Reporter.
 *
 * <p>This class provides static methods for accessing and managing the WebDriver
 * and the custom {@code HTLMReporter} instance. It includes functionality for setting
 * up a WebDriver instance based on the browser configuration provided in the
 * application properties file.</p>
 */
public class BaseUtil {
    private static WebDriver driver;
    private static HTLMReporter reporter;
    /**
     * Retrieves the current instance of the {@code HTLMReporter}.
     *
     * @return the current {@code HTLMReporter} instance
     */
    public static HTLMReporter getReporter() {
        return reporter;
    }
    /**
     * Initializes and sets a new instance of the {@code HTLMReporter}.
     */
    public static void setReporter() {
        BaseUtil.reporter = new HTLMReporter();
    }
    /**
     * Retrieves the current WebDriver instance.
     *
     * @return the current {@code WebDriver} instance
     */
    public static WebDriver getDriver() {
        return driver;
    }
    /**
     * Sets the WebDriver instance for the current session.
     *
     * @param driver the {@code WebDriver} instance to be set
     */
    public void setDriver(WebDriver driver) {
        BaseUtil.driver = driver;
    }
    /**
     * Sets up the WebDriver instance based on the browser configuration.
     *
     * <p>This method reads the browser type and driver path from the application
     * properties file and initializes the WebDriver accordingly. Currently,
     * it supports the Chrome browser. Additional browser support can be added
     * by extending the {@code switch} statement.</p>
     *
     * <p>For Chrome, the method:
     * <ul>
     *   <li>Sets the system property for the Chrome driver.</li>
     *   <li>Configures Chrome-specific options such as incognito mode and maximized window.</li>
     *   <li>Initializes the {@code WebDriver} instance with the configured options.</li>
     * </ul>
     * </p>
     *
     * @throws RuntimeException if the browser type is unsupported
     */
    public void setUpDriver(){
        Properties properties = SetProperties.getProperties();
        String browser =  properties.getProperty("Browser");
        switch (browser){
            case "Chrome":
                System.setProperty("webdriver.chrome.driver",properties.getProperty("ChromeDriverPath"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                //options.addArguments("--incognito");
                driver = new ChromeDriver(options);
                break;
            default:
                throw new RuntimeException("Unhandled browser!");
        }
        setDriver(driver);
    }
    /**
     * Initializes and starts the HTML reporter for the test execution.
     *
     * <p>This method sets up the custom {@code HTLMReporter} by creating a new
     * instance and starting the report. It ensures that the reporting framework
     * is ready to log test results and events.</p>
     */
    public void setUpReporter(){
        setReporter();
        HTLMReporter.startReport();
    }
    /**
     * Closes and quits the WebDriver instance to clean up resources.
     *
     * <p>This method ensures that the WebDriver instance is properly closed
     * and terminated after test execution. It first closes the browser window
     * and then quits the WebDriver session to release all resources.</p>
     *
     * <p>If the WebDriver instance is null, the method does nothing.</p>
     */
    public void endTest(){
        if(null!=driver){
            driver.close();
            driver.quit();
        }

    }
}
