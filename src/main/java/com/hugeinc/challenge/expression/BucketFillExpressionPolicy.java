package com.hugeinc.challenge.expression;


/**
 * Strategy class that validates bucket fill expressions. Currently this class performs no validation, as the only validation 
 * required is performed by the {@link Point#checkState() point} class when creating the 
 * {@link BucketFillExpression#getConnectionPoint() connection point}.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
class BucketFillExpressionPolicy implements DrawingPolicy<BucketFillExpression> {
	@Override
	public void check(BucketFillExpression expression) throws IllegalArgumentException {}
}