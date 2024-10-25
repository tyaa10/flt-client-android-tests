package org.tyaa.training.client.android.test.system.pages;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.tyaa.training.client.android.test.system.elements.Button;
import org.tyaa.training.client.android.test.system.elements.HyperReference;
import org.tyaa.training.client.android.test.system.elements.TextBlock;
import org.tyaa.training.client.android.test.system.elements.TextInput;
import org.tyaa.training.client.android.test.system.pages.annotations.PageModel;

@Getter
@PageModel(name = "вход")
public class SignInPage extends AbstractPage {

    @FindBy(xpath = "//android.widget.TextView[@text=\"Login\"]")
    private TextBlock titleFirstLineTextBlock;

    @FindBy(xpath = "//android.widget.TextView[@text=\"Please sign in to continue\"]")
    private TextBlock titleSecondLineTextBlock;

    @FindBy(id = "org.tyaa.training.client.android:id/activitySignIn_login_TextInputEditText")
    private TextInput usernameTextInput;

    @FindBy(id = "org.tyaa.training.client.android:id/activitySignIn_password_TextInputEditText")
    private TextInput passwordTextInput;

    @FindBy(id = "org.tyaa.training.client.android:id/activitySignIn_signIn_Button")
    private Button submitButton;

    @FindBy(xpath = "//android.widget.TextView[@text=\"Please sign in to continue\"]")
    private TextBlock goToSignUpTextBlock;

    @FindBy(id = "org.tyaa.training.client.android:id/activitySignIn_goToSignUp_Button")
    private HyperReference goToSignUpHyperReference;

    public SignInPage(AppiumDriver driver) {
        super(driver);
    }
}
