package testScripts;

import functionalLibrary.NavigateToAmazon;
import functionalLibrary.SearchAppleWatch;
import functionalLibrary.SearchIphone;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import util.BaseUtil;
import util.HTLMReporter;

public class TestAmazon extends BaseUtil {

    NavigateToAmazon navigateToAmazon;
    SearchIphone searchIphone;
    SearchAppleWatch searchAppleWatch;
    HTLMReporter reporter;

    @BeforeSuite
    public void setUp() {
        setUpDriver();
        setUpReporter();
        reporter=getReporter();
    }
    @BeforeMethod
    public void initializeFunctionClasses(){
        navigateToAmazon=new NavigateToAmazon();
        searchIphone=new SearchIphone();
        searchAppleWatch=new SearchAppleWatch();
    }

    @Test(priority = 1)
    public void validationOfRelatedSearches(){
        reporter.startTestCase("Validate Related Searches","Select Department as Electronics and search for Iphone 13");
        navigateToAmazon.navigateToURL();
        searchIphone.searchProductInDepartment("Electronics","Iphone 13");
        searchIphone.validateRelatedSearches();
    }

    @Test(priority = 2)
    public void validationNewWindow(){
        reporter.startTestCase("Validate new window","Check if new window opens for Iphone 13 128 GB variant");
        searchIphone.searchVariant(" 128 GB");
        searchIphone.validateNewWindow();
    }

    @Test(priority = 3)
    public void validationOfQuickLook(){
        reporter.startTestCase("Validate Quick Look","Check if quick look opens for Apple Watch");
        searchAppleWatch.selectAppleWatch();
        searchAppleWatch.validateQuickLook();
    }

    @AfterSuite
    public void endTestAmazon(){
        reporter.endResult();
        endTest();
    }

}
