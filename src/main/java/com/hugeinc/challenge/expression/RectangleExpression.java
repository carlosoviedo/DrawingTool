package com.hugeinc.challenge.expression;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hugeinc.challenge.core.LoggerNames;
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
	private static final Logger _logger = LoggerFactory.getLogger(LoggerNames.APPLICATION.name());
	private static final RectangleExpressionPolicy _policy = new RectangleExpressionPolicy();
	
	private static LineExpression[] getLines(Point begin, Point end) {
		LineExpression[] lines = new LineExpression[4];
		Point upperLeftCorner = begin,
				lowerLeftCorner = new Point(begin.getX(), end.getY()),
				upperRightCorner = new Point(end.getX(), begin.getY()),
				lowerRightCorner = end;
		lines[0] = LineExpression.createFrom(upperLeftCorner, upperRightCorner);
		lines[1] = LineExpression.createFrom(lowerLeftCorner, lowerRightCorner);
		lines[2] = LineExpression.createFrom(upperLeftCorner, lowerLeftCorner);
		lines[3] = LineExpression.createFrom(upperRightCorner, lowerRightCorner);
		return lines;
	}
	
	public RectangleExpression(String arguments) {
		super(arguments);
	}
	
	@Override
	public void interpret(Canvas canvas) {
		try {
			Arrays
			.stream(getLines(getBegin(), getEnd()))
			.forEach(lineExpression -> lineExpression.interpret(canvas));
		}
		catch (IllegalStateException | IllegalArgumentException e) {
			_logger.warn("RECTANGLE expression ignored: {} (Either out of canvas or canvas not initialized)", this);
		}
	}

	@Override
	protected void checkState() {
		_policy.check(this);   
	}
}