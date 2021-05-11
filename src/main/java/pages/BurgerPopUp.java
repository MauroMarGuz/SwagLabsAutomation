package pages;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BurgerPopUp extends BasePage{

    @FindBy(id = "react-burger-cross-btn")
    private WebElement burgerCloseBtn;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetStateLink;

    @Builder
    public BurgerPopUp(WebDriver driver) {
        super(driver);
    }

    public WebElement getBurgerCloseBtn() {
        return burgerCloseBtn;
    }

    public WebElement getAllItemsLink() {
        return allItemsLink;
    }

    public WebElement getAboutLink() {
        return aboutLink;
    }

    public WebElement getLogoutLink() {
        return logoutLink;
    }

    public WebElement getResetStateLink() {
        return resetStateLink;
    }
}
