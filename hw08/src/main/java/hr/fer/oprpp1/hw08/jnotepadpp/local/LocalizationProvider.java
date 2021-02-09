package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A localization provider singleton class which provides translations for given keys.
 * @author fabjanvucina
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	/**
	 * Current language of the localization provider.
	 */
	private String language;
	
	/**
	 * Resource bundle for the current language.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Single instance of the class.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Private constructor.
	 */
	private LocalizationProvider() {
		this.language = "en";
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
	}
	
	/**
	 * @return single instance of the class
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * A setter method for the localization provider which changes the language and notifies all the listeners.
	 * @param language
	 * @throws NullPointerException if <code>language == null</code>
	 */
	public void setLanguage(String language) {
		if (language == null) throw new NullPointerException("You passed a null reference as language.");
		
		this.language = language;
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
		
		//notify all the listeners of the language change
		this.fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
