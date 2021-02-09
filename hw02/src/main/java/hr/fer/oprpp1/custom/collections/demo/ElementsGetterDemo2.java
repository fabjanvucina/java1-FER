package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterDemo2 {

	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		ElementsGetter getter1 = col1.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
	}

}
