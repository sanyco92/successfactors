package capabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.MobileCapabilityType.*;

public class Capabilities {

    private static String SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private URL serverURL;

    public Capabilities() throws MalformedURLException {
        this.serverURL = new URL(SERVER_URL);
    }


    public DesiredCapabilities getIOSCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/app/iOS");
//        File app = new File(appDir, "SmartAlarm.ipa");
        File app = new File(appDir, "SmartAlarm.app");
        capabilities.setCapability(APP, app.getAbsolutePath());
        capabilities.setCapability(PLATFORM_NAME, "iOS");
        capabilities.setCapability(PLATFORM_VERSION, "11.4");

        capabilities.setCapability(NO_RESET, "true");
        capabilities.setCapability("xcodeOrgId", "5D6P3P7DV7");
        capabilities.setCapability("xcodeSigningId", "iPhone Developer");
//        capabilities.setCapability(DEVICE_NAME, "iPhone SanyCo");
        capabilities.setCapability(DEVICE_NAME, "iPhone Simulator");
//        capabilities.setCapability(UDID, "1a8f568b4a1c63513623db75029691e4a027bac0");


//        capabilities.setCapability("bundleId", "com.sawasapps.smartalarm");
//        capabilities.setCapability(BROWSER_NAME, "Safari");
//        capabilities.setCapability("startIWDP", "true");

        return capabilities;
    }

    public DesiredCapabilities getAndroidCapabilities() {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/app/Android");
        File app = new File(appDir, "FasTip.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, "Android");
        capabilities.setCapability(DEVICE_NAME, "NotUsed");
        capabilities.setCapability(APP, app.getAbsolutePath());
        capabilities.setCapability("appPackage", "org.traeg.fastip");
        capabilities.setCapability("appActivity", "org.traeg.fastip.MainActivity");
        return capabilities;

    }


    public URL getServerURL() {
        return serverURL;
    }
}
