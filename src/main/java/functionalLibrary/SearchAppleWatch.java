package functionalLibrary;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import util.BaseUtil;
import util.GenericMethods;
import util.HTLMReporter;


public class SearchAppleWatch {

    WebDriver driver = BaseUtil.getDriver();
    GenericMethods genericMethods = new GenericMethods();
    String watchProduct = "Apple Watch SE";

    // WebElement locators
    private final By appleWatchCategory = By.xpath("//*[text()='Apple Watch']//parent::a[@role='button']");
    private final By appleWatchProduct = By.xpath("//*[text()='"+watchProduct+" (GPS + Cellular)']//parent::a");
    private final By appleWatchDetails = By.xpath("//*[@title[contains(.,'40mm] Smartwatch with Starlight Aluminum Case')]]");
    private final By quickLookButton = By.xpath("//*[@title[contains(.,'40mm] Smartwatch with Starlight Aluminum Case')]]/parent::div//*[text()='Quick look']//ancestor::button");
    private final By quickLookTitle = By.xpath("//*[@class='ProductShowcase__title__SBCBw']");

    /**
     * Navigates to the Apple Watch section, selects a specific product, and performs a right-click action on its details.
     *
     * <p>This method performs the following steps:
     * <ul>
     *   <li>Waits for the "Apple Watch" category to load and clicks on it.</li>
     *   <li>Waits for the subcategory to load and clicks on it.</li>
     *   <li>Waits for the specified Apple Watch product (dynamic) to load and clicks on it.</li>
     *   <li>Performs a right-click action on the product details element.</li>
     *   <li>Uses {@code JavascriptExecutor} as a fallback to perform the click if {@code Actions} fails.</li>
     * </ul>
     * </p>
     *
     * <p>The product to be selected is determined by the {@code watchProduct} variable, which dynamically updates
     * the product XPath.</p>
     */
    public void selectAppleWatch() {
        // Wait for and click the Apple Watch category
        genericMethods.waitForElement(driver, appleWatchCategory, "Apple Watch Category");
        genericMethods.clickOn(driver, appleWatchCategory,"Apple Watch Category");

        // Wait for and click the specified Apple Watch product
        genericMethods.waitForElement(driver, appleWatchProduct, "Apple Watch Product");
        genericMethods.clickOn(driver, appleWatchProduct,"Apple Watch Product");

        // Right-click on the product details element
        try {
            WebElement element = genericMethods.waitForElement(driver, appleWatchDetails, "Apple Watch Details");
           /* Actions action = new Actions(driver);
            action.contextClick(element).perform();*/
            // Add JavaScriptExecutor as a fallback if Action class doesn't work
            /*JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            Thread.sleep(1000);*/
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
            Thread.sleep(2000);
        } catch (Exception e) {
            HTLMReporter.reportLog(driver,"Click on Apple Watch Details web element : NOK","["+appleWatchDetails+"]--> Element not clickable", Status.INFO);
        }
    }

    /**
     * Validates the "Quick Look" feature of the selected Apple Watch product.
     *
     * <p>This method performs the following steps:
     * <ul>
     *   <li>Waits for the "Quick Look" button associated with the Apple Watch product to load and clicks on it.</li>
     *   <li>Waits for the Quick Look title to load and retrieves its text.</li>
     *   <li>Compares the retrieved title with the expected product name stored in {@code watchProduct}.</li>
     *   <li>Logs the result of the validation (Test OK or Test NOK) to the console.</li>
     * </ul>
     * </p>
     *
     * <p>Ensures that the Quick Look functionality correctly displays the details of the selected product.</p>
     */
    public void validateQuickLook() {
        genericMethods.waitForElement(driver, quickLookButton, "Quick Look Button");
        genericMethods.clickOn(driver, quickLookButton,"Quick Look Button");
        WebElement element = genericMethods.waitForElement(driver, quickLookTitle, "Quick Look Title");
        if (element.getText().contains(watchProduct)) {
            HTLMReporter.reportLog(driver,"Quick look on Apple Watch Verified: OK","["+appleWatchDetails+"]--> Apple Watch Verified: OK", Status.PASS);
        } else {
            HTLMReporter.reportLog(driver,"Quick look on Apple Watch Verified: NOK","["+appleWatchDetails+"]--> Apple Watch Verified: NOK", Status.FAIL);
        }
    }
}
