package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which displays descriptions for shell commands.
 * 
 * @author fabjanvucina
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
						
		//read command that needs help
		String command = sc.next().trim();
		
		//same behaviour for "help" and "help help"
		if(command.equals("help")) {
			
			//display description of help command
			for(String line : getCommandDescription()) {
				env.writeln(line);
			}
			
			env.writeln("~");
			env.writeln("If you want to display help for a specific command, type: help <command>");
			env.writeln("~");
			env.writeln("Valid commands are:");
			
			//display valid commands
			for(String com : env.commands().keySet()) {
				env.writeln(com);
			}
			
		}
		
		//user has specified a command
		else {
			
			//invalid command 
			if(!env.commands().containsKey(command)) {
				env.writeln(command + " is not a valid command. Type help to display valid commands");
			}
			
			//valid command
			else {
				
				//display description for specific command
				for(String line : env.commands().get(command).getCommandDescription()) {
					env.writeln(line);
				}
			}
		}
				
		//close scanner
		sc.close();
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The help command displays a description of specific command or all the valid commands if a a command is not specified");
		description.add("The valid format is: help [<command>]");		
		
		return description;
	}
}
