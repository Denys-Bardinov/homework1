import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://rozetka.com.ua/");
        driver.manage().window().maximize();
    }

    @AfterEach
    public void quit() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    public void matchEmailErrorMessage() {
        driver.findElement(By.xpath("//rz-user//button")).click();
        driver.findElement(By.xpath("//div/input[@type='email']")).sendKeys("A");
        driver.findElement(By.xpath("//div/input[@type='password']")).sendKeys("B");
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='form__row validation_type_error']/p")));
        String failedAuthorizationTextpath = driver.findElement(By.xpath( "//div[@class='form__row validation_type_error']/p")).getText();
        Assert.assertTrue(failedAuthorizationTextpath.equals("Введен неверный адрес эл.почты или номер телефона"));
    }

    @Test
    public void matchFirstSearchResult() {
        driver.findElement(By.cssSelector("div input[name='search']")).click();
        driver.findElement(By.cssSelector("div input[name='search']")).sendKeys("Телефон");
        driver.findElement(By.cssSelector("div input[name='search']")).sendKeys(Keys.ENTER);
        driver.get("https://rozetka.com.ua/mobile-phones/c80003/#search_text=%D1%82%D0%B5%D0%BB%D0%B5%D1%84%D0%BE%D0%BD");
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        WebElement element = driver.findElement(By.cssSelector("a span.goods-tile__title"));
        wait.until(ExpectedConditions.textToBePresentInElement(element, "телефон" ));
        String firstResultText = driver.findElement(By.cssSelector("a span.goods-tile__title")).getText();
        Assert.assertTrue(firstResultText.contains("телефон"));
    }

    @Test
    public void comparePlaceholderText() {
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        WebElement element = driver.findElement(By.xpath("//rz-header/header"));
        wait.until(ExpectedConditions.visibilityOf(element));
        String placeholder = driver.findElement(By.name("search")).getAttribute("placeholder");
        Assert.assertTrue(placeholder.equals("Я ищу..."));
    }

    @Test
    public void comparePremiumText() {
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        WebElement element = driver.findElement(By.className("premium-button__label"));
        var until = wait.until(ExpectedConditions.attributeToBeNotEmpty(element, "class"));
        String premiumText = driver.findElement(By.className("premium-button__label")).getText();
        Assert.assertTrue(premiumText.equals("Попробуйте"));

    }

    @Test
    public void checkPasswordVisibility() {
        driver.findElement(By.xpath("/html/body/app-root/div/div/rz-header/header/div/div/ul/li[3]/rz-user/button")).click();
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/single-modal-window")));
        String typeName = driver.findElement(By.xpath("//*[@id=\"auth_pass\"]")).getAttribute("type");
        Assert.assertTrue(typeName.equals("password"));
    }

    @Test
    public void displayCarttext() {
        driver.findElement(By.xpath("//rz-cart/button")).click();
        WebDriverWait wait = (new WebDriverWait(driver,10));
        WebElement element = driver.findElement(By.xpath("//rz-shopping-cart/div/div//img"));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(element, "class"));
        String cartText = driver.findElement(By.xpath("//rz-shopping-cart/div/div//h4")).getText();
        Assert.assertTrue(cartText.contains("Корзина"));
    }

    @Test
    public void calculateCountOfMenuCategoriesItem() {
        driver.findElement(By.id("fat-menu")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//ul [@class='menu-categories ng-star-inserted']/li"));
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        WebElement element = driver.findElement(By.xpath("//rz-header-fat-menu/fat-menu/div/ul/li[1]"));
        wait.until(ExpectedConditions.textToBePresentInElement(element, "Ноутбуки"));
        int elementsSize = elements.size();
        Assert.assertTrue(elementsSize == 19);
    }

    @Test
    public void calculateCountOfSocialNetworks() {
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        wait.until(ExpectedConditions.textToBe((By.xpath("//aside/main-page-sidebar/div[3]/h3")), "Мы в социальных сетях"));
        List<WebElement> listOfSocialNetworks = driver.findElements(By.xpath("//ul [@class='socials__list']//a/.."));
        Assert.assertNotNull(listOfSocialNetworks);
    }

    @Test
    public void compareText() {
        driver.findElement(By.xpath("/html/body/app-root/div/div/rz-header/header/div/div/rz-mobile-user-menu/button")).click();
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//rz-service-links/div[3]/div/button")));
        driver.findElement(By.xpath("//rz-service-links/div[3]/div/button")).click();
       List<WebElement> elements = driver.findElements(By.xpath("//*[@id='cdk-overlay-3']/nav/div/div[2]/ul[2]/li[3]/rz-service-links/div[3]/ul/li"));
       Assert.assertNotNull(elements);
    }

    @Test
    public void checkHelpCenter() {
        driver.findElement(By.xpath("//goods-sections/section[1]/goods-section/button")).click();
        WebDriverWait wait = (new WebDriverWait(driver, 5));
        WebElement element = driver.findElement(By.xpath("//section[1]/goods-section/ul/li"));
        wait.until(ExpectedConditions.textToBePresentInElement(element, "Средство"));
        List<WebElement> goods = driver.findElements(By.xpath("//section[1]/goods-section/ul/li"));
        Assert.assertTrue(goods.size() > 5);
    }
}
