package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.ChangeLanguageAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CopyAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CutAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ExitAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.InvertCaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.NewDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.PasteAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ShowStatsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ToLowercaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ToUppercaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.UniqueLinesAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableToolBar;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * A custom notepad program.
 * 
 * @author fabjanvucina
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The localization provider bridge which connects itself to and deconnects itself automatically from the 
	 * "real" localization provider.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * The multiple document model used for the application.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Current document editor
	 */
	private JTextArea editor;
	
	/**
	 * Selected length of the text editor.
	 */
	private int selectionLength;
	
	/**
	 * Clipboard for remembering copied or cut text.
	 */
	private String clipboard;
	
	/**
	 * An action which creates a new blank document.
	 */
	private Action newDocumentAction;
	
	/**
	 * An action which opens an existing document from the disk.
	 */
	private Action openDocumentAction;
	
	/**
	 * An action which saves document changes on the disk.
	 */
	private Action saveDocumentAction;
	
	/**
	 * An action which saves document changes on the disk at a new path.
	 */
	private Action saveAsDocumentAction;
	
	/**
	 * An action which closes the current document.
	 */
	private Action closeDocumentAction;
	
	/**
	 * An action which exists the application.
	 */
	private Action exitAction;
	
	/**
	 * An action which cuts the selected text.
	 */
	private Action cutAction;
	
	/**
	 * An action which copies the selected text.
	 */
	private Action copyAction;
	
	/**
	 * An action which pastes the selected text into the document.
	 */
	private Action pasteAction;
	
	/**
	 * An action which transforms the selected text to all uppercase letters.
	 */
	private Action toUppercaseAction;
	
	/**
	 * An action which transforms the selected text to all lowercase letters.
	 */
	private Action toLowercaseAction;
	
	/**
	 * An action which transforms the selected text by inverting the case of every character.
	 */
	private Action invertCaseAction;
	
	/**
	 * An action which transforms the selected lines by sorting them in ascending order.
	 */
	private Action sortAscendingAction;
	
	/**
	 * An action which transforms the selected lines by sorting them in descending order.
	 */
	private Action sortDescendingAction;
	
	/**
	 * An action which transforms the selected lines by removing duplicates.
	 */
	private Action uniqueLinesAction;
	
	/**
	 * An action which displays statistics about the current document.
	 */
	private Action showStatsAction;
	
	/**
	 * Status bar label for document length
	 */
	private LocalizableLabel lengthLabel;
	
	/**
	 * Status bar label for the line of the pointer position
	 */
	private LocalizableLabel lineLabel;
	
	/**
	 * Status bar label for the column of the pointer position
	 */
	private LocalizableLabel columnLabel;
	
	/**
	 * Status bar label for the length of the selected text
	 */
	private LocalizableLabel selectionLabel;
	
	
	/**
	 * Public constructor.
	 */
	public JNotepadPP() {		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		initGUI();
		setSize(650, 650);
		setTitle("JNotepad++");
		setLocationRelativeTo(null);
	}

	/**
	 * A private method which initializes the components inside the notepad frame.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		
		//create JTabbedPane model and add it to the content pane
		model = new DefaultMultipleDocumentModel(flp);
		add((JTabbedPane) model);	
		
		//TAB CHANGE LISTENER: there are GUI components dependent on knowing that info
		//need to update the title and caret info, enable or disable necessary actions and register a caret 
		((JTabbedPane) model).addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {	
				String title = null;
				
				//all tabs are independent, they do not share selection and actions enabled statuses
				setSelectedTabAsCurrentDocument();
				
				//the document change was removing the only one
				if(model.getCurrentDocument() == null) {
					title = "";
					editor = null;
				}
				
				//the tab has switched to a new document
				else {
					title = model.getCurrentDocument().getFilePath() == null ? flp.getString("unnamed") + " - " 
																			 : model.getCurrentDocument()
																			   	    .getFilePath()
																			   	    .getFileName()
																			   	    .toString()
																			   	    + " - ";
					
					//set the editor to be the text component of the current document
					editor = model.getCurrentDocument().getTextComponent();
					restoreDocumentEditorInfo();
					
					//SELECTION CHANGE LISTENER
					editor.getCaret().addChangeListener(new ChangeListener() {
						
						@Override
						public void stateChanged(ChangeEvent e) {
							Caret c = (Caret)e.getSource();
							
							setSelectionLengthAndEnableOrDisableSelectedTextActions(c);
							lengthLabel.setContent(getDocumentLength());
							lineLabel.setContent(getCurrentLine() + 1);
							columnLabel.setContent(getCurrentColumn() + 1);
							selectionLabel.setContent(selectionLength);
						}
					});
				}
			
				//set the application title
				setTitle(title + "JNotepad++");
				
				//disable/enable GUI components dependant on knowing the current document
				enableOrDisableDocumentLifecycleActions();
			}
		});
		
		
		//WINDOW LISTENER: warn the user about unsaved changes in the tabs
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				boolean isAborted = false;
				
				//iterate over all opened documents
				for(SingleDocumentModel doc : model) {
					
					//warn user if a doc has changes that are not saved and get their response
					if(doc.isModified()) {
						isAborted = showUnsavedChangesDialog(doc);
					}
					
					//stop iterating over documents if user aborted the exit
					if(isAborted) break;
				}				
				
				//dispose the window if the user didn't abort the exit
				if(!isAborted) {
					dispose(); 
				}
			}		
		});
		
		createActions();
		setIcons();
		createMenus();
		createToolbar(); 
		createStatusBar();
	}
	
	/**
	 * A private method which creates the necessary notepad actions.
	 */
	private void createActions() {
		newDocumentAction = new NewDocumentAction(flp, model);
		openDocumentAction = new OpenDocumentAction(flp, model, this);
		
		saveDocumentAction = new SaveDocumentAction(flp, model, this);
		saveAsDocumentAction = new SaveAsDocumentAction(flp, model, this);
		
		closeDocumentAction = new CloseDocumentAction(flp, model, this);
		
		exitAction = new ExitAction(flp);
		
		cutAction = new CutAction(flp, this);
		copyAction = new CopyAction(flp, this);
		pasteAction = new PasteAction(flp, this);
		toUppercaseAction = new ToUppercaseAction(flp, this);
		toLowercaseAction = new ToLowercaseAction(flp, this);
		invertCaseAction = new InvertCaseAction(flp, this);
		
		sortAscendingAction = new SortAction("ascending", "ascendingAccel", "ascendingMnem", "ascendingDescript", false, flp, this);
		sortDescendingAction = new SortAction("descending", "descendingAccel", "descendingMnem", "descendingDescript", true, flp, this);
		uniqueLinesAction = new UniqueLinesAction(flp, this);
		
		showStatsAction = new ShowStatsAction(flp, model, this);
	}
	
	/**
	 * A private method which creates an action that changes the application language.
	 */
	private Action createChangeLanguageAction(String language, String nameKey, String acceleratorKey, 
											  String mnemonicKey, String descriptionKey) {
		
		Action languageAction = new ChangeLanguageAction(language, nameKey, acceleratorKey, mnemonicKey, 
														 descriptionKey, flp);
		
		return languageAction;
	}
	
	/**
	 * A private method which sets icons for actions.
	 */
	private void setIcons() {
		newDocumentAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/new.png"));
		openDocumentAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/open.png"));
		saveDocumentAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/save.png"));
		saveAsDocumentAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/saveAs.png"));
		closeDocumentAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/close.png"));
		exitAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/exit.png"));
		cutAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/cut.png"));
		copyAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/copy.png"));
		pasteAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/paste.png"));
		toUppercaseAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/toUppercase.png"));
		toLowercaseAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/toLowercase.png"));
		invertCaseAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/invertCase.png"));
		sortAscendingAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/sortAscending.png"));
		sortDescendingAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/sortDescending.png"));
		uniqueLinesAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/unique.png"));
		showStatsAction.putValue(Action.SMALL_ICON, generateIconFromResource("icons/stats.png"));
	}
	
	/**
	 * Private method which creates the notepad menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		//File
		JMenu fileMenu = new LocalizableMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(exitAction));
		
		
		//Edit
		JMenu editMenu = new LocalizableMenu("edit", flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		
		//Languages
		JMenu languagesMenu = new LocalizableMenu("languages", flp);
		menuBar.add(languagesMenu);

		languagesMenu.add(new JMenuItem(createChangeLanguageAction("hr", 
																   "croatian", 
																   "croatianAccel", 
																   "croatianMnem", 
																   "croatianDescript")));
		
		languagesMenu.add(new JMenuItem(createChangeLanguageAction("en", 
																   "english", 
																   "englishAccel", 
																   "englishMnem", 
																   "englishDescript")));
		
		languagesMenu.add(new JMenuItem(createChangeLanguageAction("de", 
																   "german", 
																   "germanAccel", 
																   "germanMnem", 
																   "germanDescript")));
		
		//Tools
		JMenu toolsMenu = new LocalizableMenu("tools", flp);
		menuBar.add(toolsMenu);
				
		JMenu changeCaseMenu = new LocalizableMenu("changeCase", flp);
		changeCaseMenu.add(new JMenuItem(toUppercaseAction));
		changeCaseMenu.add(new JMenuItem(toLowercaseAction));
		changeCaseMenu.add(new JMenuItem(invertCaseAction));
		
		JMenu sortMenu = new LocalizableMenu("sort", flp);
		sortMenu.add(new JMenuItem(sortAscendingAction));
		sortMenu.add(new JMenuItem(sortDescendingAction));
		
		toolsMenu.add(changeCaseMenu);
		toolsMenu.add(sortMenu);
		toolsMenu.add(new JMenuItem(uniqueLinesAction));
		
		
		//Info
		JMenu infoMenu = new LocalizableMenu("info", flp);
		menuBar.add(infoMenu);
		
		infoMenu.add(new JMenuItem(showStatsAction));
				
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Private method which creates the floatable notepad toolbar.
	 */
	private void createToolbar() {
		JToolBar toolBar = new LocalizableToolBar("actions", flp);
		toolBar.setFloatable(true);
		
		toolBar.add(newDocumentAction);
		toolBar.add(openDocumentAction);
		
		toolBar.addSeparator();
		
		toolBar.add(saveDocumentAction);
		toolBar.add(saveAsDocumentAction);
		
		toolBar.addSeparator();
		
		toolBar.add(closeDocumentAction);
		toolBar.add(exitAction);
		
		toolBar.addSeparator();
		
		toolBar.add(cutAction);
		toolBar.add(copyAction);
		toolBar.add(pasteAction);
		
		toolBar.addSeparator();
		
		toolBar.add(toUppercaseAction);
		toolBar.add(toLowercaseAction);
		toolBar.add(invertCaseAction);
		
		toolBar.addSeparator();
		
		toolBar.add(sortAscendingAction);
		toolBar.add(sortDescendingAction);
		toolBar.add(uniqueLinesAction);
		
		toolBar.addSeparator();
		
		toolBar.add(showStatsAction);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * A private method which creates a status bar at the bottom of the application.
	 */
	private void createStatusBar() {
		JToolBar statusBar = new LocalizableToolBar("statusBar", flp);
		statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		statusBar.setFloatable(true);
		
		JPanel statusBarPanel = new JPanel(new BorderLayout());

		lengthLabel = new LocalizableLabel("length", -1, flp);
		statusBarPanel.add(lengthLabel, BorderLayout.LINE_START);
		
		JPanel caretInfoPanel = new JPanel();
		lineLabel = new LocalizableLabel("ln", -1, flp);
		columnLabel = new LocalizableLabel("col", -1, flp);
		selectionLabel = new LocalizableLabel("sel", -1, flp);
		caretInfoPanel.add(lineLabel);
		caretInfoPanel.add(columnLabel);
		caretInfoPanel.add(selectionLabel);
		statusBarPanel.add(caretInfoPanel, BorderLayout.CENTER);
		
		JLabel dateTimeLabel = new JLabel();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Timer timer = new Timer(100, e -> dateTimeLabel.setText(dateTimeFormat.format(new Date())));
		timer.start();
		statusBarPanel.add(dateTimeLabel, BorderLayout.LINE_END);
		
		statusBar.add(statusBarPanel);
		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}
	
	/**
	 * A private method which sets the selected tab to be the current document in the model.
	 */
	private void setSelectedTabAsCurrentDocument() {
		int selectedTab = ((JTabbedPane) model).getSelectedIndex();
		SingleDocumentModel newCurrentDocument = selectedTab == -1 ? null : model.getDocument(selectedTab);
		model.setCurrentDocument(newCurrentDocument);
		selectionLength = 0;
	}
	
	/**
	 * A private method which restores selection and status bar info when the tab changes.
	 */
	private void restoreDocumentEditorInfo() {
		
		//restore selection
		setSelectionLengthAndEnableOrDisableSelectedTextActions(editor.getCaret());
		
		//restore document length
		lengthLabel.setContent(getDocumentLength());
		
		//restore current line
		lineLabel.setContent(getCurrentLine() + 1);
		
		//restore current column
		columnLabel.setContent(getCurrentColumn() + 1);
		
		//restore selection length
		selectionLabel.setContent(selectionLength);
	}
	
	/**
	 * Private method which calculates the selection length from the passed caret and enables or disables dependant actions accordingly.
	 * @param c editor caret
	 */
	private void setSelectionLengthAndEnableOrDisableSelectedTextActions(Caret c) {
		selectionLength = Math.abs(c.getDot() - c.getMark());
		
		cutAction.setEnabled(selectionLength != 0);
		copyAction.setEnabled(selectionLength != 0);
		toUppercaseAction.setEnabled(selectionLength != 0);
		toLowercaseAction.setEnabled(selectionLength != 0);
		invertCaseAction.setEnabled(selectionLength != 0);
		sortAscendingAction.setEnabled(selectionLength != 0);
		sortDescendingAction.setEnabled(selectionLength != 0);
		uniqueLinesAction.setEnabled(selectionLength != 0);
	}
	
	/**
	 * Private method which enables or disables actions dependant on existance of a document.
	 */
	private void enableOrDisableDocumentLifecycleActions() {
		((SaveDocumentAction) saveDocumentAction).setEnabledOrDisabled();
		((SaveAsDocumentAction) saveAsDocumentAction).setEnabledOrDisabled();
		((CloseDocumentAction) closeDocumentAction).setEnabledOrDisabled();
		((ShowStatsAction) showStatsAction).setEnabledOrDisabled();
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
	
	/**
	 * Helper method which saves the changes to the disk. It is used by the save and saveAs actions.
	 * If passed path is null, the document's file path will be used.
	 */
	public void save(Path path) {
		
		//write current document content to file
		try {
			model.saveDocument(model.getCurrentDocument(), path);
		}	 
		catch (IOException e1) {
			Path filePath = model.getDocument(((JTabbedPane) model).getSelectedIndex()).getFilePath();
			String docName = filePath == null ? "(unnamed)" : filePath.getFileName().toString();
		
			JOptionPane.showMessageDialog(JNotepadPP.this, 
										  flp.getString("saveError") + " " + docName, 
										  flp.getString("error"),
										  JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		//saving has been successful
		JOptionPane.showMessageDialog(JNotepadPP.this, 
									  flp.getString("fileSaved"), 
									  flp.getString("information"), 
									  JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * A method which strategizes copying, cutting and pasting text.
	 * @param te <code>ITextEditor</code> object
	 * @throws NullPointerException if <code>te == null</code>
	 */
	public void editTextEditor(ITextEditor te) {
		if(te == null) throw new NullPointerException("ITextEditor object should not be null");
		
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		
		try { 
			te.edit(len, offset, pasteAction, clipboard);
		} 
		catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * A method which sorts the selected lines or removes duplicate lines.
	 * @param le line editor
	 * @throws NullPointerException if <code>le == null</code>
	 */
	public void manipulateSelectedLines(ILinesEditor le) {
		if(le == null) throw new NullPointerException("ILinesEditor object should not be null");
		
		Document doc = editor.getDocument();
		Element root = doc.getDefaultRootElement();
		
		//get indexes of selected rows
		Set<Integer> selectedRowsIndexes = getSelectedRowsIndexes();
	
		//get Element objects representing those rows
		List<Element> selectedRowsElements = selectedRowsIndexes.stream()
													  			.map(i -> root.getElement(i))
													  			.collect(Collectors.toList());
	
		//get final rows
		Collection<String> finalLines = null;
		try {
			finalLines = le.editLines(selectedRowsElements, doc);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//remove old row values
		int startOffset = selectedRowsElements.get(0).getStartOffset();
		int endOffset = selectedRowsElements.get(selectedRowsElements.size()-1).getEndOffset();
		
		try {
			doc.remove(startOffset, endOffset - startOffset - 1);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		
		//write final lines to the document
		for(String sortedRowValue : finalLines) {
			try {
				doc.insertString(startOffset, sortedRowValue, null);
				startOffset += sortedRowValue.length();
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * A method which calculates which rows are affected by the selection. A row is affected even if only one character is selected.
	 * @return set of selected rows indexes
	 */
	public Set<Integer> getSelectedRowsIndexes() {
		Set<Integer> rows = new TreeSet<>();
		Element root = editor.getDocument().getDefaultRootElement();
		
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		
		//iterate over all characters
		for(int i = offset; i < offset + len; i++) {
			
			//calculate row for every character, set will make sure there are no duplicates
			rows.add(root.getElementIndex(i));
		}
		
		return rows;
	}
	
	/**
	 * @return editor
	 */
	public JTextArea getEditor() {
		return editor;
	}
	
	/**
	 * @return document length
	 */
	public int getDocumentLength() {
		return editor.getText().length();
	}
	
	/**
	 * @return number of rows
	 */
	public int getNumberOfRows() {
		return editor.getDocument().getDefaultRootElement().getElementCount();
	}
	
	/**
	 * @return current line
	 */
	public int getCurrentLine() {
		int pos = editor.getCaretPosition();
		return editor.getDocument().getDefaultRootElement().getElementIndex(pos);
	}
	
	/**
	 * @return number of rows
	 */
	public int getCurrentColumn() {
		int pos = editor.getCaretPosition();
		int line = getCurrentLine();
		return pos - editor.getDocument().getDefaultRootElement().getElement(line).getStartOffset();
	}
	
	/**
	 * Public setter method for the clipboard
	 * @param clipboard 
	 * @throws NullPointerException if <code>clipboard == null</code>
	 */
	public void setClipboard(String clipboard) {
		if(clipboard == null) throw new NullPointerException("New clipboard value should not be null");
		
		this.clipboard = clipboard;
	}
	
	/**
	 * Helper method which displays the localized dialog warning the user of unsaved changes.
	 * @param doc
	 * @return <code>true</code> if user canceled the document closing, <code>false</code> otherwise
	 * @throws NullPointerException if <code>doc == null</code>
	 */
	public boolean showUnsavedChangesDialog(SingleDocumentModel doc) {
		if(doc == null) throw new NullPointerException("Doc should not be null");
		
		boolean isAborted = false;

		String[] options = new String[] {flp.getString("yes"), 
										 flp.getString("no"),
										 flp.getString("cancel")};
		String docName = doc.getFilePath() == null ? "(unnamed)" : doc.getFilePath()
																	  .getFileName()
																	  .toString();
		
		//get user response
		int res = JOptionPane.showOptionDialog(JNotepadPP.this, 
											   docName + " " + flp.getString("unsavedChanges"),
											   flp.getString("warning"), 
											   JOptionPane.DEFAULT_OPTION, 
											   JOptionPane.WARNING_MESSAGE, null, 
											   options, 
											   options[0]);
		
		//perform appropriate action
		try {
			switch(res) {
			case 0:
				model.saveDocument(doc, null);
				break;
			case 1:
				break;
			case 2:
			case JOptionPane.CLOSED_OPTION:
				isAborted = true;
				break;
			default:
				break;
			}
		}
		catch (IOException e2) {
			e2.printStackTrace();
		}
		
		return isAborted;
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
