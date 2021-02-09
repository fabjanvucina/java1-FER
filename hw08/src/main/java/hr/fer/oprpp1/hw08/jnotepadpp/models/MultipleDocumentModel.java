package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.io.IOException;
import java.nio.file.Path;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.MultipleDocumentListener;

/**
 * An interface which represents a model capable of holding multiple documents and keeping track of the current working document. 
 * 
 * @author fabjanvucina
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	
	/**
	 * A method which creates a new single document model and adds it to this multiple document model.
	 * The new document becomes the current document in this multiple document model.
	 * @return new single document model
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * A setter method which sets the current document in this multiple document model.
	 * @param newCurrenDocument 
	 */
	void setCurrentDocument(SingleDocumentModel newCurrentDocument);
	
	/**
	 * @return current document 
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * A method which loads a file from the disk and returns its single document model object.
	 * @param path
	 * @return single document model representing the loaded file
	 * @throws Exception if an error occurs during the file read
	 */
	SingleDocumentModel loadDocument(Path path) throws Exception;
	
	/**
	 * A method which saves changes in the document model to the disk at the specified path. 
	 * If <code>newPath == null</code>, the used path is the one specified in the model.
	 * @param model
	 * @param newPath
	 * @throws IOException if an error occurs during the file write
	 */
	void saveDocument(SingleDocumentModel model, Path newPath) throws IOException;
	
	/**
	 * A method which removes the specified model from this multiple document model.
	 * @param model the removed document
	 * @throws NullPointerException if <code>model</code> is <code>null</code>
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * A method which adds a listener to this multiple document model.
	 * @param l listener
	 * @throws NullPointerException if <code>l == null</code>
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * A method which removes a listener from this multiple document model.
	 * @param l listener
	 * @throws NullPointerException if <code>l == null</code>
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * @param index
	 * @return document at specified index
	 */
	SingleDocumentModel getDocument(int index);
}
