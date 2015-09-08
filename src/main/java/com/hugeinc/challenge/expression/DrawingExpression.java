package com.hugeinc.challenge.expression;

import com.hugeinc.challenge.model.Canvas;

/**
 * Interface to be implemented by all expressions that interpret commands that
 * draw on a canvas.
 * 
 * @author <a href="mailto:carlos.oviedo"></a>
 */
public interface DrawingExpression {
	char DEFAULT_CHAR = 'x';
	
	void interpret(Canvas canvas);
}