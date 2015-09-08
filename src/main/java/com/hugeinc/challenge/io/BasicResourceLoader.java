package com.hugeinc.challenge.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.hugeinc.challenge.core.LoggerNames;

/**
 * Abstract implementation of the {@link ResourceLoader strategy interface} that converts most of the
 * interface declared methods to template methods, reducing the scope / size of the API that must be
 * implemented by strategy classes.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public abstract class BasicResourceLoader implements ResourceLoader {
	protected static final Logger _logger = LoggerFactory.getLogger(LoggerNames.APPLICATION.name());
	
	@Override
	public final Optional<Reader> loadTextResource(String fileName) {
		return  loadResource(fileName).transform(InputStreamReader::new);
	}
	
	@Override
	public final Optional<BufferedReader> loadBufferedTextResource(String fileName, int bufferSize) {
		return loadTextResource(fileName).transform(reader -> new BufferedReader(reader, bufferSize));
	}
}