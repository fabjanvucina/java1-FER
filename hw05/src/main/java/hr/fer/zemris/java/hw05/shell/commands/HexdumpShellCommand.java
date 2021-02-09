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
 * A shell command which creates a hexdump output for a specified file.
 * 
 * @author fabjanvucina
 */
public class HexdumpShellCommand implements ShellCommand {

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
		if(pathString.equals("hexdump")) {
			env.writeln("Invalid format of hexdump command. Valid format is: hexdump <file_path>");	
		}
		
		//path is specified, but doesn't lead to a file
		else if(Files.isDirectory(path) || !Files.isReadable(path)){
			env.writeln("Invalid path specified. You have to input a path which leads to a file");	
		}
		
		//path is specified and leads to a file
		else {
			
			//invalid command format
			if(sc.hasNext()) {
				env.writeln("Invalid format of hexdump command. Valid format is: hexdump <file_path>");	
			}
				
			//correct command format
			else {
				
				//get input from file
				String fileText = Files.readString(path);
				
				//write to console
				formatHexdumpLine(env, fileText, 0);
	
			}
		}
				
		//close scanner
		sc.close();
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * A private method which splits text from a file into hexdump output lines.
	 * @param env the shell environment
	 * @param fileText
	 * @param offset
	 */
	private void formatHexdumpLine(Environment env, String fileText, int offset) {
		
		//calculate the number of output lines
		int numOfHexdumpLines = (int)Math.ceil((double)fileText.length() / 16);
		
		//split fileText into hexdump lines
		for(int i = 0; i < numOfHexdumpLines; i++, offset += 16) {
			
			StringBuilder sb = new StringBuilder();
			
			//get number of digits in offset
			int numOfOffsetDigits = Integer.toHexString(offset).length();
			
			//append leading zeros
			for(int j = numOfOffsetDigits; j < 8; j++, sb.append("0"));
			
			//append offset
			sb.append(Integer.toHexString(offset)).append(": ");
			
			
			
			//get substring of fileText for hexdump line
			int lineLength = i == numOfHexdumpLines - 1 ? fileText.length() % 16 : 16;
			String hexdumpLine = fileText.substring(offset, offset + lineLength);
			
			//display the line in UTF-8 hex symbols
			for(int k = 0; k < 16; k++) {
				
				if(k == 8) 
					sb.append("|");
				
				//display unicode symbol if it exists
				if(k < lineLength) {
					
					//check for leading zero
					String hexUnicode = Integer.toHexString(hexdumpLine.charAt(k));
					if(hexUnicode.length() == 1) {
						sb.append("0");
					}
					
					sb.append(hexUnicode);
					if(k != 7) sb.append(" ");
					
				}
				
				//display blank with space
				else if (k != 7){
					sb.append("   ");
				}
				
				//display blank without space
				else {
					sb.append("  ");
				}
				
			}
			
			sb.append("| ");
			
			
			
			//display original fileText line
			for(int p = 0; p < lineLength; p++) {
				
				//display . if UTF-8 value of character is < 32 or > 127
				int value = hexdumpLine.charAt(p);
				
				if(value < 32 || value > 127) {
					sb.append(".");
				}
				
				//display regular character
				else {
					sb.append(hexdumpLine.charAt(p));
				}
			}
			
			
			
			//display the whole line
			env.writeln(sb.toString());
			
		}
		
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The hexdump command outputs the source file in a ususal hexdump format.");
		description.add("The valid format is: hexdump <source_path>");		
		
		return description;
	}
}
