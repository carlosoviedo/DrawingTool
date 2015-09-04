package com.hugeinc.challenge.expression;

import java.util.Arrays;

import rx.functions.Func0;

/**
 * Utility methods used by expression classes.
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public final class ExpressionUtils {
	/**
	 * Scope set to private to prevent instantiation.
	 */
	private ExpressionUtils() {}
	
	public static String[] splitArguments(String arguments) {
		if (arguments == null || arguments.length() == 0) throw new IllegalArgumentException("Arguments cannot be null or empty");
		return arguments.split("\\s+");
	}
	
	/**
	 * Transforms a string argument to its numeric representation.
	 * 
	 * @param argument
	 * @return
	 * @throws IllegalArgumentException if the specified argument is not a number.
	 */
	public static int getArgumentAsInteger(String argument) {
		try {
			return Integer.parseInt(argument);
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Argument is not a number: " + argument);
		}
	}
	
	public static void checkGreaterThanZero(Func0<Integer> accessor) {
		Integer value = accessor.call();
		if (value <= 0) throw new IllegalArgumentException("Invalid property value");
	}
	
	public static void checkGreaterOrEqualToZero(Func0<Integer> accessor) {
		Integer value = accessor.call();
		if (value < 0) throw new IllegalArgumentException("Invalid property value");
	}
	
	@SafeVarargs
	public static void checkAnyTrue(Func0<Boolean>... conditions) {
		if (conditions == null || conditions.length == 0) throw new IllegalArgumentException("Conditions cannot be null or empty");
		if (!Arrays.stream(conditions).anyMatch(predicate -> predicate.call())) throw new IllegalArgumentException("No condition is satisfied");
	}
	
	@SafeVarargs
	public static void checkAllTrue(Func0<Boolean>... conditions) {
		if (conditions == null || conditions.length == 0) throw new IllegalArgumentException("Conditions cannot be null or empty");
		if (!Arrays.stream(conditions).allMatch(predicate -> predicate.call())) throw new IllegalArgumentException("At least one condition was satisified");
	}
	
	@SafeVarargs
	public static void checkNoneTrue(Func0<Boolean>... conditions) {
		if (conditions == null || conditions.length == 0) throw new IllegalArgumentException("Conditions cannot be null or empty");
		if (!Arrays.stream(conditions).noneMatch(predicate -> predicate.call())) throw new IllegalArgumentException("At least one condition was satisified");
	}
}