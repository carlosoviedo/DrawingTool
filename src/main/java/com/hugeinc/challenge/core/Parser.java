package com.hugeinc.challenge.core;

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
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public final class Parser {
	public ParseResult parse(String command) {
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
	public static class ParseResult {
		private char drawingCommand;
		private Optional<String> commandArgs;
		
		ParseResult(char command) {
			this(command, null);
		}
		
		ParseResult(char command, String args) {
			drawingCommand = command;
			commandArgs = Optional.fromNullable(args);
		}
		
		public char getDrawingCommand() {
			return drawingCommand;
		}
		
		public Optional<String> getCommandArgs() {
			return commandArgs;
		}
	}
}