package pages;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutInfoPage extends BasePage{

    private BurgerPopUp burgerPopUp;

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalInput;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className = "react-burger-menu-btn")
    private WebElement burgerBtn;

    @Builder
    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
        burgerPopUp = BurgerPopUp.builder()
                .driver(driver)
                .build();
    }

    public boolean isFirstNameInputDisplayed(){
        return elementExist(firstNameInput);
    }

    public boolean isLastNameInputDisplayed(){
        return elementExist(lastNameInput);
    }

    public boolean isPostalInputDisplayed(){
        return elementExist(postalInput);
    }

    public boolean isContinueButtonDisplayed(){
        return continueBtn.isDisplayed() && continueBtn.getText().equals("CONTINUE");
    }

    public boolean isCancelButtonDisplayed(){
        return cancelBtn.isDisplayed() && cancelBtn.getText().equals("CANCEL");
    }

    public void clickOnCancel(){
        click(cancelBtn);
        getLog().info("Cancel button was clicked");
    }

    public void clickOnContinue(){
        click(continueBtn);
        getLog().info("Continue button was clicked");
    }

    public CheckoutInfoPage fillFirstName(String name){
        type(firstNameInput, name);
        getLog().info("First name was written");
        return this;
    }

    public CheckoutInfoPage fillLastName(String lastName){
        type(lastNameInput, lastName);
        getLog().info("Last name was written");
        return this;
    }

    public CheckoutInfoPage fillPostalCode(String code){
        type(postalInput, code);
        getLog().info("Postal code was written");
        return this;
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }

    public WebElement getCartBadge(){
        try {
            return cartBtn.findElement(By.className("shopping_cart_link"));
        }catch (Exception e){
            getLog().error("Element is not present "+e.getMessage());
            return null;
        }
    }

    public boolean isCartEmpty(){
        return getCartBadge() == null;
    }

    public boolean isTitleDisplayed(){
        return elementExist(pageTitle) && pageTitle.getText().contains("YOUR INFORMATION");
    }
}
