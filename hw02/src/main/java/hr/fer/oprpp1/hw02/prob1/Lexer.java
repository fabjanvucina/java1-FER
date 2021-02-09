package hr.fer.oprpp1.hw02.prob1;

import java.util.ArrayList;

/**
 * Simple lexical analyzer class.
 * 
 * @author fabjanvucina
 */
public class Lexer {
	
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
	 * @param text
	 * @throws <code>NullPointerException</code> if <code>text == null</code>.
	 */
	public Lexer(String text) {
		if(text == null) throw new NullPointerException("Input text should not be null");
		        
        this.data = text.toCharArray();
        this.currentIndex = 0;
        this.token = null;
        this.state = LexerState.BASIC;
	}
	
	
	/**
	 * Setter method for mode of operation.
	 * @param state The new state we want to apply to our lexical analyser
	 * @throws <code>NullPointerException</code> if <code>state == null</code>
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("State should not be null");
		
		this.state = state;
	}
	
	
	/**
	 * @return most recently generated token.
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
		
		//trying to reach first relevant character
		skipBlanks();
		
		//check if skipping blanks reached the end of the input, token is EOF
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}		
		
		//extract token with the appropriate method according to the state variable
		if(state == LexerState.BASIC) {
			extractBasicToken();
		}
		
		else {
			extractExtendedToken();
		}
		
		return token;
	}
	
	
	/**
	 * A private method which generates a new token from the input text following the BASIC ruleset for parsing.
	 */
	private void extractBasicToken() {
		//enter this method this method if first character after blanks is a letter or "\" -> start of a WORD or SYMBOL "\"
		if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			extractBasicWord();
		}
				
		//first character after blanks is a digit -> start of a NUMBER
		else if(Character.isDigit(data[currentIndex])) {
			extractNumber();
		}
				
		//first character is a SYMBOL
		else {
			extractSymbol();
		}
					
	}
	
	
	/**
	 * A private method which generates a new token from the input text following the EXTENDED ruleset for parsing.
	 */
	private void extractExtendedToken() {
		if(data[currentIndex] == '#') {
			extractSymbol();
		}
		
		else {
			extractExtendedWord();
		}
	}
	
	
	/**
	 * A private method which generates a WORD token from input using the B rule set.
	 */
	private void extractBasicWord() {
		var wordChunks = new ArrayList<String>();
		int startIndex = currentIndex;
		
		while(currentIndex != data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
			
			//we read "\"
			if(data[currentIndex] == '\\' && currentIndex != data.length-1) {
				
				// escape sequence for a digit or '\' which become a part of the word
			    if(Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\'){
			    	//add stuff before \
					wordChunks.add(new String(data, startIndex, currentIndex-startIndex));
					
					//add what we are escaping
					wordChunks.add(Character.toString(data[currentIndex + 1]));
					currentIndex += 2;
					startIndex = currentIndex;
				}
				
				// "\" isn't a start of an escape sequence, invalid input
			    else {
			    	throw new LexerException("Single backslash is invalid input");
			    }
			}
			
			// we read "\" on the last position of the input text
			else if(data[currentIndex] == '\\' && currentIndex == data.length-1) {
				throw new LexerException("Single backslash is invalid input");
			}	
			
			//we read a letter
			else {
				currentIndex++;
			}
		}
		
		//connect all the chubks
		String lastChunk = new String(data, startIndex, currentIndex-startIndex);
		wordChunks.add(lastChunk);
		
		StringBuilder sb = new StringBuilder();
		for(String chunk : wordChunks) {
			sb.append(chunk);
		}
		
		token = new Token(TokenType.WORD, sb.toString());
	}
	
	
	/**
	 * A private method which generates a WORD token from input using the EXTENDED rule set.
	 */
	private void extractExtendedWord() {
		int startIndex = currentIndex;
		
		//delimiters of extended word
		while(currentIndex != data.length && data[currentIndex] != ' ' && data[currentIndex] != '\t' && data[currentIndex] != '\r' && data[currentIndex] != '\n') {
			//don't include the #
			if(data[currentIndex] == '#') {
				break;
			}
			currentIndex++;
		}
		
		String word = new String(data, startIndex, currentIndex-startIndex);
		token = new Token(TokenType.WORD, word);
	}
	
	/**
	 * A private method that generates a NUMBER token.
	 */
	private void extractNumber() {
		int startIndex = currentIndex++;
		
		while(currentIndex != data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		int endIndex = currentIndex;
		
		String numberString = new String(this.data, startIndex, endIndex-startIndex);
		Long number;
		
		//try parsing Long
		try {
			
			number = Long.parseLong(numberString);
					
		} catch(Exception ex) {
			throw new LexerException(numberString + " cannot be shown as a Long");
		}
		
		token = new Token(TokenType.NUMBER, number);
	}
	
	
	/**
	 * A private method that generates a SYMBOL token.
	 */
	private void extractSymbol() {
		token = new Token(TokenType.SYMBOL, data[currentIndex++]);
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
