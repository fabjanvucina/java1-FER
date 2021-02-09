package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class which represents a command which changes the color of the current turtle state
 * 
 * @author fabjanvucina
 */
public class ColorCommand implements Command {
	
	private Color color;
	
	/**
	 * Public constructor.
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().color = color;
	}
}
