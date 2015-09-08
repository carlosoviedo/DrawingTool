package com.hugeinc.challenge.io;

import java.io.BufferedReader;
import java.io.Reader;

import com.hugeinc.challenge.io.ExpressionObservable;

import rx.Observable;

/**
 * Tests the custom {@link <a href="http://reactivex.io/documentation/observable.html">Observable</a>}
 * implementation that is based on streams created from a buffered reader.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ExpressionObservableTest extends ObservableTest {
	@Override
	protected Observable<String> createObservable(Reader inputReader) {
		return ExpressionObservable.from(inputReader != null ? new BufferedReader(inputReader, 5) : null);
	}
}