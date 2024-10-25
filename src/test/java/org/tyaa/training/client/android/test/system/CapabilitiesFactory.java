package org.tyaa.training.client.android.test.system;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.tyaa.training.client.android.test.system.utils.FilePropertiesStore;
import org.tyaa.training.client.android.test.system.utils.interfaces.IPropertiesStore;

import java.net.MalformedURLException;
import java.net.URL;

public class CapabilitiesFactory {

    private static final IPropertiesStore properties = new FilePropertiesStore();

    public static UiAutomator2Options getAndroidOptions() {
        return new UiAutomator2Options()
                .setPlatformName(properties.getPlatformName())
                .setPlatformVersion(properties.getPlatformVersion())
                .setDeviceName(properties.getDeviceName())
                .setApp(properties.getApkPath())
                .setAutomationName(properties.getAutomationName())
                .setFullReset(true)
                .setAppWaitActivity("*");
    }

    public static URL getAppiumServerURL() {
        try {
            return new URL(properties.getLocalAppiumServerUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
