package functionalLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import util.BaseUtil;
import util.GenericMethods;


public class SearchAppleWatch {

    WebDriver driver = BaseUtil.getDriver();
    GenericMethods genericMethods = new GenericMethods();

    String watchProduct = "Apple Watch SE";

    public void selectAppleWatch(){
      genericMethods.waitForElement(driver,By.xpath("//*[text()='Apple Watch']//parent::a[@role='button']"));
      genericMethods.clickOn(driver,By.xpath("//*[text()='Apple Watch']//parent::a[@role='button']"));
      genericMethods.waitForElement(driver,By.xpath("//*[text()='Apple Watch']/parent::a[@aria-haspopup='false']"));
      genericMethods.clickOn(driver,By.xpath("//*[text()='"+watchProduct+" (GPS + Cellular)']//parent::a"));
      WebElement element = genericMethods.waitForElement(driver,By.xpath("//*[@title[contains(.,'40mm] Smartwatch with Starlight Aluminum Case')]]"));
      Actions action = new Actions(driver);
      action.contextClick(element);
    // Action class does not work, Add JavaScriptExecutor
    }

    public void validateQuickLook(){
        genericMethods.waitForElement(driver,
                By.xpath("//*[@title[contains(.,'40mm] Smartwatch with Starlight Aluminum Case')]]/parent::div//*[text()='Quick look']//ancestor::button"));
        genericMethods.clickOn(driver,
                By.xpath("//*[@title[contains(.,'40mm] Smartwatch with Starlight Aluminum Case')]]/parent::div//*[text()='Quick look']//ancestor::button"));
        WebElement element = genericMethods.waitForElement(driver,By.xpath("//*[@class='ProductShowcase__title__SBCBw']"));
        if(element.getText().contains(watchProduct)){
            System.out.println("Test OK - Watch");
        }else
            System.out.println("Test NOK - Watch");
    }


}
