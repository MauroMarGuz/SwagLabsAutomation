package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage{

    private BurgerPopUp burgerPopUp;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className = "react-burger-menu-btn")
    private WebElement burgerBtn;

    @FindBy(id = "back-to-products")
    private WebElement backBtn;

    @FindBy(className = "inventory_details_img")
    private WebElement productImage;

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_name")
    private WebElement productDescription;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(className = "btn_inventory")
    private WebElement addDelCartBtn;

    public ProductPage(WebDriver driver) {
        super(driver);
        burgerPopUp = BurgerPopUp.builder()
                .driver(driver)
                .build();
    }

    public WebElement getCartBadge(){
        try {
            return cartBtn.findElement(By.className("shopping_cart_link"));
        }catch (Exception e){
            getLog().error("Element is not present"+ e.getMessage());
            return null;
        }
    }

    public boolean isCartEmpty(){
        return getCartBadge() == null;
    }

    public boolean isProductImageDisplayed(){
        return elementExist(productImage);
    }

    public boolean isProductNameDisplayed(){
        return elementExist(productName);
    }

    public boolean isProductDescriptionDisplayed(){
        return elementExist(productDescription);
    }

    public boolean isProductPriceDisplayed(){
        return elementExist(productPrice);
    }

    public boolean isAddCartBtnDisplayed(){
        return elementExist(addDelCartBtn) && addDelCartBtn.getText().equals("ADD TO CART");
    }

    public boolean isRemoveCartBtnDisplayed(){
        return elementExist(addDelCartBtn) && addDelCartBtn.getText().equals("REMOVE");
    }

    public HomePage goBackToProducts(){
        click(backBtn);
        return HomePage.builder()
                .driver(getDriver())
                .build();
    }

    public boolean isProductRemoved(){
        if(isRemoveCartBtnDisplayed() && !isCartEmpty()){
            int previousNumber = Integer.parseInt(getCartBadge().getText());
            click(addDelCartBtn);
            int actualNumber = Integer.parseInt(getCartBadge().getText());
            return previousNumber != actualNumber;
        }else{
            click(addDelCartBtn);
            return isProductRemoved();
        }
    }
}