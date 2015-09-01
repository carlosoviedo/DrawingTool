package com.hugeinc.challenge.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.util.Arrays;

import org.junit.Test;

import rx.Observable;

import com.google.common.base.Optional;

/**
 * Base class for observable tests. This class defines template methods for each of the tests.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public abstract class ObservableTest {
	@Test
	public final void testObservableWithValidInput() {
		Observable<String> expressionsObservable = createObservable(ObservableTestUtils.createValidInputReader()); 
		doTestSuccessfulObservation(expressionsObservable, ObservableTestUtils._validInputExpressions);
	}
	
	@Test
	public final void testObservableTransformationWithValidInput() {
		Observable<String> expressionObservable = createObservable(ObservableTestUtils.createValidInputReader());
		Observable<TestCommand> commandObservable = expressionObservable.map(this::createTestCommand);
		doTestSuccessfulObservation(commandObservable, ObservableTestUtils._validInputCommands);
	}
	
	@Test
	public final void testObservableWithInvalidInput() {
		Observable<String> expressionObservable = createObservable(ObservableTestUtils.createInvalidInputReader());
		doTestErrorObservation(expressionObservable);
	}
	
	@Test
	public final void testUnsubscribeObservable() {
		Observable<String> expressionObservable = createObservable(ObservableTestUtils.createValidInputReader());
		String[] expectedExpressions = Arrays.stream(ObservableTestUtils._validInputExpressions, 0, 3).toArray(size -> new String[size]);
		doTestUnsubscribeObservable(expressionObservable, expectedExpressions, 3);
	}
	
	private TestCommand createTestCommand(String expr) {
		return new TestCommand(expr.charAt(0), expr.substring(2).split(" "));
	}
	
	protected abstract Observable<String> createObservable(Reader inputReader);
	
	private <T> void doTestSuccessfulObservation(Observable<T> expressionObservable, T[] expectedExpressions) {
		DataCollector<T> observer = new DataCollector<>();
		expressionObservable.subscribe(observer);
		Optional<T[]> expressions = observer.getData();
		
		assertTrue("Data not available due to an exception", expressions.isPresent());
		assertTrue("Complete was not called", observer.isCompleteCalled());
		assertTrue("Error was called", !observer.isErrorCalled());
		assertEquals("Incorrect number of items", 5, expressions.get().length);
		assertTrue("One or more items are not as expected", Arrays.equals(expectedExpressions, expressions.get()));
	}
	
	private <T> void doTestErrorObservation(Observable<T> expressionObservable) {
		DataCollector<T> observer = new DataCollector<>();
		expressionObservable.subscribe(observer);
		Optional<T[]> expressions = observer.getData();
		
		assertTrue("Exception was not registered", !expressions.isPresent());
		assertTrue("Complete was called", !observer.isCompleteCalled());
		assertTrue("Error was not called", observer.isErrorCalled());
	}
	
	private <T> void doTestUnsubscribeObservable(Observable<T> expressionObservable, T[] expectedExpressions, int unsubscribeAfter) {
		DataCollector<T> collector = new DataCollector<>();
		UnsubscribeDataCollector<T> observer = new UnsubscribeDataCollector<>(collector, unsubscribeAfter);
		expressionObservable.subscribe(observer);
		Optional<T[]> expressions = collector.getData();
		
		assertTrue("Data not available due to an exception", expressions.isPresent());
		assertTrue("Complete was called", !collector.isCompleteCalled());
		assertTrue("Error was called", !collector.isErrorCalled());
		assertEquals("Incorrect number of items", 3, expressions.get().length);
		assertTrue("One or more items are not as expected", Arrays.equals(expectedExpressions, expressions.get()));
	}
}