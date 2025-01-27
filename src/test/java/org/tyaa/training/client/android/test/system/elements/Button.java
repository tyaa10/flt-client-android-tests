package org.tyaa.training.client.android.test.system.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class Button extends AbstractElement {

    public Button(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    /* Выполнить клик по текущему элементу,
     * и если элемент был перекрыт другим элементом
     * или обновлялся - повторять попытку снова, пока не получится выполнить клик */
    public void safeClick(Duration timeOutInSeconds) {
        safeAction(() -> element.click(), timeOutInSeconds);
    }

    /* Выполнить клик по текущему элементу,
     * затем ожидать максимум до timeOutInSeconds секунд,
     * пока не исчезнет элемент с селектором locatorToWaitForDisappear */
    public void safeClickThenWaitForDisappear(By locatorToWaitForDisappear, Duration timeOutInSeconds) {
        performAndWaitForDisappear(
                driver,
                () -> this.safeClick(timeOutInSeconds),
                locatorToWaitForDisappear,
                timeOutInSeconds
        );
    }
}
