package util;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class GenericMethods {

    Properties properties = SetProperties.getProperties();

    public void clickOn(WebDriver driver, By by){
        try{
            WebElement element = driver.findElement(by);
            element.click();
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
    public void clickOn(WebDriver driver, WebElement element){
        try{
            element.click();
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    public WebElement waitForElement(WebDriver driver, By by){
        int secs = Integer.parseInt(properties.getProperty("wait"));
        WebElement element = null;
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
            element=wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception ex){
            System.out.println(ex);
        }
        return element;
    }
    public List<WebElement> waitForElements(WebDriver driver, By by){
        int secs = Integer.parseInt(properties.getProperty("wait"));
        List<WebElement> list = List.of();                               ////// to be improved
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
            list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        }catch (Exception ex){
           System.out.println(ex);
        }
        return list;
    }
    public void selectDropdown(WebDriver driver, String option){
        try{
            WebElement element = driver.findElement(By.xpath("//option[contains(text(),'"+option+"')]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            element.click();
            Thread.sleep(2000);
        } catch (Exception ex){

        }
    }
    public void selectDropdown(WebDriver driver, By by){
        try{
            WebElement element = driver.findElement(by);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            element.click();
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    public void sendKeys(WebDriver driver, By by, String value){
        try {
            WebElement element = driver.findElement(by);
            Thread.sleep(500);
            element.sendKeys(value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void switchWindow(WebDriver driver){

    }
}
