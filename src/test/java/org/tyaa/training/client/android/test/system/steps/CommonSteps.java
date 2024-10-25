package org.tyaa.training.client.android.test.system.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import org.tyaa.training.client.android.test.system.MobileDriverFactory;
import org.tyaa.training.client.android.test.system.facades.AbstractFacade;
import org.tyaa.training.client.android.test.system.facades.CommonFacade;
import org.tyaa.training.client.android.test.system.pages.IHostPage;
import org.tyaa.training.client.android.test.system.utils.ValueWrapper;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonSteps {

    @Дано("Запущенное Android-приложение")
    public void openWebApplication() {
        MobileDriverFactory.getInstance().getDriver();
    }

    @Тогда("Отображается экран {string}")
    public void checkPage(String page) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new AbstractFacade(MobileDriverFactory.getInstance()){}.getPageInstanceByName(page);
    }

    @Тогда("Отображается экран {string} с содержимым {string}")
    public void checkPage(String hostPage, String contentPage) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ((IHostPage) new AbstractFacade(MobileDriverFactory.getInstance()){}.getPageInstanceByName(hostPage))
                .setContentPage(new AbstractFacade(MobileDriverFactory.getInstance()){}.getPageInstanceByName(contentPage));
    }

    @И("Отображается всплывающее уведомление с текстом {string}")
    public void checkToast(String expectedToastText) {
        ValueWrapper<String> actualToastTextWrapper = new ValueWrapper<>();
        new CommonFacade(MobileDriverFactory.getInstance()).getToastText(actualToastTextWrapper);
        assertEquals(expectedToastText, actualToastTextWrapper.getValue());
    }
}
