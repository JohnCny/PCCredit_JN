package com.cardpay.pccredit.creditEvaluation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.*;

/*
 * This class is used to package the methods to parse
 * data from a JSON object or generate it from data you
 * get;
 */
public class JsonParser {
	// Method try to parse data from JSON Object
	// and this method is specific to parse JSON
	// from file "model.json"
	public void parseJSON(JSONObject jsonObj) throws Exception {
		EvaluateModule.livingConditions = parse(jsonObj, "livingCondition", EvaluateModule.livingConditions);
		EvaluateModule.creditConditions = parse(jsonObj, "creditCondition", EvaluateModule.creditConditions);
		EvaluateModule.operateConditions = parse(jsonObj, "operateCondition", EvaluateModule.operateConditions);
		EvaluateModule.repayAbilities = parse(jsonObj, "repayAbilities", EvaluateModule.repayAbilities);
		//EvaluateModule.evaluateModel = getJsonFromFile("./model.json");
	}
	
	// trunk JSONObject into several sub JSONObjects
	private JSONObject trunkJSON(JSONObject jsonObj, String key) throws Exception {
		return jsonObj.getJSONObject(key);
	}
	 
	private String[][] parse(JSONObject jsonObj, String jsonObjKey, String result[][]) throws Exception {
		int i = 0;
		JSONObject json = (JSONObject)jsonObj.get(jsonObjKey);
		String key, value;
		
		result = new String[2][json.length()];
		Iterator iterator = json.keys();
		
		while(iterator.hasNext()){
			key = (String) iterator.next();
            value = json.getString(key);
            result[0][i] = key;
            result[1][i] = value;
            i++;
        }
		return result;
	}
	
	// Method try to generate a JSON Obj
	public JSONObject PackageJSON (String toJSON) throws Exception {
		JSONObject jsonObj = new JSONObject(toJSON);
		return jsonObj;
	}
	
	// get JSON object from file
	public JSONObject getJsonFromFile(String fileName) throws Exception {
		File file = new File(fileName);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
            	buffer.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
        	System.out.println("jp");
        	//System.out.println(e.getStackTrace());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        JSONObject result = new JSONObject(buffer.toString());
        return result;
	}
}
