package com.hugeinc.challenge.expression;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class that validates that point x and y coordinates are both greater than zero.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class PointPolicy implements DrawingPolicy<Point> {
	private static PointPolicy _instance;
	private static final Lock _lock = new ReentrantLock();
	
	public static PointPolicy getInstance() {
		_lock.lock();
		if (_instance == null) _instance = new PointPolicy();
		_lock.unlock();
		
		return _instance;
	}
	
	/* (non-Javadoc)
	 * @see com.hugeinc.challenge.expression.DrawingPolicy#check(com.hugeinc.challenge.expression.DrawingExpression)
	 */
	@Override
	public void check(Point expression) throws IllegalArgumentException {
		ExpressionUtils.checkGreaterOrEqualToZero(expression::getX);
		ExpressionUtils.checkGreaterOrEqualToZero(expression::getX);
	}
	
	/**
	 * Default constructor scoped as <code>private</code> to avoid direct instantiation.
	 */
	private PointPolicy() {}
}