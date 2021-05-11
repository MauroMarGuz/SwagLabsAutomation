package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

@Slf4j
public abstract class BasePage {

    private WebDriver driver;

    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        wait =  new WebDriverWait(this.driver, 30);
    }

    public WebDriver getDriver() { return driver; }

    public WebDriverWait getWait() { return wait; }

    public Logger getLog() { return log; }

    public void dispose() {
        if (driver != null) {
            driver.quit();
        } else {
            log.error("Driver is null cannot close it");
        }
    }

    public boolean elementExist(WebElement element){
        try{
            getWait().until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch (ElementNotVisibleException e){
            log.error("Element is not visible "+ e.getMessage() );
            return false;
        }
    }

    public void type(WebElement element, String text) {
        try{
            getWait().until(ExpectedConditions.elementToBeClickable(element));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            log.error("Element is nor clickable "+ e.getMessage() );
        }
    }

    public String getText(WebElement element){
        String text = "";
        if(elementExist(element)) {
            text = element.getText();
        }else{
            log.error("Couldn't get the text of the element");
        }
        return text;
    }

    public void click(WebElement element){
        try{
            getWait().until(ExpectedConditions.elementToBeClickable(element)).click();
        }catch (Exception e){
            log.error("Element couldn't be clicked "+ e.getMessage() );
        }
    }
}