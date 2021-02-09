package hr.fer.oprpp1.custom.scripting.parser;

import java.util.ArrayList;

import hr.fer.oprpp1.custom.scripting.lexer.LexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Simple parser class.
 * 
 * @author fabjanvucina
 */
public class SmartScriptParser {
	
	/**
	 * Private instance of a lexer designed to return tokens from an input following specific lexical rules.
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * Private stack object which represents the document tree;
	 */
	private ObjectStack documentTree;
	
	/**
	 * Public constructor.
	 * @param documentBody 
	 */
	public SmartScriptParser(String documentBody) {
		if(documentBody == null) throw new NullPointerException("Document body should not be empty, it generates a null reference String object");
		
		//create new lexer to return TEXT and TAG tokens
		lexer = new SmartScriptLexer(documentBody);
		
		//generate document tree using a stack object and push the root document node
		documentTree = new ObjectStack();
		documentTree.push(new DocumentNode());
		
		//parse the input document
		parseTokens();
	}
	
	
	/**
	 * @return root <code>DocumentNode</code> object.
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode)documentTree.peek();
	}
	

	/**
	 * A private method which parses tokens obtained from the <code>SmartScriptLexer</code> object.
	 */
	private void parseTokens() {
		try {
			
			//getting tokens sequentially and parsing immediately with a method relative to the token type.
			Token nextToken = lexer.nextToken();
			while(nextToken.getValue() != null) {
				
				//lexer vratio TEXT token
				if(nextToken.getType() == TokenType.TEXT) {
					
					//provjeri je li to pocetni "" prije(TAG prvi token)
					if(!((String)nextToken.getValue()).equals("")) {
						//izbaci '\' iz "\{" i generiraj TextNode 
						parseTextToken(nextToken);
					}
					lexer.setState(LexerState.TAG);
				}
				
				//lexer vratio TAG token
				else {
					//pomocu novog lexera s obzirom na vrstu taga, generiraj ForLoopNode i pushaj ga s FOR tagom, poppaj ForLoopNode s END tagom ili generiraj EchoNode s = tagom
					parseTagToken(nextToken);
					lexer.setState(LexerState.TEXT);	
				}
				
				//get next token
				nextToken = lexer.nextToken();
			}
			
			//GOTOVO PARSIRANJE, lexer vratio (EOF, null) token
			//System.out.println("PARSIRANJE ULAZNOG DOKUMENTA USPJEŠNO ZAVRŠENO!");
				
			
		} 
		
		catch(Exception ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
	}
	
	
	/**
	 * A private method which parses a TEXT token into a TextNode.
	 */
	private void parseTextToken(Token token) {		
		//add textNode as a child of the most recently pushed node
		TextNode textNode = new TextNode((String)token.getValue());
		Node parent = (Node)documentTree.peek();
		parent.addChildNode(textNode);	
	}
	
	
	/**
	 * A private method which parses a TAG token.
	 */
	private void parseTagToken(Token nextToken) {	
		char[] tokenData = ((String)nextToken.getValue()).toCharArray();
		IndexWrapper currentIndex = new IndexWrapper(0);
		
		//skip blanks, reach start of tag name
		skipBlanks(currentIndex, tokenData);
		
		//FOR tag
		if(tokenData.length >= 3 && (new String(tokenData, currentIndex.getValue(), 3)).equalsIgnoreCase("FOR")) {
			ElementVariable variable = null;
			Element startExpression = null;
			Element endExpression = null;
			Element stepExpression = null;
			
			//jump over FOR
			currentIndex.setValue(currentIndex.getValue() + 3);
			
			//initialize new lexer for getting element tokens
			String forTagContent = new String(tokenData, currentIndex.getValue(), tokenData.length - currentIndex.getValue());
			SmartScriptLexer tagLexer = new SmartScriptLexer(forTagContent);
			tagLexer.setState(LexerState.ELEMENTS);
			Token token;
			
			//variable
			token = tagLexer.nextToken();
			if(token.getType() != TokenType.VARIABLE) {
				throw new SmartScriptParserException("Given first for-loop parameter is not a variable.");
			}
			if(token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Too few for-loop arguments.");
			}
			variable = new ElementVariable((String)token.getValue());
			
			//startExpression
			token = tagLexer.nextToken();
			if(token.getType() == TokenType.FUNCTION || token.getType() == TokenType.OPERATOR) {
				throw new SmartScriptParserException("Start expression should be a variable, number or string.");
			}
			if(token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Too few for-loop arguments.");
			}
			if(token.getType() == TokenType.VARIABLE) {
				startExpression = new ElementVariable((String)token.getValue());
			}
			if(token.getType() == TokenType.CONSTANT_INTEGER) {
				startExpression = new ElementConstantInteger(((Integer)token.getValue()).intValue());
			}
			if(token.getType() == TokenType.CONSTANT_DOUBLE) {
				startExpression = new ElementConstantDouble(((Double)token.getValue()).doubleValue());
			}
			if(token.getType() == TokenType.STRING) {
				startExpression = new ElementString((String)token.getValue());
			}
			
			//endExpression
			token = tagLexer.nextToken();
			if(token.getType() == TokenType.FUNCTION || token.getType() == TokenType.OPERATOR) {
				throw new SmartScriptParserException("End expression should be a variable, number or string.");
			}
			if(token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Too few for-loop arguments.");
			}
			if(token.getType() == TokenType.VARIABLE) {
				endExpression = new ElementVariable((String)token.getValue());
			}
			if(token.getType() == TokenType.CONSTANT_INTEGER) {
				endExpression = new ElementConstantInteger(((Integer)token.getValue()).intValue());
			}
			if(token.getType() == TokenType.CONSTANT_DOUBLE) {
				endExpression = new ElementConstantDouble(((Double)token.getValue()).doubleValue());
			}
			if(token.getType() == TokenType.STRING) {
				endExpression = new ElementString((String)token.getValue());
			}
			
			//? stepExpression
			token = tagLexer.nextToken();
			if(token.getType() == TokenType.FUNCTION || token.getType() == TokenType.OPERATOR) {
				throw new SmartScriptParserException("Step expression should be a variable, number or string.");
			}
			if(token.getType() == TokenType.VARIABLE) {
				stepExpression = new ElementVariable((String)token.getValue());
			}
			if(token.getType() == TokenType.CONSTANT_INTEGER) {
				stepExpression = new ElementConstantInteger(((Integer)token.getValue()).intValue());
			}
			if(token.getType() == TokenType.CONSTANT_DOUBLE) {
				stepExpression = new ElementConstantDouble(((Double)token.getValue()).doubleValue());
			}
			if(token.getType() == TokenType.STRING) {
				stepExpression = new ElementString((String)token.getValue());
			}
			
			//check too many arguments
			token = tagLexer.nextToken();
			if(token.getType() != TokenType.EOF) {
				throw new SmartScriptParserException("Too many for-loop arguments.");
			}
			
			//and forLoopNode as a child of the most recently pushed node
			ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
			Node parent = (Node)documentTree.peek();
			parent.addChildNode(forLoopNode);	
			
			//push forLoopNode to the documentTree
			documentTree.push(forLoopNode);
			
		}
		
		//END tag
		else if(tokenData.length >= 3 && (new String(tokenData, currentIndex.getValue(), 3)).equalsIgnoreCase("END")){
			//jump over END
			currentIndex.setValue(currentIndex.getValue() + 3);
			
			//reach enclosing tag
			skipBlanks(currentIndex, tokenData);
			
			//incorrect END tag format
			if(currentIndex.getValue() < tokenData.length) {
				throw new SmartScriptParserException("Incorrect END tag format");
			}
			
			//correct END tag format
			else {
				//pop the ForLoopNode
				documentTree.pop();
				
				if(documentTree.isEmpty()) throw new SmartScriptParserException("The END tag did not have a corresponding FOR tag that it was closing");
				
			}		
			
		}
		
		//= tag
		else if(tokenData.length >= 1 && tokenData[currentIndex.getValue()] == '=') {
			Element[] echoElements;
			var elements = new ArrayList<Element>();
			
			//jump over =
			currentIndex.setValue(currentIndex.getValue() + 1);
			
			//skip blanks, reach start of tag name
			skipBlanks(currentIndex, tokenData);
			
			//initialize new lexer for getting element tokens
			String echoTagContent = new String(tokenData, currentIndex.getValue(), tokenData.length - currentIndex.getValue());
			SmartScriptLexer tagLexer = new SmartScriptLexer(echoTagContent);
			tagLexer.setState(LexerState.ELEMENTS);
			Token token;
			
			//start parsing
			token = tagLexer.nextToken();
			while(token.getType() != TokenType.EOF) {
				
				if(token.getType() == TokenType.VARIABLE) {
					elements.add(new ElementVariable((String)token.getValue()));
				}
				
				if(token.getType() == TokenType.CONSTANT_INTEGER) {
					elements.add(new ElementConstantInteger((int)token.getValue()));
				}
				
				if(token.getType() == TokenType.CONSTANT_DOUBLE) {
					elements.add(new ElementConstantDouble((double)token.getValue()));
				}
				
				if(token.getType() == TokenType.STRING) {
					elements.add(new ElementString((String)token.getValue()));
				}
				
				if(token.getType() == TokenType.FUNCTION) {
					elements.add(new ElementFunction((String)token.getValue()));
				}
				
				if(token.getType() == TokenType.OPERATOR) {
					elements.add(new ElementOperator((String)token.getValue()));
				}
				
				token = tagLexer.nextToken();
			}
			
			//create echo node
			echoElements = (Element[])elements.toArray(new Element[0]);
			EchoNode echoNode = new EchoNode(echoElements);
			
			//add echoNode as a child to the most recently pushed node
			Node parent = (Node)documentTree.peek();
			parent.addChildNode(echoNode);
			
		}
		
		//invalid tag name
		else {
			throw new SmartScriptParserException("Invalid tag name.");
		}
	}
	
	
	/**
	 * A private method which skips all blanks in the input text.
	 */
	private void skipBlanks(IndexWrapper currentIndex, char[] tokenData) {
		while(currentIndex.getValue() < tokenData.length) {
			
			char c = tokenData[currentIndex.getValue()];
			
			//skip blanks
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex.setValue(currentIndex.getValue() + 1);
			}
			
			//stop when you reach a non-blank character
			else {
				break;
			}
		}
	}
	
	
	/**
	 * Private wrapper class for primitive integer index.
	 * 
	 * @author fabjanvucina
	 */
	private class IndexWrapper {
		private int value;
		
		public IndexWrapper(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
}
