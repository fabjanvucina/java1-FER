package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.hw05.shell.*;

/**
 * A shell command which displays the current value or sets a new value for one of the special symbols in the shell environment.
 * The special symbols include: prompt symbol, multiline symbol and more lines symbol.
 * 
 * @author fabjanvucina
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		//create new scanner object from arguments string
		Scanner sc = new Scanner(arguments);
		
		//read which symbols is getting changed
		String symbolType = sc.next().trim();
		
		//valid symbol types
		if(symbolType.equals("PROMPT") || symbolType.equals("MORELINES") || symbolType.equals("MULTILINE")) {
			
			//delegate the command logic to other method
			performCommandLogic(env, sc, symbolType);
		}
		
		//no specified arguments
		else if(symbolType.equals("symbol")) {
			env.writeln("Invalid format of symbol command. Valid format is: symbol <symbol_type> [<new_symbol_value>]");	
		}
		
		//invalid symbol type
		else {
			System.out.println(symbolType + " is an invalid program symbol type. Valid symbol types are: PROMPT, MORELINES, MULTILINE");
		}
		
		//close scanner
		sc.close();
		
		//returned status of the command
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Private helper method which generalizes the command logic which repeats for different symbol types.
	 * @param env the shell environment
	 * @param sc the <code>Scanner</code> object for reading command arguments
	 * @param symbolName name of symbol in the command
	 */
	private void performCommandLogic(Environment env, Scanner sc, String symbolType) {
		
		//initializing variables according to symbol type
		Character symbolValue;
		Character[] otherSymbolValues = new Character[2];
		
		if(symbolType.equals("PROMPT")) {
			symbolValue = env.getPromptSymbol();
			otherSymbolValues[0] = env.getMorelinesSymbol();
			otherSymbolValues[1] = env.getMultilineSymbol();
		}
		
		else if(symbolType.equals("MORELINES")) {
			symbolValue = env.getMorelinesSymbol();
			otherSymbolValues[0] = env.getPromptSymbol();
			otherSymbolValues[1] = env.getMultilineSymbol();
		}
		
		else {
			symbolValue = env.getMultilineSymbol();
			otherSymbolValues[0] = env.getMorelinesSymbol();
			otherSymbolValues[1] = env.getPromptSymbol();
		}
		
		
		
		//display current value of symbol
		if(!sc.hasNext()) {
			env.writeln("Symbol for " + symbolType + " is \'" + symbolValue + "\'");
		}
		
		//change value of symbol
		else {
			
			//read new value of symbol
			String newValue = sc.next().trim();
			
			//check if there is more input
			if(sc.hasNext()) {
				env.writeln("Invalid format of symbol command. Valid format is: symbol <symbol_type> [<new_symbol_value>]");	
			}
			
			//new value of symbol cannot be equal to other two symbols
			else if(newValue.equals(Character.toString(otherSymbolValues[0])) || newValue.equals(Character.toString(otherSymbolValues[1]))) {
				env.writeln(newValue + " is an invalid value for " + symbolType + " because it's already taken.");
			}
			
			//new value isn't a char
			else if(newValue.length() != 1) {
				env.writeln(newValue + " is an invalid value for " + symbolType + " because the symbol has to be a char");
			}
			
			//change value of symbol
			else {
				
				env.writeln("Symbol for " + symbolType + " has changed from \'" + symbolValue + "\' to \'" + newValue.charAt(0) + "\'");
				
				if(symbolType.equals("PROMPT")) {
					env.setPromptSymbol(newValue.charAt(0));
				}
				
				else if(symbolType.equals("MORELINES")) {
					env.setMorelinesSymbol(newValue.charAt(0));
				}
				
				else {
					env.setMultilineSymbol(newValue.charAt(0));
				}
				
			}
		}
		
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The symbol displays the current value of some shell specific smybol or sets a new value for it.");
		description.add("The valid format is: symbol {PROMPT, MORELINES, MULTILINE} [<new_value>]");		
		
		return description;
	}
}
