import capabilities.Capabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import support.Time;

import java.net.MalformedURLException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BaseTest {
    static AppiumDriver<MobileElement> driver;
    static WebDriverWait wait;
    public Platform platform = Platform.IOS;
    public Device device = Device.SIMULATOR;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        if (platform.equals(Platform.IOS)) {

            Capabilities capabilities = new Capabilities();
            driver = new IOSDriver<MobileElement>(capabilities.getServerURL(), capabilities.getIOSCapabilities());

        } else if (platform.equals(Platform.ANDROID)) {
            Capabilities capabilities = new Capabilities();
            driver = new AndroidDriver<MobileElement>(capabilities.getServerURL(), capabilities.getAndroidCapabilities());
        }
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        try {
            driver.findElement(By.name("Allow")).click();
        } catch (NoSuchElementException e) {
            System.out.println("\nSorry, there's no Push Notifications dialog.");
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    public Time generateTime() {
        Time alarmTime;
        if (device.equals(Device.REAL)) {
            alarmTime = new Time("24h");
        } else {
            alarmTime = new Time("12h");
        }
        return alarmTime;
    }

    enum Platform {
        IOS,
        ANDROID
    }

    enum Device {
        REAL,
        SIMULATOR
    }
}