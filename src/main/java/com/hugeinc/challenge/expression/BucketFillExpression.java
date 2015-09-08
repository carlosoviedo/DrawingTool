package com.hugeinc.challenge.expression;

/**
 * Represents an expression of the form <pre>B x y c</pre>. This class makes the following 
 * validations regarding its arguments:
 * 
 * <ul>
 * 	<li>They must be exactly three arguments</li>
 * 	<li>They first two arguments must be numeric</li>
 * 	<li>They third argument must be numeric</li>
 * 	<li>They numeric arguments must be greater than or equal to zero</li>
 * </ul>
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class BucketFillExpression implements DrawingExpression {
	private static final BucketFillExpressionPolicy _policy = new BucketFillExpressionPolicy();
	
	private Point connectionPoint;
	private char color;
	
	public BucketFillExpression(String arguments) {
		String[] individualArguments = ExpressionUtils.splitArguments(arguments);
		connectionPoint = getConnectionPoint(individualArguments);
		color = getColor(individualArguments);
		
		checkState();
	}

	@Override
	public void interpret(Canvas canvas) {
		System.out.println("BUCKET FILL: " + toString());
	}
	
	public Point getConnectionPoint() {
		return Point.clonePoint(connectionPoint);
	}
	
	public char getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return String.format("{connectionPoint: %s}", connectionPoint);
	}
	
	protected void checkState() {
		_policy.check(this);
	}
	
	private Point getConnectionPoint(String[] individualArguments) {
		return Point.doCreatePoint(individualArguments, 2);
	}
	
	private char getColor(String[] individualArguments) {
		if (individualArguments.length < 3) throw new IllegalArgumentException("Missing color");
		if (individualArguments[2].length() > 1) throw new IllegalArgumentException("3 argument not a color");
		return individualArguments[2].charAt(0);
	}
}