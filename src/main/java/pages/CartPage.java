package pages;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage{

    @FindBy(className = "cart_item")
    private List<WebElement> items;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingBtn;

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "checkout")
    private WebElement checkoutBtn;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className = "react-burger-menu-btn")
    private WebElement burgerBtn;

    private BurgerPopUp burgerPopUp;

    @Builder
    public CartPage(WebDriver driver) {
        super(driver);
        burgerPopUp = BurgerPopUp.builder()
                .driver(driver)
                .build();
    }

    public void continueShopping(){
        click(continueShoppingBtn);
    }

    public void checkout(){
        click(checkoutBtn);
        getLog().info("Checkout button clicked");
    }

    public boolean isItemsInfoDisplayed(){
        try{
            return items.stream()
                    .filter(item -> {
                        boolean quantity = elementExist(item.findElement(By.className("cart_quantity")));
                        boolean title = elementExist(item.findElement(By.className("inventory_item_name")));
                        boolean description = elementExist(item.findElement(By.className("inventory_item_desc")));
                        boolean price = elementExist(item.findElement(By.className("item_pricebar")));
                        boolean removeButton = elementExist(item.findElement(By.className("cart_button")));

                        return quantity && title && description && price && removeButton;
                    })
                    .count() == Integer.parseInt(getCartBadge().getText());
        }catch (NullPointerException e){
            getLog().error("Cart badge is not displayed");
            return false;
        }
    }

    public void removeAll(){
        items.forEach(item -> click(item.findElement(By.className("cart_button"))));
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
        return getCartBadge() != null;
    }

    public boolean isTitleDisplayed(){
        return elementExist(pageTitle) && pageTitle.getText().contains("CART");
    }

    public HomePage clickOnAllItemsLink(){
        click(burgerBtn);
        click(burgerPopUp.getAllItemsLink());
        return HomePage.builder()
                .driver(getDriver())
                .build();
    }

    public boolean isCartBtnDisplayed(){
        return elementExist(cartBtn);
    }
    public boolean isBurgerBtnDisplayed(){
        return elementExist(burgerBtn);
    }
}
