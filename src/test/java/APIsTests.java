import apis.JsonPlaceHolderApi;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.GooglePage;
import pages.GoogleSearchResultsPage;
import pages.WuzzufResultsPage;
import utils.ApiActions;
import utils.PropertiesReader;
import utils.TestngListener;

import java.util.List;
import java.util.Random;


@Listeners(TestngListener.class)
public class APIsTests {
    String baseUri = "https://jsonplaceholder.typicode.com";
    ApiActions apiActions = null;
    JsonPlaceHolderApi jsonPlaceHolderApi ;

    @BeforeMethod
    public void setup() {
        apiActions = new ApiActions(baseUri);
        jsonPlaceHolderApi = new JsonPlaceHolderApi(apiActions);


    }



    @Test
    public void GenerateRandomUserAndPrintEmail() {
        //Generate Random user id
        Random random = new Random();
        Integer randomID = random.nextInt(10) + 1;
        // get the user with the generated id
        Response res = jsonPlaceHolderApi.getUserByID(randomID.toString());
        // print email
        System.out.println(res.jsonPath().getString("email"));
    }

    @Test
    public void GetTheUserPostsAndVerifyPostIds() {
        //Generate Random user id
        Random random = new Random();
        Integer randomID = random.nextInt(10) + 1;
        // get all posts of that user
        Response res = jsonPlaceHolderApi.getPostsByUserID(randomID.toString());
        // read all posts ids from the response
        List<Integer> idList = JsonPath.parse(res.getBody().asString()).read("$..id");
        // Verify that all ids are in between 1 - 100
        for (int postId : idList) {
            Assert.assertTrue(postId >= 1 && postId <= 100, "Invalid Post ID: " + postId);
        }
    }

    @Test
    public void createPostAndValidateResponse() {
        //create post with the following data
        String userID = "2";
        String title = "Test";
        String body = "Test Body";
        Response res = jsonPlaceHolderApi.createPostForUser(title, body ,userID);
        //verify that the response contains same data of the request
        Assert.assertTrue(res.jsonPath().getString("userId").equals(userID));
        Assert.assertTrue(res.jsonPath().getString("body").equals(body));
        Assert.assertTrue(res.jsonPath().getString("title").equals(title));

    }


}
