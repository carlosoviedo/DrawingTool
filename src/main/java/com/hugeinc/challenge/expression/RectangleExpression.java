package com.hugeinc.challenge.expression;

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
	public RectangleExpression(String arguments) {
		super(arguments);
	}
	
	@Override
	protected void checkState() {
		RectangleExpressionPolicy.getInstance().check(this);   
	}
}