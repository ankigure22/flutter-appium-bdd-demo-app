package com.appium.stepDefinitions;

import com.appium.manager.DriverManager;
import com.appium.pages.LoginPage;
import com.appium.utils.TestUtils;
import com.testsigma.flutter.FlutterElement;
import com.testsigma.flutter.FlutterFinder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Set;

public class LoginStepDef {
    private AppiumDriver<?> driver;
    TestUtils utils = new TestUtils();
    public LoginStepDef(){
        this.driver = new DriverManager().getDriver();
    }
    public void switchContext(String context) {
        driver.getContext();
        Set<String> con = driver.getContextHandles();
        for (String c : con) {
            if (c.contains(context)) {
                driver.context(c);
                break;
            }
        }
    }

    @When("I enter username as {string}")
    public void iEnterUsernameAs(String username) throws InterruptedException {
        switchContext("FLUTTER");
        utils.log().info("FLUTTER Switch Context");
        utils.log().info("FLUTTER Switch Context passed");
        // switchContext("NATIVE_APP");
        FlutterFinder find = new FlutterFinder(driver);
        Thread.sleep(3000);

        FlutterElement txt_username = find.byValueKey("txt_username");
        FlutterElement txt_password = find.byValueKey("txt_password");
        FlutterElement button_login = find.byValueKey("button_login");


        txt_username.sendKeys("user@yopmailcom");
        txt_password.sendKeys("123456");
        button_login.click();
        utils.log().info("Flutter data input & Click test");
        utils.log().info("Flutter data input & Click test passed");
        Thread.sleep(5000);
    }

    @When("I enter password as {string}")
    public void iEnterPasswordAs(String password) {
        new LoginPage().enterPassword(password);
    }

    @When("I login")
    public void iLogin() {
        new LoginPage().pressLoginBtn();
    }

    /* @Then -> Assertions should be done here */
    @Then("login should fail with an error {string}")
    public void loginShouldFailWithAnError(String expectedError) {
        Assert.assertEquals(new LoginPage().getErrTxt(), expectedError);
    }

}
