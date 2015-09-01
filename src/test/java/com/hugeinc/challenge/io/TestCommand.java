package com.hugeinc.challenge.io;

import java.util.Arrays;

class TestCommand {
	private char command;
	private String[] arguments;
	
	public TestCommand(char command, String[] arguments) {
		this.command = command;
		this.arguments = arguments;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof TestCommand)) return false;
	
		TestCommand other = (TestCommand) obj;
		return command == other.command && Arrays.equals(arguments, other.arguments);
	}
	
	@Override
	public String toString() {
		return "{command: " + command + ", arguments: " + toString(arguments) + "}";
	}

	private String toString(String[] arguments) {
		StringBuilder representation = new StringBuilder('[');
		Arrays.stream(arguments).forEach(arg -> representation.append(arg).append(' '));
		return representation
				.deleteCharAt(representation.length()-1)
				.append(']')
				.toString();
	}
}