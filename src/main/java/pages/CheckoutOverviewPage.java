package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CheckoutOverviewPage extends BasePage{

    private BurgerPopUp burgerPopUp;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className = "react-burger-menu-btn")
    private WebElement burgerBtn;

    @FindBy(className = "cart_item")
    private List<WebElement> items;

    @FindBy(css = "div[class='summary_info'] div:nth-child(2)")
    private WebElement paymentInfo;

    @FindBy(css = "div[class='summary_info'] div:nth-child(4)")
    private WebElement shippingInfo;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotal;

    @FindBy(className = "summary_tax_label")
    private WebElement taxes;

    @FindBy(className = "summary_total_label")
    private WebElement totalPrice;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    @FindBy(id = "finish")
    private WebElement finishBtn;

    @FindBy(className = "title")
    private WebElement pageTitle;

    private double itemTotalValue;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
       burgerPopUp = BurgerPopUp.builder()
                .driver(driver)
                .build();
       itemTotalValue = 0;
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

    public void clickOnCancel(){
        click(cancelBtn);
    }

    public void clickOnFinish(){
        click(finishBtn);
    }

    public boolean isItemTotalCorrect(){
        items.forEach(item -> {
            itemTotalValue += Double.parseDouble(item.findElement(By.className("inventory_item_price")).getText().substring(1));
        });

        return Double.parseDouble(itemTotal.getText().substring(13)) == itemTotalValue;
    }

    public boolean isTotalPriceCorrect(){
        double tax = Double.parseDouble(taxes.getText().substring(6));
        return (itemTotalValue + tax) == Double.parseDouble(totalPrice.getText().substring(8));
    }

    public boolean isItemsInfoDisplayed(){
        try{
            return items.stream()
                    .filter(item -> {
                        boolean quantity = elementExist(item.findElement(By.className("cart_quantity")));
                        boolean title = elementExist(item.findElement(By.className("inventory_item_name")));
                        boolean description = elementExist(item.findElement(By.className("inventory_item_desc")));
                        boolean price = elementExist(item.findElement(By.className("inventory_item_price")));

                        return quantity && title && description && price;
                    })
                    .count() == Integer.parseInt(getCartBadge().getText());
        }catch (NullPointerException e){
            getLog().error("Cart badge is not displayed");
            return false;
        }
    }

    public boolean isPaymentInfoDisplayed(){
        return elementExist(paymentInfo);
    }

    public boolean isShippingInfoDisplayed(){
        return elementExist(shippingInfo);
    }

    public boolean isTitleDisplayed(){
        return elementExist(pageTitle) && pageTitle.getText().contains("OVERVIEW");
    }

    public boolean resetApp(){
        click(burgerBtn);
        elementExist(burgerPopUp.getResetStateLink());
        click(burgerPopUp.getResetStateLink());
        click(burgerPopUp.getBurgerCloseBtn());
        return isCartEmpty();
    }
}