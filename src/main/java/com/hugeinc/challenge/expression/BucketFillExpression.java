package com.hugeinc.challenge.expression;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hugeinc.challenge.core.LoggerNames;
import com.hugeinc.challenge.model.Canvas;

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
	private static final Logger _logger = LoggerFactory.getLogger(LoggerNames.APPLICATION.name());
	private static final BucketFillExpressionPolicy _policy = new BucketFillExpressionPolicy();
	
	private Point connectionPoint;
	private char color;
	
	private static Point[] getSurroundingPoints(Point connectionPoint) {
		int x = connectionPoint.getX(),
			y = connectionPoint.getY();
		Point upperLeft = new Point(x-1, y-1),
				upperMiddle = new Point(x, y-1),
				upperRight = new Point(x+1, y-1),
				middleLeft = new Point(x-1, y),
				middleRight = new Point(x+1, y),
				lowerLeft = new Point(x-1, y+1),
				lowerMiddle = new Point(x, y+1),
				lowerRight = new Point(x+1, y+1);
		return new Point[]{upperLeft, upperMiddle, upperRight, middleLeft, middleRight, lowerLeft, lowerMiddle, lowerRight};
	}
	
	private static BucketFillExpression createFrom(Point connectionPoint, char color) {
		return new BucketFillExpression(connectionPoint, color);
	}
	
	public BucketFillExpression(String arguments) {
		String[] individualArguments = ExpressionUtils.splitArguments(arguments);
		connectionPoint = getConnectionPoint(individualArguments);
		color = getColor(individualArguments);
		
		checkState();
	}

	private BucketFillExpression(Point connectionPoint, char color) {
		this.connectionPoint = Point.clonePoint(connectionPoint);
		this.color = color;
	}

	@Override
	public void interpret(Canvas canvas) {
		try {
			int row = connectionPoint.getX(),
				column = connectionPoint.getY();
				
			if (canvas.isBlank(connectionPoint.getX(), connectionPoint.getY())) canvas.draw(column, row, row, color);
			
			Arrays.stream(getSurroundingPoints(connectionPoint))
				.filter(point -> canvas.isWithin(point.getX(), point.getY()) && canvas.isBlank(point.getX(), point.getY()))
				.parallel()
				.forEach(point -> createFrom(point, color).interpret(canvas));
		}
		catch (IllegalStateException | IllegalArgumentException e) {
			_logger.warn("BUCKET FILL expression ignored: {} (Either out of canvas or canvas not initialized)", this);
		}
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