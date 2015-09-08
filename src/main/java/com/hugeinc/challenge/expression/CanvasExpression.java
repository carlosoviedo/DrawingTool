package com.hugeinc.challenge.expression;

import com.hugeinc.challenge.model.Canvas;

/**
 * Represents an expression of the form <pre>C w h</pre>. This class makes the following 
 * validations regarding its arguments:
 * 
 * <ul>
 * 	<li>They must be exactly two arguments</li>
 * 	<li>They must be numeric</li>
 * 	<li>They must be greater than zero</li>
 * </ul>
 * 
 * Instances of this class are immutable.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class CanvasExpression implements DrawingExpression {
	private static final CanvasExpressionPolicy _policy = new CanvasExpressionPolicy();
	
	private int width;
	private int height;
	
	public CanvasExpression(String arguments) {
		String[] individualArguments = ExpressionUtils.splitArguments(arguments);
		width = getWidth(individualArguments);
		height = getHeight(individualArguments);
		
		checkState();
	}

	@Override
	public void interpret(Canvas canvas) {
		canvas.init(width, height);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	/**
	 * Allows for validation algorithm to be extended.
	 */
	protected void checkState() {
		_policy.check(this);
	}

	private int getWidth(String[] individualArguments) {
		return ExpressionUtils.getArgumentAsInteger(individualArguments[0]);
	}
	
	private int getHeight(String[] individualArguments) {
		if (individualArguments.length < 2) throw new IllegalArgumentException("Missing height");
		return ExpressionUtils.getArgumentAsInteger(individualArguments[1]);
	}
	
	@Override
	public String toString() {
		return String.format("{height: %d, width: %d}", height, width);
	}
}