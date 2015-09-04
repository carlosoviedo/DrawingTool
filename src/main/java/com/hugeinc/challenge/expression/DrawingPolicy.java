package com.hugeinc.challenge.expression;

/**
 * Strategy interface to be implemented by classes that know how to validate drawing
 * expressions.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public interface DrawingPolicy<T extends DrawingExpression> {
	/**
	 * 
	 * @param expression
	 * @return
	 * @throws IllegalArgumentException if the state of the expression is not valid.
	 */
	void check(T expression) throws IllegalArgumentException; 
}