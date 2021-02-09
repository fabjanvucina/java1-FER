package hr.fer.zemris.java.hw05.shell;

import java.io.IOException; 
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.zemris.java.hw05.shell.commands.*;

/**
 * A custom shell program which implements the <code>Environment</code> interface as a ruleset for communicating with the user via the console. <br>
 * Start the shell and run commmand <code>help</help> to display available commands.
 * 
 * @author fabjanvucina
 */
public class MyShell implements Environment {
	
	/**
	 * Symbol for PROMPT
	 */
	private Character promptSymbol;
	
	/**
	 * Symbol for MORELINES
	 */
	private Character morelinesSymbol;
	
	/**
	 * Symbol for MULTILINE
	 */
	private Character multilineSymbol;
	
	/**
	 * The scanner object used for communication via the console.
	 */
	public Scanner sc;
	
	/**
	 * Public constructor for <code>MyShell</code>.
	 */
	public MyShell() {
		 promptSymbol = '>';
		 morelinesSymbol = '\\';
	     multilineSymbol = '|';
	     sc = new Scanner(System.in);
	}
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			return sc.nextLine().trim();
		}
		
		catch(RuntimeException e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		}
		
		catch(RuntimeException e) {
			throw new ShellIOException(e.getMessage());
		}		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		}
		
		catch(RuntimeException e) {
			throw new ShellIOException(e.getMessage());
		}	
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("exit", new ExitShellCommand());
		
		//return unmodifiable version of the commands map
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
		
	}


	public static void main(String[] args) throws IOException {
		
		//new instance of shell class
		MyShell shell = new MyShell();	
		
		//display welcome message
		shell.writeln("Welcome to MyShell v 1.0");
		
		//start shell status
		ShellStatus status = ShellStatus.CONTINUE;
		
		
		
		//shell program
		while(status != ShellStatus.TERMINATE) {
			
			//write prompt symbol to invite user input
			shell.write(shell.promptSymbol + " ");
			
			
			
			//create single command string from one or more input lines if there is MORELINES symbol
			StringBuilder sb = new StringBuilder();
			
			while(true) {
				
				//user input line
				String line = shell.readLine();
				
				//there is a MORELINES symbol at the end of the line
				if(line.charAt(line.length() - 1) == shell.morelinesSymbol) {
					
					//append the line without the MORELINES symbol
					sb.append(line.substring(0, line.length() - 1)).append(" ");
					
					//write multiline symbol to signalize multiline input to user
					shell.write(shell.multilineSymbol + " ");
				}
				
				//there is no MORELINES symbol
				else {
					
					//append whole line
					sb.append(line);
					break;
				}
			}
			
			
			
			//create new scanner object from full command
			String fullCommand = sb.toString();
			Scanner sc = new Scanner(fullCommand);
			
			//read command type
			String command = sc.next().trim();
			sc.close();
			
			//command arguments
			String arguments = fullCommand.substring(fullCommand.indexOf(" ") + 1);
			
			//command is valid
			 if(shell.commands().containsKey(command)) {
				
				//execute command
				status = shell.commands().get(command).executeCommand(shell, arguments);
				
			}
			
			//command is invalid
			else {
				System.out.println(command + " is an invalid command. Type help to display valid commands.");
			}
		}
		
		shell.writeln("~The shell has stopped running~");
		
	}
}
