package org.ibs.basetestsclass;

import org.ibs.framework.managers.DatabaseManager;
import org.ibs.framework.managers.TestPropManager;
import org.ibs.framework.managers.WebDriverManager;
import org.ibs.framework.pages.ProductPage;
import org.ibs.framework.utils.Consts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import java.sql.SQLException;

/**
 * Класс с настройками перед тестами
 */

public class BaseTest {

    protected WebDriverManager webDriverManager;
    protected WebDriver driver;
    protected ProductPage productPage;
    protected DatabaseManager databaseManager;

    private static final TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * Метод для выбора URL в зависимости от типа окружения
     */
    private void initUrl() {
        if ("remote".equalsIgnoreCase(props.getProperty("type.driver"))) {
            initRemoteUrl();
        } else {
            initLocalUrl();
        }
    }

    /**
     * Устанавливает URL для локального окружения
     */
    private void initLocalUrl() {
        driver.get(Consts.LOCAL_URL);
    }

    /**
     * Устанавливает URL для удаленного окружения
     */
    private void initRemoteUrl() {
        driver.get(Consts.REMOTE_URL);
    }

    /**
     * Метод, выполняемый перед каждым тестом
     * Инициализирует менеджер веб-драйвера, получает экземпляр веб-драйвера,
     * создает экземпляр страницы и открывает базовую ссылку.
     * создает экземпляр базы данных
     */


    @BeforeEach
    public void beforeAll() throws SQLException {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        productPage = new ProductPage(driver);
        initUrl();
        databaseManager = new DatabaseManager();
    }

    /**
     * Метод, выполняемый после каждого теста
     * постусловие - удаляет товары из БД
     * закрывает сессию драйвера и браузер
     * закрывает соединение с базой данных
     */
    @AfterEach
    public void afterEach() throws SQLException {
        databaseManager.deleteProductFromDatabase("Огурец");
        databaseManager.deleteProductFromDatabase("Маракуйя");
        webDriverManager.quitDriver();
        databaseManager.closeConnection();
    }
}