package org.tyaa.training.client.android.test.system.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Работа с портами TCP
 * */
public class PortChecker {

    /**
     * Проверка, занят ли указанный порт
     * @param port - номер порта
     * @return true - если порт занят, false - если порт свободен
     * */
    public static boolean isPortInUse(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true; // Если возникла ошибка, значит порт занят
        }
    }

    /**
     * Получение множества уникальных идентификаторов процессов (PID), занимающих указанный порт
     * @param port - номер порта
     * @return множество уникальных идентификаторов процессов (PID), использующих указанный порт
     * */
    public static Set<Integer> getPidsUsingPort(int port) {
        Set<Integer> pids = new HashSet<>();
        // Подготовить команду для получения списка идентификаторов процессов (PID), использующих указанный порт, в зависимости от операционной системы
        final String shell = System.getProperty("os.name").toLowerCase().contains("win") ? "cmd" : "bash";
        final String shellCommandParameter = System.getProperty("os.name").toLowerCase().contains("win") ? "/c" : "-c";
        final String command = System.getProperty("os.name").toLowerCase().contains("win") ?
                "netstat -ano | findstr :" + port :
                "lsof -t -i :" + port;
        System.out.printf("Для получения списка идентификаторов процессов (PID), использующих указанный порт, будет выполнена команда: %s\n", command);
        // Попытаться получить список идентификаторов процессов (PID), использующих указанный порт
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(shell, shellCommandParameter, command);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (errorReader.ready()) {
                String line = reader.readLine();
                if (line != null) {
                    System.out.println(line);
                } else {
                    break;
                }
            }
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.trim().split("\\s+");
                if (parts.length > 0) {
                    int pid = System.getProperty("os.name").toLowerCase().contains("win") ?
                            Integer.parseInt(parts[parts.length - 1]) : Integer.parseInt(parts[0]);
                    pids.add(pid);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pids;
    }

    /**
     * Завершение процесса по его идентификатору PID
     * @param pid - идентификатор процесса
     * @return true - если процесс удалось завершить, false - если не удалось
     * */
    public static boolean killProcess(int pid) {
        // Подготовить команду для завершения процесса в зависимости от операционной системы
        String command = System.getProperty("os.name").toLowerCase().contains("win") ?
                "taskkill /PID " + pid + " /F" :
                "kill -9 " + pid;
        System.out.printf("Для попытки завершения процесса будет выполнена команда: %s\n", command);
        // Запустить заверение процесса
        try {
            Process process = Runtime.getRuntime().exec(command);
            // Ожидание завершения процесса до истечения 10 секунд
            process.waitFor(10, TimeUnit.SECONDS);
            // Вернуть булевское значение результата завершения процесса
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
