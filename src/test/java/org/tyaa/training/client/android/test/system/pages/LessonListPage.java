package org.tyaa.training.client.android.test.system.pages;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.tyaa.training.client.android.test.system.elements.Button;
import org.tyaa.training.client.android.test.system.elements.TextBlock;
import org.tyaa.training.client.android.test.system.pages.annotations.PageModel;

@Getter
@PageModel(name = "список уроков")
public class LessonListPage extends AbstractPage {

    @FindBy(id = "org.tyaa.training.client.android:id/activityMain_fragmentEducationProcessLessonList_title_TextView")
    private TextBlock titleTextBlock;

    public LessonListPage(AppiumDriver driver) {
        super(driver);
    }
}
