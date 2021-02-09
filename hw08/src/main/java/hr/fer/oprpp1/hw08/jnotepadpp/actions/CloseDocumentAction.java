package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;  

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

/**
 * An implementation of the LocalizableAction abstract class which closes the current document when performed.
 * 
 * @author fabjanvucina
 */
public class CloseDocumentAction extends LocalizableAction {
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
	 * Public constructor.
	 * @param lp
	 * @param model
	 * @param frame
	 */
	public CloseDocumentAction(ILocalizationProvider lp, MultipleDocumentModel model, JNotepadPP frame) {
		super("close", "closeAccel", "closeMnem", "closeDescript", lp);
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
		
		//warn user if changes are not saved
		if(model.getCurrentDocument().isModified()) {
			frame.showUnsavedChangesDialog(model.getCurrentDocument());
		}
		 
		model.closeDocument(model.getCurrentDocument());
	}
}
