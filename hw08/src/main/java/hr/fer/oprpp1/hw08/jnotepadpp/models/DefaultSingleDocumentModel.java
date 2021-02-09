package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;

/**
 * Default implementation of the <code>SingleDocumentModel</code> interface.
 * 
 * @author fabjanvucina
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/**
	 * File path of the document.
	 */
	private Path filePath;
	
	/**
	 * Text editing component of the document.
	 */
	private JTextArea textComponent;
	
	/**
	 * Modification status of the document.
	 */
	private boolean isModified;
	
	/**
	 * List of document change observers.
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Public constructor.
	 * @param filePath
	 * @param content
	 */
	public DefaultSingleDocumentModel(Path filePath, String content) {
		
		this.filePath = filePath;
		this.textComponent = new JTextArea(content);
		this.isModified = filePath == null ? true : false;
		this.listeners = new ArrayList<>();
		
		//register a listener on the text components document model and listen for changes
		this.textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null) throw new NullPointerException("You passed a null reference as path");
		
		this.filePath = path;
		
		//notify listeners
		for(SingleDocumentListener l : this.listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		
		//notify listeners
		for(SingleDocumentListener l : this.listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if(l == null) throw new NullPointerException("You passed a null reference as l");
		
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		if(l == null) throw new NullPointerException("You passed a null reference as l");
		
		this.listeners.remove(l);		
	}
}
