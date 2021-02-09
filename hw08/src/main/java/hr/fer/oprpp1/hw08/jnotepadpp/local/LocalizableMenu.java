package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * A localizable subclass of JMenu.
 * 
 * @author fabjanvucina
 */
public class LocalizableMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor.
	 * @param nameKey
	 * @param lp
	 */
	public LocalizableMenu(String nameKey, ILocalizationProvider lp) {
		super(lp.getString(nameKey));
		lp.addLocalizationListener(() -> setText(lp.getString(nameKey)));
	}
}
