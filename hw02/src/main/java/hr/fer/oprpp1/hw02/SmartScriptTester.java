package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Demo class for the lexical analyis of an example input text. Language rules can be found in the task assignment.
 * @author fabjanvucina
 */
public class SmartScriptTester {

	public static void main(String[] args) throws IOException {
		
		String docBody = new String(Files.readAllBytes(Paths.get("src/test/resources/example.txt")),StandardCharsets.UTF_8);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} 
		
		catch(SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} 
		
		catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println("Original text:");
		System.out.println("~");
		System.out.println(docBody);
		System.out.println("~");
		System.out.println();
		System.out.println("Text generated from lexical analysis tokens:");
		System.out.println("~");
		System.out.println(originalDocumentBody); // should write something like original content of docBody
		System.out.println("~");
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println();
		System.out.println("The texts are identical: " + document.equals(document2));

	}
}