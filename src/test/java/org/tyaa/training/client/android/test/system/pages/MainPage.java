package org.tyaa.training.client.android.test.system.pages;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.support.FindBy;
import org.tyaa.training.client.android.test.system.elements.Button;
import org.tyaa.training.client.android.test.system.pages.annotations.PageModel;

@Getter
@PageModel(name = "главный")
public class MainPage extends AbstractPage implements IHostPage {

    @FindBy(id = "org.tyaa.training.client.android:id/activityMain_signOut_Button")
    private Button signOutButton;

    @Setter
    private AbstractPage contentPage;

    public MainPage(AppiumDriver driver) {
        super(driver);
    }
}
