package com.amazon.test;

import com.amazon.pages.ProceedToBuy;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.pages.SearchBox;
import com.amazon.pages.ProductCart;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class AmazonTestFlow {
    public static WebDriver driver = null;

    @BeforeTest
    public void setup() {
        // Set the path to ChromeDriver executable
        WebDriverManager.chromedriver().setup();

        // Initialize ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in/");
    }

    @Test(priority = 0)
    public void SearchAndClickFirstProduct() {

        SearchBox.textbox_search(driver).sendKeys("apple phones", Keys.ENTER);
        SearchBox.button_search(driver).click();
        Assert.assertTrue(driver.getTitle().contains("apple phones"), "Search results page did not load successfully");

        // Click on the first product
        SearchBox.clickonFirstItem(driver).click();
        Assert.assertTrue(SearchBox.clickonFirstItem(driver).isEnabled(), "First product is not clickable");

        Set<String> handle = driver.getWindowHandles();
        Iterator<String> it = handle.iterator();
        String parentwindowid = it.next();
        System.out.println("your parent window is : " + parentwindowid);

        String childwindowid = it.next();
        System.out.println("your child window is : " + childwindowid);

        driver.switchTo().window(childwindowid);
        String title1 = driver.getTitle();
        System.out.println(title1);

    }

    @Test(priority = 1)
    public void addToCart() {
//        ProductCart.addToCart(driver).click();
        ProductCart.addToCart(driver);
        Assert.assertTrue(ProductCart.isItemAddedToCart(driver));
    }

    @Test(priority = 1)
    public void proceedBuy() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        ProceedToBuy.proceedToBuy(driver).click();


    }
}
