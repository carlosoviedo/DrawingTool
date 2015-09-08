package com.hugeinc.challenge.expression;

import com.hugeinc.challenge.model.Canvas;

/**
 * Represents an expression of the form <pre>R x1 y1 x2 y2</pre>. This class makes the following 
 * validations regarding its arguments:
 * 
 * <ul>
 * 	<li>They must be exactly four arguments</li>
 * 	<li>They must be numeric</li>
 * 	<li>They must be greater than or equal to zero</li>
 * 	<li>They must <b>not</b> form a {@link #isHorizontal() horizontal} or {@link #isVertical() vertical} line</li>
 * 	<li>{@link #getEnd() End} point must be to he right of {@link #getBegin() begin} point</li>
 * 	<li>{@link #getEnd() End} point must be lower (in the <code>y-axis</code>) than {@link #getBegin() begin} point</li>
 * </ul>
 * 
 * Instances of this class are immutable.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class RectangleExpression extends LineExpression {
	private static final RectangleExpressionPolicy _policy = new RectangleExpressionPolicy();
	
	public RectangleExpression(String arguments) {
		super(arguments);
	}
	
	@Override
	public void interpret(Canvas canvas) {
		System.out.println("RECTANGLE: " + toString());
	}
	
	@Override
	protected void checkState() {
		_policy.check(this);   
	}
}