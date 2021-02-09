package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

/**
 * An implementation of the LocalizableAction abstract class which creates a new blank document when performed.
 * 
 * @author fabjanvucina
 */
public class NewDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model for which the action is defined.
	 */
	private MultipleDocumentModel model;

	/**
	 * Public constructor.
	 * @param lp
	 * @param model
	 */
	public NewDocumentAction(ILocalizationProvider lp, MultipleDocumentModel model) {
		super("new", "newAccel", "newMnem", "newDescript", lp);
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.createNewDocument();
	}
}
