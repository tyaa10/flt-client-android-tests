package org.tyaa.training.client.android.test.system;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.tyaa.training.client.android.test.system.utils.FilePropertiesStore;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.util.Objects;

/**
 * Фабрика объектов AppiumDriver для тестов с доступом к мобильным устройствам
 * */
public class MobileDriverFactory {

    private static final IPropertiesStore properties = new FilePropertiesStore();
    private static MobileDriverFactory instance;
    private final ThreadLocal<AndroidDriver> androidDriverThreadLocal = new ThreadLocal<>();

    /**
     * Закрытый конструктор для предотвращения создания более одного экземпляра фабрики
     * */
    private MobileDriverFactory() {}

    /**
     * Получение экземпляра фабрики
     * */
    public synchronized static MobileDriverFactory getInstance() {
        // Если экземпляр фабрики еще не создан
        if (instance == null) {
            // Создание экземпляра фабрики
            instance = new MobileDriverFactory();
        }
        // Возвращение экземпляра фабрики
        return instance;
    }

    /**
     * Получение экземпляра драйвера для текущего потока выполнения
     * */
    public synchronized AppiumDriver getDriver() {
        // Если экземпляр драйвера для текущего потока выполнения ещё не создан
        if (androidDriverThreadLocal.get() == null) {
            // Создание драйвера
            AndroidDriver driver = new AndroidDriver(
                    Objects.requireNonNull(CapabilitiesFactory.getAppiumServerURL()),
                    CapabilitiesFactory.getAndroidOptions()
            );
            // Установка таймаута
            driver.manage()
                    .timeouts()
                    .implicitlyWait(properties.getImplicitlyWaitSeconds());
            // Сохранение экземпляра драйвера для текущего потока выполнения
            androidDriverThreadLocal.set(driver);
        }
        // Возвращение экземпляра драйвера для текущего потока выполнения
        // (в этот момент драйвер начинает установку и запуск тестируемого приложения)
        return androidDriverThreadLocal.get();
    }

    /**
     * Закрытие драйвера для текущего потока выполнения
     * */
    public synchronized void closeDriver() {
        // Если экземпляр драйвера для текущего потока выполнения существует
        if (androidDriverThreadLocal.get() != null) {
            // Попытка закрытия драйвера для текущего потока выполнения
            try {
                androidDriverThreadLocal.get().quit();
            } catch (Exception e) {
                System.err.println("ERROR: Can not close AndroidDriver!");
                System.err.println(e.getMessage());
                e.printStackTrace();
            } finally {
                // Удаление экземпляра драйвера для текущего потока выполнения
                androidDriverThreadLocal.remove();
            }
        }
    }
}
