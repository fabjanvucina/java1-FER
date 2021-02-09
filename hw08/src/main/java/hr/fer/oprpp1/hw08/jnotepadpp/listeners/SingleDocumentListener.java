package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * An interface which represents a listener that observes a single document model.
 * 
 * @author fabjanvucina
 */
public interface SingleDocumentListener {
	
	/**
	 * A method which is triggered by the subject single document model when the modification status gets updated.
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * A method which is triggered by the subject single document model when the file path gets updated.
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
