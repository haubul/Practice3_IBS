package org.ibs.basetestsclass;

import org.ibs.framework.managers.DatabaseManager;
import org.ibs.framework.managers.WebDriverManager;
import org.ibs.framework.pages.ProductPage;
import org.ibs.framework.utils.Consts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import java.sql.SQLException;

/**
 *  ласс с настройками перед тестами
 */

public class BaseTest {

    protected WebDriverManager webDriverManager;
    protected WebDriver driver;
    protected ProductPage productPage;
    protected DatabaseManager databaseManager;

    /**
     * ћетод, выполн€емый перед каждым тестом
     * »нициализирует менеджер веб-драйвера, получает экземпл€р веб-драйвера,
     * создает экземпл€р страницы и открывает базовую ссылку.
     * создает экземпл€р базы данных
     */
    @BeforeEach
    public void beforeAll() throws SQLException {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        productPage = new ProductPage(driver);
        driver.get(Consts.BASE_URL);
        databaseManager = new DatabaseManager();
    }

    /**
     * ћетод, выполн€емый после каждого теста
     * постусловие - удал€ет товары из Ѕƒ
     * закрывает сессию драйвера и браузер
     * закрывает соединение с базой данных
     */
    @AfterEach
    public void afterEach() throws SQLException {
        databaseManager.deleteProductFromDatabase("ќгурец");
        databaseManager.deleteProductFromDatabase("ћаракуй€");
        webDriverManager.quitDriver();
        databaseManager.closeConnection();
    }
}