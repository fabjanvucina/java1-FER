package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.ArrayList;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.hw02.prob1.LexerException;


/**
 * A class which represents a simple lexical analyzer for the purposes of a <code>SmartScriptParser</code> object.
 * 
 * @author fabjanvucina
 */
public class SmartScriptLexer {
	
	/**
	 * Array of character which represents the input text.
	 */
	private char[] data;
	
	/**
	 * Current token that has been extracted out of the input text.
	 */
	private Token token;
	
	/**
	 * Index of the first unprocessed character in the input text.
	 */
	private int currentIndex;
	
	
	/**
	 * Current mode of operation of the <code>Lexer</code> object.
	 */
	private LexerState state;
	
	/**
	 * Public constructor.
	 * @param documentBody
	 */
	public SmartScriptLexer(String documentBody) {		        
        this.data = documentBody.toCharArray();
        this.currentIndex = 0;
        this.token = null;
        this.state = LexerState.TEXT;
	}
	
	
	/**
	 * A setter for the <code>SmartScriptLexer's</code> mode of operation.
	 * 
	 * @param state The new state we want to apply to our lexical analyser
	 * @throws <code>NullPointerException</code> if <code>state</code> is <code>null</code>
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("State should not be null");
		
		this.state = state;
	}
	
	
	/**
	 * A method which returns the last generated token in the input document.
	 * 
	 * @return The last generated token.
	 */
	public Token getToken() {
		return this.token;
	}
	
	
	/**
	 * A method which generates and returns the next token in the input text.
	 * @return next token
	 * @throws <code>LexerException</code> if an error occurs
	 */
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) throw new LexerException("There are no tokens remaining in the input text");
		
		//check if we reached the end of the input document, token is EOF
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}	
		
		//getting next TEXT token from document
		if(state == LexerState.TEXT) {
			extractTextToken();
		}
				
		//getting next TAG token from document
		else if(state == LexerState.TAG){
			extractTagToken();
		}
		
		//getting next element token from tag.
		else {
			extractElementTokenFromTag();
		}
		
		return token;
	}
	
	
	/**
	 * A private method which generates a TEXT token from the input document.
	 */
	private void extractTextToken() {
		var textChunks = new ArrayList<String>();
		int startIndex = currentIndex;
		
		while(currentIndex < data.length) {
			
			//checking escape sequences
			if(data[currentIndex] == '\\') {
				
				//inavalid escape sequences
				if(currentIndex == data.length-1 || (data[currentIndex+1] != '\\' && data[currentIndex+1] != '{')) {
					throw new SmartScriptParserException("Invalid escape sequence.");
				}
				
				//valid escape sequences
				else {
					textChunks.add(new String(data, startIndex, currentIndex-startIndex));
					textChunks.add(Character.toString(data[currentIndex + 1]));
					currentIndex += 2;
					startIndex = currentIndex;
				}

			}
			
			//naisli na pocetak taga
			else if((currentIndex != 0 && data[currentIndex] == '{' && data[currentIndex-1] != '\\' && currentIndex != data.length-1 && data[currentIndex+1] == '$') || (data[currentIndex] == '{' && currentIndex != data.length-1 && data[currentIndex+1] == '$') ) {
				break;
			}
			
			//regular character
			else {
				currentIndex++;
			}
		}
		
		//connect the chunks
		String lastChunk = new String(data, startIndex, currentIndex-startIndex);
		textChunks.add(lastChunk);
		
		StringBuilder sb = new StringBuilder();
		for(String chunk : textChunks) {
			sb.append(chunk);
		}
		
		token = new Token(TokenType.TEXT, sb.toString());
		
		//skip over opening tag {$
		currentIndex += 2;
	}
	
	
	/**
	 * A private method which extracts a TAG token from the input document.
	 */
	private void extractTagToken() {	
		int startIndex = currentIndex;
		boolean foundEndOfTag = false;
		
		while(currentIndex < data.length) {
			//naisli na kraj taga
			if(data[currentIndex] == '$' && currentIndex != data.length-1 && data[currentIndex+1] == '}') {
				foundEndOfTag = true;				
				break;
			}
			
			currentIndex++;
		}
		
		if(!foundEndOfTag) throw new LexerException("There is no enclosing tag for the TAG token");
		
		String value= new String(this.data, startIndex, currentIndex-startIndex);
		token = new Token(TokenType.TAG, value);
		
		//skip over enclosing tag
		currentIndex += 2;
	}

	/**
	 * A private method which extracts an element token from echo tag or loop tag.
	 */
	private void extractElementTokenFromTag() {	
		//get to first non blank element
		skipBlanks();
		int startIndex = currentIndex;
		
		if(currentIndex != data.length) {
			
			//string
			if(data[currentIndex] == '\"') {
				currentIndex++;
				boolean foundClosingQuoteMarks = false;
				
				//find enclosing quote marks
				while(currentIndex != data.length) {
					
					//skip over escape string
					if(data[currentIndex] == '\\' && currentIndex != data.length && data[currentIndex+1] == '\"') {
						currentIndex += 2;
					}	
					
					else if(data[currentIndex] == '\"') {
						foundClosingQuoteMarks = true;
						currentIndex++;
						break;
					}		
					
					else if(data[currentIndex] == '\\' && data[currentIndex+1] != '\\' && data[currentIndex+1] != '\"' && data[currentIndex+1] != 'n' && data[currentIndex+1] != 'r' && data[currentIndex+1] != 't') {
						throw new LexerException("Invalid escape sequence in string inside tag");
					}
					
					else {
						currentIndex++;
					}
				}
				
				if(!foundClosingQuoteMarks) throw new LexerException("There is no enclosing tag for the quote marks");
				
				//don't include the quote marks
				String value= new String(this.data, startIndex + 1, currentIndex-startIndex - 2);
				token = new Token(TokenType.STRING, value);
			}
			
			//function
			else if(data[currentIndex] == '@') {
				currentIndex++;
				
				while(currentIndex != data.length) {
					if(data[currentIndex] == ' ') {
						break;
					}
					
					currentIndex++;
				}
				
				//don't include the @
				String value = new String(this.data, startIndex + 1, currentIndex-startIndex - 1);
				
				if(!isValidFunction(value.toCharArray())) throw new LexerException("The input contains an invalid function definition.");
				
				token = new Token(TokenType.FUNCTION, value);
			}
			
			//Integer or Double
			else if(Character.isDigit(data[currentIndex]) || (data[currentIndex] == '-' && currentIndex != data.length && Character.isDigit(data[currentIndex+1]))) {
				currentIndex++;
				boolean foundDot = false;
				
				while(currentIndex != data.length) {
					
					//second dot or not a digit(also not a dot)
					if((data[currentIndex] == '.' && foundDot == true) || (data[currentIndex] != '.' && !Character.isDigit(data[currentIndex]))) {
						break;
					}
					
					//first dot
					else if(data[currentIndex] == '.' && foundDot == false) {
						currentIndex++;
						foundDot = true;
					}
					
					//just a digit
					else {
						currentIndex++;
					}
				}
				
				//Double
				if(foundDot == true) {
					String value = new String(this.data, startIndex, currentIndex-startIndex);				
					token = new Token(TokenType.CONSTANT_DOUBLE, Double.parseDouble(value));
				}
				
				//Integer
				else {
					String value = new String(this.data, startIndex, currentIndex-startIndex);				
					token = new Token(TokenType.CONSTANT_INTEGER, Integer.parseInt(value));
				}
				
			}
			
			//other tokens
			else {
				
				while(currentIndex != data.length) {
					
					//regular delimiter
					if(data[currentIndex] == ' ') {
						break;
					}
					
					//start of string
					if(data[currentIndex] == '\"') {
						break;
					}
					
					//start of number
					if(data[currentIndex] == '-' && currentIndex != data.length && Character.isDigit(data[currentIndex+1])) {
						break;
					}
					
					currentIndex++;
				}
				
				
				String value = new String(this.data, startIndex, currentIndex-startIndex);
				
				//variable
				if(isValidVariable(value.toCharArray())) {
					token = new Token(TokenType.VARIABLE, value);
				}
				
				//operator
				else if(value.length() == 1 && isValidOperator(value.toCharArray()[0])) {
					token = new Token(TokenType.OPERATOR, value);
				}
				
				//error
				else {
					throw new LexerException("Invalid tag token.");
				}
			}
		}
		
		//empty
		else {
			token = new Token(TokenType.EOF, null);
		}
	}
	
	
	/**
	 * Private method which determines wheter the passed value is a valid variable.
	 * @param value
	 * @return <code>true</code> if valid, <code>false</code> if not.
	 */
	private boolean isValidVariable(char[] value) {
		if(!Character.isLetter(value[0])) {
			return false;
		}
		
		int i = 1;
		while(i < value.length) {
			if(!Character.isLetter(value[i]) && !Character.isDigit(value[i]) && value[i] != '_') {
				return false;
			}
			
			i++;
		}
		
		return true;
	}
	
	
	/**
	 * Private method which determines whether the passed value is a valid function.
	 * @param value
	 * @return <code>true</code> if valid, <code>false</code> if not.
	 */
	private boolean isValidFunction(char[] value) {
		if(!Character.isLetter(value[0])) {
			return false;
		}
		
		int i = 1;
		while(i < value.length) {
			if(!Character.isLetter(value[i]) && !Character.isDigit(value[i]) && value[i] != '_') {
				return false;
			}
			
			i++;
		}
		
		return true;
	}
	
	
	/**
	 * Private method which determines wheter the passed value is a valid operator.
	 * @param value
	 * @return <code>true</code> if valid, <code>false</code> if not.
	 */
	private boolean isValidOperator(char value) {
		if(value == '+' || value == '-' || value == '*' || value == '/' || value == '^') {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * A private method which skips all blanks in the input text.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			
			char c = data[currentIndex];
			
			//skip blanks
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
			}
			
			//stop when you reach a non-blank character
			else {
				break;
			}
		}
	}
}
