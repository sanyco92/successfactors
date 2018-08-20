import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.AddAlarmPage;
import pages.AlarmStartedPage;
import pages.AlarmsPage;
import support.Time;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
        alarmsPage.verifyAlarmCreated(alarmTime);
    }

    @Test
    public void removeAlarm() throws IOException, InterruptedException {
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        int alarmsCountBefore = alarmsPage.getAlarmsCount();
        alarmsPage.deleteAlarm(alarmsCountBefore);
        int alarmsCountAfter = alarmsPage.getAlarmsCount();
        assertEquals((alarmsCountBefore - 1), alarmsCountAfter);
//        alarmsPage.verifyNoAlarmsLabel();
    }

    @Test
    public void startAndCancelAlarm() throws IOException, InterruptedException {
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        alarmCreation();
        alarmsPage.clickStartButton();
        AlarmStartedPage alarmStartedPage = new AlarmStartedPage(driver);
        alarmStartedPage.swipeCancelSlider();
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

    @Test
    public void startAlarmAndWaitForAlarmFire() throws IOException, InterruptedException, ScriptException {
        String description = generateText();
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        alarmsPage.clickAddAlarm();
        AddAlarmPage addAlarmPage = new AddAlarmPage(driver);
        int currentMinutes = Integer.valueOf(addAlarmPage.getMinutes().substring(0, Math.min(addAlarmPage.getMinutes().length(), 2))) + 1;
        String strCurrentMinutes;
        if (currentMinutes < 10) {
            strCurrentMinutes = "0" + String.valueOf(currentMinutes);
            addAlarmPage.setMinutesWheel(strCurrentMinutes);
        }
        else addAlarmPage.setMinutesWheel(String.valueOf(currentMinutes));
        addAlarmPage.clickRingtonesCell();
        addAlarmPage.selectRingtone();
        addAlarmPage.clickBackButton();
        addAlarmPage.clickTasksCell();
        addAlarmPage.selectTask("Math Equation");
        addAlarmPage.setDifficulty("Easy");

        addAlarmPage.clickBackButton();
        addAlarmPage.clickAddAlarmButton();
        alarmsPage.clickStartButton();
        AlarmStartedPage alarmStartedPage = new AlarmStartedPage(driver);
        Thread.sleep(60000);
        System.out.println("Alarm Fire!");
        alarmStartedPage.swipePerformSlider();
        String equation = driver.findElement(By.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[2]")).getText();
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        System.out.println(engine.eval(equation));
        driver.findElement(By.name("Answer")).sendKeys(String.valueOf(engine.eval(equation)));
        addAlarmPage.clickDoneButton();
        driver.findElement(By.name("All Tasks Completed!")).isDisplayed();

}

    public String generateText() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

}
