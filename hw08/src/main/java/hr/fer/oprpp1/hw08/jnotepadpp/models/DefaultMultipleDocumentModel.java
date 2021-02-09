package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.awt.BorderLayout; 
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Default implementation of the <code>MultipleDocumentModel</code> interface.
 * 
 * @author fabjanvucina
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * List of documents in the model.
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * Current active document in the model.
	 */
	private SingleDocumentModel currentDocument;
	
	/**
	 * List of document collection change observers.
	 */
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * Tab icon which is displayed if the document is modified.
	 */
	private ImageIcon unsavedIcon;
	
	/**
	 * Tab icon which is displayed if the document is not modified.
	 */
	private ImageIcon savedIcon;
	
	/**
	 * The localization provider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Public constructor.
	 */
	public DefaultMultipleDocumentModel(ILocalizationProvider lp) {
		super();
		
		this.documents = new ArrayList<>();
		this.currentDocument = null;
		this.listeners = new ArrayList<>();
		
		this.unsavedIcon = generateIconFromResource("../icons/unsaved.png");
		this.savedIcon = generateIconFromResource("../icons/saved.png");
		
		this.lp = lp;
	}
	
	/**
	 * Private method which generates the image icon from the resource folder.
	 * @param path
	 * @return icon
	 */
	private ImageIcon generateIconFromResource(String path) {
		byte[] bytes = null;
		
		try(InputStream is = this.getClass().getResourceAsStream(path)) {
			if(is == null) throw new IllegalArgumentException("The " + path + " resource doesn't exist.");
			bytes = is.readAllBytes();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(bytes);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}
	
	@Override
	public void setCurrentDocument(SingleDocumentModel newCurrentDocument) {
		currentDocument = newCurrentDocument;
	}
	
	/**
	 * Private helper method which creates a new single document model from the passed path and content, adds a change listener to it, 
	 * adds it to the multiple document model, adds it as a tab to the tabbed pane and sets its tab title and tooltip.
	 * @param path
	 * @param content
	 * @return new document in the model
	 */
	private SingleDocumentModel addDocumentToModelAndPanel(Path path, String content) {
		
		//create document
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, content);
		
		//listen for modification status or file path changes on the new document
		document.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if(model.isModified()) {
					setIconAt(documents.indexOf(document), unsavedIcon);
				}
				else {
					setIconAt(documents.indexOf(document), savedIcon);
				}
				
				fireStateChanged();
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				if(model.getFilePath() == null) {
					setTitleAt(documents.indexOf(document), lp.getString("unnamed"));
					setToolTipTextAt(documents.indexOf(document), lp.getString("unnamed"));
				}
				else {
					setTitleAt(documents.indexOf(document), document.getFilePath().getFileName().toString());
					setToolTipTextAt(documents.indexOf(document), document.getFilePath().toString());
				}
				
				fireStateChanged();
			}
		});
		
		//add the document to the model and set it as the current one
		documents.add(document);
		currentDocument = document;
		
		//add the corresponding tab component to the panel
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(document.getTextComponent()), BorderLayout.CENTER);
		
		//set the tab title and tooltip
		String tabTitle = path == null ? lp.getString("unnamed") : path.getFileName().toString();
		String toolTip = path == null ? lp.getString("unnamed") : path.toString();
		
		//add tab panel
		this.addTab(tabTitle, path == null ? unsavedIcon : savedIcon, panel, toolTip);
		this.setSelectedIndex(documents.indexOf(currentDocument));
		
		return document;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return addDocumentToModelAndPanel(null, "");
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) throws Exception {	
		if(path == null) throw new NullPointerException("You passed a null reference as path");
		
		//check if document already opened
		for(SingleDocumentModel doc : documents) {
			if(doc.getFilePath().equals(path)) {
				currentDocument = doc;
				setSelectedIndex(documents.indexOf(currentDocument));
				return doc;
			}
		}
		
		
		//read bytes from file path
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} 
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		//convert read bytes to string content
		String content = new String(bytes, StandardCharsets.UTF_8);
		
		return addDocumentToModelAndPanel(path, content);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) throws IOException {
		byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		try {
			if(newPath == null) {
				Files.write(model.getFilePath(), bytes);
				currentDocument.setModified(false);
			}
			else {
				Files.write(newPath, bytes);
				currentDocument.setFilePath(newPath);
				currentDocument.setModified(false);
				
				this.fireStateChanged();
			}
			
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(model == null) throw new NullPointerException("You passed a null reference as model");
		
		//remember index of current document
		int idxOfCurrent = getSelectedIndex();
		
		//set new current document
		if(getNumberOfDocuments() == 1) {
			currentDocument = null;
			setSelectedIndex(-1);
		}
		
		else {
			
			//calculate index of next document that is going to be the current model
			int idxOfNewCurrent = (idxOfCurrent + 1) % getNumberOfDocuments();
			
			//set new current document
			currentDocument = getDocument(idxOfNewCurrent);
			setSelectedIndex(documents.indexOf(currentDocument));
		}
		
		//remove the closed document from the tabbed pane and the multiple document model
		remove(idxOfCurrent);
		documents.remove(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if(l == null) throw new NullPointerException("You passed a null reference as l");
		
		this.listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		if(l == null) throw new NullPointerException("You passed a null reference as l");
		
		this.listeners.remove(l);	
	}

	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}
}
