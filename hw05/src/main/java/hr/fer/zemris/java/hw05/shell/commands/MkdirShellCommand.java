package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which creates a new directory.
 * 
 * @author fabjanvucina
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
						
		//read path(path is either specified with absolute path or just .)
		String newDirectoryString = sc.next().trim();
		Path directoryPath = Paths.get(newDirectoryString);
		
		//no specified arguments
		if(newDirectoryString.equals("mkdir")) {
			env.writeln("Invalid format of mkdir command. Valid format is: mkdir <new_directory>");	
		}
		
		//user has specified new directory name
		else {
			
			//invalid command format
			if(sc.hasNext()) {
				env.writeln("Invalid format of mkdir command. Valid format is: mkdir <new_directory>");	
			}
				
			//correct command format, create new directory
			else {
				
				//directory already exists
				if(Files.exists(directoryPath)) {
					env.writeln(directoryPath.toString() + " already exists.");
				}
				
				//create directory
				else {
					Files.createDirectories(directoryPath);
				}
			}
		}
				
		//close scanner
		sc.close();
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The mkdir command creates a new directory in the current directory.");
		description.add("The valid format is: mkdir <directory_name>");		
		
		return description;
	}
}
