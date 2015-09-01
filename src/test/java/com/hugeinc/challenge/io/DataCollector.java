package com.hugeinc.challenge.io;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

import rx.Observer;

import com.google.common.base.Optional;

/**
 * Generic subscriber used throughout the observable tests.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class DataCollector<T> implements Observer<T> {
	private List<T> data = new LinkedList<>();
	private boolean completedCalled;
	private boolean errorCalled;

	@Override
	public void onNext(T item) {
		data.add(item);	
	}
	
	@Override
	public void onCompleted() {
		completedCalled = true;
	}

	@Override
	public void onError(Throwable e) {
		errorCalled = true;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<T[]> getData() {
		if (data.size() == 0) return Optional.absent();
		
		Class<?> arrayType = data.get(0).getClass();
		Object array = Array.newInstance(arrayType, data.size());
		int index = 0;
		for (T datum : data) Array.set(array, index++, datum);
		return Optional.of((T[]) array);
	}
	
	public boolean isCompleteCalled() {
		return completedCalled;
	}

	public boolean isErrorCalled() {
		return errorCalled;
	}
};