package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * An abstract class which models a localized <code>AbstractAction</code> object.
 * 
 * @author fabjanvucina
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor.
	 * @param nameKey
	 * @param accelaratorKey
	 * @param mnemonicKey
	 * @param descriptionKey
	 * @param lp localization provider
	 * @throws NullPointerException if any of the keys is <code>null</code> or if the localization provider is <code>null</code>
	 */
	public LocalizableAction(String nameKey, String accelaratorKey, String mnemonicKey, 
							 String descriptionKey, ILocalizationProvider lp) {
		
		if(nameKey == null) throw new NullPointerException("Name key should not be null");
		if(accelaratorKey == null) throw new NullPointerException("Accelerator key should not be null");
		if(mnemonicKey == null) throw new NullPointerException("Mnemonic key should not be null");
		if(descriptionKey == null) throw new NullPointerException("Description key should not be null");
		
		//set action properties
		putValue(Action.NAME, lp.getString(nameKey));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(lp.getString(accelaratorKey)));
		putValue(Action.MNEMONIC_KEY, (int)lp.getString(mnemonicKey).charAt(0));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(descriptionKey));
		
		//listen for changes on the localization provider(lp is the proxy localization provider, no need for deregistering)
		lp.addLocalizationListener(new ILocalizationListener() {	
			@Override
			public void localizationChanged() {
				
				//remember old property values
				String oldNameValue = (String) getValue(Action.NAME);
				KeyStroke oldAccelaratorValue = (KeyStroke) getValue(Action.ACCELERATOR_KEY);
				int oldMnemonicValue = (int) getValue(Action.MNEMONIC_KEY);
				String oldDescriptionValue = (String) getValue(Action.SHORT_DESCRIPTION);
				
				//set new action properties
				putValue(Action.NAME, lp.getString(nameKey));
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(lp.getString(accelaratorKey)));
				putValue(Action.MNEMONIC_KEY, (int)lp.getString(mnemonicKey).charAt(0));
				putValue(Action.SHORT_DESCRIPTION, lp.getString(descriptionKey));
				
				//notify GUI property change listeners
				firePropertyChange(Action.NAME, oldNameValue, getValue(Action.NAME));
				firePropertyChange(Action.ACCELERATOR_KEY, oldAccelaratorValue, getValue(Action.ACCELERATOR_KEY));
				firePropertyChange(Action.MNEMONIC_KEY, oldMnemonicValue, getValue(Action.MNEMONIC_KEY));
				firePropertyChange(Action.SHORT_DESCRIPTION, oldDescriptionValue, getValue(Action.SHORT_DESCRIPTION));
			}
		});
	}
}
