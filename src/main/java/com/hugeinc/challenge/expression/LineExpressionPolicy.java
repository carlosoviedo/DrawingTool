package com.hugeinc.challenge.expression;

/**
 * Strategy class that validates that origin and end points form either a horizontal or a vertical line.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class LineExpressionPolicy implements DrawingPolicy<LineExpression> {
	@Override
	public void check(LineExpression expression) throws IllegalArgumentException {
		ExpressionUtils.checkAnyTrue(expression::isHorizontal, expression::isVertical);
	}
}