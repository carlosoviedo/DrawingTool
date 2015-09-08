package com.hugeinc.challenge.expression;


/**
 * Strategy class that validates that point x and y coordinates are both greater than zero.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class PointPolicy implements DrawingPolicy<Point> {
	@Override
	public void check(Point expression) throws IllegalArgumentException {
		ExpressionUtils.checkGreaterOrEqualToZero(expression::getX);
		ExpressionUtils.checkGreaterOrEqualToZero(expression::getX);
	}
}