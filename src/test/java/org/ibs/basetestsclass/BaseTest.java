package org.ibs.basetestsclass;

import org.ibs.pages.managers.WebDriverManager;
import org.ibs.pages.pages.ProductPage;
import org.ibs.pages.utils.Consts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 *  ласс с настройками перед тестами
 */

public class BaseTest {

    protected WebDriverManager webDriverManager;
    protected WebDriver driver;
    protected ProductPage productPage;

    /**
     * ћетод, выполн€емый перед каждым тестом
     * »нициализирует менеджер веб-драйвера, получает экземпл€р веб-драйвера,
     * создает экземпл€р страницы и открывает базовую ссылку.
     */
    @BeforeEach
    public void beforeAll() {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        productPage = new ProductPage(driver);
        driver.get(Consts.BASE_URL);
    }

    /**
     * ћетод, выполн€емый после каждого теста
     * закрывает сессию драйвера и браузер
     */
    @AfterEach
    public void afterEach() {
        webDriverManager.quitDriver();
    }
}