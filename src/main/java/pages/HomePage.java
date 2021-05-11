package pages;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class HomePage extends BasePage{

    public final static int TOTAL_ITEMS = 6;

    private List<WebElement> items;

    @FindBy(className = "product_sort_container")
    private WebElement filterBtn;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className = "react-burger-menu-btn")
    private WebElement burgerBtn;

    @FindBy(className = "title")
    private WebElement screenTitle;

    @FindBy(className = "peek")
    private WebElement screenIcon;

    @FindBy(className = "app_logo")
    private WebElement appLogo;

    @FindBy(className = "inventory_list")
    private WebElement container;

    private BurgerPopUp burgerPopUp;

    @FindBy(className = "btn_secondary")
    private List<WebElement> removeBtn;

    @Builder
    public HomePage(WebDriver driver) {
        super(driver);
        items = getItemList();
        burgerPopUp = BurgerPopUp.builder()
                .driver(driver)
                .build();
    }

    public void addLowestPriceItem() {
        try{
            double minPrice = getLowestPrice();

            click(items.stream()
                    .filter(item -> item.findElement(By.className("inventory_item_price"))
                                        .getText()
                                        .contains(Double.toString(minPrice)))
                    .findFirst()
                    .orElseThrow(Exception::new)
                    .findElement(By.className("btn_primary")));

        }catch (Exception e){
            getLog().error("Lowest price couldn't been found "+ e.getMessage());
        }
    }

    public void filterItemsBy(String value){
        Select selectObject = new Select(filterBtn);
        selectObject.selectByValue(value);
        items = getItemList();
    }

    public void addHighestPriceItem(){
        try{
            double maxPrice = getHighestPrice();

            click(items.stream()
                    .filter(item -> item.findElement(By.className("inventory_item_price"))
                            .getText()
                            .contains(Double.toString(maxPrice)))
                    .findFirst()
                    .orElseThrow(Exception::new)
                    .findElement(By.className("btn_primary")));

        }catch (Exception e){
            getLog().error("Highest price couldn't been found "+ e.getMessage());
        }
    }

    public boolean isSortedAZ(){
        //todo
        return true;
    }

    public boolean isSortedZA(){
        //todo
        return true;
    }

    public boolean isSortedByLoHiPrice(){
        //todo
        return true;
    }

    public boolean isSortedByHiLoPrice(){
        //todo
        return true;
    }

    public boolean isCartBadgePresent(){
        return elementExist(getCartBadge());
    }

    public boolean isItemsInfoDisplayed(){
       return items.stream()
                .filter(item -> {
                    boolean image = elementExist(item.findElement(By.className("inventory_item_img")));
                    boolean title = elementExist(item.findElement(By.className("inventory_item_name")));
                    boolean description = elementExist(item.findElement(By.className("inventory_item_desc")));
                    boolean price = elementExist(item.findElement(By.className("inventory_item_price")));
                    boolean addButton = elementExist(item.findElement(By.className("btn_inventory")));

                    return image && title && description && price && addButton;
                })
               .count() == TOTAL_ITEMS;
    }

    public double getLowestPrice() throws Exception {
        return items.stream()
                .mapToDouble(item -> getPriceFromText(item.findElement(By.className("inventory_item_price")).getText()))
                .min().orElseThrow(Exception::new);
    }

    public double getHighestPrice() throws Exception {
        return items.stream()
                .mapToDouble(item -> getPriceFromText(item.findElement(By.className("inventory_item_price")).getText()))
                .max().orElseThrow(Exception::new);
    }

    public double getPriceFromText(String text){
        return Double.parseDouble(text.substring(1));
    }

    public boolean isBadgeCartNumberCorrect(){
        long count = items.stream()
             .filter(item -> elementExist(item.findElement(By.className("btn_secondary"))))
             .count();
        return getCartBadge().getText().equals(Long.toString(count));
    }

    /**
     * Method to get/refresh the item list after filtering them
     * @return list of items
     */
    public List<WebElement> getItemList(){
        return container.findElements(By.cssSelector("div[class='inventory_item']"));
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

    public LoginPage logout(){
        click(burgerBtn);
        if(elementExist(burgerPopUp.getLogoutLink())) {
            click(burgerPopUp.getLogoutLink());
        }else {
            getLog().error("Logout link is not displayed");
        }
        return LoginPage.builder()
                .webDriver(getDriver())
                .build();
    }

    public boolean isSauceLabsWebPageDisplayed(){
        WebElement sauceLabLogin = getDriver().findElement(By.className("link has-text-white"));
        return elementExist(sauceLabLogin) && sauceLabLogin.isDisplayed();
    }
}