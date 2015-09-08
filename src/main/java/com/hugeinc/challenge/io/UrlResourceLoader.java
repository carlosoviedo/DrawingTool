package com.hugeinc.challenge.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.common.base.Optional;

/**
 * Concrete implementation of the {@link ResourceLoader strategy interface} that loads resources from an url endpoint.
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class UrlResourceLoader extends BasicResourceLoader {
	@Override
	public Optional<InputStream> loadResource(String fileName) {
		_logger.debug("Opening text file from url");
		try {
			return Optional.of(new URL(fileName).openStream());
		} 
		catch (IOException e) {
			return Optional.absent();
		}
	}
}