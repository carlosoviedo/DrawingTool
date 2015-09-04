package com.hugeinc.challenge.expression;

/**
 * Interface to be implemented by all expressions that interpret commands that
 * draw on a canvas.
 * 
 * @author <a href="mailto:carlos.oviedo"></a>
 */
public interface DrawingExpression {
	void interpret(Canvas canvas);
}