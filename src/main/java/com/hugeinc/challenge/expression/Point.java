package com.hugeinc.challenge.expression;

/**
 * Abstraction that represents a point in a cartesian plane. This class makes the following 
 * validations regarding its constructor arguments:
 * 
 * <ul>
 * 	<li>They must be greater than or equal to zero</li>
 * </ul>
 * 
 * Instances of this class are immutable. 
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class Point implements DrawingExpression, Cloneable  {
	private static final PointPolicy _policy = new PointPolicy();
	
	private int x;
	private int y;
	
	public static Point clonePoint(Point point) {
		try {
			return point != null ? point.clone() : null;
		} 
		catch (CloneNotSupportedException e) {/* Not gonna happen */}
		return null;
	}
	
	public static Point doCreatePoint(String[] individualArguments, int size) {
		if (individualArguments.length < size) throw new IllegalArgumentException("Arguments are not a point");
		return new Point(ExpressionUtils.getArgumentAsInteger(individualArguments[size-2]), 
							ExpressionUtils.getArgumentAsInteger(individualArguments[size-1]));
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		
		checkState();
	}
	
	public boolean isRightOf(Point other) {
		return this.x > other.x;
	}
	
	public boolean isLeftOf(Point other) {
		return this.x < other.x;
	}
	
	public boolean isLowerThan(Point other) {
		return this.y < other.y;
	}
	
	public boolean isHigherThan(Point other) {
		return this.y > other.y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	@Override
	public void interpret(Canvas canvas) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Point clone() throws CloneNotSupportedException {
		return new Point(x, y);
	}
	
	@Override
	public String toString() {
		return String.format("{x: %d, y: %d}", x, y);
	}
	
	/**
	 * Allows for validation algorithm to be extended.
	 */
	protected void checkState() {
		_policy.check(this);
	}
}