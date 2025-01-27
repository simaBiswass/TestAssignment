package util;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class GenericMethods {

    private static final Logger log = LogManager.getLogger(GenericMethods.class);
    Properties properties = SetProperties.getProperties();
    int secs = Integer.parseInt(properties.getProperty("wait"));
    int longerSecs = Integer.parseInt(properties.getProperty("longWait"));

    /**
     * Waits for a web element to become clickable and clicks on it.
     *
     * <p>This method waits for the specified element, located by the given {@code By} locator,
     * to become clickable. If the element is clickable, it performs a click action. If the
     * action fails, the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the web element
     * @param elementName the name of the element, used for logging purposes
     */
    public void clickOn(WebDriver driver, By by, String elementName){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
            WebElement element=wait.until(ExpectedConditions.elementToBeClickable(by));
            element.click();
        }catch (Exception ex){
            log.error("e: ", ex);
            HTLMReporter.reportLog(driver,"Click on "+elementName+" web element : NOK","["+by+"]--> Element not clickable", Status.FAIL);
        }
    }
    /**
     * Waits for a given web element to become clickable and clicks on it.
     *
     * <p>This method waits for the specified {@code WebElement} to become clickable.
     * If the element is clickable, it performs a click action. If the action fails,
     * the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param element the web element to be clicked
     * @param elementName the name of the element, used for logging purposes
     */
    public void clickOn(WebDriver driver, WebElement element, String elementName){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
            element=wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        }catch (Exception ex){
            log.error("Click on error: ", ex);
            HTLMReporter.reportLog(driver,"Click on "+elementName+" web element : NOK","["+elementName+"]--> Element not clickable", Status.FAIL);
        }
    }
    /**
     * Waits for a web element to become visible on the page.
     *
     * <p>This method waits for the specified element, located by the given {@code By} locator,
     * to become visible. If the element does not become visible within the specified timeout,
     * the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the web element
     * @param elementName the name of the element, used for logging purposes
     * @return the visible web element, or {@code null} if not found
     */
    public WebElement waitForElement(WebDriver driver, By by, String elementName){
        WebElement element = null;
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
            element=wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception ex){
            log.error("wait error: ", ex);
            HTLMReporter.reportLog(driver,"Wait for "+elementName+" web element : NOK","["+by+"]--> Element not visible", Status.INFO);
        }
        return element;
    }
    /**
     * Waits for longer a web element to become visible on the page.
     *
     * <p>This method waits for the specified element, located by the given {@code By} locator,
     * to become visible. If the element does not become visible within the specified timeout,
     * the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the web element
     * @param elementName the name of the element, used for logging purposes
     * @return the visible web element, or {@code null} if not found
     */
    public WebElement waitLongerForElement(WebDriver driver, By by, String elementName){
        WebElement element = null;
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(longerSecs));
            element=wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception ex){
            log.error("wait error: ", ex);
            HTLMReporter.reportLog(driver,"Wait for "+elementName+" web element : NOK","["+by+"]--> Element not visible", Status.INFO);
        }
        return element;
    }
    /**
     * Waits for a list of web elements to become visible on the page.
     *
     * <p>This method waits for all elements, located by the given {@code By} locator,
     * to become visible. If the elements do not become visible within the specified timeout,
     * the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the web elements
     * @param elementName the name of the elements, used for logging purposes
     * @return a list of visible web elements, or an empty list if not found
     */
    public List<WebElement> waitForElements(WebDriver driver, By by, String elementName){
        List<WebElement> list = List.of();
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
            list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
            Thread.sleep(1000);
        }catch (Exception ex){
            log.error("wait error: ", ex);
            HTLMReporter.reportLog(driver,"Wait for "+elementName+" web elements : NOK","["+by+"]--> Elements not visible", Status.INFO);
        }
        return list;
    }

    /**
     * Selects a dropdown option by its visible text.
     *
     * <p>This method finds a dropdown option containing the specified text,
     * scrolls it into view using JavaScript, and clicks on it. If the action fails,
     * the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param option the visible text of the option to select
     */
    public void selectDropdown(WebDriver driver, String option){
        try{
            WebElement element = driver.findElement(By.xpath("//option[contains(text(),'"+option+"')]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            element.click();
            Thread.sleep(2000);
        } catch (Exception ex){
            log.error("Select dropdown error: ", ex);
            HTLMReporter.reportLog(driver,"Click on "+option+" web element : NOK","["+option+"]--> Option not clickable", Status.FAIL);
        }
    }
    /**
     * Selects a dropdown option located by a specific {@code By} locator.
     *
     * <p>This method scrolls the specified element into view using JavaScript
     * and clicks on it. If the action fails, the error is logged using {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the dropdown option
     * @param elementName the name of the element, used for logging purposes
     */
    public void selectDropdown(WebDriver driver, By by, String elementName){
        try{
            WebElement element = driver.findElement(by);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            element.click();
        } catch (Exception ex){
            log.error("Select dropdown error: ", ex);
            HTLMReporter.reportLog(driver,"Click on "+elementName+" web element : NOK","["+by+"]--> Option not clickable", Status.FAIL);
        }
    }
    /**
     * Sends text input to a web element.
     *
     * <p>This method finds the element located by the given {@code By} locator and
     * sends the specified text to it. If the action fails, the error is logged using
     * {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the text input element
     * @param value the text to send to the input field
     */
    public void sendKeys(WebDriver driver, By by, String value){
        try {
            WebElement element = driver.findElement(by);
            Thread.sleep(500);
            element.sendKeys(value);
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error("Send Keys error: ", e);
            HTLMReporter.reportLog(driver,"Send text to "+value+" web element : NOK","["+by+"]--> Text box not editable", Status.INFO);
        }
    }
    /**
     * Sends text input to a web element.
     *
     * <p>This method finds the element located by the given {@code By} locator and
     * sends the specified text to it. If the action fails, the error is logged using
     * {@code HTLMReporter}.</p>
     *
     * @param driver the WebDriver instance used to interact with the browser
     * @param by the locator used to identify the text input element
     * @param value the Key to send to the input field
     */
    public void sendKeys(WebDriver driver, By by, Keys value){
        try {
            WebElement element = driver.findElement(by);
            Thread.sleep(500);
            element.sendKeys(value);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error("Send Keys error: ", e);
            HTLMReporter.reportLog(driver,"Send text to "+value+" web element : NOK","["+by+"]--> Text box not editable", Status.INFO);
        }
    }
}
