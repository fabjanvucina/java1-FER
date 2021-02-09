package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.ILinesEditor;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * An implementation of the LocalizableAction abstract class which sorts lines from the selected text when performed.
 * 
 * @author fabjanvucina
 */
public class SortAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Flag which determines whether the comparator is reversed
	 */
	private boolean isReversed;
	
	/**
	 * The frame for which the option dialogs will be shown
	 */
	private JNotepadPP frame;
	
	/**
	 * The localization provider
	 */
	private ILocalizationProvider lp;

	/**
	 * Public constructor.
	 * @param nameKey
	 * @param accelaratorKey
	 * @param mnemonicKey
	 * @param descriptionKey
	 * @param lp
	 * @param frame
	 */
	public SortAction(String nameKey, String accelaratorKey, String mnemonicKey, String descriptionKey,
					 			boolean isReversed, ILocalizationProvider lp, JNotepadPP frame) {
		
		super(nameKey, accelaratorKey, mnemonicKey, descriptionKey, lp);
		this.isReversed = isReversed;
		this.lp = lp;
		this.frame = frame;
		
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.manipulateSelectedLines(new ILinesEditor() {
			
			@Override
			public Collection<String> editLines(List<Element> selectedRowsElements, Document doc) throws BadLocationException {
				//get sorted list of selected row values
				List<String> sortedRowsValues = selectedRowsElements.stream()
																	.map(e -> {
																		  try {
																				return doc.getText(e.getStartOffset(), 
																								   e.getEndOffset() - e.getStartOffset());
																			} catch (BadLocationException e1) {
																				e1.printStackTrace();
																				return null;
																			}
																	  })
																	  .sorted(getComparator())
																	  .collect(Collectors.toList());
				
				return sortedRowsValues;
			}
		});
	}
	
	/**
	 * Private method which returns the comparator for the current language.
	 * @return current language comparator
	 */
	private Comparator<Object> getComparator() {
		
		Locale locale = new Locale(lp.getCurrentLanguage());
		Comparator<Object> collator = Collator.getInstance(locale);
		
		return isReversed ? collator.reversed() : collator;
	}
}
