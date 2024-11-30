package org.ibs.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    @Test
    void test() {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost:8080");

        // Открыть выпадающий список
        WebElement openNavbarDropdown = driver.findElement(By.xpath("//*[@id='navbarDropdown']"));
        openNavbarDropdown.click();

        // Открыть список товаров
        WebElement getProductsListPage = driver.findElement(By.xpath("//*[@href='/food']"));
        getProductsListPage.click();

        //Проверка, что список товаров открылся
        WebElement titleProductListPage = driver.findElement(By.xpath("//*[.='Список товаров']"));
        Assertions.assertEquals("Список товаров", titleProductListPage.getText(),
                "Не перешли на страницу со списком товаров");

        // Добавление товара без чекбокса
        WebElement addVegetableToList = driver.findElement(By.xpath("//*[.='Добавить']"));
        addVegetableToList.click();

        WebElement putVegetableName = driver.findElement(By.xpath("//*[@placeholder='Наименование']"));
        putVegetableName.sendKeys("Огурец");

        WebElement chooseVegetableType = driver.findElement(By.xpath("//*[@id='type']"));
        Select selectVegetable = new Select(chooseVegetableType);
        selectVegetable.selectByVisibleText("Овощ");


        WebElement saveButton = driver.findElement(By.xpath("//*[@id='save']"));
        saveButton.click();

        // Проверка, что товар добавился
        Assertions.assertDoesNotThrow(() ->
            driver.findElement(By.xpath("//*[contains(text(), 'Огурец')]")));


        // Добавление товара с чекбоксом
        WebElement addFruitToList = driver.findElement(By.xpath("//*[.='Добавить']"));
        addFruitToList.click();

        WebElement putFruitName = driver.findElement(By.xpath("//*[@placeholder='Наименование']"));
        putFruitName.sendKeys("Маракуйя");

        WebElement chooseFruitType = driver.findElement(By.xpath("//*[@id='type']"));
        Select selectFruit = new Select(chooseFruitType);
        selectFruit.selectByVisibleText("Фрукт");

        WebElement chooseCheckbox = driver.findElement(By.xpath("//*[@id='exotic']"));
        chooseCheckbox.click();

        saveButton = driver.findElement(By.xpath("//*[@id='save']"));
        saveButton.click();

        // Проверка, что товар добавился
        Assertions.assertDoesNotThrow(() ->
            driver.findElement(By.xpath("//*[contains(text(), 'Маракуйя')]")));

        openNavbarDropdown = driver.findElement(By.xpath("//*[@id='navbarDropdown']"));
        openNavbarDropdown.click();

        // Сброс добавленных товаров
        WebElement resetButton = driver.findElement(By.xpath("//*[@id='reset']"));
        resetButton.click();

        // Проверка, что товары удалились
        Assertions.assertThrows(NoSuchElementException.class, () ->
            driver.findElement(By.xpath("//*[contains(text(), 'Огурец')]")));

        Assertions.assertThrows(NoSuchElementException.class, () ->
            driver.findElement(By.xpath("//*[contains(text(), 'Маракуйя')]")));

        driver.quit();


    }
}
