package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * An interface which represents a listener that observes a multiple document model.
 * 
 * @author fabjanvucina
 */
public interface MultipleDocumentListener {
	
	/**
	 * A method which is triggered by the subject multiple document model when the current single document model changes.
	 * @param previousModel
	 * @param currentModel
	 * @throws NullPointerException if <code>previousModel</code> is <code>null</code> 
	 * 								or <code>currentModel</code> is <code>null</code>
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * A method which is triggered by the subject multiple document model when a new single document model gets added.
	 * @param model
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * A method which is triggered by the subject multiple document model when a single document model gets removed.
	 * @param model 
	 */
	void documentRemoved(SingleDocumentModel model);
}
