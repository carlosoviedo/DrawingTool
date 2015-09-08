package com.hugeinc.challenge.core;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

/**
 * Performs the following tests over the {@link ObjectFactory} class:
 * 
 * <ol>
 * 	<li>Object factory creation with a {@link #testFactoryCreationWithNonEmptyConfiguration() non-empty properties} object</li>
 * 	<li>Object factory creation with an {@link #testFactoryCreationWithEmptyConfiguration() empty properties} object</li>
 * 	<li>Object factory creation with a {@link #testFactoryCreationWithNullConfiguration() null} properties object</li>
 * 	<li>Creation of component whose {@link #testComponentCreationSuccessful() interface is registered} in the configuration 
 * 		and that implements that interface</li>
 * 	<li>Creation of component whose {@link #testComponentCreationWithOtherInterface() interface is registered} in the 
 * 		configuration and that does not implement that interface</li>
 * 	<li>Creation of component whose {@link #testComponentNotRegisteredCreation() interface is not registered} in the 
 * 		configuration</li>
 * </ol>
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ObjectFactoryTest {
	@Test
	public void testFactoryCreationWithNonEmptyConfiguration() {
		ObjectFactory factory = ObjectFactory.createObjectFactory(this::mockNonEmptyConfiguration);
		assertNotNull("Object factory is null", factory);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFactoryCreationWithEmptyConfiguration() {
		ObjectFactory.createObjectFactory(new Properties());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFactoryCreationWithNullConfiguration() {
		ObjectFactory.createObjectFactory((Properties)null);
	}

	@Test
	public void testComponentCreationSuccessful() {
		ObjectFactory factory = ObjectFactory.createObjectFactory(this::mockNonEmptyConfiguration);
		factory.createCanvas();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testComponentCreationWithOtherInterface() {
		doCreateFactoryWith("java.lang.StringBuilder").createCanvas();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testComponentNotRegisteredCreation() {
		doCreateFactoryWith(null).createCanvas();
	}
	
	private ObjectFactory doCreateFactoryWith(String canvasComponentimplementation) {
		Properties configuration = mockNonEmptyConfiguration();
		when(configuration.getProperty("com.hugeinc.challenge.Canvas")).thenReturn(canvasComponentimplementation);
		return ObjectFactory.createObjectFactory(configuration);
	}
	
	private Properties mockNonEmptyConfiguration() {
		Map<String, String> properties = new HashMap<>(2);
		properties.put("com.hugeinc.challenge.Canvas", "com.hugeinc.challenge.model.Canvas");
		return ObjectFactoryTestUtils.mockNonEmptyConfiguration(properties);
	}
}