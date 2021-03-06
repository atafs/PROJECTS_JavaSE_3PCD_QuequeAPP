package com.iscte.queque.client.io.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.iscte.queque.client.io.read.Reader;
import com.iscte.queque.client.io.write.Writer;
import com.iscte.queque.client.log.LogMessage;

public class Main {
	
	//PATHS (absolute for windows)
	final static String READ_FROM_FILE = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurrencyAndDistribution_CLIENT\\message\\txt\\readFromFile.txt";
	final static String WRITE_TO_FILE = "D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurrencyAndDistribution_CLIENT\\message\\txt\\writeToFile.txt";
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();
	
	/** MAIN */
	public static void main(String... aArgs) throws IOException {
		Reader reader = new Reader();
		Writer writer = new Writer();

		// treat as a small file
		//List<String> lines = reader.readSmallTextFile(READ_FROM_FILE);


		// treat as a large file - use some buffering
		reader.readLargerTextFile(READ_FROM_FILE);
		
		List<String> lines = Arrays.asList("Agora estou a Escrever", "Para o Ficheiro");
		logger.getLog().debug(lines);
		
		writer.writeSmallTextFile(lines, READ_FROM_FILE);
		writer.writeLargerTextFile(WRITE_TO_FILE, lines);
	}
}
