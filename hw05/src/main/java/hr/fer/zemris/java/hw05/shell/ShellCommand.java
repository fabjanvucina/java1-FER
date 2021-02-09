package hr.fer.zemris.java.hw05.shell;

import java.io.IOException;
import java.util.List;

/**
 * An interface which provides a contract of behaviour for shell environment commands
 * @author fabjanvucina
 */
public interface ShellCommand {
	
	/**
	 * A method which executes behavior of the command object which implements this interface.
	 * @param env active shell environment
	 * @param arguments
	 * @return status of the shell
	 * @throws IOException 
	 */
	ShellStatus executeCommand(Environment env, String arguments) throws IOException;
	
	/**
	 * @return name of the command
	 */
	String getCommandName();
	
	/**
	 * @return description of the command
	 */
	List<String> getCommandDescription();
}
