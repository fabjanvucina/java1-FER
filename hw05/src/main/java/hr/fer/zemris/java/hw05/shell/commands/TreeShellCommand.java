package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which displays the specified directory in a tree format.
 * 
 * @author fabjanvucina
 */
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
				
		//read path(path is either specified with absolute path or just .)
		String pathString = sc.next().trim();
		Path path = Paths.get(pathString);
		
		//no specified arguments
		if(pathString.equals("tree")) {
			env.writeln("Invalid format of tree command. Valid format is: tree <directory_path>");	
		}
		
		//path is specified, but doesn't lead to a directory
		else if(!Files.isDirectory(path)){
			env.writeln("Invalid path specified. You have to input a path which leads to a directory");	
		}
		
		//path is specified and leads to a directory
		else {
			
			//invalid command format
			if(sc.hasNext()) {
				env.writeln("Invalid format of tree command. Valid format is: tree <directory_path>");
			}
				
			//correct command format
			else {
				outputTreeLevel(env, path, 0);
			}
		}
				
		//close scanner
		sc.close();
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Private method which outputs the current tree level.
	 * @param env shell environment
	 * @param directory
	 * @param level
	 * @throws IOException
	 */
	private void outputTreeLevel(Environment env, Path directory, int level) throws IOException {
		
		//get all entries from the current directory
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			
			//output every entry in specific format
			for (Path entry : stream) {
				
				//output entry name
				StringBuilder sb = new StringBuilder();
				
				for(int i = 0; i < level; i++, sb.append("  "));
				sb.append(entry.toString().substring(2));
				
				env.writeln(sb.toString());
				
				
				//if entry is directory, enter it
				if(Files.isDirectory(entry)) {
					outputTreeLevel(env, entry, level + 1);
				}
			}
		}
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The tree command displays the specified directory in tree format.");
		description.add("The valid format is: tree <directory_name>");		
		
		return description;
	}
}
