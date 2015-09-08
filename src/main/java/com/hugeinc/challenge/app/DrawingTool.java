package com.hugeinc.challenge.app;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.hugeinc.challenge.app.DrawingToolUtils.createExpression;
import static com.hugeinc.challenge.app.DrawingToolUtils.getConfigurationFrom;
import static com.hugeinc.challenge.app.DrawingToolUtils.getIntProperty;

import java.io.BufferedReader;
import java.util.Properties;

import com.hugeinc.challenge.core.ObjectFactory;
import com.hugeinc.challenge.core.Parser;
import com.hugeinc.challenge.expression.Canvas;
import com.hugeinc.challenge.io.ExpressionObservable;
import com.hugeinc.challenge.io.ResourceLoader;

/**
 * Entry point to the application.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class DrawingTool {
	private static final Parser _parser = new Parser();
	
	private static final String INPUT_PATH = "input.txt";
	private static final String CONFIG_APP_PATH = "config/application.properties";
	private static final String CONFIG_FACTORY_PATH = "config/factory.properties";
	private static final String CONFIG_BUFFER_SIZE = "io.bufferSize";
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties applicationConfig = getConfigurationFrom(CONFIG_APP_PATH).orNull();
		checkNotNull(applicationConfig, "Application configuration is null");
		
		Properties factoryConfig = getConfigurationFrom(CONFIG_FACTORY_PATH).orNull();
		checkNotNull(factoryConfig, "Factory configuration is null");
		
		ObjectFactory factory = ObjectFactory.createObjectFactory(factoryConfig);
		ResourceLoader applicationLoader = factory.createResourceLoader();
		Canvas canvas = factory.createCanvas();
		
		int bufferSize = getIntProperty(CONFIG_BUFFER_SIZE, applicationConfig, DEFAULT_BUFFER_SIZE); 
		BufferedReader expressionSource = applicationLoader.loadBufferedTextResource(INPUT_PATH, bufferSize).orNull();
		checkNotNull(expressionSource, "Expression source is null");
		
		ExpressionObservable
			.from(expressionSource)
			.map(_parser::parse)
			.map(parseResult -> createExpression(parseResult, factory))
			.reduce(canvas, DrawingToolUtils::evaluateExpression)
			.subscribe(System.out::println);
	}
}