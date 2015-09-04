package com.hugeinc.challenge.expression;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class that validates that origin and end points do not form neither a horizontal 
 * nor a vertical line.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class RectangleExpressionPolicy implements DrawingPolicy<RectangleExpression> {
	private static RectangleExpressionPolicy _instance;
	private static final Lock _instanceLock = new ReentrantLock();
	
	public static RectangleExpressionPolicy getInstance() {
		_instanceLock.lock();
		if (_instance == null) _instance = new RectangleExpressionPolicy();
		_instanceLock.unlock();
		
		return _instance;
	}
	
	@Override
	public void check(RectangleExpression expression) throws IllegalArgumentException {
		ExpressionUtils.checkNoneTrue(expression::isHorizontal,expression::isVertical);
		
		Point begin = expression.getBegin(),
				end = expression.getEnd();
		ExpressionUtils.checkAllTrue(() ->end.isRightOf(begin), () -> end.isHigherThan(begin));
	}
	
	/**
	 * Default constructor scoped as <code>private</code> to avoid direct instantiation.
	 */
	private RectangleExpressionPolicy() {}
}