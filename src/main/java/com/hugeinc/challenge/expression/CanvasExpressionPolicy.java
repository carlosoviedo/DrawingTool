package com.hugeinc.challenge.expression;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class that validates that canvas width and height is greater than zero.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class CanvasExpressionPolicy implements DrawingPolicy<CanvasExpression> {
	private static CanvasExpressionPolicy _instance = null;
	private static final Lock _lock = new ReentrantLock();
	
	public static CanvasExpressionPolicy getInstance() {
		_lock.lock();
		if (_instance == null) _instance = new CanvasExpressionPolicy();
		_lock.unlock();
		
		return _instance;
	}
	
	@Override
	public void check(CanvasExpression expression) throws IllegalArgumentException {
		ExpressionUtils.checkGreaterThanZero(expression::getWidth);
		ExpressionUtils.checkGreaterThanZero(expression::getHeight);
	}
	
	/**
	 * Default constructor scoped as <code>private</code> to avoid direct instantiation.
	 */
	private CanvasExpressionPolicy() {}
}