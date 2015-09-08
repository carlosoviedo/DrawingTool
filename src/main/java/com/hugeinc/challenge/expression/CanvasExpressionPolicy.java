package com.hugeinc.challenge.expression;


/**
 * Strategy class that validates that canvas width and height is greater than zero.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class CanvasExpressionPolicy implements DrawingPolicy<CanvasExpression> {
	@Override
	public void check(CanvasExpression expression) throws IllegalArgumentException {
		ExpressionUtils.checkGreaterThanZero(expression::getWidth);
		ExpressionUtils.checkGreaterThanZero(expression::getHeight);
	}
}