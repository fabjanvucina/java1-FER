package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Lindermayer system build command functional interface.
 * 
 * @author fabjanvucina
 */
public interface Command {

	/**
	 * @param ctx the current turtle state context
	 * @param painter the current canvas
	 */
	void execute(Context ctx, Painter painter);
}
