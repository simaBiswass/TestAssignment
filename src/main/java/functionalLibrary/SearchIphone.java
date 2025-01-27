package functionalLibrary;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.BaseUtil;
import util.GenericMethods;
import util.HTLMReporter;

import java.util.List;
import java.util.Set;


public class SearchIphone {
    WebDriver driver = BaseUtil.getDriver();
    GenericMethods genericMethods = new GenericMethods();
    String linkName = "";

    //WebElement locators
    private final By options = By.xpath("//option");
    private final By searchBox = By.id("twotabsearchtextbox");
    private final By suggestionsList = By.xpath("//*[@class='s-suggestion-container']/*[@role='button']");
    private final By appleStoreXpath = By.xpath("//a[contains(text(),'Visit the Apple Store')]");
    private final By searchDropdown = By.xpath("//*[@class='nav-icon']");
    //private final By suggestionContainer = By.xpath("//*[@class='s-suggestion-container']/*[@role='button']");
    private final By productLinks = By.xpath("//*[@aria-label[contains(.,'iPhone')]]//ancestor::h2");

    /**
     * Searches for a product in a specified department.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Waits for the department dropdown options to load.</li>
     *   <li>Selects the specified department from the dropdown menu.</li>
     *   <li>Clicks on the search box.</li>
     *   <li>Enters the specified product name into the search box.</li>
     * </ul>
     * </p>
     *
     * @param department the name of the department to select (e.g., "Electronics").
     * @param product the name of the product to search for (e.g., "iPhone 13").
     */
    public void searchProductInDepartment(String department, String product) {
        genericMethods.waitLongerForElement(driver,searchDropdown,"Select Category");
        //genericMethods.clickOn(driver,searchDropdown,"Select Category");
        genericMethods.waitForElements(driver, options, "List of departments");
        genericMethods.selectDropdown(driver, department);
        genericMethods.clickOn(driver, searchBox,"Search Box");
        genericMethods.sendKeys(driver, searchBox, product);
    }

    /**
     * Validates the related search suggestions displayed on the page.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Waits for the related search suggestions to load on the page.</li>
     *   <li>Iterates through each suggestion and checks if it contains the keyword "iphone".</li>
     *   <li>Logs a failure in the report for any suggestion that does not contain "iphone".</li>
     *   <li>Logs a success in the report if all suggestions are relevant.</li>
     * </ul>
     * </p>
     *
     * <p>Uses {@code HTLMReporter} to log the validation status (PASS or FAIL) for each suggestion
     * and for the overall test case.</p>
     */
    public void validateRelatedSearches(){
        List<WebElement> suggestions = genericMethods.waitForElements(driver,suggestionsList,"List of Suggestions");
        boolean testCaseStatus = true;
        for(WebElement element : suggestions){
            if(!element.getText().contains("iphone")){
                HTLMReporter.reportLog(driver,element.getText()+" : incorrect suggestion",
                        "["+element.getText()+"]--> incorrect suggestion", Status.FAIL);
                testCaseStatus=false;
            }
        }
        if(testCaseStatus)
            HTLMReporter.reportLog(driver,"All suggestions are relevant","[All]--> relevant suggestions", Status.PASS);
    }

    /**
     * Searches for a specific product variant and selects the first suggestion.
     *
     * @param searchText the text to enter into the search dropdown (e.g., "iPhone 13 128 GB").
     */
    public void searchVariant(String searchText) {
        genericMethods.sendKeys(driver, searchBox, searchText);
        genericMethods.waitForElement(driver,searchBox, "Search Box");
        genericMethods.sendKeys(driver, searchBox, Keys.ENTER);
        List<WebElement> products = genericMethods.waitForElements(driver, productLinks, "Product Links");
        linkName = products.get(0).getText(); // Save the product link text
        genericMethods.clickOn(driver, products.get(0),searchText+" product");
        // Add a wait to ensure the next page loads completely
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Validates a newly opened browser tab or window and performs an action on the new tab.
     *
     * <p>This method performs the following steps:
     * <ul>
     *   <li>Captures the parent window handle before the new tab or window is opened.</li>
     *   <li>Switches the driver's focus to the new window.</li>
     *   <li>Retrieves the title of the new tab and compares it with the expected title stored in {@code linkName}.</li>
     *   <li>Logs the verification result (PASS or FAIL) using {@code HTLMReporter}.</li>
     *   <li>Waits for the "Visit Apple Store" element to load and clicks on it.</li>
     * </ul>
     * </p>
     */
    public void validateNewWindow(){
        String parentWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();

        for(String handle: windowHandles){
            if(!handle.equals(parentWindow))
                driver.switchTo().window(handle);
        }
        String newTabTitle = driver.getTitle();
        if(null!=newTabTitle){
            if(newTabTitle.contains(linkName))
                HTLMReporter.reportLog(driver,"Tab verified","["+newTabTitle+"]--> Tab verified", Status.PASS);
            else
                HTLMReporter.reportLog(driver,"Tab Verification : NOK","[Actual: "+newTabTitle+", Expected: "+linkName+"]--> Tab Verification : NOK",
                        Status.FAIL);
        }else
            HTLMReporter.reportLog(driver,"Tab Verification : NOK","[Actual: No new Tab, Expected: "+linkName+"]--> Tab Verification : NOK",
                    Status.FAIL);
        genericMethods.waitForElement(driver,appleStoreXpath,"Visit Apple Store");
        genericMethods.clickOn(driver,appleStoreXpath,"Visit Apple Store");
    }

}
