package org.tyaa.training.client.android.test.system;

import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.tyaa.training.client.android.test.system.elements.annotations.HiddenOrUnstable;
import org.tyaa.training.client.android.test.system.utils.FilePropertiesStore;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.lang.reflect.Field;

import static java.lang.String.format;

public class CustomUIElementFieldDecorator extends DefaultFieldDecorator {

    private final WebDriver driver;
    private static final IPropertiesStore properties = new FilePropertiesStore();

    public CustomUIElementFieldDecorator(SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
        this.driver = (WebDriver) searchContext;
    }
    /**
     * Переопределение метода, который вызывается стандартным IoC-контейнером - декоратором для каждого поля в классе
     */
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        // определить, требуется ли дополнительное нестандартное декорирование поля,
        // и если да - получить описание типа этого поля,
        // иначе - получить значение null
        Class<?> decoratableClass = decoratableClass(field);
        // если класс поля требует дополнительного нестандартного декорирования
        if (decoratableClass != null) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }
            // найти в DOM-документе элемент по заданному локатору
            WebElement element = (WebElement) createElement(loader, locator, decoratableClass);
            // проверить, отображается ли элемент, если он не является скрытым или нестабильным
            checkElementIsDisplayed(field, element);
            return createElement(loader, locator, decoratableClass);
        } else {
            // иначе - передать описание поля стандартному IoC-контейнеру - декоратору
            WebElement element = (WebElement) super.decorate(loader, field);
            if (element != null) {
                // проверить, отображается ли элемент, если он не является скрытым или нестабильным
                checkElementIsDisplayed(field, element);
            }
            return element;
        }
    }

    /**
     * Проверка, отображается ли элемент
     * @param element - элемент, отображение которого ожидается
     * */
    void checkElementIsDisplayed(Field field, WebElement element) {
        if (!isHiddenOrUnstable(field)) {
            boolean displayed = element.isDisplayed();
            if (!displayed) {
                throw new AssertionError(format("Element %s is not displayed", element));
            }
        }
    }

    /**
     * Возвращает булевское значение, отвечающее на вопрос,
     * описывает ли поле скрытый или нестабильный элемент в DOM
     */
    protected Boolean isHiddenOrUnstable(Field field) {
        // если в описании поля найдена аннотация HiddenOrUnstable - вернуть true, иначе false
        return field.isAnnotationPresent(HiddenOrUnstable.class);
    }

    /**
     * Возвращает описание класса декорируемого поля,
     * либо null если класс не подходит для декоратора
     */
    protected Class<?> decoratableClass(Field field) {
        // получить описание типа поля
        Class<?> clazz = field.getType();
        // попытаться найти класс конструктора
        try {
            clazz.getConstructor(WebDriver.class, WebElement.class);
        } catch (Exception e) {
            // если указанный конструктор не найден - возвращаем null
            return null;
        }
        // если указанный конструктор найден - возвращаем описание типа поля
        return clazz;
    }

    /**
     * Создание элемента.
     * Находит WebElement и передает его в кастомный класс
     */
    protected <T> T createElement(
            ClassLoader loader,
            ElementLocator locator,
            Class<T> clazz
    ) {
        WebElement proxy = proxyForLocator(loader, locator);
        return createInstance(clazz, proxy);
    }

    /**
     * Создает экземпляр класса,
     * вызывая конструктор с аргументом WebElement
     */
    private <T> T createInstance(Class<T> clazz, WebElement element) {
        try {
            return (T) clazz.getConstructor(WebDriver.class, WebElement.class)
                    .newInstance(driver, element);
        } catch (Exception e) {
            throw new AssertionError(
                    "UIElement can't be represented as " + clazz
            );
        }
    }
}
