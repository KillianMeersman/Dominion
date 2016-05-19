package Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Read {
            
    public static ArrayList<String> returnArray = new ArrayList<>();
    public static String returnObject;
    
    public static String Object(String where) {

        JSONParser parser = new JSONParser();
        
        try {

            Object Variables = parser.parse(new FileReader("./src/json/Variables.json"));

            JSONObject jsonObject = (JSONObject) Variables;

            String returnObject = (String) jsonObject.get(where);
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }
        return returnObject;
    }
    
//    public static String Array(String where) {
//
//        JSONParser parser = new JSONParser();
//
//	try {
//
//		Object Variables = parser.parse(new FileReader("./src/json/Variables.json"));
//
//		JSONObject jsonObject = (JSONObject) Variables;
//
//		JSONArray msg = (JSONArray) jsonObject.get(where);
//		Iterator<String> iterator = msg.iterator();
//		while (iterator.hasNext()) {
//                    returnArray.add(iterator.next());
//		}
//
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	} catch (ParseException e) {
//		e.printStackTrace();
//	}
//        return returnArray;
}