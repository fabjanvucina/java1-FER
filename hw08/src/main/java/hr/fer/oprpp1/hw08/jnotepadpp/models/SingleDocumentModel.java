package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;

/**
 * An interface which represents a model of a single document with information about the file path (can be null for new document), document modification status 
 * and the reference to the Swing component it uses for editing text.
 * 
 * @author fabjanvucina
 */
public interface SingleDocumentModel {
	
	/**
	 * @return text component of the text editor
	 */
	JTextArea getTextComponent();
	
	/**
	 * @return file path
	 */
	Path getFilePath();
	
	/**
	 * A setter method which sets the file path of the model.
	 * @param path 
	 * @throws NullPointerException if <code>path == null</code>
	 */
	void setFilePath(Path path);
	
	/**
	 * @return <code>true</code> if there were changes in the editor after the last save, <code>false</code> otherwise.
	 */
	boolean isModified();
	
	/**
	 * A setter method which sets the modification status flag.
	 * @param modifiedStatus
	 */
	void setModified(boolean modifiedStatus);
	
	/**
	 * A method which adds a listener to this single document model.
	 * @param l listener
	 * @throws NullPointerException if <code>l == null</code>
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * A method which removes a listener from this multiple document model.
	 * @param l listener
	 * @throws NullPointerException if <code>l == null</code>
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
