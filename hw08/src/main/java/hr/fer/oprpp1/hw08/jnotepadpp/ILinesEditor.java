package hr.fer.oprpp1.hw08.jnotepadpp;

import java.util.Collection;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * Strategy interface for sorting and removing duplicate lines.
 * 
 * @author fabjanvucina
 */
public interface ILinesEditor {

	/**
	 * A method which does something with the lines of the selected text.
	 * @param selectedRowsElements
	 * @param doc
	 * @return collection of final line values
	 * @throws BadLocationException
	 */
	Collection<String> editLines(List<Element> selectedRowsElements, Document doc) throws BadLocationException;
}
