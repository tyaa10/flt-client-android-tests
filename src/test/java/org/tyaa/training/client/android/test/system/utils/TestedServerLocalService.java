package org.tyaa.training.client.android.test.system.utils;

import org.openqa.selenium.os.ExternalProcess;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import static org.tyaa.training.client.android.test.system.utils.PortChecker.*;

/**
 * Управление тестируемым сервером
 * */
public final class TestedServerLocalService {

    private static final Duration DESTROY_TIMEOUT = Duration.ofSeconds(60L);
    private static final IPropertiesStore properties = new FilePropertiesStore();

    private final String serverAppDirectoryPath;
    private final ReentrantLock lock = new ReentrantLock(true);

    private ExternalProcess process;

    public TestedServerLocalService(String serverAppDirectoryPath) {
        this.serverAppDirectoryPath = serverAppDirectoryPath;
    }

    /**
     * Проверка наличия процесса серверной части тестируемого приложения, использующего указанный порт
     * */
    public boolean isRunning() {

        this.lock.lock();
        boolean isRunning;
        try {
            isRunning = this.process != null && this.process.isAlive();
        } finally {
            this.lock.unlock();
        }
        return isRunning;
    }

    /**
     * Попытка начать запуск процесса серверной части тестируемого приложения
     * @param port номер порта, на котором ожидается прослушивание http-запросов серверной частью тестируемого приложения
     * */
    public void start(int port) {
        System.out.printf("Попытка запуска серверной части тестируемого приложения с расчётом на прослушивание им порта %d\n", port);
        // Заблокировать доступ к следующему ниже коду из других потоков выполнения
        this.lock.lock();
        try {
            // Если процесс уже запущен - завершаем работу запускающего метода
            if (this.isRunning()) {
                System.out.println("Процесс серверной части тестируемого приложения уже запущен");
                return;
            }
            // Обеспечение свободного порта с указанным номером
            ensureFreePort(port);
            // Построение объекта настроек для запуска серверной части тестируемого приложения
            ExternalProcess.Builder processBuilder =
                    ExternalProcess.builder()
                            .command(Path.of(this.serverAppDirectoryPath, properties.getMavenCommandOrFileName()).toString(), "spring-boot:run")
                            .directory(this.serverAppDirectoryPath)
                            .copyOutputTo(System.out);
            // Если объект настроек для запуска серверной части тестируемого приложения не удалось создать - выбросить исключение
            Objects.requireNonNull(processBuilder);
            // Запуск процесса серверной части тестируемого приложения
            this.process = processBuilder.start();
        } finally {
            // Разблокировать доступ к следующему ниже коду из других потоков выполнения
            this.lock.unlock();
        }
    }

    /**
     * Попытка начать завершение процесса серверной части тестируемого приложения
     * */
    public void stop() {
        // Заблокировать доступ к следующему ниже коду из других потоков выполнения
        this.lock.lock();
        try {
            // Если процесс запущен - начать его завершение
            if (this.process != null) {
                this.destroyProcess();
            }
            this.process = null;
        } finally {
            // Разблокировать доступ к следующему ниже коду из других потоков выполнения
            this.lock.unlock();
        }
    }

    /**
     * Обеспечение свободного порта с указанным номером
     * @param port номер порта
     * */
    private void ensureFreePort(Integer port) {
        // Если порт занят
        if (isPortInUse(port)) {
            // Найти PID процессов, использующих указанный порт
            System.out.println("Порт " + port + " занят. Попытка завершить процесс...");
            // Получить множества уникальных PID процессов, использующих указанный порт
            Set<Integer> pids = getPidsUsingPort(port);
            // Запустить завершение процессов, использующих указанный порт
            for (Integer pid : pids) {
                boolean success = killProcess(pid);
                System.out.println("Процесс с PID " + pid + (success ? " был успешно завершён." : " не удалось завершить."));
            }
        } else {
            System.out.println("Порт " + port + " свободен.");
        }
    }

    /**
     * Запуск завершения процесса серверной части тестируемого приложения
     * */
    private void destroyProcess() {
        // Если процесс запущен - завершить его
        if (this.process != null && this.process.isAlive()) {
            this.process.shutdown(DESTROY_TIMEOUT);
        }
    }
}
