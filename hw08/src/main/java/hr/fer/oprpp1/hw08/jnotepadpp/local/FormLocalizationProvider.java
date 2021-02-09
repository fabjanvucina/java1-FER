package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * A localization provider proxy class which registers itself as window listener on the Swing frame container passed in the 
 * constructor and connects itself to the "real" localization provider when the frame opens or disconnects itself when the frame closes.
 * 
 * @author fabjanvucina
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		
		//add the window listener
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		});
	}
}
