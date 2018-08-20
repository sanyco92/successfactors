/**
 * Created by aleksandr.kot on 4/26/18.
 */

package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import support.Time;

import java.util.List;

import static org.testng.Assert.assertEquals;

//import org.openqa.selenium.WebDriver;


public class AlarmsPage extends BasePage {

    @FindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Alarms\"]/XCUIElementTypeButton[1]")
    private IOSElement settingsButton;

    @FindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Alarms\"]/XCUIElementTypeButton[2]")
    private IOSElement addAlarmButton;

    @FindBy(xpath = "(//XCUIElementTypeButton[@name=\"Delete\"])")
    private IOSElement deleteButton;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name='No Alarms']")
    private IOSElement noAlarmsLabel;

    @FindBy(name = "START")
    private IOSElement startButton;

    @FindAll({@FindBy(xpath = "//XCUIElementTypeButton[@name=\"ic clock disabled\"]")})
    private List<IOSElement> alarmStateElements;

    public AlarmsPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }


    public void clickSettings() {
        settingsButton.click();
    }

    public void clickAddAlarm() {
        wait.until(ExpectedConditions.elementToBeClickable(addAlarmButton));
        addAlarmButton.click();
    }

    public void clickStartButton() {
        startButton.click();
    }

    public boolean verifyStartButtonClickable() {
        System.out.println("State of START button is = " + startButton.isEnabled());
        return startButton.isEnabled();
    }

    public void clickAlarmState(int index) {
        alarmStateElements.get(index).click();
    }

    public String getAlarmState(int index) {
        return alarmStateElements.get(index).getText();
    }

    public void enableAllAlarms(int alarmsCount) {
        int i = 0;
        while (i < alarmsCount) {
            if (getAlarmState(i).equals("ic clock disabled")) {
                clickAlarmState(i);
                System.out.println("State of alarm in cell with index " + (i + 1) + " changed to active");
                i++;
            } else {
                System.out.println("State of alarm in cell with index " + (i + 1) + " is active already");
                i++;
            }
        }
    }

    public void disableAllAlarms(int alarmsCount) {
        int i = 0;
        while (i < alarmsCount) {
            if (getAlarmState(i).equals("1")) {
                clickAlarmState(i);
                System.out.println("Alarm in cell with index " + (i + 1) + " deactivated");
                i++;
            } else {
                System.out.println("Alarm in cell with index  " + (i + 1) + " deactivated already");
                i++;
            }
        }
    }

    public void deleteAlarm(int number) {
        TouchAction action = new TouchAction(driver);
        try {
            MobileElement alarmCellArrowIcon = driver.findElement(By.xpath("(//XCUIElementTypeImage[@name=\"ic_next_arrow\"])[" + number + "]"));
            MobileElement alarmCellCenter = driver.findElement(By.xpath("//XCUIElementTypeCell[" + number + "]"));
            action.tap(alarmCellArrowIcon).waitAction(100).moveTo(alarmCellCenter).perform();
            deleteButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("Nothing to delete");
        }

    }

    public void verifyNoAlarmsLabel() {
        noAlarmsLabel.isDisplayed();
        Assert.assertEquals(noAlarmsLabel.getText(), "No Alarms");
    }

    public int getAlarmsCount() {
        return driver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell")).size();
    }

    public void verifyNoAd() {
        try {
            MobileElement closeAD = driver.findElement(By.name("Close Advertisement"));
            closeAD.click();
        } catch (NoSuchElementException e) {
            System.out.println("Advertisement is not shown");
        }
    }

    public void verifyAlarmCreated(Time alarmTime) {
        if (!alarmTime.getTimeFormat().equals("24h"))
            assertEquals(driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"" +
                    alarmTime.getHours() + ":" + alarmTime.getMinutes() +
                    " " + alarmTime.getTimePeriod() + "\"]"))
                    .getText(), alarmTime.getHours() + ":" + alarmTime.getMinutes() + " " + alarmTime.getTimePeriod());
        else assertEquals(driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"" +
                alarmTime.getHours() + ":" + alarmTime.getMinutes() + "\"]"))
                .getText(), alarmTime.getHours() + ":" + alarmTime.getMinutes());
    }

    public void verifyStartButtonDisplayed() {
        startButton.isDisplayed();
        assertEquals(startButton.getText(), "START");
    }

}
