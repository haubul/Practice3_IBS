package org.ibs.tests;

import org.ibs.basetestsclass.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.sql.SQLException;

/**
 * Тестовый класс для UI
 */

public class ProductPageTest extends BaseTest {

    /**
     * Метод логики теста
     */
    @Test
    void AddAndDeleteNewProductTest() throws SQLException {
        // Открыть выпадающий список
        productPage.openNavbarDropdown();

        // Открыть список товаров
        productPage.openProductsListPage();

        // Проверка, что список товаров открылся
        Assertions.assertEquals("Список товаров", productPage.getTitleProductListPage(),
                "Не перешли на страницу со списком товаров");

        // Добавление товара без чекбокса
        productPage.addProductToList("Огурец", "Овощ", false);

        // Проверка, что товар добавился на сайте
        Assertions.assertTrue(productPage.isProductAdded("Огурец"));

        // Проверка, что товар добавился в БД
        Assertions.assertTrue(databaseManager.isProductInDatabase("Огурец", "VEGETABLE", 0),
                "Товар 'Огурец' не найден в БД");

        // Добавление товара с чекбоксом
        productPage.addProductToList("Маракуйя", "Фрукт", true);

        // Проверка, что товар добавился
        Assertions.assertTrue(productPage.isProductAdded("Маракуйя"));

        // Проверка, что товар добавился в БД
        Assertions.assertTrue(databaseManager.isProductInDatabase("Маракуйя", "FRUIT", 1),
                "Товар 'Маракуйя' не найден в БД");


        // Сброс добавленных товаров
        productPage.openNavbarDropdown();
        productPage.resetProducts();

        // Проверка, что товары удалились
        Assertions.assertThrows(NoSuchElementException.class, () ->
                driver.findElement(By.xpath("//*[contains(text(), 'Огурец')]")));

        Assertions.assertThrows(NoSuchElementException.class, () ->
                driver.findElement(By.xpath("//*[contains(text(), 'Маракуйя')]")));

        // Проверка, что товары удалились из БД
        Assertions.assertFalse(databaseManager.isProductInDatabase("Огурец", "Овощ", 0),
                "Товар 'Огурец' не был удален из БД");

        Assertions.assertFalse(databaseManager.isProductInDatabase("Маракуйя", "Фрукт", 1),
                "Товар 'Маракуйя' не был удален из БД");
    }
}