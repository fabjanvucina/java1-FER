package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.File; 

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * A subclass of JFileChooser which warns the user in the case of an overwrite while saving.
 * 
 * @author fabjanvucina
 */
public class MyFileChooser extends JFileChooser {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The frame for which we are showing a warning dialog
	 */
	private JNotepadPP frame;
	
	/**
	 * The localization provider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Public constructor.
	 * @param frame
	 * @param flp
	 * @throws NullPointerException if <code>frame</code> or <code>lp</code> is <code>null</code>
	 */
	public MyFileChooser(JNotepadPP frame, ILocalizationProvider lp) {
		if(frame == null) throw new NullPointerException("Frame should not be null.");
		if(lp == null) throw new NullPointerException("Localization provider should not be null.");
		
		this.frame = frame;
		this.lp = lp;
	}
	
	 @Override
	    public void approveSelection(){
	        File f = getSelectedFile();
	        if(f.exists() && getDialogType() == SAVE_DIALOG){
	        	
	        	//ask the user if they want to overwrite ean existing file
				String[] options = new String[] {lp.getString("yes"), 
												 lp.getString("no"),
												 lp.getString("cancel")};
				
				//get user response
				int res = JOptionPane.showOptionDialog(frame, 
													   lp.getString("fileExists"),
													   lp.getString("warning"), 
													   JOptionPane.DEFAULT_OPTION, 
													   JOptionPane.WARNING_MESSAGE, null, 
													   options, 
													   options[0]);
				
				//perform appropriate action
				switch(res) {
				case 0:
					break;
				default:
					return;
				}
	        }
	        
	        super.approveSelection();
	    }        

}
