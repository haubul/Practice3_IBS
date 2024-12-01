package org.ibs.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Описание страницы с продуктами
 */

public class ProductPage {

    /**
     * Переменная для хранения объекта веб-драйвера
     */
    private final WebDriver driver;

    /**
     * Конструктор, принимающий экземпляр веб-драйвераа
     */
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Метод для открытия выпадающего списка
     */
    public void openNavbarDropdown() {
        WebElement openNavbarDropdown = driver.findElement(By.xpath("//*[@id='navbarDropdown']"));
        openNavbarDropdown.click();
    }

    /**
     * Метод для открытия страницы со списком продуктов
     */
    public void openProductsListPage() {
        WebElement getProductsListPage = driver.findElement(By.xpath("//*[@href='/food']"));
        getProductsListPage.click();
    }

    /**
     * Метод для возврата заголовка страницы
     */
    public String getTitleProductListPage() {
        WebElement titleProductListPage = driver.findElement(By.xpath("//*[.='Список товаров']"));
        return titleProductListPage.getText();
    }

    /**
     * Метод для добавления товара в список
     * @param productName параметр - имя продукта
     * @param productType параметр - тип продукта
     * @param isExotic параметр - чекбокс (экзотический или не экзотический)
     */
    public void addProductToList(String productName, String productType, boolean isExotic) {
        WebElement addProductToList = driver.findElement(By.xpath("//*[.='Добавить']"));
        addProductToList.click();

        WebElement putProductName = driver.findElement(By.xpath("//*[@placeholder='Наименование']"));
        putProductName.sendKeys(productName);

        WebElement chooseProductType = driver.findElement(By.xpath("//*[@id='type']"));
        Select selectProduct = new Select(chooseProductType);
        selectProduct.selectByVisibleText(productType);

        if (isExotic) {
            WebElement chooseCheckbox = driver.findElement(By.xpath("//*[@id='exotic']"));
            chooseCheckbox.click();
        }

        WebElement saveButton = driver.findElement(By.xpath("//*[@id='save']"));
        saveButton.click();
    }

    /**
     * Метод для проверки добавился ли товар с указанными параметрами
     * @param productName параметр - название продукта
     */
    public boolean isProductAdded(String productName) {
        try {
            driver.findElement(By.xpath("//*[contains(text(), '" + productName + "')]"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод для сброса товаров
     */
    public void resetProducts() {
        WebElement resetButton = driver.findElement(By.xpath("//*[@id='reset']"));
        resetButton.click();
    }
}
