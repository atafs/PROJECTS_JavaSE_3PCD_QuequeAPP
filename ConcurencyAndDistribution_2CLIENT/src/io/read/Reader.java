package io.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Reader {

	//ATTRIBUTES
	final static Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * Note: the javadoc of Files.readAllLines says it's intended for small
	 * files. But its implementation uses buffering, so it's likely good even
	 * for fairly large files.
	 */
	
	/** IO: */
	public List<String> readSmallTextFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}

	/** IO: For larger files */
	public void readLargerTextFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		try (Scanner scanner = new Scanner(path, ENCODING.name())) {
			while (scanner.hasNextLine()) {
				// process each line in some way
				log(scanner.nextLine());
			}
		}
	}

	/** IO: */
	public void readLargerTextFileAlternate(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				// process each line in some way
				log(line);
			}
		}
	}

	//PRINT SYSOUT
	public static void log(Object aMsg) {
		System.out.println(String.valueOf(aMsg));
	}
}
