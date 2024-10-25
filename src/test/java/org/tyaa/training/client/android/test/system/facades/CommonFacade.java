package org.tyaa.training.client.android.test.system.facades;

import org.openqa.selenium.By;
import org.tyaa.training.client.android.test.system.MobileDriverFactory;
import org.tyaa.training.client.android.test.system.pages.SignInPage;
import org.tyaa.training.client.android.test.system.utils.FilePropertiesStore;
import org.tyaa.training.client.android.test.system.utils.ValueWrapper;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.util.Optional;

import static java.lang.String.format;

public class CommonFacade extends AbstractFacade {

    public CommonFacade(MobileDriverFactory mobileDriverFactory) {
        super(mobileDriverFactory);
    }

    public CommonFacade getToastText(ValueWrapper<String> resultWrapper) {
        resultWrapper.setValue(
                mobileDriverFactory.getDriver()
                        .findElement(By.xpath("//android.widget.Toast"))
                        .getText()
        );
        return this;
    }
}
