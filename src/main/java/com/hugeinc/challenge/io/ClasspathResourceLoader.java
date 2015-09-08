package com.hugeinc.challenge.io;

import java.io.InputStream;

import com.google.common.base.Optional;

/**
 * Concrete implementation of the {@link ResourceLoader strategy interface} that loads resources from the application's
 * classpath.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ClasspathResourceLoader extends BasicResourceLoader {
	@Override
	public Optional<InputStream> loadResource(String fileName) {
		if (fileName == null || fileName.length() == 0) throw new IllegalArgumentException("File name cannot be null");
		
		_logger.debug("Opening text file from classpath: {}", fileName);
		return Optional.fromNullable(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
	}
}