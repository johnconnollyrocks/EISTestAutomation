package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

 
public class JsonParser {
	public JSONObject jsonObject=null;
	public FileReader reader=null;
	
/*public void JsonReadFile(){
		try {
			
			final String filePath = System.getProperty("user.dir")+"\\JsonResp.json";
			
			reader = new FileReader(filePath);

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
}*/

public ArrayList<String> GetStringArrayElementsFromJSON(String ArrayElement,String Value){
		ArrayList<String> ArrayOfJsonElements=new ArrayList<String>();
				//JsonReadFile();
				String JsonElementValue=null;
				
				// get an array from the JSON object
				JSONArray lang= (JSONArray) jsonObject.get(ArrayElement);
				
				Iterator i = lang.iterator();
				//ArrayOfJsonElements.clear();
				// take each value from the json array separately
				while (i.hasNext()) {
					JSONObject innerObj = (JSONObject) i.next();
					if(innerObj.toString().contains(Value)){
						JsonElementValue=(String) innerObj.get(Value);
					}else{
						JsonElementValue=null;
					}
					if(JsonElementValue.contains("®")) {
					JsonElementValue=JsonElementValue.replace("®", "�");
					}
					Util.sleep(4000);
					if(JsonElementValue.contains("™")) {
						JsonElementValue=JsonElementValue.replace("™", "�");
						}
					ArrayOfJsonElements.add(JsonElementValue);
				}
				
				return ArrayOfJsonElements;
}

@SuppressWarnings("unchecked")
public
ArrayList<Integer> GetIntegerArrayElementsFromJSON(String ArrayElement,String Value){
	ArrayList<Integer> ArrayOfJsonIntegerElements=new ArrayList<Integer>();
	//JsonReadFile();
	long JsonElementValue=0;
	Integer intgr=0;
	
	// get an array from the JSON object
	JSONArray lang= (JSONArray) jsonObject.get(ArrayElement);
	ArrayOfJsonIntegerElements.clear();
	Iterator i = lang.iterator();

	// take each value from the json array separately
	while (i.hasNext()) {
		JSONObject innerObj = (JSONObject) i.next();
		if(innerObj.toString().contains(Value)){
			JsonElementValue= (long) innerObj.get(Value);
			intgr=new Integer((int) JsonElementValue);
		}else{
			intgr=1234;
		}
		ArrayOfJsonIntegerElements.add(intgr);
	}
	
	return ArrayOfJsonIntegerElements;
}

public String GetStringFromJsonObject (String Name,String JsonStr){
	//JsonReadFile();
	JSONParser jsonParser = new JSONParser();
	try {
		jsonObject = (JSONObject) jsonParser.parse(JsonStr);
	} catch (ParseException e) {
		e.printStackTrace();
	}

	// get a String from the JSON object
	String Value = (String) jsonObject.get(Name);
	/*try {
		reader.close();
	} catch (IOException e) {
		e.printStackTrace();
	}*/
	return Value;
 }

public String GetStructureOfJsonObject(String StructureName,String Name){
	//JsonReadFile();
	// handle a structure into the json object
		            JSONObject structure = (JSONObject) jsonObject.get(StructureName);
		            String Value=(String)structure.get(Name);		            
		            return Value;
}
}