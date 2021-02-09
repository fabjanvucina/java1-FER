package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**
 * An interface which represents a contract for communication with the user via the console.
 * 
 * @author fabjanvucina
 */
public interface Environment {

	/**
	 * A method which reads a user input line from the console.
	 * @return the input line
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * A method which outputs text to the console.
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * A method which outputs text to the console and jumps to a new line.
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * @return sorted map of valid commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * @return MULTILINE character
	 */
	Character getMultilineSymbol();
	
	/**
	 * A setter method which sets a new value for the MULTILINE symbol.
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * @return PROMPT character
	 */
	Character getPromptSymbol();
	
	/**
	 * A setter method which sets a new value for the PROMPT symbol.
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * @return MORELINES character
	 */
	Character getMorelinesSymbol();
	
	/**
	 * A setter method which sets a new value for the MORELINES symbol.
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
