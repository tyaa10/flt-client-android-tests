package org.tyaa.training.client.android.test.system.steps;

import io.cucumber.java.ru.Когда;
import org.tyaa.training.client.android.test.system.MobileDriverFactory;
import org.tyaa.training.client.android.test.system.facades.SignInFacade;

public class SignInSteps {

    @Когда("Пользователь выполняет попытку входа с логином {string} и паролем {string}")
    public void login(String login, String password) {
        new SignInFacade(MobileDriverFactory.getInstance()).signIn(login, password);
    }
}
