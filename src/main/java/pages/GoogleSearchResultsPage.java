package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.UiActions;

import java.util.ArrayList;
import java.util.List;

public class GoogleSearchResultsPage {

    WebDriver driver;

    By searchResultLinks = By.xpath("//div[@class='yuRUbf']/a");
    By nextPageBtn = By.id("pnnext");


    public GoogleSearchResultsPage(WebDriver driver) {
        this.driver = driver;
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
                UiActions.click(driver, nextPageBtn);

            }

        }

        return wuzzufLinks;

    }
}
