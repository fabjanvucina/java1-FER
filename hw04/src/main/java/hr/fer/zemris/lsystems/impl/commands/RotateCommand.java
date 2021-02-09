package hr.fer.zemris.lsystems.impl.commands;

import static java.lang.Math.toRadians;
import hr.fer.zemris.lsystems.Painter; 
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class which represents a command which rotates the direction vector of the current turtle state
 * 
 * @author fabjanvucina
 */
public class RotateCommand implements Command {

	/**
	 * Rotation angle variable
	 */
	private double angle;
	
	/**
	 * Public constructor.
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = toRadians(angle);
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState()
		   .direction
		   .rotate(angle);
	}
}
