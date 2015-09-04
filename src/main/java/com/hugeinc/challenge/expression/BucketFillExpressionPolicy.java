package com.hugeinc.challenge.expression;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class that validates bucket fill expressions. Currently this class performs no
 * validation, as the only validation required is performed by the {@link Point#checkState() point} class when
 * creating the {@link BucketFillExpression#getConnectionPoint() connection point}.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class BucketFillExpressionPolicy implements DrawingPolicy<BucketFillExpression> {
	private static BucketFillExpressionPolicy _instance;
	private static final Lock _instanceLock = new ReentrantLock();
	
	public static BucketFillExpressionPolicy getInstance() {
		_instanceLock.lock();
		if (_instance == null) _instance = new BucketFillExpressionPolicy();
		_instanceLock.unlock();
		
		return _instance;
	}
	
	@Override
	public void check(BucketFillExpression expression) throws IllegalArgumentException {}
	
	/**
	 * Default constructor scoped as <code>private</code> to avoid direct instantiation.
	 */
	private BucketFillExpressionPolicy() {}
}