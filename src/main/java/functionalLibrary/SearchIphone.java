package functionalLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.BaseUtil;
import util.GenericMethods;
import java.util.List;
import java.util.Set;

public class SearchIphone {
    WebDriver driver = BaseUtil.getDriver();
    GenericMethods genericMethods = new GenericMethods();
    String linkName = "";
    public void selectDepartment(){
        genericMethods.waitForElements(driver,By.xpath("//option"));
        genericMethods.selectDropdown(driver,"Electronics");
        genericMethods.clickOn(driver,By.xpath("//*[@id='twotabsearchtextbox']"));
        genericMethods.sendKeys(driver,By.xpath("//*[@id='twotabsearchtextbox']"),"Iphone13");
    }

    public void validateRelatedSearches(){
        List<WebElement> suggestions = genericMethods.waitForElements(
                driver,By.xpath("//*[@class='s-suggestion-container']/*[@role='button']"));
        for(WebElement element : suggestions){
            if(!element.getText().contains("iphone"))
                System.out.println(element.getText()+" : incorrect suggestion");
        }
    }

    public void searchVariant(){
        genericMethods.sendKeys(driver,By.xpath("//*[@id='searchDropdownBox']"),"iphone 13 128 GB");
        List<WebElement> suggestions = genericMethods.
                waitForElements(driver,By.xpath("//*[@class='s-suggestion-container']/*[@role='button']"));
        genericMethods.clickOn(driver,suggestions.get(0));
        List<WebElement> productLinks = genericMethods.
                waitForElements(driver,By.xpath("//*[@aria-label[contains(.,'iPhone')]]//ancestor::h2"));
        linkName=productLinks.get(0).getText();
        genericMethods.clickOn(driver,productLinks.get(0));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void validateNewWindow(){
        String parentWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();

        for(String handle: windowHandles){
            if(!handle.equals(parentWindow))
                driver.switchTo().window(handle);
        }
        String newTabTitle = driver.getTitle();
        if(newTabTitle.contains(linkName))
            System.out.println("Tab verified");
        else
            System.out.println((linkName+"Tab NOK"+newTabTitle));

        String appleStoreXpath = "//a[contains(text(),'Visit the Apple Store')]";
        genericMethods.waitForElement(driver,By.xpath(appleStoreXpath));
        genericMethods.clickOn(driver,By.xpath(appleStoreXpath));
    }

}
