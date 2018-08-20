/**
 * Created by aleksandr.kot on 4/26/18.
 */

package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.FindBy;

public class AlarmStartedPage extends BasePage {

    @FindBy(name = "ic_sleep_close")
    private IOSElement cancelSliderButton;

    @FindBy(name = "ic_sleep_checkmark")
    private IOSElement performSliderButton;

    public AlarmStartedPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void swipeCancelSlider() {
        TouchAction action = new TouchAction(driver);
        action.tap(cancelSliderButton).waitAction(3000).moveTo(
                cancelSliderButton.getCoordinates().inViewPort().getX() + 250,
                cancelSliderButton.getCoordinates().inViewPort().getY()).perform();
    }

    public void swipePerformSlider() {
        TouchAction action = new TouchAction(driver);
        action.tap(performSliderButton).waitAction(3000).moveTo(
                performSliderButton.getCoordinates().inViewPort().getX() + 250,
                performSliderButton.getCoordinates().inViewPort().getY()).perform();
    }
}
