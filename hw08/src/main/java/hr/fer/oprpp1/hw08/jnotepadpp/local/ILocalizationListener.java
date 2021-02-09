package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * A functional interface which represents a listener which listents to language changes in a localization provider.
 * 
 * @author fabjanvucina
 */
public interface ILocalizationListener {

	/**
	 * A method called by the observed subject localization provider when the language changes.
	 */
	void localizationChanged();
}
