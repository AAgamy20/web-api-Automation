import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.GooglePage;
import pages.GoogleSearchResultsPage;
import pages.WuzzufResultsPage;
import utils.TestngListener;
import utils.UiActions;

import java.util.ArrayList;
import java.util.List;


@Listeners(TestngListener.class)
public class WebTests {

    public WebDriver driver;
    GooglePage googlePage ;
    GoogleSearchResultsPage googleSearchResultsPage ;
    WuzzufResultsPage wuzzufResultsPage;
    String baseUrl = "https://www.google.com/";

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(baseUrl);
        googlePage = new GooglePage(driver);
        googleSearchResultsPage = new GoogleSearchResultsPage(driver);
        wuzzufResultsPage = new WuzzufResultsPage(driver);

    }

    @Test
    public void tc001() {

        String searchKeyWord = "Python Jobs in Egypt";
        // search for "Python Jobs in Egypt"
        googlePage.search(searchKeyWord);
        // get links that contains wuzzuf.net
        List <String > wuzzufLinks = googleSearchResultsPage.getWuzzfLinks();
        //Assert that number of links is greater than 0
        Assert.assertTrue(wuzzufLinks.size() > 0);
        // navigate to all wuzzuf net links
        for (String link : wuzzufLinks) driver.get(link);
        //Asset that number of jobs in each link is greater than 0
        Assert.assertTrue(wuzzufResultsPage.getNumberOfJobs() > 0);
        System.out.println(wuzzufResultsPage.getNumberOfJobs());

    }

    @AfterMethod
    public void cleanUp() {
        driver.close();

    }


    }
