package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * A class which represents the state of a symbolic turtle object which travels along the display. 
 * The actions performed to generate a Lindermayer system always take into account the current state of the "turtle".
 * 
 * @author fabjanvucina
 *
 */
public class TurtleState {
	
	/**
	 * Current vector position of the turtle
	 */
	public Vector2D currentPos;
	
	/**
	 * Current riection vector of the turtle
	 */
	public Vector2D direction;
	
	/**
	 * Current drawing color of the turtle
	 */
	public Color color;
	
	/**
	 * Current single unit of travel distance for the turtle
	 */
	public double effDistance;
	
	/**
	 * Public constructor for a new <code>TurtleState</code> object.
	 * @param currentPos
	 * @param direction
	 * @param color
	 * @param effDistance
	 */
	public TurtleState(Vector2D currentPos, Vector2D direction, Color color, double effDistance) {
		this.currentPos = currentPos;
		this.direction = direction;
		this.color = color;
		this.effDistance = effDistance;
	}
	
	/**
	 * Changes to the copy don't affect the original turtle
	 * @return copy of current turtle
	 */
	public TurtleState copy() {
		return new TurtleState(this.currentPos.copy(), this.direction.copy(), this.color, this.effDistance);
	}
}
