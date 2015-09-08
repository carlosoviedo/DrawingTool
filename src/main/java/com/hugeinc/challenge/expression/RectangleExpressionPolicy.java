package com.hugeinc.challenge.expression;


/**
 * Strategy class that validates that origin and end points do not form neither a horizontal 
 * nor a vertical line.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class RectangleExpressionPolicy implements DrawingPolicy<RectangleExpression> {
	@Override
	public void check(RectangleExpression expression) throws IllegalArgumentException {
		ExpressionUtils.checkNoneTrue(expression::isHorizontal,expression::isVertical);
		
		Point begin = expression.getBegin(),
				end = expression.getEnd();
		ExpressionUtils.checkAllTrue(() ->end.isRightOf(begin), () -> end.isHigherThan(begin));
	}
}