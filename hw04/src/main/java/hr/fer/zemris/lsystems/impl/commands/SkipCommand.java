package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
* A class which represents a command that skips a part of the canvas while generating a new Lindermayer system
* 
* @author fabjanvucina
*/
public class SkipCommand implements Command {

	/**
	 * A variable that stores the step which the skip command takes
	 */
	private double step;
	
	/**
	 * Public constructor.
	 * @param step factor with which the turtle effective distance is multiplied by
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currTurtleState = ctx.getCurrentState();
		
		//calculate destination coordinates
		Vector2D stepVector = currTurtleState.direction.scaled(step * currTurtleState.effDistance);
		Vector2D destinationVector = currTurtleState.currentPos.added(stepVector);
		
		//set turtle position to destination coordinates
		currTurtleState.currentPos = destinationVector;
	}

}
