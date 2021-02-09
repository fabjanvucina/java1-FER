package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which terminates the runtime of the shell.
 * 
 * @author fabjanvucina
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		//invalid arguments
		if(!arguments.equals("exit")) {
			env.writeln("exit command should not accept any arguments");
			
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The exit command terminates the runtime of the shell environment.");
		description.add("The valid format is: exit");		
		
		return description;
	}
}
