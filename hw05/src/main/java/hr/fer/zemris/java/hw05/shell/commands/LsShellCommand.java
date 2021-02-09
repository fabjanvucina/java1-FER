package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which outputs all the files and directories inside the specified directory.
 * 
 * @author fabjanvucina
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
		
		//path is written inside quote marks
		if(arguments.charAt(0) == '\"') {
			sc.useDelimiter("\"");
		}
				
		//read path(path is either specified with absolute path or just .)
		String pathString = sc.next().trim();
		Path path = Paths.get(pathString);
		
		//no specified arguments
		if(pathString.equals("ls")) {
			env.writeln("Invalid format of ls command. Valid format is: ls <directory_path>");	
		}
		
		//path is specified, but doesn't lead to a directory
		else if(!Files.isDirectory(path)){
			env.writeln("Invalid path specified. You have to input a path which leads to a directory");	
		}
		
		//path is specified and leads to a directory
		else {
			
			//invalid command format
			if(sc.hasNext()) {
				env.writeln("Invalid format of ls command. Valid format is: ls <directory_path>");
			}
				
			//correct command format
			else {
				
				//get all entries from the directory
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
					
					//output every entry in specific format
					for (Path entry : stream) {
						formatDirectoryEntry(env, entry);
					}
				}
			}
		}
				
		//close scanner
		sc.close();
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Private method which takes a directory entry and outputs it in informative format.
	 * @param entry
	 * @throws IOException 
	 */
	private void formatDirectoryEntry(Environment env, Path entry) throws IOException {
		
		//create first column - attributes
		StringBuilder sb1 = new StringBuilder();
		
		if(Files.isDirectory(entry)) sb1.append("d"); else sb1.append("-");
		if(Files.isReadable(entry)) sb1.append("r"); else sb1.append("-");
		if(Files.isWritable(entry)) sb1.append("w"); else sb1.append("-");
		if(Files.isExecutable(entry)) sb1.append("x"); else sb1.append("-");
		
		env.write(sb1.toString() + " ");
		
		
		//create second column - size
		String entrySize = Long.toString(Files.size(entry));
		
		StringBuilder sb2 = new StringBuilder();
		for(int i = entrySize.length(); i < 10; i++) {
			sb2.append(" ");
		}
		
		sb2.append(entrySize);
		
		env.write(sb2.toString() + " ");
		
		
		//create third column - date and time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(entry, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		env.write(formattedDateTime + " ");
		
		
		//create fourth column - entry name
		env.writeln(entry.toString().substring(2));
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The ls command displays all the directories and files in the specified directory.");
		description.add("The valid format is: ls <directory_path>");		
		
		return description;
	}
}
