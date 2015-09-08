package com.hugeinc.challenge.expression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hugeinc.challenge.core.LoggerNames;
import com.hugeinc.challenge.model.Canvas;


/**
 * Represents an expression of the form <pre>L x1 y1 x2 y2</pre>. This class makes the following 
 * validations regarding its arguments:
 * 
 * <ul>
 * 	<li>They must be exactly four arguments</li>
 * 	<li>They must be numeric</li>
 * 	<li>They must be greater than or equal to zero</li>
 * 	<li>They must form a {@link #isHorizontal() horizontal} or {@link #isVertical() vertical} line</li>
 * </ul>
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class LineExpression implements DrawingExpression {
	private static final Logger _logger = LoggerFactory.getLogger(LoggerNames.APPLICATION.name());
	private static final LineExpressionPolicy _policy = new LineExpressionPolicy();
	
	private Point begin;
	private Point end;
	
	static LineExpression createFrom(Point begin, Point end) {
		return new LineExpression(begin, end);
	}
	
	private static Point getBegin(String[] individualArguments) {
		return Point.doCreatePoint(individualArguments, 2);
	}
	
	private static Point getEnd(String[] individualArguments) {
		return Point.doCreatePoint(individualArguments, 4);
	}
	
	public LineExpression(String arguments) {
		String[] individualArguments = ExpressionUtils.splitArguments(arguments);
		
		begin = getBegin(individualArguments);
		end = getEnd(individualArguments);
		
		checkState();
	}
	
	private LineExpression(Point begin, Point end) {
		this.begin = Point.clonePoint(begin);
		this.end = Point.clonePoint(end);
		
		checkState();
	}
	
	@Override
	public void interpret(Canvas canvas) {
		try {
			if (isHorizontal()) canvas.draw(begin.getY(), begin.getX(), end.getX(), DEFAULT_CHAR);
			else {
				int column = begin.getX();
				for (int row=begin.getY(); row<=end.getY(); row++) canvas.draw(row, column, column, DEFAULT_CHAR);
			}
		}
		catch (IllegalStateException | IllegalArgumentException e) {
			_logger.warn("LINE expression ignored: {} (Either out of canvas or canvas not initialized)", this);
		}
	}
	
	/**
	 * Returns <code>true</code> if a line is horizontal, and <code>false</code> otherwise. A line
	 * is horizontal if the <code>Y</code> coordinate of the origin and end points is equal.
	 * 
	 * @return
	 */
	public boolean isHorizontal() {
		return begin.getY() == end.getY();
	}
	
	/**
	 * Returns <code>true</code> if a line is vertical, and <code>false</code> otherwise. A line
	 * is vertical if the <code>X</code> coordinate of the origin and end points is equal.
	 * 
	 * @return
	 */
	public boolean isVertical() {
		return begin.getX() == end.getX();
	}
	
	public Point getBegin() {
		return Point.clonePoint(begin);
	}
	
	public Point getEnd() {
		return Point.clonePoint(end);
	}
	
	@Override
	public String toString() {
		return String.format("{begin: %s, end: %s}", begin, end);
	}
	
	protected void checkState() {
		_policy.check(this);
	}
}