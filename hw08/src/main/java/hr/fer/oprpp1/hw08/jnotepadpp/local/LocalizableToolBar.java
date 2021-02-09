package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JToolBar;

/**
 * A localizable subclass of JToolBar.
 * 
 * @author fabjanvucina
 */
public class LocalizableToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor.
	 * @param value
	 * @param lp localization provider
	 */
	public LocalizableToolBar(String name, ILocalizationProvider lp) {
		super(lp.getString(name));
		
		//listen for changes on the localization provider(lp is the proxy localization provider, no need for deregistering)
		lp.addLocalizationListener(new ILocalizationListener() {	
			@Override
			public void localizationChanged() {
				setName(lp.getString(name));
			}
		});
	}
}
