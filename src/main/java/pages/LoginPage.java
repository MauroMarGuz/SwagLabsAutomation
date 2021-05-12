package pages;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(className = "login_logo")
    private WebElement logo;

    @FindBy(id = "user-name")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginBtn;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @Builder
    public LoginPage(WebDriver webDriver) { super(webDriver); }

    public LoginPage clickOnLoginButton(){
        click(loginBtn);
        getLog().info("Login button clicked");
        return this;
    }

    public LoginPage writeUserName(String user){
        type(userName,user);
        getLog().info("User name typed");
        return this;
    }

    public LoginPage writePassword(String password){
        type(this.password, password);
        getLog().info("Password typed");
        return this;
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }

    public boolean isUserNameInputDisplayed(){
        return elementExist(userName);
    }

    public boolean isPasswordInputDisplayed(){
        return elementExist(password);
    }

    public boolean isLoginButtonDisplayed(){
        return elementExist(loginBtn);
    }

    public boolean isErrorMessageDisplayed(){
        return elementExist(errorMessage);
    }

    public boolean isAppLogoDisplayed(){
        return elementExist(logo);
    }
}