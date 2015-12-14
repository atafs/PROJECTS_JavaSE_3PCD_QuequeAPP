package com.iscte.queque.client.io.read;

import java.io.FileReader;

import java.util.Properties;

public class Reader_v2 {
	
	//ATTRIBUTES 
	private String inputFile;

	//CONSTRUCTOR
	public Reader_v2(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public Reader_v2() {
		inputFile = null;
	}

	//READ FROM FILE READER PROPERTIES
	public String readFileReader(String path, String string) {
		String toReturn = "";
		try{
			FileReader reader = new FileReader(path);
			Properties properties = new Properties();
			properties.load(reader);
			toReturn = properties.getProperty(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	//GETTERS
	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	//PARAGRAPH
	public void paragraph() {
		System.out.println();
	}
}
