package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static utils.ExtentReport.fail;

public class Helper {

    public static String getCurrentTime(String dateFormat) {
        String currentTime = "";
        try {
            currentTime = new SimpleDateFormat(dateFormat).format(new Date());
        } catch (IllegalArgumentException e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        return currentTime;
    }
    public static String getCurrentTimeStamp() {
        return getCurrentTime("ddMMyyyyHHmmssSSS");
    }

    public static int getRandomNumberBetweenTwoValues(int lowValue, int highValue) {
        return new Random().nextInt(highValue - lowValue) + lowValue;
    }

    public static String getRandomNumberBetweenTwoValuesAsString(int lowValue, int highValue) {
        return Integer.toString(getRandomNumberBetweenTwoValues(lowValue, highValue));
    }

    public static Integer getCurrentDay()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        String dayString = date1.substring(0, 2);
        Integer day = Integer.parseInt(dayString);
        return day;
    }
}
