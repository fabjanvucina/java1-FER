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
 * An implementation of the LocalizableAction abstract class which transforms the selected text by inverting the capitalization when performed.
 * 
 * @author fabjanvucina
 */
public class InvertCaseAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The frame for which the option dialogs will be shown
	 */
	private JNotepadPP frame;

	/**
	 * Public constructor.
	 * @param nameKey
	 * @param accelaratorKey
	 * @param mnemonicKey
	 * @param descriptionKey
	 * @param lp
	 * @param frame
	 */
	public InvertCaseAction(ILocalizationProvider lp, JNotepadPP frame) {
		super("invertCase", "invertCaseAccel", "invertCaseMnem", "invertCaseDescript", lp);
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
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			}
		});
	}
	
	/**
	 * Private method which inverts capitalization of letters in the passed string.
	 * @param text
	 * @return <code>text</code> with inverted capitalization on letters
	 */
	private String changeCase(String text) {
		char[] characters = text.toCharArray();
		
		for(int i = 0; i < characters.length; i++) {
			char c = characters[i];
			characters[i] = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);	
		}
		
		return new String(characters);	
	}
}
