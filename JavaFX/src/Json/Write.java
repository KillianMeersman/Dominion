package Json;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Write {
    
    /**
     *
     * @param what Define object to write.
     * @param where Define where to write the object.
     */
    public static void Object(String what, String where) {

	JSONObject Variables = new JSONObject();
	Variables.put(where, what);

	try {
		FileWriter file = new FileWriter("./src/json/Variables.json");
		file.write(Variables.toJSONString());
		file.flush();
		file.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    /**
     *
     * @param what Define the array to write.
     * @param where Define where to write the array.
     */
    public static void Array(String[] what, String where) {

	JSONObject Variables = new JSONObject();

	JSONArray Array = new JSONArray();
	Array.add(what);
	Array.add(what);
	Array.add(what);

	Variables.put(where, Array);

	try {

		FileWriter file = new FileWriter("./src/json/Variables.json");
		file.write(Variables.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
     }  
}
