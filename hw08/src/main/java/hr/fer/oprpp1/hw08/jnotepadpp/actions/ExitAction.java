package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * An implementation of the LocalizableAction abstract class which exists the application when performed.
 * 
 * @author fabjanvucina
 */
public class ExitAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param lp
	 */
	public ExitAction(ILocalizationProvider lp) {
		
		super("exit", "exitAccel", "exitMnem", "exitDescript", lp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
