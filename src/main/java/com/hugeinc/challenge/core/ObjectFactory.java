package com.hugeinc.challenge.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.functions.Func0;

import com.google.common.base.Optional;
import com.hugeinc.challenge.expression.Canvas;
import com.hugeinc.challenge.expression.DrawingExpression;

/**
 * <p>
 * AbstractFactory implementation that can be used to {@link # retrieve} instances of the different
 * components that make part of the solution.
 * 
 * The following are the configuration properties that can be specified, and correspond to the 
 * interface names of the components that can be specified:
 * 
 * <ul>
 * 	<li><b>com.hugeinc.challenge.Canvas</b></li>
 * 	<li><b>com.hugeinc.challenge.Expression.C</b> (for line canvas commands)</li>
 * 	<li><b>com.hugeinc.challenge.Expression.L</b> (for line commands)</li>
 * 	<li><b>com.hugeinc.challenge.Expression.R</b> (for rectangle commands)</li>
 * 	<li><b>com.hugeinc.challenge.Expression.B</b> (for bucket fill commands)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * This factory assumes no default configuration; the specified properties are the ones that in which 
 * this factory is going to rely when attempting to resolve component implementations.
 * </p>
 * 
 * <p>
 * All factory methods in this class throw an <code>IllegalStateException</code> if a suitable 
 * implementation for a component interface cannot be found (by suitable it means that it is 
 * not null, and that it implements the component's interface).
 * </p>
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ObjectFactory {
	private static final Logger _logger = LoggerFactory.getLogger(LoggerNames.ERROR.name());
	
	private static final String CANVAS_IMPLEMENTATION = "com.hugeinc.challenge.Canvas";
	
	private static final String EXPRESSION_IMPLEMENTATION = "com.hugeinc.challenge.Expression.";
	
	private final Properties configuration;
	
	/**
	 * Creates an object factory that will be backed up the the supplied configuration.
	 * 
	 * @param configuration
	 * @return
	 * @throws IllegalArgumentException if <code>configuration</code> is <code>null</code> or
	 * empty.
	 */
	public static ObjectFactory createObjectFactory(Properties configuration) {
		return new ObjectFactory(configuration);
	}
	
	/**
	 * Same as {@link ObjectFactory#createObjectFactory(Properties)}, but calls the supplied 
	 * function to obtain the configuration with which the factory is to be initialized.
	 * 
	 * @param configurationFunction
	 * @return
	 * @throws IllegalArgumentException if <code>configuration</code> is <code>null</code> or
	 * empty.
	 */
	public static ObjectFactory createObjectFactory(Func0<Properties> configurationFunction) {
		return new ObjectFactory(configurationFunction.call());
	}
	
	private static void checkConfiguration(Properties configuration) {
		if (configuration == null) throw new IllegalArgumentException("Object factory configuration cannot be null");
		if (configuration.size() == 0) throw new IllegalArgumentException("Object factory confguration cannot be empty");
	}
	
	public Canvas createCanvas() {
		return createImplementationFor(CANVAS_IMPLEMENTATION, Canvas.class);
	}
	
	public DrawingExpression createExpression(String drawingCommand, Optional<String> commandArgs) {
		if (commandArgs.isPresent()) return createImplementationFor(EXPRESSION_IMPLEMENTATION + drawingCommand, DrawingExpression.class, commandArgs.get());
		return createImplementationFor(EXPRESSION_IMPLEMENTATION + drawingCommand, DrawingExpression.class);
	}

	/**
	 * Constructor that uses the supplied property object to resolve the implementation of the
	 * different components that make part of the solution. It's kept private to avoid direct
	 * instantiation of this class. With the current implementation this doesn't really make 
	 * a difference, but future implementations might perform some optimization in the way the
	 * factory is created (e.g. by caching factories based on the supplied property object).
	 * 
	 * @param configuration
	 */
	private ObjectFactory(Properties configuration) {
		checkConfiguration(configuration);
		this.configuration = (Properties) configuration.clone();
	}
	
	@SuppressWarnings("unchecked")
	private <T> T createImplementationFor(String componentInterface, Class<T> clazz, String... componentArgs) {
		return (T) getComponentImplementation(componentInterface)
					.transform(implementation -> createComponentImplementation(implementation, clazz, componentArgs))
					.get();
	}
	
	private Optional<String> getComponentImplementation(String componentInterface) {
		return Optional.fromNullable(configuration.getProperty(componentInterface));
	}
	
	@SuppressWarnings("unchecked")
	private <T> T createComponentImplementation(String componentImplementationName, Class<?> componentInterfaceClazz, String... constructorArgs) {
		try {
			Object component = constructorArgs != null && constructorArgs.length > 0 
								? createComponentWithAlternateConstructor(Class.forName(componentImplementationName), constructorArgs)
								: createComponentWithDefaultConstructor(Class.forName(componentImplementationName));
			checkComponentImplementation(component, componentInterfaceClazz);
			return (T) component;
		} 
		catch (Exception e) {
			Throwable cause = e instanceof InvocationTargetException ? e.getCause() : e;
			_logger.error("Unable to create component: " + componentInterfaceClazz.getName(), cause);
			throw new IllegalStateException("Unable to create component instance: " + componentImplementationName, cause);
		}
	}
	
	private Object createComponentWithDefaultConstructor(Class<?> forName) throws InstantiationException, IllegalAccessException {
		return forName.newInstance();
	}

	private Object createComponentWithAlternateConstructor(Class<?> componentImplementationClazz, String[] constructorArgs) 
		throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return componentImplementationClazz
				.getConstructor(Arrays
								.stream(constructorArgs)
								.map(arg -> String.class)
								.toArray(Class[]::new)
				).newInstance((Object[])constructorArgs);
	}

	private void checkComponentImplementation(Object component, Class<?> componentInterface) {
		if (!componentInterface.isInstance(component)) {
			_logger.error("Component class '" 
							+ component.getClass().getName() 
							+ "' does not implement interface: " 
							+ componentInterface.getClass().getName());
			throw new IllegalStateException("Invalid component implementation for: " + componentInterface.getName());
		}
	}
}