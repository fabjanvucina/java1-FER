package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;   

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * An implementation of the LocalizableAction abstract class which transforms the selected text to uppercase when performed.
 * 
 * @author fabjanvucina
 */
public class ChangeLanguageAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The new language
	 */
	private String language;

	/**
	 * Public constructor.
	 * @param language
	 * @param nameKey
	 * @param accelaratorKey
	 * @param mnemonicKey
	 * @param descriptionKey
	 * @param lp
	 */
	public ChangeLanguageAction(String language, String nameKey, String accelaratorKey, String mnemonicKey, 
								String descriptionKey, ILocalizationProvider lp) {
		
		super(nameKey, accelaratorKey, mnemonicKey, descriptionKey, lp);
		
		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
	}
}
