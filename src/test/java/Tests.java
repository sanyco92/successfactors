import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import pages.AddAlarmPage;
import pages.AlarmStartedPage;
import pages.AlarmsPage;
import support.Time;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class Tests extends BaseTest {

    @Test
    public void alarmCreation() throws IOException, InterruptedException {
        Time alarmTime = generateTime();
        String description = generateText();
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        alarmsPage.clickAddAlarm();
        AddAlarmPage addAlarmPage = new AddAlarmPage(driver);
        addAlarmPage.setTimeWheel(alarmTime);
        addAlarmPage.clickRingtonesCell();
        addAlarmPage.selectRingtone();
        addAlarmPage.clickBackButton();
        addAlarmPage.clickTasksCell();
        addAlarmPage.selectTask("Face Recognition");
        addAlarmPage.selectTask("Scan Barcode");
        addAlarmPage.clickBackButton();
        addAlarmPage.setDescription(description);
        addAlarmPage.clickAddAlarmButton();
        alarmsPage.verifyNoAd();
        alarmsPage.verifyAlarmCreated(alarmTime);
    }

    @Test
    public void removeAlarm() throws IOException, InterruptedException {
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        int alarmsCount = alarmsPage.getAlarmsCount();
        alarmsPage.deleteAlarm(alarmsCount);
        alarmsPage.verifyNoAlarmsLabel();
    }

    @Test
    public void startAndCancelAlarm() throws IOException, InterruptedException {
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        alarmCreation();
        alarmsPage.clickStartButton();
        alarmsPage.verifyNoAd();
        AlarmStartedPage alarmStartedPage = new AlarmStartedPage(driver);
        alarmStartedPage.swipeSlider();
        alarmsPage.verifyStartButtonDisplayed();
    }

    @Test
    public void switchAlarmsState() throws IOException, InterruptedException {
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        int alarmsCount = alarmsPage.getAlarmsCount();
        alarmsPage.enableAllAlarms(alarmsCount);
        assertEquals(alarmsPage.verifyStartButtonClickable(), true);
        alarmsPage.disableAllAlarms(alarmsCount);
        assertEquals(alarmsPage.verifyStartButtonClickable(), false);
    }

    public String generateText() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

}
