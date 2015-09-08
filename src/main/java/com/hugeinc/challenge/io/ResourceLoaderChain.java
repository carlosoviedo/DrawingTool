package com.hugeinc.challenge.io;

import java.io.InputStream;

import com.google.common.base.Optional;

/**
 * Implementation of the chain of responsibility that delegates the resource loading to a chain of
 * {@link ResourceLoader resource loaders} ordered in the following way:
 * 
 * <ol>
 * 	<li>{@link ClasspathResourceLoader class path} resource loader</li>
 * 	<li>{@link FileSystemResourceLoader file system} resource loader</li>
 * 	<li>{@link UrlResourceLoader url} resource loader</li>
 * </ol>
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ResourceLoaderChain extends BasicResourceLoader {
	private static final ClasspathResourceLoader _classpathLoader = new ClasspathResourceLoader();
	private static final FileSystemResourceLoader _fileSystemLoader = new FileSystemResourceLoader();
	private static final UrlResourceLoader _urlLoader = new UrlResourceLoader();
	
	/* (non-Javadoc)
	 * @see com.hugeinc.challenge.io.ResourceLoader#loadResource(java.lang.String)
	 */
	@Override
	public Optional<InputStream> loadResource(String fileName) {
		return _classpathLoader.loadResource(fileName)
				.or(_fileSystemLoader.loadResource(fileName))
				.or(_urlLoader.loadResource(fileName))
				.or(Optional.absent());
	}
}