package hr.fer.oprpp1.custom.collections.demo;

import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class IteratorDemo7 {

	public static void main(String[] args) {
		
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		var removedElement = iter.next();
		iter.remove();
		
		examMarks.put("Roko", 3);
		
		System.out.println("After removing element: " + removedElement.getKey() + " = " + removedElement.getValue() + " and putting new element, the map looks like this");
		
		for(var element : examMarks) {
			System.out.println(element.getKey() + " = " + element.getValue());
		}
		
	}

}
