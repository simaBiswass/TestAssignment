package testScripts;

import functionalLibrary.NavigateToAmazon;
import functionalLibrary.SearchAppleWatch;
import functionalLibrary.SearchIphone;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import util.BaseUtil;

public class TestAmazon extends BaseUtil {

    NavigateToAmazon navigateToAmazon;
    SearchIphone searchIphone;
    SearchAppleWatch searchAppleWatch;

    @BeforeSuite
    public void setUp() {
        setUpDriver();
    }
    @BeforeMethod
    public void initializeFunctionClasses(){
        navigateToAmazon=new NavigateToAmazon();
        searchIphone=new SearchIphone();
        searchAppleWatch=new SearchAppleWatch();
    }

    @Test
    public void validation(){
        navigateToAmazon.navigateToURL();
        searchIphone.selectDepartment();
        searchIphone.validateRelatedSearches();
        searchIphone.searchVariant();
        searchIphone.validateNewWindow();
        searchAppleWatch.selectAppleWatch();
        searchAppleWatch.validateQuickLook();
    }

    @AfterSuite
    public void endTestAmazon(){
        endTest();
    }

}
