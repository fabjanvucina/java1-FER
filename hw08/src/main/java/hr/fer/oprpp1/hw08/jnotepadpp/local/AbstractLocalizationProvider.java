package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * A localization provider abstract class which provides a mechanism for registering and de-registering listeners.
 * 
 * @author fabjanvucina
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	/**
	 * Collection of listeners on a localization provider.
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Constructor for initializing the listeners collection.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if (l == null) throw new NullPointerException("You passed a null reference as l.");
		
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		if (l == null) throw new NullPointerException("You passed a null reference as parent.");
		
		listeners.remove(l);
	}
	
	/**
	 * A method which notifies all the listeners of a language change.
	 */
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
