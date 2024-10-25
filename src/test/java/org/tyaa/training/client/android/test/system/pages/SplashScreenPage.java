package org.tyaa.training.client.android.test.system.pages;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.tyaa.training.client.android.test.system.elements.TextBlock;
import org.tyaa.training.client.android.test.system.elements.annotations.HiddenOrUnstable;
import org.tyaa.training.client.android.test.system.pages.annotations.PageModel;

@Getter
@PageModel(name = "заставка")
public class SplashScreenPage extends AbstractPage {

    @HiddenOrUnstable
    @FindBy(xpath = "//android.widget.TextView[@text=\"Foreign Language Training\"]")
    private TextBlock titleFirstLineTextBlock;

    @HiddenOrUnstable
    @FindBy(xpath = "//android.widget.TextView[@text=\"Android\"]")
    private TextBlock titleSecondLineTextBlock;

    public SplashScreenPage(AppiumDriver driver) {
        super(driver);
    }
}
