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
	private static final char HORIZONTAL_BORDER = '-';
	private static final char VERTICAL_BORDER = '|';
	private enum State {CREATED, DRAWING}
	
	private State state = State.CREATED;
	private char[][] internalCanvas;
	
	/**
	 * Initializes the canvas dimensions to the specified width and height.
	 * 
	 * @param width
	 * @param height
	 */
	public void init(int width, int height) {
		internalCanvas = new char[height+2][width+2];
		drawBorders();
		state = State.DRAWING;
	}
	
	private void drawBorders() {
		int height = internalCanvas.length,
			width = internalCanvas[0].length;
		Arrays.fill(internalCanvas[0], HORIZONTAL_BORDER);
		Arrays.fill(internalCanvas[height-1], HORIZONTAL_BORDER);
		for (int i=1; i<height-1; i++) {
			internalCanvas[i][0] = internalCanvas[i][width-1] = VERTICAL_BORDER;
			Arrays.fill(internalCanvas[i], 1, width-1, ' ');
		}
	}

	public void drawOn(OutputStream out) {
		if (state != State.DRAWING) throw new IllegalStateException("Canvas has not been initialized");
		Arrays.stream(internalCanvas).forEach(row -> writeRow(row, out));
	}

	@Override
	public String toString() {
		return "CANVAS: {}";
	}
}