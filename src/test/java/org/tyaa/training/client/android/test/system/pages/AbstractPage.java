package org.tyaa.training.client.android.test.system.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;
import org.tyaa.training.client.android.test.system.CustomUIElementFieldDecorator;
import org.tyaa.training.client.android.test.system.pages.annotations.PageModel;

@PageModel
public abstract class AbstractPage {

    protected AppiumDriver driver;

    public AbstractPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new CustomUIElementFieldDecorator(driver), this);
    }
}
