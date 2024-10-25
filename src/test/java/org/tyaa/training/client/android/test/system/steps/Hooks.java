package org.tyaa.training.client.android.test.system.steps;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.tyaa.training.client.android.test.system.MobileDriverFactory;
import org.tyaa.training.client.android.test.system.facades.AbstractFacade;
import org.tyaa.training.client.android.test.system.utils.FilePropertiesStore;
import org.tyaa.training.client.android.test.system.utils.TestedServerLocalService;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.time.Duration;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Обработчики событий жизненного цикла сценариев тестирования
 * */
public class Hooks {

    private static final IPropertiesStore properties = new FilePropertiesStore();

    private final static AppiumDriverLocalService appiumDriverLocalService =
            AppiumDriverLocalService.buildService(
                    new AppiumServiceBuilder()
                            .withEnvironment(Map.of("ANDROID_HOME", properties.getAndroidSdkPath()))
                            .withTimeout(Duration.ofSeconds(properties.getAppiumDriverTimeout()))
                            .withLogOutput(System.out)
            );
    private final static TestedServerLocalService testedServerLocalService =
            new TestedServerLocalService(properties.getTestedAppServerDirPath());

    /**
     * Перед выполнением всех сценариев
     * */
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        // Запустить сервер тестируемого приложения, ожидая, что он прослушивает указанный порт
        testedServerLocalService.start(properties.getTestedAppServerPort());
        sleep(properties.getTestedAppServerTimeout() * 1000);
        // Запустить сервер доступа к мобильным устройствам
        System.out.println("Попытка запуска сервера Appium для доступа к мобильному устройству");
        appiumDriverLocalService.start();
        sleep(properties.getAppiumDriverTimeout() * 1000);
    }

    /**
     * После выполнения сценария
     * @param scenario объект доступа к текущему сценарию
     * */
    @After
    public void afterScenario(Scenario scenario) {
        // Закрыть мобильное приложение
        (new AbstractFacade(MobileDriverFactory.getInstance()){}).close();
    }

    /**
     * После выполнения всех сценариев
     * */
    @AfterAll
    public static void afterAll() throws InterruptedException {
        // Остановить сервер доступа к мобильным устройствам
        appiumDriverLocalService.stop();
        sleep(properties.getAppiumDriverTimeout() * 1000);
        // Остановить сервер тестируемого приложения
        testedServerLocalService.stop();
        sleep(properties.getTestedAppServerTimeout() * 1000);
    }
}
