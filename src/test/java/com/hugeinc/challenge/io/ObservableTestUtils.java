package com.hugeinc.challenge.io;

import java.io.Reader;
import java.io.StringReader;

/**
 * Common methods used by the different observable tests.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
final class ObservableTestUtils {
	public static final String[] _validInputExpressions;
	public static final TestCommand[] _validInputCommands;
	
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
}