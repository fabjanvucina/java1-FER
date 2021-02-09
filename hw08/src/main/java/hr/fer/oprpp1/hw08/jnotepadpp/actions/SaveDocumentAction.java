package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;  
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.MyFileChooser;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

/**
 * An implementation of the LocalizableAction abstract class which saves a file to the disk when performed.
 * 
 * @author fabjanvucina
 */
public class SaveDocumentAction extends LocalizableAction {
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
	public SaveDocumentAction(ILocalizationProvider lp, MultipleDocumentModel model, JNotepadPP frame) {
		
		super("save", "saveAccel", "saveMnem", "saveDescript", lp);
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
		if(model.getCurrentDocument() == null || model.getCurrentDocument().isModified() == false) {
			this.setEnabled(false);
		}
		else {
			this.setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Path newPath = null;
		
		//set new path if it was unamed document
		if(model.getCurrentDocument().getFilePath() == null) {
			JFileChooser jfc = new MyFileChooser(frame, lp);
			jfc.setDialogTitle("Save document");
			
		
			if(jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(frame, 
											  lp.getString("nothingSaved"), 
											  lp.getString("warning"), 
											  JOptionPane.WARNING_MESSAGE);
				return;
			}
		
			//get the selected path
			newPath = jfc.getSelectedFile().toPath();
		}
	
		//save the changes
		frame.save(newPath);
	}
}
