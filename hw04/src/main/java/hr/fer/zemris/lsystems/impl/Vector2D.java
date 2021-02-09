package hr.fer.zemris.lsystems.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Two-dimensional vector class.
 * 
 * @author fabjanvucina
 */
public class Vector2D {

	/**
	 * A public static final object which represents the "i" vector
	 */
	public static final Vector2D I = new Vector2D(1, 0);
	
	/**
	 * A public static final object which represents the "i" vector
	 */
	public static final Vector2D J = new Vector2D(0, 1);

	/**
	 * Horizontal component of the vector
	 */
	private double x;
	
	/**
	 * Vertical component of the vector
	 */
	private double y;
	
	
	/**
	 * Public constructor.
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * @return horizontal component of this vector
	 */
	public double getX() {
		return x;
	}


	/**
	 * @return vertical component of this vector
	 */
	public double getY() {
		return y;
	}



	/**
	 * A method which adds the <code>offset</code> vector to the current vector.
	 * @param offset 
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	
	/**
	 * A method which adds the <code>offset</code> vector to the current vector and returns it as a new object.
	 * @param offset
	 * @return new vector object
	*/
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
		
	}
	
	
	/**
	 * A method which rotates the current vector by a specified angle.
	 * @param angle
	 */
	public void rotate(double angle) {
		var currentX = this.x;
		var currentY = this.y;
		
		this.x = currentX * cos(angle) - currentY * sin(angle);
		this.y = currentX * sin(angle) + currentY * cos(angle);
	}
	
	
	/**
	 * A method which rotates the current vector by specified angle and returns the result as a new vector.
	 * @param angle
	 * @return new vector object
	 */
	public Vector2D rotated(double angle) {
		var currentX = this.x;
		var currentY = this.y;
		
		x = currentX * cos(angle) - currentY * sin(angle);
		y = currentX * sin(angle) + currentY * cos(angle);
		
		return new Vector2D(x, y);
	}
	
	
	/**
	 * A method which scales the current vector by a specified factor.
	 * @param scaler
	 */

	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	
	/**
	 * A method which scales the current vector by specified factor and returns the result as a new vector.
	 * @param scaler
	 * @return new vector object
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	
	/**
	 * @return copy of the current vector object
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
