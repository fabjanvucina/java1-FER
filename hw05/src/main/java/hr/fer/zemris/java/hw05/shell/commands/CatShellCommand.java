package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which outputs the file at the specified path to the console using a default or specified charset.
 * 
 * @author fabjanvucina
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
		
		//path is written inside quote marks
		if(arguments.charAt(0) == '\"') {
			sc.useDelimiter("\"");
		}
				
		//read path(path is either specified with absolute path or just test_cat.txt)
		String pathString = sc.next().trim();
		Path path = Paths.get(pathString);
		
		//no specified arguments
		if(pathString.equals("cat")) {
			env.writeln("Invalid format of cat command. Valid format is: cat <file_path> [<charset>]");	
		}
		
		//path is specified, but doesn't lead to a file
		else if(!Files.isReadable(path)){
			env.writeln("Invalid path specified. You have to input a path which leads to a readable file.");	
		}
		
		//path is specified and leads to a readable file
		else {
			
			//the user has specified a charset
			if(sc.hasNext()) {
				
				//read charset
				String charset = sc.next().trim();
				
				//the charset is not supported
				if(!Charset.availableCharsets().keySet().contains(charset)) {
					env.writeln(charset + " is not a supported charset. Type charsets to display supported charsets.");
				}
				
				//the charset is supported, but the command format is invalid
				else if(sc.hasNext()){
					env.writeln("Invalid format of cat command. Valid format is: cat <file_path> [<charset>]");	
				}
				
				//the charset is supported and the command format is correct
				else {
					
					//get lines from file
					List<String> outputLines = Files.readAllLines(path, Charset.availableCharsets().get(charset));
					
					//write to console
					for(String line : outputLines) {
						env.writeln(line);
					}
				}
			}
			
			//the user has not specified a charset
			else {
				
				//get lines from file
				List<String> outputLines = Files.readAllLines(path, Charset.defaultCharset());
				
				//write to console
				for(String line : outputLines) {
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
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The cat command displays the content of a specified file using the default charset or a user-specified one.");
		description.add("The valid format is: cat <file_path> [<charset_name>]");		
		
		return description;
	}

}
