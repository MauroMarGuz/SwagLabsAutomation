package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverConfig {

    private WebDriver driver;

    public DriverConfig() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "src\\main\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public WebDriver getDriver(){return driver;}
}
