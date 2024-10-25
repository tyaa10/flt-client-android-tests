package org.tyaa.training.client.android.test.system.facades;

import org.tyaa.training.client.android.test.system.MobileDriverFactory;
import org.tyaa.training.client.android.test.system.pages.SignInPage;
import org.tyaa.training.client.android.test.system.utils.FilePropertiesStore;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

public class SignInFacade extends AbstractFacade {

    private static final IPropertiesStore properties = new FilePropertiesStore();

    public SignInFacade(MobileDriverFactory mobileDriverFactory) {
        super(mobileDriverFactory);
    }

    public SignInFacade signIn(String login, String password) {
        final SignInPage signInPage = new SignInPage(mobileDriverFactory.getDriver());
        signInPage.getUsernameTextInput().safeSendKeys(properties.getImplicitlyWaitSeconds(), login);
        signInPage.getPasswordTextInput().safeSendKeys(properties.getImplicitlyWaitSeconds(), password);
        signInPage.getSubmitButton().safeClick(properties.getImplicitlyWaitSeconds());
        return this;
    }
}
