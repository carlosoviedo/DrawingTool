package com.hugeinc.challenge.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;

import com.google.common.base.Optional;

/**
 * Strategy interface that allows clients to use different algorithms for resource loading.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public interface ResourceLoader {
	/**
	 * Loads a resource (text or binary) and returns a binary stream that can be used to read its bytes.
	 * Returned Optional must be queried to determine if resource could be loaded or not.
	 * 
	 * @param fileName The name / URL of the resource to load.
	 * @return
	 */
	Optional<InputStream> loadResource(String fileName);
	
	/**
	 * Loads a text resource and returns a reader that can be used to read its characters. Returned optional
	 * must be queried to determine if resource could be loaded or not.
	 * 
	 * @param fileName The name / URL of the resource to load.
	 * @return
	 */
	Optional<Reader> loadTextResource(String fileName);
	
	/**
	 * Same as {@link #loadTextResource(String)}, but wraps the returned reader in a <code>BufferedReader</code>
	 * implementation.
	 * 
	 * @param fileName
	 * @param bufferSize
	 * @return
	 */
	Optional<BufferedReader> loadBufferedTextResource(String fileName, int bufferSize);
}