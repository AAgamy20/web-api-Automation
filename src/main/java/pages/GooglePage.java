package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.UiActions;

import java.util.ArrayList;
import java.util.List;

public class GooglePage {

    WebDriver driver;

    By searchTxtField = By.name("q");
    By searchResultLinks = By.xpath("//div[@class='yuRUbf']/a");


    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String keyword) {
        UiActions.type(driver, searchTxtField, keyword);
        driver.findElement(searchTxtField).sendKeys(Keys.RETURN);
    }

    public List getWuzzfLinks() {
        List<String> wuzzufLinks = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            List<WebElement> searchResults = driver.findElements(searchResultLinks);
            for (WebElement result : searchResults) {
                String url = result.getAttribute("href");
                if (url.contains("wuzzuf.net")) {
                    wuzzufLinks.add(url);
                }
            }
            if (page < 5) {
                UiActions.click(driver, By.xpath("//a[@aria-label='Page " + (page + 1) + "']"));

            }

        }

        return wuzzufLinks;

    }
}
