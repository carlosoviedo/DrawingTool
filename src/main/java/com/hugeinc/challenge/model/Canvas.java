package com.hugeinc.challenge.model;

import static com.hugeinc.challenge.model.CanvasUtils.writeRow;

import java.io.OutputStream;
import java.util.Arrays;

/**
 * The canvas that can be used for painting. Clients can call is API to draw on
 * it. This class represents the <b>Context</b> in the Interpreter behavioural 
 * gof design pattern.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public class Canvas {
	private static final String INIT_NOT_ALLOWED_ERROR = "Initialization not allowed: canvas already initialized";
	private static final String DRAWING_NOT_ALLOWED_ERROR = "Drawing not allowed: canvas has not been initialized";
	private static final String DRAWING_OUT_OF_CANVAS_ERROR = "Drawing outside of canvas";
	
	private static final char HORIZONTAL_BORDER = '-';
	private static final char VERTICAL_BORDER = '|';
	private static final char BLANK = ' ';
	private enum State {CREATED, DRAWING}
	
	private State state = State.CREATED;
	private char[][] internalRepresentation;
	
	/**
	 * Initializes the canvas dimensions to the specified width and height.
	 * 
	 * @param width
	 * @param height
	 */
	public void init(int width, int height) {
		checkState(State.CREATED, INIT_NOT_ALLOWED_ERROR);
		
		internalRepresentation = new char[height+2][width+2];
		drawBorders();
		state = State.DRAWING;
	}

	public void draw(int row, int from, int to, char character) {
		checkState(State.DRAWING, DRAWING_NOT_ALLOWED_ERROR);
		checkLimitsWithinCanvas(row, to);
		Arrays.fill(internalRepresentation[row], from, to+1, character);
	}
	
	public boolean isBlank(int row, int column) {
		checkDotWithinCanvas(row, column);
		return internalRepresentation[column][row] == BLANK;
	}
	
	public boolean isWithin(int row, int column) {
		return !(isRowOutOfCanvas(row) || isColOutOfCanvas(column));
	}

	/**
	 * Draws the internal representation of the canvas on the specified output stream.
	 * 
	 * @param out
	 */
	public void drawOn(OutputStream out) {
		checkState(State.DRAWING, DRAWING_NOT_ALLOWED_ERROR);
		Arrays.stream(internalRepresentation).forEach(row -> writeRow(row, out));
	}
	
	@Override
	public String toString() {
		return "CANVAS: {}";
	}
	
	private void drawBorders() {
		int height = internalRepresentation.length,
			width = internalRepresentation[0].length;
		Arrays.fill(internalRepresentation[0], HORIZONTAL_BORDER);
		Arrays.fill(internalRepresentation[height-1], HORIZONTAL_BORDER);
		for (int i=1; i<height-1; i++) {
			internalRepresentation[i][0] = internalRepresentation[i][width-1] = VERTICAL_BORDER;
			Arrays.fill(internalRepresentation[i], 1, width-1, BLANK);
		}
	}

	private void checkState(State drawing, String drawingNotAllowedError) {
		if (state != drawing) throw new IllegalStateException("Operation not allowed on canvas");
	}
	
	private void checkLimitsWithinCanvas(int fromRow, int toRow) {
		if (fromRow > toRow) throw new IllegalArgumentException("Final row less than initial row");
		if (isRowOutOfCanvas(fromRow) || isRowOutOfCanvas(toRow)) throw new IllegalArgumentException(DRAWING_OUT_OF_CANVAS_ERROR);
	}
	
	private void checkDotWithinCanvas(int row, int column) {
		if (isRowOutOfCanvas(row) || isColOutOfCanvas(column)) throw new IllegalArgumentException(DRAWING_OUT_OF_CANVAS_ERROR);	
	}

	private boolean isRowOutOfCanvas(int row) {
		return row < 1 || row >= internalRepresentation[0].length-1;
	}
	
	private boolean isColOutOfCanvas(int column) {
		return column < 1 || column >= internalRepresentation.length-1;
	}
}