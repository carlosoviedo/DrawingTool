package com.hugeinc.challenge.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

/**
 * Common methods used by the different observable tests.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
final class ObservableTestUtils {
	public static final String[] _validInputExpressions;
	public static final TestCommand[] _validInputCommands;
	
	private static final Logger logger = LoggerFactory.getLogger(ObservableTestUtils.class);
	
	static {
		_validInputExpressions = new String[] {"C 20 4", "L 1 2 6 2", "L 6 3 6 4", "R 16 1 20 3", "B 10 3 o"};
		_validInputCommands = new TestCommand[] {
			new TestCommand('C',  new String[]{"20", "4"}),
			new TestCommand('L',  new String[]{"1", "2", "6", "2"}),
			new TestCommand('L',  new String[]{"6", "3", "6", "4"}),
			new TestCommand('R',  new String[]{"16", "1", "20", "3"}),
			new TestCommand('B',  new String[]{"10", "3", "o"})
		};
	}
	
	public static Reader createValidInputReader() {
		return new StringReader("C 20 4\n" 
								+ "L 1 2 6 2\n"
								+ "L 6 3 6 4\n"
								+ "R 16 1 20 3\n"
								+ "B 10 3 o");
	}
	
	public static Reader createInvalidInputReader() {
		return null;
	}
	
	public static Optional<Reader> openTextFile(String fileName) {
		if (fileName == null || fileName.length() == 0) throw new IllegalArgumentException("File name cannot be null");
		logger.debug("Opening text file: {}", fileName);
		return  openFile(fileName).transform(InputStreamReader::new);
	}
	
	public static Optional<BufferedReader> openAndBufferTextFile(String fileName, int bufferSize) {
		return openTextFile(fileName).transform(reader -> new BufferedReader(reader, bufferSize));
	}
	
	public static Optional<InputStream> openFile(String fileName) {
		return openFileFromClasspath(fileName)
				.or(openFileFromFileSystem(fileName))
				.or(openFileFromUrl(fileName))
				.or(Optional.absent());
	}
	
	private static Optional<InputStream> openFileFromClasspath(String fileName) {
		logger.debug("From classpath");
		return openFileFromRelativeClasspath(fileName).or(openFileFromAbsoluteClasspath(fileName)).or(Optional.absent());
	}
	
	private static Optional<InputStream> openFileFromFileSystem(String fileName) {
		logger.debug("From file system");
		try {
			return Optional.of(new FileInputStream(fileName));
		}
		catch (FileNotFoundException e) {
			return Optional.absent();
		}
	}
	
	private static Optional<InputStream> openFileFromUrl(String fileName) {
		logger.debug("From url");
		try {
			return Optional.of(new URL(fileName).openStream());
		} 
		catch (IOException e) {
			return Optional.absent();
		}
	}
	
	private static Optional<InputStream> openFileFromRelativeClasspath(String fileName) {
		String fileNameToUse = fileName.charAt(0) == '/' ? fileName.substring(1) : fileName;
		return openSanitizedFileFromClasspath(fileNameToUse);
	}
	
	private static Optional<InputStream> openFileFromAbsoluteClasspath(String fileName) {
		String fileNameToUse = fileName.charAt(0) != '/' ? "/" + fileName : fileName;
		return openSanitizedFileFromClasspath(fileNameToUse);
	}

	private static Optional<InputStream> openSanitizedFileFromClasspath(String fileName) {
		return Optional.fromNullable(ObservableTestUtils.class.getResourceAsStream(fileName));
	}
}