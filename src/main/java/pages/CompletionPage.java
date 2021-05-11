package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompletionPage extends BasePage{

    private BurgerPopUp burgerPopUp;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className = "react-burger-menu-btn")
    private WebElement burgerBtn;

    @FindBy(id = "back-to-products")
    private WebElement homeBtn;

    @FindBy(className = "pony_express")
    private WebElement ponyImage;

    @FindBy(className = "complete-header")
    private WebElement msgHeader;

    @FindBy(className = "complete-text")
    private WebElement msgDescription;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public CompletionPage(WebDriver driver) {
        super(driver);
        burgerPopUp = BurgerPopUp.builder()
                .driver(driver)
                .build();
    }

    public boolean isPonyImageDisplayed(){
        return elementExist(ponyImage);
    }

    public boolean isHeaderMsgDisplayed(){
        return elementExist(msgHeader) && msgHeader.getText().equals("THANK YOU FOR YOUR ORDER");
    }

    public boolean isDescriptionMsgDisplayed(){
        return elementExist(msgDescription) && msgDescription.getText().contains("Your order has been dispatched");
    }

    public boolean isTitleDisplayed(){
        return elementExist(pageTitle) && pageTitle.getText().contains("COMPLETE!");
    }

}
