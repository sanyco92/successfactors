/**
 * Created by aleksandr.kot on 4/26/18.
 */

package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.FindBy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PerformTasksPage extends BasePage {

    @FindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[2]")
    private IOSElement equation;

    @FindBy(name = "Answer")
    private IOSElement answerField;

    public PerformTasksPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public String getEquationText() {
        return equation.getText();
    }

    public String solveEquation(String equation) throws ScriptException {
        String answer;
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        System.out.println("The answer is " + engine.eval(equation));
        answer = String.valueOf(engine.eval(equation));
        return answer;
    }

    public void enterAnswer(String answer) {
        answerField.sendKeys(answer);
    }
}
