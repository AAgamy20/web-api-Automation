package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private static String propRoot = "src/main/resources/";

    public static String getProperty(String fileName,String propertyName){
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(propRoot+fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return prop.getProperty(propertyName);
    }



    public static void setProperty(){

    }
}
