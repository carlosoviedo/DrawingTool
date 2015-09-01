package com.hugeinc.challenge.io;

import java.io.Reader;

import rx.Observable;
import rx.observables.StringObservable;

/**
 * Tests the {@link <a href="http://reactivex.io/documentation/observable.html">Observable</a>} implementation  
 * of {@link <a href="https://github.com/ReactiveX/RxJavaString">RxJava String</a>}.
 * 
 * @author <a href="carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class StringObservableTest extends ObservableTest {
	@Override
	protected Observable<String> createObservable(Reader inputReader) {
		Observable<String> expressionObservable = StringObservable.from(inputReader, 5);
		return StringObservable.byLine(expressionObservable);
	}
}