package com.iscte.queque.client.io.write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Writer {
	
	//ATTRIBUTES
	final static Charset ENCODING = StandardCharsets.UTF_8;

	/** IO: write to a txt file using FileReader class */
	public void writeToTxt() throws IOException{
		
		//LOCAL VARIABLES
		FileWriter writer = null;
		
		//FILE AND WRITER
		try {
			File file = new File(".//message//txt//contactTEST.txt");
		    file.createNewFile();
		    writer = new FileWriter(file); 
		    writer.write("OLA A TODOS!!!\n"); 
		    System.err.println("FILE="+file.getAbsolutePath());
		    writer.flush();
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	/** IO: */
	public void writeSmallTextFile(List<String> aLines, String aFileName)
			throws IOException {
		Path path = Paths.get(aFileName);
		Files.write(path, aLines, ENCODING);
	}
	
	/** IO: */
	public void writeLargerTextFile(String aFileName, List<String> aLines)
			throws IOException {
		Path path = Paths.get(aFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)) {
			for (String line : aLines) {
				writer.write(line);
				writer.newLine();
			}
		}
	}

}