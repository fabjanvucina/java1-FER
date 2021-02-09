package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent; 
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.ILinesEditor;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * An implementation of the LocalizableAction abstract class which filters out repeating lines from selected text.
 * 
 * @author fabjanvucina
 */
public class UniqueLinesAction extends LocalizableAction {
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
	public UniqueLinesAction(ILocalizationProvider lp, JNotepadPP frame) {
		super("unique", "uniqueAccel", "uniqueMnem", "uniqueDescript", lp);
		this.frame = frame;
		
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		frame.manipulateSelectedLines(new ILinesEditor() {
			
			@Override
			public Collection<String> editLines(List<Element> selectedRowsElements, Document doc) throws BadLocationException {
				
				//set of unique row values
				Set<String> uniqueRowValues = new LinkedHashSet<>();
						
				for(Element rowElement : selectedRowsElements) {
					try {
						String content = doc.getText(rowElement.getStartOffset(),
													 rowElement.getEndOffset() - rowElement.getStartOffset());
						
						//unique
						if(!uniqueRowValues.contains(content)) {
							uniqueRowValues.add(content);
						}
					} 
					catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
				
				return uniqueRowValues;
				
			}
		});
	}
}
