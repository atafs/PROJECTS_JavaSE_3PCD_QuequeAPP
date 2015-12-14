package com.iscte.queque.client.test.list.json;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONMain {

	public static void main(String[] args) {
		 // Initialize a list of type DataObject
	    List<DataObject> objList = new ArrayList<DataObject>();
	    objList.add(new DataObject(0, "zero"));
	    objList.add(new DataObject(1, "one"));
	    objList.add(new DataObject(2, "two"));

	    // Convert the object to a JSON string
	    String json = new Gson().toJson(objList);
	    System.out.println(json);

	    // Now convert the JSON string back to your java object
	    Type type = new TypeToken<List<DataObject>>(){}.getType();
	    List<DataObject> inpList = new Gson().fromJson(json, type);
	    for (int i=0;i<inpList.size();i++) {
	      DataObject x = inpList.get(i);
	      System.out.println(x);
	    }
	}
	
	//INNER CLASS
	 private static class DataObject {
		
		 //ATTRIBUTES
		private int a;
		private String b;

		//CONSTRUCTOR
		public DataObject(int a, String b) {
			this.a = a;
			this.b = b;
		}

		//TOSTRING
		public String toString() {
			return "a = " + a + ", b = " + b;
		}
	}
}
