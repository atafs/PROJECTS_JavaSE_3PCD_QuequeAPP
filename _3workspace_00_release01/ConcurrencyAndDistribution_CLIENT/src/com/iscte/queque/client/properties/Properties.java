package com.iscte.queque.client.properties;

import com.iscte.queque.client.io.read.Reader_v2;

public class Properties {

	//ATTRIBUTES
	private String path = "";
	private Reader_v2 reader;
	
	//PROPERTIES(my data)
	private String myAccount = "";

	//CONSTRUCTOR
	public Properties() {
		path = "./properties/config.properties";
		
		//SYSTEM PROPERTIES
		reader = new Reader_v2();
		myAccount = reader.readFileReader(path, "myAccount");
	}
	
	//OVERLOAD
	public Properties(String path) {
		this.path = path;
		
		//SYSTEM PROPERTIES
		reader = new Reader_v2();
		myAccount = reader.readFileReader(path, "myAccount");
	}
	
	//GETTERS AND SETTERS
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(String myAccount) {
		this.myAccount = myAccount;
	}


}
