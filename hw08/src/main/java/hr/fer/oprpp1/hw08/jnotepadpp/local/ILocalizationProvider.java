package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * An interface which represents a contract of communication with objects which provide translations for given keys.
 * 
 * @author fabjanvucina
 */
public interface ILocalizationProvider {

	/**
	 * A method which registers a new listener which is interested if the selected language of the localization provider changes.
	 * @param l new listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * A method for de-registering a listener of language changes.
	 * @param l listener
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * A method which returns the translation for the given key
	 * @param key
	 * @return translation
	 */
	String getString(String key);
	
	/**
	 * @return current language
	 */
	String getCurrentLanguage();
}
