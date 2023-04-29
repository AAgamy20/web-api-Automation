package utils;

import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileManager {

    String fileName = null;

    public JsonFileManager(String fileName) {
        this.fileName = fileName;
    }

    public String readJsonDataFromFile(String key){
        String projPath = System.getProperty("user.dir");
        FileReader reader = null;
        try {
            reader = new FileReader("src/test/resources/testDataFiles/"+fileName);
            JSONParser parser = new JSONParser();
            JSONObject data = null;
            data = (JSONObject) parser.parse(reader);
            data.get(key);
            String text =  data.get(key).toString();
            return text;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }


    public String getTestData(String jsonPath) {
        Object testData = null;
        FileReader reader = null;
        try {
            reader = new FileReader("src/test/resources/testDataFiles/"+fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        testData = JsonPath.from(reader).getString(jsonPath);
        return String.valueOf(testData);
    }


}
