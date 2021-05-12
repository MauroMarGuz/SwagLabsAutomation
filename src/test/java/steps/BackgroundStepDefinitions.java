package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import config.DriverConfig;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.CompletionPage;
import pages.HomePage;
import pages.LoginPage;

public class BackgroundStepDefinitions extends DriverConfig{

    private LoginPage loginPage;
    private HomePage homePage;
    private CompletionPage completionPage;

    @Before
    public void before(){

    }

    @Given("^user navigate to sauce demo page$")
    public void userNavigateToSauceDemoPage() {
        setDriver();
        homePage = HomePage.builder()
                .driver(getDriver())
                .build();
        loginPage = LoginPage.builder()
                .webDriver(getDriver())
                .build();
        completionPage = CompletionPage.builder()
                .driver(getDriver())
                .build();
        getDriver().manage().window().maximize();
        getDriver().get("https://www.saucedemo.com/");
    }

    @And("{string} page is displayed")
    public void pageIsDisplayed(String pageName) {
        switch (pageName) {
            case "login":
                loginPageValidation();
                break;
            case "home":
                homePageValidation();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pageName);
        }
    }

    @When("user fills login form with {string} and {string}")
    public void userFillsLoginFormWithAnd(String username, String password) {
        loginPage.writeUserName(username)
                .writePassword(password);
    }

    @And("user clicks on login button")
    public void userClicksOnLoginButton() {
        loginPage.clickOnLoginButton();
    }

    @Then("user should see the {string} page displayed")
    public void userShouldSeeThePageDisplayed(String pageName) {
        switch (pageName){
            case "home":
                homePageValidation();
                break;
            case "complete":
                Assert.assertTrue("The header message was not displayed", completionPage.isHeaderMsgDisplayed());
                Assert.assertTrue("The description message was not displayed", completionPage.isDescriptionMsgDisplayed());
                Assert.assertTrue("The title was not displayed", completionPage.isTitleDisplayed());
                Assert.assertTrue("The image was not displayed", completionPage.isPonyImageDisplayed());
                break;
            case "login":
                loginPageValidation();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pageName);
        }
    }

    public void homePageValidation(){
        Assert.assertTrue("The cart is not empty", homePage.isCartEmpty());
        Assert.assertTrue("The list of items is not displayed correctly", homePage.isItemsInfoDisplayed());
        Assert.assertTrue("The app logo is not displayed", homePage.isAppLogoDisplayed());
        Assert.assertTrue("The icon image is not displayed", homePage.isScreenIconDisplayed());
        Assert.assertTrue("The screen title is not displayed", homePage.isScreenTitleDisplayed());
        Assert.assertTrue("The burger button is not displayed", homePage.isBurgerBtnDisplayed());
        Assert.assertTrue("The cart button is not displayed", homePage.isCartBtnDisplayed());
        Assert.assertTrue("The filter button is not displayed", homePage.isFilterBtnDisplayed());
    }

    public void loginPageValidation(){
        Assert.assertTrue("The app logo is not displayed", loginPage.isAppLogoDisplayed());
        Assert.assertTrue("The user name input field is not displayed", loginPage.isUserNameInputDisplayed());
        Assert.assertTrue("The password input field is not displayed", loginPage.isPasswordInputDisplayed());
        Assert.assertTrue("The login button is not displayed", loginPage.isLoginButtonDisplayed());
    }
}