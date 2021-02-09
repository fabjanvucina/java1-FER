package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.Action;  
import javax.swing.text.BadLocationException;

/**
 * Strategy interface for cut, copy, paste.
 * 
 * @author fabjanvucina
 */
public interface ITextEditor {

	/**
	 * A method which does something with the selected text.
	 * @param len
	 * @param offset
	 * @param pasteAction
	 * @param clipboard
	 * @throws BadLocationException
	 */
	void edit(int len, int offset, Action pasteAction, String clipboard) throws BadLocationException;
}
