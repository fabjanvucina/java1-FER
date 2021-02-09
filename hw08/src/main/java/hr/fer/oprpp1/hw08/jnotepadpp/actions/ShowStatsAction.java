package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

/**
 * An implementation of the LocalizableAction abstract class which displays the current document stats when performed.
 * 
 * @author fabjanvucina
 */
public class ShowStatsAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model for which the action is defined.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * The frame for which the option dialogs will be shown
	 */
	private JNotepadPP frame;
	
	/**
	 * The localization provider
	 */
	private ILocalizationProvider lp;

	/**
	 * Public constructor.
	 * @param lp
	 * @param model
	 * @param frame
	 */
	public ShowStatsAction(ILocalizationProvider lp, MultipleDocumentModel model, JNotepadPP frame) {
		super("stats", "statsAccel", "statsMnem", "statsDescript", lp);
		this.lp = lp;
		this.model = model;
		this.frame = frame;
		
		setEnabledOrDisabled();
	}
	
	/**
	 * A method which sets the action to be disabled or enabled relative to the current conditions.
	 */
	public void setEnabledOrDisabled() {
		
		//set disabled if no document or unmodified document
		if(model.getCurrentDocument() == null) {
			this.setEnabled(false);
		}
		else {
			this.setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String currentEditorContent = model.getCurrentDocument().getTextComponent().getText();
		int characterCount = frame.getDocumentLength();
		int nonBlankCharacterCount = currentEditorContent.replaceAll("\\s+", "").length();
		
		JOptionPane.showMessageDialog(frame, 
									  lp.getString("yourDoc") + characterCount + " " + lp.getString("characters") 
											  				  + nonBlankCharacterCount + " " + lp.getString("nonBlank")
											  				  + frame.getNumberOfRows() + " " + lp.getString("lines"),
									  lp.getString("information"), 
									  JOptionPane.INFORMATION_MESSAGE);
		
	}
}
