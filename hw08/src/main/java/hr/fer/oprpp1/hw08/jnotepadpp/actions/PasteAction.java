package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent; 

import javax.swing.Action;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.ITextEditor;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * An implementation of the LocalizableAction abstract class which pastes the selected text when performed.
 * 
 * @author fabjanvucina
 */
public class PasteAction extends LocalizableAction {
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
	public PasteAction(ILocalizationProvider lp, JNotepadPP frame) {
		super("paste", "pasteAccel", "pasteMnem", "pasteDescript", lp);
		this.frame = frame;
		
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		frame.editTextEditor(new ITextEditor() {
			
			@Override
			public void edit(int len, int offset, Action pasteAction, String clipboard) 
					throws BadLocationException {
				
				//paste
				frame.getEditor()
					 .getDocument()
					 .insertString(frame.getEditor().getCaretPosition(), clipboard, null);
			}
		});
	}
}
