package com.hugeinc.challenge.core;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Optional;
import com.hugeinc.challenge.expression.DrawingExpression;

/**
 * <p>
 * Helper used by the {@link ObjectFactory factory} to create 
 * {@link DrawingExpression expression} objects. Expressions are of the form
 * <code>&lt;command&gt; &lt;arguments&gt;</code>, like: 
 * <b>L</b> <code>1 2 6 2</code>.
 * </p>
 * 
 * <p>
 * This class is a singleton.
 * </p>
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
final class Parser {
	private static Parser _instance;
	private static final Lock _instanceLock = new ReentrantLock();
	
	static Parser getInstance() {
		_instanceLock.lock();
		if (_instance == null) _instance = new Parser();
		_instanceLock.unlock();
		
		return _instance;
	}
	
	ParseResult parseCommand(String command) {
		checkCommand(command);
		return new ParseResult(getCommandName(command), getCommandArgs(command));
	}

	private void checkCommand(String command) {
		if (command == null || command.length() == 0) throw new IllegalArgumentException("Command cannot be empty");
	}
	
	private char getCommandName(String command) {
		return command.charAt(0);
	}
	
	private String getCommandArgs(String command) {
		return command.length() > 2 ? command.substring(2) : null;
	}
	
	/**
	 * Represents the result of parsing a drawing command string into its
	 * corresponding command and arguments.
	 */
	static class ParseResult {
		private char drawingCommand;
		private Optional<String> commandArgs;
		
		ParseResult(char command) {
			this(command, null);
		}
		
		ParseResult(char command, String args) {
			drawingCommand = command;
			commandArgs = Optional.fromNullable(args);
		}
		
		char getDrawingCommand() {
			return drawingCommand;
		}
		
		Optional<String> getCommandArgs() {
			return commandArgs;
		}
	}
}