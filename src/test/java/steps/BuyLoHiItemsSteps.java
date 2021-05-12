package steps;

import config.DriverConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.*;

public class BuyLoHiItemsSteps extends DriverConfig {

    private LoginPage loginPage;
    private HomePage homePage;
    private CompletionPage completionPage;
    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CartPage cartPage;

    @Before
    public void before(){
        setDriver();
        loginPage = LoginPage.builder()
                .webDriver(getDriver())
                .build();
        homePage = HomePage.builder()
                .driver(getDriver())
                .build();
        completionPage = CompletionPage.builder()
                .driver(getDriver())
                .build();
        checkoutInfoPage = CheckoutInfoPage.builder()
                .driver(getDriver())
                .build();
        checkoutOverviewPage = CheckoutOverviewPage.builder()
                .driver(getDriver())
                .build();
        cartPage = CartPage.builder()
                .driver(getDriver())
                .build();
    }
    
    @And("user filters products by {string}")
    public void userFiltersProductsBy(String criteria) {
        homePage.filterItemsBy(getCriteria(criteria));
    }

    public String getCriteria(String text){
        String criteria;
        switch (text){
            case "lowest price":
                criteria = "lohi";
                break;
            case "highest price":
                criteria = "hilo";
                break;
            case "A to Z":
                criteria = "az";
                break;
            case "Z to A":
                criteria = "za";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + text);
        }
        return criteria;
    }

    @When("user adds the lowest and highest products to cart")
    public void userAddsTheLowestAndHighestProductsToCart() {
        homePage.addLowestPriceItem()
                .addHighestPriceItem();
    }

    @And("user clicks on {string} button")
    public void userClicksOnButton(String btnName) {
        switch (btnName){
            case "cart":
                homePage.clickOnCart();
                break;
            case "checkout":
                cartPage.checkout();
                break;
            case "continue":
                checkoutInfoPage.clickOnContinue();
                break;
            case  "finish":
                checkoutOverviewPage.clickOnFinish();
                break;
            case "burger":
                homePage.clickOnBurgerBtn();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + btnName);
        }
    }

    @And("user verifies that {string} page is displayed")
    public void userVerifiesThatPageIsDisplayed(String pageName) {
        switch (pageName){
            case "information":
                Assert.assertTrue("The cancel button was not displayed", checkoutInfoPage.isCancelButtonDisplayed());
                Assert.assertTrue("The continue button was not displayed", checkoutInfoPage.isContinueButtonDisplayed());
                Assert.assertTrue("The title was not displayed", checkoutInfoPage.isTitleDisplayed());
                Assert.assertTrue("The postal input field was not displayed", checkoutInfoPage.isPostalInputDisplayed());
                Assert.assertTrue("The first name input field was not displayed", checkoutInfoPage.isFirstNameInputDisplayed());
                Assert.assertTrue("The last name input field was not displayed", checkoutInfoPage.isLastNameInputDisplayed());
                break;
            case "cart":
                Assert.assertTrue("The page title was not displayed", cartPage.isTitleDisplayed());
                Assert.assertTrue("The burger button was not displayed", cartPage.isBurgerBtnDisplayed());
                Assert.assertTrue("The cart button was not displayed", cartPage.isCartBtnDisplayed());
                Assert.assertTrue("The items information have errors", cartPage.isItemsInfoDisplayed());
                break;
            case "overview":
                Assert.assertTrue("The page title was not displayed", checkoutOverviewPage.isTitleDisplayed());
                Assert.assertTrue("The burger button was not displayed", checkoutOverviewPage.isBurgerBtnDisplayed());
                Assert.assertTrue("The cart button was not displayed", checkoutOverviewPage.isCartBtnDisplayed());
                Assert.assertTrue("The items information have errors", checkoutOverviewPage.isItemsInfoDisplayed());
                Assert.assertTrue("The payment information was not displayed", checkoutOverviewPage.isPaymentInfoDisplayed());
                Assert.assertTrue("The shipping information was not displayed", checkoutOverviewPage.isShippingInfoDisplayed());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pageName);
        }
    }

    @And("user verifies the values are correct")
    public void userVerifiesTheValuesAreCorrect() {
        Assert.assertTrue("The item total value was not correct", checkoutOverviewPage.isItemTotalCorrect());
        Assert.assertTrue("The total price was not correct", checkoutOverviewPage.isTotalPriceCorrect());
    }

    @And("user should see the cart empty")
    public void userShouldSeeTheCartEmpty() {
        Assert.assertTrue("The cart was not empty", completionPage.isCartEmpty());
    }

    @And("user fills the user form with {string}, {string} and {string}")
    public void userFillsTheUserFormWithAnd(String firstName, String lastName, String code) {
        checkoutInfoPage.fillFirstName(firstName)
            .fillLastName(lastName)
            .fillPostalCode(code);
    }

    @And("user clicks on {string} link")
    public void userClicksOnLink(String link) {
        switch (link){
            case "logout":
                homePage.clickOnLogoutLink();
                break;
            case "all items":
                homePage.clickOnAllItemsLink();
                break;
            case "about":
                homePage.clickOnAboutLink();
                break;
            case "reset state":
                homePage.clickOnResetLink();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + link);
        }
    }

    @After
    public void dispose(){
        getDriver().quit();
        getDriver().close();
    }
}