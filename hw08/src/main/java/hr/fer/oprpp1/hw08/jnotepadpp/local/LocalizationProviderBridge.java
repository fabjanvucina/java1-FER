package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * A localization provider class which decorates the "real" localization provider. 
 * It registers itself as a listener on the "real" localization provider and provides a bridge(proxy) between it and the frame actions that need translating.
 * Its function is to prevent a memory leak when the frame gets disposed. 
 * On frame closing, the bridge has to de-register itself from the "real" localization provider.
 * 
 * @author fabjanvucina
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * The connection status.
	 */
	private boolean connected;
	
	/**
	 * Listener for the "real" localization provider.
	 */
	private ILocalizationListener listener;
	
	/**
	 * The decorated ILocalizationProvider object, we need it to get translations
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Language cache used to notify all the listeners on reconnection if there was a change during the disconnected stage.
	 */
	private String languageCache;
	
	/**
	 * Public constructor
	 * @param parent the "real" localization provider
	 * @throws NullPointerException if <code>parent == null</code>
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		if (parent == null) throw new NullPointerException("You passed a null reference as parent.");
		
		this.parent = parent;
		this.connected = false;
		this.languageCache = null;
	}
	
	/**
	 * Method which connects the bridge to the real localization provider.
	 */
	public void connect() {
		if(!connected) {
			connected = true;
			
			//there was a language change during the disconnected phase
			if(languageCache != null && !languageCache.equals(getCurrentLanguage())) {
				fire();
			}
			
			//create a listener instance
			listener = new ILocalizationListener() {
				@Override
				public void localizationChanged() {
					fire();
					
				}
			};
			
			//register it on the "real" localization provider
			parent.addLocalizationListener(listener);
		}
	}
	
	/**
	 * Method which disconnects the bridge from the real localization provider.
	 */
	public void disconnect() {
		if(connected) {
			connected = false;
			
			//cache the language
			languageCache = getCurrentLanguage();
			
			//de-register the listener from the "real" localization provider
			//this allows the frame to be collected by the garbage collector
			parent.removeLocalizationListener(listener);
		}
	}
	
	
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
}
