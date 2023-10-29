package com.appium.manager;

import com.appium.utils.TestUtils;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CapabilitiesManager {
 TestUtils utils = new TestUtils();


 public DesiredCapabilities getCaps() throws IOException {
        GlobalParams params = new GlobalParams();
        Properties props = new PropertyManager().getProps();

        try {
            utils.log().info("getting capabilities");
            DesiredCapabilities flutterCapabilities = new DesiredCapabilities();
            flutterCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, params.getPlatformName());
            flutterCapabilities.setCapability(MobileCapabilityType.UDID, params.getUDID());
            flutterCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, params.getDeviceName());
            flutterCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
            flutterCapabilities.setCapability("noReset", "true");
            flutterCapabilities.setCapability("skipUnlock","true");
            flutterCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
            flutterCapabilities.setCapability("newCommandTimeout",300);
            String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                  + File.separator + "resources" + File.separator + "app" + File.separator
                  + "flutterlogin-debug.apk";

            utils.log().info("appUrl is" + androidAppUrl);
            return flutterCapabilities;
        } catch (Exception e) {
            e.printStackTrace();
            utils.log().fatal("Failed to load capabilities. ABORT!!" + e.toString());
            throw e;
        }
    }
}
