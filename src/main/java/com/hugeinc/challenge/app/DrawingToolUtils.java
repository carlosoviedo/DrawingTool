package com.hugeinc.challenge.app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.hugeinc.challenge.core.LoggerNames;
import com.hugeinc.challenge.core.ObjectFactory;
import com.hugeinc.challenge.core.Parser;
import com.hugeinc.challenge.expression.DrawingExpression;
import com.hugeinc.challenge.io.ResourceLoader;
import com.hugeinc.challenge.io.ResourceLoaderChain;
import com.hugeinc.challenge.model.Canvas;

/**
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public final class DrawingToolUtils {
	private static final Logger _logger = LoggerFactory.getLogger(LoggerNames.ERROR.name());
	private static final ResourceLoader resourceLoader = new ResourceLoaderChain();
	
	static Optional<Properties> getConfigurationFrom(String configPath) {
		Properties configuration = new Properties();
		try {
			configuration.load(resourceLoader.loadResource(configPath).get());
			return Optional.of(configuration);
		} 
		catch (IOException | IllegalStateException e) {
			_logger.error("Unable to load application configuration from: " + configPath, e);
			return Optional.absent();
		}
	}
	
	static int getIntProperty(String propertyName, Properties configuration, int defaultValue) {
		try {
			return Integer.parseInt(configuration.getProperty(propertyName, Integer.toString(defaultValue)));
		}
		catch (NumberFormatException e) {
			_logger.error("Configured buffer size is not a number: " + configuration.getProperty(propertyName), e);
			return defaultValue;
		}
	}
	
	static OutputStream getOutputstreamProperty(String propertyName, Properties configuration, OutputStream defaultValue) {
		String fileName = configuration.getProperty(propertyName);
		if (fileName != null) {
			try {
				return new FileOutputStream(fileName);
			} 
			catch (FileNotFoundException e) {
				_logger.warn("Unable to initialize output file: " + fileName + ". Output will be sent to console.", e);
			}
		}
		else _logger.info("No output file configured. Output will be sent to console.");
		return defaultValue;
	}
	
	static DrawingExpression createExpression(Parser.ParseResult result, ObjectFactory factory) {
		return factory.createExpression(Character.toString(result.getDrawingCommand()), result.getCommandArgs());
	}
	
	static Canvas evaluateExpression(Canvas canvas, DrawingExpression expression) {
		expression.interpret(canvas);
		return canvas;
	}
	
	/**
	 * Default constructor scoped to private to avoid instantiation.
	 */
	private DrawingToolUtils() {}
}