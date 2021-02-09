package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

/**
 * An implementation of the LocalizableAction abstract class which opens a file from the disk when performed.
 * 
 * @author fabjanvucina
 */
public class OpenDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The localization provider.
	 */
	private ILocalizationProvider lp;
	
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
	public OpenDocumentAction(ILocalizationProvider lp, MultipleDocumentModel model, JNotepadPP frame) {
		super("open", "openAccel", "openMnem", "openDescript", lp);
		this.lp = lp;
		this.model = model;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		
		if(fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		//get selected path
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(frame, 
										  fileName.getAbsolutePath() + " " + lp.getString("doesNotExist"), 
										  lp.getString("error"), 
										  JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			//send opened path to the model
			model.loadDocument(filePath);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(frame, 
										  lp.getString("readError") + fileName.getAbsolutePath() + "!", 
										  lp.getString("error"), 
										  JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
