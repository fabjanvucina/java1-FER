package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which displays all the supported charsets in the user's Java platform.
 * 
 * @author fabjanvucina
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		//invalid arguments
		if(!arguments.equals("charsets")) {
			env.writeln("charsets command should not accept any arguments");
		}
		
		else {
			
			//get supported charsets
			Set<String> charsets = Charset.availableCharsets().keySet();
			
			for(String charset : charsets) {
				env.writeln(charset);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The charsets command displays all the supported charsets.");
		description.add("The valid format is: charsets");		
		
		return description;
	}

}
