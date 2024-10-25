package org.tyaa.training.client.android.test.system.utils.interfaces;

import java.time.Duration;

/**
 * Абстракция хранилища свойств
 * */
public interface IPropertiesStore {

    /**
     * Получить название платформы, на которой будет запущена клиентская часть тестируемого приложения
     * */
    String getPlatformName();
    /**
     * Получить версию платформы, на которой будет запущена клиентская часть тестируемого приложения
     * */
    String getPlatformVersion();
    /**
     * Получить название устройства, на котором будет запущена клиентская часть тестируемого приложения
     * */
    String getDeviceName();
    /**
     * Получить путь к установочному файлу клиентской части тестируемого приложения
     * */
    String getApkPath();
    /**
     * Получить название драйвера доступа к устройству
     * */
    String getAutomationName();
    /**
     * Получить путь к каталогу программных инструментов Android SDK
     * */
    String getAndroidSdkPath();
    /**
     * Получить время ожидания драйвера доступа к устройству в секундах
     * */
    Long getAppiumDriverTimeout();
    /**
     * Получить номер порта, на котором ожидается прослушивание http-запросов сервером тестируемого приложения
     * */
    Integer getTestedAppServerPort();
    /**
     * Получить путь к каталогу проекта сервера тестируемого приложения
     * */
    String getTestedAppServerDirPath();
    /**
     * Получить название команды или имени исполняемого файла для запуска серверной части приложения при помощи Maven
     * */
    String getMavenCommandOrFileName();
    /**
     * Получить время ожидания сервера тестируемого приложения в секундах
     * */
    Long getTestedAppServerTimeout();
    /**
     * Получить стандартное время ожидания доступности элементов UI в секундах
     * */
    Duration getImplicitlyWaitSeconds();
    /**
     * Получить URL запускаемого локально сервера Appium с драйвером доступа к мобильным устройствам
     * */
    String getLocalAppiumServerUrl();
}
