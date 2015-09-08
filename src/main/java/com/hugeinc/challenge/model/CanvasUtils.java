package com.hugeinc.challenge.model;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hugeinc.challenge.core.LoggerNames;

/**
 * Contains utility methods that operate on {@link Canvas canvases}.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
final class CanvasUtils {
	private static final Logger _logger = LoggerFactory.getLogger(LoggerNames.ERROR.name());
	
	static void writeRow(char[] row, OutputStream out) {
		try {
			out.write(createLineFromRow(row).getBytes());
		} 
		catch (IOException e) {
			final String errorMessage = "Unable to continue drawing canvas";
			_logger.error(errorMessage, e);
			throw new IllegalStateException(errorMessage, e);
		}
	}

	static String createLineFromRow(char[] row) {
		char[] line = new char[row.length+1];
		line[line.length-1] = '\n';
		System.arraycopy(row, 0, line, 0, row.length);
		return new String(line);
	}
	
	/**
	 * Default constructor scoped to private to avoid direct instantiation.
	 */
	private CanvasUtils() {}
}