package org.ibs.framework.managers;


import org.ibs.framework.utils.Consts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.ibs.framework.utils.Consts.*;


/**
 * Класс для управления веб-драйвером
 */

public class WebDriverManager {

    /**
     * Переменная для хранения объекта веб-драйвера
     */
    private  WebDriver driver;

    /**
     * Менеджер для properties
     */
    private static final TestPropManager props = TestPropManager.getTestPropManager();


    /**
     * Метод инициализации веб-драйвера
     * @return возвращает веб-драйвер
     */
    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
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


    /**
     * Метод для инициализации драйвера в зависимости от значения type.driver
     */
    private void initDriver() {
        if ("remote".equalsIgnoreCase(props.getProperty("type.driver"))) {
            initRemoteDriver();
        } else {
            initLocalDriver();
        }
    }

    /**
     * конструктор, который устанавливает свойства ChromeDriver для локального окружения,
     * создает экземпляр WebDriver,
     * максимизирует окно браузера и устанавливает неявное ожидание
     */
    public void initLocalDriver() {
        System.setProperty("webdriver.chrome.driver", Consts.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
    }

    /**
     * конструктор, который устанавливает свойства ChromeDriver для удаленного окружения,
     * создает экземпляр WebDriver,
     * максимизирует окно браузера и устанавливает неявное ожидание
     */
    public void initRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("browserName", props.getProperty("type.browser"));
        selenoidOptions.put("browserVersion", "browser.version");
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", false);
        capabilities.setCapability("selenoid:options", selenoidOptions);
        try {
            driver = new RemoteWebDriver(URI.create(props.getProperty("selenoid.url")).toURL(), capabilities);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
