package org.ibs.framework.managers;


import org.ibs.framework.utils.Consts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static org.ibs.framework.utils.Consts.*;


/**
 * Класс для управления веб-драйвером
 */

public class WebDriverManager {

    /**
     * Переменная для хранения объекта веб-драйвера
     */
    private final WebDriver driver;

    /**
     * Менеджер для properties
     */
    private static final TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * конструктор, который устанавливает свойства ChromeDriver,
     * создает экземпляр WebDriver,
     * максимизирует окно браузера и устанавливает неявное ожидание
     */
    public WebDriverManager() {
        System.setProperty("webdriver.chrome.driver", Consts.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
    }

    /**
     * Метод инициализации веб-драйвера
     * @return возвращает веб-драйвер
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Метод закрытия сессии драйвера и браузера
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
