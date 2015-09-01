package com.hugeinc.challenge.input;

import java.io.BufferedReader;
import java.util.stream.Stream;

import rx.Observable;
import rx.Subscriber;
import rx.Observable.OnSubscribe;

/**
 * Observable that emits items based on a text input stream. Each item corresponds to a line from
 * the input source.
 * 
 * @author <a href="carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ExpressionObservable {
	public static Observable<String> from(BufferedReader reader) {
		return Observable.create(new OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try (Stream<String> stream = reader.lines()) {
					stream.forEach(expression -> {
						if (!observer.isUnsubscribed()) observer.onNext(expression);
					});
					
					if (!observer.isUnsubscribed()) observer.onCompleted();;
				}
				catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}
}