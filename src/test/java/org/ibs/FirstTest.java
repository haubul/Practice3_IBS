package org.ibs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    @Test
    void test() {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost:8080");

        WebElement openNavbarDropdown = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        openNavbarDropdown.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement getProductsListPage = driver.findElement(By.xpath("//a[@href='/food']"));
        getProductsListPage.click();

        WebElement titleProductListPage = driver.findElement(By.xpath("//h5[text()='Список товаров']"));

        Assertions.assertEquals("Список товаров", titleProductListPage.getText(),
                "Не перешли на страницу со списком товаров");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement addVegetableToList = driver.findElement(By.xpath("//div/button[text()='Добавить']"));

        addVegetableToList.click();

        WebElement putVegetableName = driver.findElement(By.xpath("//input[@placeholder='Наименование']"));

        putVegetableName.sendKeys("Огурец");

        WebElement chooseVegetableType = driver.findElement(By.xpath("//select[@id='type']"));
        Select selectVegetable = new Select(chooseVegetableType);
        selectVegetable.selectByVisibleText("Овощ");


        WebElement saveButton = driver.findElement(By.xpath("//button[@id='save']"));

        saveButton.click();

        WebElement rowNumberOfAddedProduct = driver.findElement(By.xpath("//tr[th[text()='5']]"));

        Assertions.assertEquals("5 Огурец Овощ false", rowNumberOfAddedProduct.getText(),
                "Неверные данные добавленного товара");


    }
}
