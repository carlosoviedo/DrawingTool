package com.hugeinc.challenge.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Properties;

/**
 * Common methods used by the different object factory tests.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class ObjectFactoryTestUtils {
	public static Properties mockNonEmptyConfiguration(Map<String, String>  properties) {
		if (properties == null || properties.size() == 0) throw new IllegalStateException("Properties map cannot be null");
		
		Properties configuration = mock(Properties.class);
		properties.forEach((key,value) -> when(configuration.getProperty(key)).thenReturn(value));
		when(configuration.size()).thenReturn(properties.size());
		when(configuration.clone()).thenReturn(configuration);
		return configuration;
	}
}