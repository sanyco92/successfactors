import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.AddAlarmPage;
import pages.AlarmStartedPage;
import pages.AlarmsPage;
import pages.PerformTasksPage;
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
        AlarmsPage alarmsPage = new AlarmsPage(driver);
        alarmsPage.clickAddAlarm();
        AddAlarmPage addAlarmPage = new AddAlarmPage(driver);
        addAlarmPage.incrementAlarmTimeByMinute();
        addAlarmPage.clickRingtonesCell();
        addAlarmPage.selectRingtone();
        addAlarmPage.clickBackButton();
        addAlarmPage.clickTasksCell();
        addAlarmPage.selectTask("Math Equation");
        addAlarmPage.setDifficulty("Easy");
        addAlarmPage.clickBackButton();
        addAlarmPage.clickAddAlarmButton();
        verifyNoAd();
        alarmsPage.clickStartButton();
        AlarmStartedPage alarmStartedPage = new AlarmStartedPage(driver);
        alarmStartedPage.swipePerformSlider();
        PerformTasksPage performTasksPage = new PerformTasksPage(driver);
        String equation = performTasksPage.getEquationText();
        String result = performTasksPage.solveEquation(equation);
        performTasksPage.enterAnswer(result);
        addAlarmPage.clickDoneButton();
        driver.findElement(By.name("All Tasks Completed!")).isDisplayed();
        Thread.sleep(3000);

    }

    public String generateText() {
        int length = 10;
        return RandomStringUtils.random(length, true, false);
    }

    public void verifyNoAd() {
        try {
            Thread.sleep(2000);
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Close Advertisement")));
            MobileElement closeAD = driver.findElement(By.name("Close Advertisement"));
            closeAD.click();
        } catch (NoSuchElementException e) {
            System.out.println("Advertisement is not shown");
        } catch (TimeoutException e) {
            System.out.println("There's no AD");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
