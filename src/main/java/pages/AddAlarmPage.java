/**
 * Created by aleksandr.kot on 4/26/18.
 */

package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import support.Time;

public class AddAlarmPage extends BasePage {

    @FindBy(xpath = "//XCUIElementTypePickerWheel[1]")
    private IOSElement hoursWheel;

    @FindBy(xpath = "//XCUIElementTypePickerWheel[2]")
    private IOSElement minutesWheel;

    @FindBy(xpath = "//XCUIElementTypePickerWheel[3]")
    private IOSElement timeFormatWheel;

    @FindBy(name = "ADD ALARM")
    private IOSElement addAlarmButton;

    @FindBy(name = "Ringtones")
    private IOSElement ringtonesCell;

    @FindBy(name = "Tasks")
    private IOSElement tasksCell;

    @FindBy(name = "Alarm Description")
    private IOSElement descriptionCell;

    @FindBy(xpath = "//XCUIElementTypeCell[1]")
    private IOSElement ringroneCell;

    @FindBy(name = "Face Recognition")
    private IOSElement faceRecognitionCell;

    @FindBy(name = "Add Alarm")
    private IOSElement backButton;

    @FindBy(xpath = "//XCUIElementTypePicker/XCUIElementTypePickerWheel")
    private IOSElement difficultyPicker;

    @FindBy(name = "Toolbar Done Button")
    private IOSElement toolbarDoneButton;

    public AddAlarmPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void setHoursWheel(String hours) {
        hoursWheel.sendKeys(hours);
    }

    public void setMinutesWheel(String minutes) {
        minutesWheel.sendKeys(minutes);
    }

    public void setTimeFormatWheel(String timeFormat) {
        timeFormatWheel.sendKeys(timeFormat);
    }

    public void setTimeWheel(Time time) {
        hoursWheel.sendKeys(time.getHours());
        minutesWheel.sendKeys(time.getMinutes());
        if (!time.getTimeFormat().equals("24h"))
            timeFormatWheel.sendKeys(time.getTimePeriod());
    }

    public void setDescription(String text) {
        descriptionCell.sendKeys(text);
        toolbarDoneButton.click();
    }

    public void clickAddAlarmButton() {
        addAlarmButton.click();

    }

    public void clickRingtonesCell() {
        ringtonesCell.click();
    }

    public void clickTasksCell() {
        tasksCell.click();
    }

    public void clickDoneButton() {
        toolbarDoneButton.click();
    }

    public void clickBackButton() {
        backButton.click();
    }

    public void selectRingtone() {
        ringroneCell.click();
    }

    public void selectTask(String name) {
        MobileElement cell = driver.findElement(By.name(name));
        cell.click();
        try {
            driver.findElement(By.name("OK")).click();
        } catch (NoSuchElementException e) {
            System.out.println("There's no request for the Camera permission");
        }
    }

    public void setDifficulty(String difficulty) {
        difficultyPicker.sendKeys(difficulty);
        toolbarDoneButton.click();
    }

    public String getMinutes() {
        return minutesWheel.getText();
    }

    public void incrementAlarmTimeByMinute() {
        int currentMinutes = Integer.valueOf(getMinutes().substring(0, Math.min(getMinutes().length(), 2))) + 1;
        String strCurrentMinutes;
        if (currentMinutes < 10) {
            strCurrentMinutes = "0" + String.valueOf(currentMinutes);
            setMinutesWheel(strCurrentMinutes);
        } else setMinutesWheel(String.valueOf(currentMinutes));
    }


}
