package com.hugeinc.challenge.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import rx.functions.Action2;

import com.google.common.base.Optional;
import com.hugeinc.challenge.expression.DrawingExpression;
import com.hugeinc.challenge.expression.Point;

/**
 * Performs tests over {@link ObjectFactory} class scoped to just expression creation. 
 * The following tests are performed:
 * 
 * <ol>
 * 	<li>Create a drawing expression wit {@link #testCreateDrawingExpressionWithNoArguments() no arguments}. This test 
 * 		would work for all drawing expressions, as all of them expect at least one argument</li>
 * 	<li>Create a <b>canvas</b> expression with {@link #testCreateCanvasExpressionWithCorrectArguments() correct arguments}</li>
 * 	<li>Create a <b>canvas</b> expression with {@link #testCreateCanvasExpressionWithWrongNumberArguments() wrong number} 
 * 		of arguments</li>
 * 	<li>Create a <b>canvas</b> expression with right number of arguments but 
 * 		{@link #testCreateCanvasExpressionWithInvalidArguments invalid values}</li>
 * 	<li>Create a <b>line</b> expression with {@link #testCreateLineExpressionWithCorrectArguments() correct arguments}</li>
 * 	<li>Create a <b>line</b> expression with {@link #testCreateLineExpressionWithWrongNumberArguments() wrong number} of 
 * 		arguments</li>
 * 	<li>Create a <b>line</b> expression with right number of arguments but {@link #testCreateLineExpressionWithInvalidArguments() invalid values}</li>
 * 	<li>Create a <b>line</b> expression that does not represent 
 * 		{@link #testCreateLineExpressionNotHorizontalVertical() neither a horizontal nor a vertical} line</li>
 * 	<li>Create a <b>rectangle</b> expression with {@link #testCreateRectangleExpressionWithCorrectArguments() correct arguments}</li>
 * 	<li>Create a <b>rectangle</b> expression with {@link #testCreateRectangleExpressionWithWrongNumberArguments() wrong number} of arguments</li>
 * 	<li>Create a <b>rectangle</b> expression with right number of arguments but 
 * 		{@link #testCreateRectangleExpressionWithInvalidArguments() invalid values}</li>
 *  <li>Create a <b>rectangle</b> expression that {@link #testCreateRectangleExpressionIsNotRectangle() is not a rectangle}, i.e., the end point is not 
 *  	{@link Point#isRightOf(Point) to the right} of the begin point, and the end point is not {@link Point#isHigherThan(Point) higher}
 *  	than begin point</li>
 * 	<li>Create a <b>bucket fill</b> expression with {@link #testCreateBucketFillExpressionWithCorrectArguments() correct arguments}</li>
 * 	<li>Create a <b>bucket fill</b> expression with {@link #testCreateBucketFillExpressionWithWrongNumberArguments() wrong number} of arguments</li>
 * 	<li>Create a <b>bucket fill</b> expression with right number of arguments but 
 * 		{@link #testCreateBucketFillExpressionWithInvalidArguments() invalid values}</li>
 * </ol>
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class ObjectFactoryExpressionTest {
	@Test(expected=IllegalStateException.class)
	public void testCreateDrawingExpressionWithNoArguments() throws ClassNotFoundException {
		doTestCreateCanvasExpression(null, (createdExpression, expectedClass) -> {});
	}
	
	@Test
	public void testCreateCanvasExpressionWithCorrectArguments() throws ClassNotFoundException {
		doTestCreateCanvasExpression("20 4", ObjectFactoryExpressionTest::testCreateExpressionAssertions);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateCanvasExpressionWithWrongNumberArguments() throws Throwable {
		try {
			doTestCreateCanvasExpression("20", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateCanvasExpressionWithInvalidArguments() throws Throwable {
		try {
			doTestCreateCanvasExpression("2E 4", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public void testCreateLineExpressionWithCorrectArguments() throws ClassNotFoundException {
		doTestCreateLineExpression("1 2 6 2", ObjectFactoryExpressionTest::testCreateExpressionAssertions);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateLineExpressionWithWrongNumberArguments() throws Throwable {
		try {
			doTestCreateLineExpression("1 2 6", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateLineExpressionWithInvalidArguments() throws Throwable {
		try {
			doTestCreateLineExpression("1 S 6 2", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateLineExpressionNotHorizontalVertical() throws Throwable {
		try {
			doTestCreateLineExpression("1 2 3 4", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public void testCreateRectangleExpressionWithCorrectArguments() throws ClassNotFoundException {
		doTestCreateRectangleExpression("16 1 20 3", ObjectFactoryExpressionTest::testCreateExpressionAssertions);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateRectangleExpressionWithWrongNumberArguments() throws Throwable {
		try {
			doTestCreateRectangleExpression("16 20 3", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateRectangleExpressionWithInvalidArguments() throws Throwable {
		try {
			doTestCreateRectangleExpression("16 1 2g0 3", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateRectangleExpressionIsNotRectangle() throws Throwable {
		try {
			doTestCreateRectangleExpression("16 1 14 3", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public void testCreateBucketFillExpressionWithCorrectArguments() throws ClassNotFoundException {
		doTestCreateBucketFillExpression("10 3 o", ObjectFactoryExpressionTest::testCreateExpressionAssertions);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateBucketFillExpressionWithWrongNumberArguments() throws Throwable {
		try {
			doTestCreateBucketFillExpression("10 o", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateBucketFillExpressionWithInvalidArguments() throws Throwable {
		try {
			doTestCreateBucketFillExpression("10 3 ox", (createdExpression, expectedClass) -> {});
		}
		catch (IllegalStateException e) {
			throw e.getCause();
		}
	}

	private static void doTestCreateCanvasExpression(String commandArgs, Action2<DrawingExpression, Class<?>> assertions) throws ClassNotFoundException {
		doTestCreateExpression("C", commandArgs, assertions, "com.hugeinc.challenge.Expression.C", "com.hugeinc.challenge.expression.CanvasExpression");
	}
	
	private static void doTestCreateLineExpression(String commandArgs, Action2<DrawingExpression, Class<?>> assertions) throws ClassNotFoundException {
		doTestCreateExpression("L", commandArgs, assertions, "com.hugeinc.challenge.Expression.L", "com.hugeinc.challenge.expression.LineExpression");
	}
	
	private void doTestCreateRectangleExpression(String commandArgs, Action2<DrawingExpression, Class<?>> assertions) throws ClassNotFoundException {
		doTestCreateExpression("R", commandArgs, assertions, "com.hugeinc.challenge.Expression.R", "com.hugeinc.challenge.expression.RectangleExpression");
	}
	
	private void doTestCreateBucketFillExpression(String commandArgs, Action2<DrawingExpression, Class<?>> assertions) throws ClassNotFoundException {
		doTestCreateExpression("B", commandArgs, assertions, "com.hugeinc.challenge.Expression.B", "com.hugeinc.challenge.expression.BucketFillExpression");
	}
	
	private static void doTestCreateExpression(String commandName,
										String commandArgs, 
										Action2<DrawingExpression, Class<?>> assertions, 
										String componentInterfaceName, 
										String implementationClassName) throws ClassNotFoundException {
		Class<?> implementationClass = Class.forName(implementationClassName);
		
		Map<String, String> properties = new HashMap<>(2);
		properties.put(componentInterfaceName, implementationClassName);
		Properties configuration = ObjectFactoryTestUtils.mockNonEmptyConfiguration(properties);
		
		ObjectFactory factory = ObjectFactory.createObjectFactory(configuration);		
		DrawingExpression expr = factory.createExpression(commandName, Optional.fromNullable(commandArgs));
		assertions.call(expr, implementationClass);
	}
	
	private static void testCreateExpressionAssertions(DrawingExpression createdExpression, Class<?> expectedClass) {
		assertNotNull("Drawing expression is null", createdExpression);
		assertTrue("Drawing expression is of unexpected class", expectedClass.isInstance(createdExpression));
	}
}