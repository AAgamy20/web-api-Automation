package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WuzzufResultsPage {

    WebDriver driver;

    By jobsLinks = By.xpath("//h2/a");

    public WuzzufResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getNumberOfJobs (){
        return driver.findElements(jobsLinks).size();
    }

}
