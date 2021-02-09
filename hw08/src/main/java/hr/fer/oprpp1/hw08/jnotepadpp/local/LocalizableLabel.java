package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * A localizable subclass of JLabel.
 * 
 * @author fabjanvucina
 */
public class LocalizableLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Label name
	 */
	private String name;
	
	/**
	 * The localization provider
	 */
	private ILocalizationProvider lp;

	/**
	 * Public constructor.
	 * @param name
	 * @param lp localization provider
	 */
	public LocalizableLabel(String name, int value, ILocalizationProvider lp) {		
		this.name = name;
		this.lp = lp;
		
		setContent(value);
		
		//listen for changes on the localization provider(lp is the proxy localization provider, no need for deregistering)
		lp.addLocalizationListener(new ILocalizationListener() {	
			@Override
			public void localizationChanged() {
				setContent(value);
			}
		});
	}
	
	/**
	 * A public setter for the label text content.
	 * @param value
	 */
	public void setContent(int value) {
		setText(value == -1 ? lp.getString(name) : lp.getString(name) + String.valueOf(value));
	}
}
