package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.ExtentReport.fail;

public class UiActions {




    public static void click(WebDriver driver, By elementLocator) {
        //1- locate the element
        locateElement(driver, elementLocator);
        try {
            // 2- wait for element to be clickable
            new WebDriverWait(driver, Duration.ofSeconds(4)).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (TimeoutException toe) {
            Logger.logMessage(toe.getMessage());
            fail(toe.getMessage());
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        //3- Log the action to the report
        logElementAction(driver, "Click on", elementLocator);

        //4-
        try {
            //5- Hover on the element to simulate user
            new Actions(driver).moveToElement(driver.findElement(elementLocator)).perform();
            //6.1- now click using native selenuim click
            driver.findElement(elementLocator).click();

        }
        catch (Exception e1){
            // 6.2- in case of the native click failed unfortunately will handle this by js click
            try {

                ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();",
                        driver.findElement(elementLocator));

            }catch (Exception e2) {
                //7-log errors
                Logger.logMessage(e1.getMessage());
                Logger.logMessage(e2.getMessage());
                fail("Couldn't click on the element:" + elementLocator);

            }
        }

    }

    public static void type(WebDriver driver, By elementLocator, String text) {
        locateElement(driver, elementLocator);
        try {
            Logger.logMessage("[UI Action] Clear and Type [" + text + "] on element [" + elementLocator + "]");
            //clear the text field
            driver.findElement(elementLocator).clear();
            //then type
            driver.findElement(elementLocator).sendKeys(text);
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }


    public static void locateElement(WebDriver driver, By elementLocator){
        // 1- wait for element  ot be visible
        //2-scroll to the element
        try {
            // Wait for the element to be visible
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            // Scroll the element into view to handle some browsers cases
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
                    driver.findElement(elementLocator));
            // Check if the element is displayed
            if (!driver.findElement(elementLocator).isDisplayed()) {
                Logger.logMessage("The element [" + elementLocator.toString() + "] is not Displayed");
                fail("The element [" + elementLocator.toString() + "] is not Displayed");
            }
        } catch (TimeoutException toe) {
            Logger.logMessage(toe.getMessage());
            fail(toe.getMessage());
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }
    public static void scrollToElement(WebDriver driver, By elementLocator){
        //scroll to the element
        try {
            //wait for the element to be in dom before scroll
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            // Scroll the element into view to handle some browsers cases
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
                    driver.findElement(elementLocator));
            // Check if the element is displayed
        } catch (TimeoutException toe) {
            Logger.logMessage(toe.getMessage());
            fail(toe.getMessage());
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }


    private static void logElementAction(WebDriver driver, String action, By elementLocator) {
        String elementName = driver.findElement(elementLocator).getText();
        if ((elementName != null && !elementName.isEmpty())) {
            Logger.logMessage("[UI Action] " + action + " [" + elementName + "] element");
        } else {
            Logger.logMessage("[UI Action] " + action + " [" + elementLocator + "] element");
        }

    }




}
