package org.tyaa.training.client.android.test.system.utils;

import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

/**
 * Реализация хранилища свойств на основе локальных текстовых файлов
 * */
public class FilePropertiesStore implements IPropertiesStore {

    private static final String PROPS_CATALOG = "src/test/resources/system/properties/";
    private static final Set<String> PROPS_FILE_NAMES =
            Set.of("appium", "selenium", "tested-app-server");
    private static final Properties properties = new Properties();

    public static final String PLATFORM_NAME_KEY = "platformName";
    public static final String PLATFORM_VERSION_KEY = "platformVersion";
    public static final String DEVICE_NAME_KEY = "deviceName";
    public static final String APK_PATH_KEY = "app";
    public static final String AUTOMATION_NAME_KEY = "automationName";
    public static final String ANDROID_SDK_PATH_KEY = "androidSdkPath";
    public static final String TESTED_APP_SERVER_DIR_PATH_KEY = "testedAppServerDirPath";
    public static final String MAVEN_COMMAND_OR_FILE_NAME_KEY = "mavenCommandOrFileName";
    public static final String APPIUM_DRIVER_TIMEOUT_KEY = "appiumDriverTimeout";
    public static final String TESTED_APP_SERVER_TIMEOUT_KEY = "testedAppServerTimeout";
    public static final String TESTED_APP_SERVER_PORT_KEY = "testedAppServerPort";

    public static final String IMPLICITLY_WAIT_SECONDS_KEY = "implicitlyWaitSeconds";

    public static final String LOCAL_URL_KEY = "localUrl";

    static {
        // Загружаем свойства из файлов, названия которых указаны в списке PROPS_FILE_NAMES
        for (String propsFileName : PROPS_FILE_NAMES) {
            try (FileInputStream fis =
                         new FileInputStream(
                                 String.format("%s%s.properties", PROPS_CATALOG, propsFileName)
                         )
            ) {
                properties.load(fis);
            } catch (IOException ex) {
                System.err.printf("ERROR: properties file '%s' does not exist\n", propsFileName);
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getPlatformName() {
        return properties.getProperty(PLATFORM_NAME_KEY);
    }

    @Override
    public String getPlatformVersion() {
        return properties.getProperty(PLATFORM_VERSION_KEY);
    }

    @Override
    public String getDeviceName() {
        return properties.getProperty(DEVICE_NAME_KEY);
    }

    @Override
    public String getApkPath() {
        return properties.getProperty(APK_PATH_KEY);
    }

    @Override
    public String getAutomationName() {
        return properties.getProperty(AUTOMATION_NAME_KEY);
    }

    @Override
    public String getAndroidSdkPath() {
        return properties.getProperty(ANDROID_SDK_PATH_KEY);
    }

    @Override
    public Long getAppiumDriverTimeout() {
        return Long.valueOf(properties.getProperty(APPIUM_DRIVER_TIMEOUT_KEY));
    }

    @Override
    public Integer getTestedAppServerPort() {
        return Integer.valueOf(properties.getProperty(TESTED_APP_SERVER_PORT_KEY));
    }

    @Override
    public String getTestedAppServerDirPath() {
        return properties.getProperty(TESTED_APP_SERVER_DIR_PATH_KEY);
    }

    @Override
    public String getMavenCommandOrFileName() {
        return properties.getProperty(MAVEN_COMMAND_OR_FILE_NAME_KEY);
    }

    @Override
    public Long getTestedAppServerTimeout() {
        return Long.valueOf(properties.getProperty(TESTED_APP_SERVER_TIMEOUT_KEY));
    }

    @Override
    public Duration getImplicitlyWaitSeconds() {
        return Duration.ofSeconds(Integer.parseInt(properties.getProperty(IMPLICITLY_WAIT_SECONDS_KEY)));
    }

    @Override
    public String getLocalAppiumServerUrl() {
        return properties.getProperty(LOCAL_URL_KEY);
    }
}
