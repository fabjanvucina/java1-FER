package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class LSystemBuilderImplTest {
	
	@Test 
	public void testGenerateLevel0() {
		assertEquals("F", generate(0));
	}
	
	@Test 
	public void testGenerateLevel1() {
		assertEquals("F+F--F+F", generate(1));
	}
	
	@Test 
	public void testGenerateLevel2() {
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", generate(2));
	}
	

	public String generate(int level) {
		String axiom = "F";
		Dictionary<String,String> productions = new Dictionary<>();
		productions.put("F", "F+F--F+F");
		
		//starting sequence
		String currentSequence = axiom;
		
		//repeat process for every level
		for(int i=0; i<level; i++) {
			
			//store right sides of applied productions
			var sequenceChunks = new ArrayList<String>();
			
			//apply production on every symbol in current sequence
			for(int j=0, n=currentSequence.length(); j<n; j++) {
				String symbol = String.valueOf(currentSequence.charAt(j));
				String production = productions.get(symbol);
				
				//apply production if it exists
				if(production != null) {
					sequenceChunks.add(production);
				}
				
				//leave symbol
				else {
					sequenceChunks.add(symbol);
				}
			}
			
			//assemble chunks into current sequence
			StringBuilder sb = new StringBuilder();
			for(String chunk : sequenceChunks) {
				sb.append(chunk);
			}
			
			currentSequence = sb.toString();
		}
		
		return currentSequence;
	}
}
