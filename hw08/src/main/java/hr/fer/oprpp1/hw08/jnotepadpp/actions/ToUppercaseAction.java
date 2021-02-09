package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.ITextEditor;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * An implementation of the LocalizableAction abstract class which transforms the selected text to uppercase when performed.
 * 
 * @author fabjanvucina
 */
public class ToUppercaseAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The frame for which the option dialogs will be shown
	 */
	private JNotepadPP frame;

	/**
	 * Public constructor.
	 * @param lp
	 * @param frame
	 */
	public ToUppercaseAction(ILocalizationProvider lp, JNotepadPP frame) {
		super("uppercase", "uppercaseAccel", "uppercaseMnem", "uppercaseDescript", lp);
		this.frame = frame;
		
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		frame.editTextEditor(new ITextEditor() {
			
			@Override
			public void edit(int len, int offset, Action pasteAction, String clipboard) throws BadLocationException {
				
				Document doc = frame.getEditor().getDocument();
				
				String text = doc.getText(offset, len);
				doc.remove(offset, len);
				doc.insertString(offset, text.toUpperCase(), null);
			}
		});
	}
}
