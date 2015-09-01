package com.hugeinc.challenge.io;

import rx.Subscriber;

/**
 * {@link Subscriber} implementation that unsubscribes after a predefined amount of time.
 * 
 * @see 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class UnsubscribeDataCollector<T> extends Subscriber<T> {
	private static final int DO_NOT_UNSUBSCRIBE = 0;
	private DataCollector<T> collector;
	private int unsubscribeAfter;
	private int itemCount;

	public UnsubscribeDataCollector(DataCollector<T> collector) {
		this(collector, DO_NOT_UNSUBSCRIBE);
	}
	
	public UnsubscribeDataCollector(DataCollector<T> collector, int numItems) {
		this.collector = collector;
		unsubscribeAfter = numItems;
	}
	
	@Override
	public void onNext(T item) {
		if (unsubscribeAfter != DO_NOT_UNSUBSCRIBE && ++itemCount == unsubscribeAfter) unsubscribe();
		collector.onNext(item);
	}

	@Override
	public void onCompleted() {		
		collector.onCompleted();
	}

	@Override
	public void onError(Throwable e) {
		collector.onError(e);
	}
}