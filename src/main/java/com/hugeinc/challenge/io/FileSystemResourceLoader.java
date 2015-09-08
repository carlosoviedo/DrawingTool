package com.hugeinc.challenge.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.google.common.base.Optional;

/**
 * Concrete implementation of the {@link ResourceLoader strategy interface} that loads resources from the file system.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class FileSystemResourceLoader extends BasicResourceLoader {
	@Override
	public Optional<InputStream> loadResource(String fileName) {
		_logger.debug("Opening text file from file system");
		try {
			return Optional.of(new FileInputStream(fileName));
		}
		catch (FileNotFoundException e) {
			return Optional.absent();
		}
	}
}