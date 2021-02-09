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
 * A shell command which copies a specified file into a new one.
 * 
 * @author fabjanvucina
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
		
		//path is written inside quote marks
		if(arguments.charAt(0) == '\"') {
			sc.useDelimiter("\"");
		}
				
		//read path(path is either specified with absolute path or just test_cat.txt)
		String sourceString = sc.next().trim();
		Path sourcePath = Paths.get(sourceString);
		
		//no specified arguments
		if(sourceString.equals("copy")) {
			env.writeln("Invalid format of copy command. Valid format is: copy <source_file_path> <destination_file_path>");	
		}
		
		//source path is specified, but doesn't lead to a file
		else if(!Files.isReadable(sourcePath)){
			env.writeln("Invalid path specified. You have to input a path which leads to a readable file.");	
		}
		
		//source path is specified and leads to a readable file
		else {
			
			//the user has specified a destination file
			sc.reset();
			if(sc.hasNext()) {
				
				//read destination
				sc.useDelimiter("\"");
				String destinationString = sc.next().trim();
				Path destinationPath = Paths.get(destinationString);
				
				//destination path is written without quote marks
				if(destinationString.equals("")) {
					destinationString = sc.next().trim();
					destinationPath = Paths.get(destinationString);
				}
				
				
				//invalid command format
			    if(sc.hasNext()){
			    	env.writeln("Invalid format of copy command. Valid format is: copy <source_file_path> [<destination_file_path>]");
				}
			    
			    //destination is an existing writable file
			    else if(Files.isWritable(destinationPath)) {
			    	
			    	//ask for permission of overwriting
			    	env.writeln("Write YES to grant permission for overwriting \"" + destinationString + "\"");
			    	
			    	
			    	String permission = env.readLine().toLowerCase();
			    	
			    	//overwrite
			    	if(permission.equals("yes")) {
			    		writeToFile(sourcePath, destinationPath);
			    	}
			    	
			    	//cancel command
			    	else {
			    		env.writeln("Copy operation has been canceled");
			    	}
			    }
				
			    //destination is an existing directory
			    else if(Files.exists(destinationPath)) {
			    	
			    	//create file
			    	Files.createFile(Paths.get(destinationString + "/" + sourceString));
			    	
			    	//write to file
			    	writeToFile(sourcePath, Paths.get(destinationString + "/" + sourceString));
			    }
			    
			    //copy file into new directory or new file
			    else {
			    	
			    	//destination is a file
			    	if(destinationString.contains(".")) {
			    		
			    		//create file
				    	Files.createFile(destinationPath);
				    	
				    	//write to file
				    	writeToFile(sourcePath, destinationPath);
			    
			    	}
			    	
			    	//destination is a directory
			    	else {
			    		
			    		//create directory
				    	Files.createDirectory(destinationPath);
				    	
				    	//write to file
				    	writeToFile(sourcePath, Paths.get(destinationString + "/" + sourceString));
			    	}
			    }
			}
			
			//the user has not specified a destination path
			else {
				env.writeln("Invalid format of copy command. Valid format is: copy <source_file_path> <destination_file_path>");
			}
		}
				
		//close scanner
		sc.close();
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * A private method which reads data from source path and writes it to the destination path
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IOException
	 */
	private void writeToFile(Path sourcePath, Path destinationPath) throws IOException {
		
		//read from sourcePath
		String input = Files.readString(sourcePath, Charset.defaultCharset());
		
		//write to destinationPath
		Files.writeString(destinationPath, input);
		
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The copy command copies a specified file into a new file.");
		description.add("The file can be a file with a specified name or a file with the same name if the destination is set to a directory.");
		description.add("The valid format is: copy <source_path> [<description_path>]");		
		
		return description;
	}
}
