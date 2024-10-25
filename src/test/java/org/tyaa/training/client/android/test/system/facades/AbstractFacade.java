package org.tyaa.training.client.android.test.system.facades;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.tyaa.training.client.android.test.system.MobileDriverFactory;
import org.tyaa.training.client.android.test.system.pages.AbstractPage;
import org.tyaa.training.client.android.test.system.pages.annotations.PageModel;
import org.tyaa.training.client.android.test.system.utils.ReflectionActions;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public abstract class AbstractFacade {

    protected final MobileDriverFactory mobileDriverFactory;

    public AbstractFacade(MobileDriverFactory mobileDriverFactory) {
        this.mobileDriverFactory = mobileDriverFactory;
    }

    public AbstractPage getPageInstance() {
        return new AbstractPage(mobileDriverFactory.getDriver()) {};
    }

    public AbstractFacade close() {
        mobileDriverFactory.closeDriver();
        return this;
    }

    public AbstractPage getPageInstanceByName(String name)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        Set<Class> classes = ReflectionActions.getAllClassesFromPackage("org.tyaa.training.client.android.test.system.pages");
        return (AbstractPage) classes.stream()
                .filter(page -> page.isAnnotationPresent(PageModel.class))
                .filter(page -> {
                    try {
                        return PageModel.class.getDeclaredMethod("name")
                                .invoke(page.getAnnotation(PageModel.class), null).equals(name);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }).findFirst()
                .get()
                .getConstructor(AppiumDriver.class)
                .newInstance(mobileDriverFactory.getDriver());
    }
}
