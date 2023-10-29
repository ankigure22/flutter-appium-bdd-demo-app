package com.appium.runners;

import com.appium.manager.PropertyManager;
import com.appium.utils.TestUtils;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.appium.manager.DriverManager;
import com.appium.manager.GlobalParams;
import com.appium.manager.ServerManager;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

import java.io.IOException;
import java.util.Properties;


public class RunnerBase extends TestUtils {
    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();
    TestUtils utils=new TestUtils();

    public RunnerBase()  {

    }


    private static void setRunner(TestNGCucumberRunner testNGCucumberRunner1) {
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }

    public static TestNGCucumberRunner getRunner() {

        return testNGCucumberRunner.get();
    }



  @Parameters({ "platformName", "udid", "deviceName" })
  @BeforeClass(alwaysRun = true)
    public void setUpClass(@Optional("Android") String platformName,
                           @Optional("emulator-5554") String udid,
                           @Optional("Pixel_3") String deviceName) throws Exception {
        /* This is for Log4J2 */
        ThreadContext.put("ROUTINGKEY", platformName + "_" + deviceName);
        utils.log().info("\n Executing @beforeclass");
        GlobalParams params=new GlobalParams();
        params.setPlatformName(platformName);
        params.setUDID(udid);
        params.setDeviceName(deviceName);

        new ServerManager().startServer();
        new DriverManager().initializeDriver();

        setRunner(new TestNGCucumberRunner(this.getClass()));
    }

    @DataProvider
    public Object[][] scenarios() {

        return getRunner().provideScenarios();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) throws Throwable {
        getRunner().runScenario(pickle.getPickle());
        String scenarioName = pickle.getPickle().getName();
    }


    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        utils.log().info("\n Executing teardownclass ");
        DriverManager driverManager = new DriverManager();
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();
        }
        if (testNGCucumberRunner != null) {
            getRunner().finish();
        }
    }
}
