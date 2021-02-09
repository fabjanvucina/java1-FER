package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class which represents a command which scales the current effective distance the current turtle takes
 * 
 * @author fabjanvucina
 */
public class ScaleCommand implements Command {
	
	/**
	 * Factor variable
	 */
	private double factor;
	
	/**
	 * Public constructor.
	 * @param factor the factor the effective distance of the turtle is multiplied with
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().effDistance *= factor;
	}
}
