package com.hugeinc.challenge.expression;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class that validates that origin and end points form either a horizontal or 
 * a vertical line.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class LineExpressionPolicy implements DrawingPolicy<LineExpression> {
	private static LineExpressionPolicy _instance;
	private static final Lock _lock = new ReentrantLock();
	
	public static LineExpressionPolicy getInstance() {
		_lock.lock();
		if (_instance == null) _instance = new LineExpressionPolicy();
		_lock.unlock();
		
		return _instance;
	}
	
	@Override
	public void check(LineExpression expression) throws IllegalArgumentException {
		ExpressionUtils.checkAnyTrue(expression::isHorizontal, expression::isVertical);
	}
	
	/**
	 * Default constructor scoped as <code>private</code> to avoid direct instantiation.
	 */
	private LineExpressionPolicy() {}
}