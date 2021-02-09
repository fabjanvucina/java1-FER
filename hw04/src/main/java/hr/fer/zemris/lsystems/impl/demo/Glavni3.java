package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo class for building Lindermayer systems.
 * 
 * @author fabjanvucina
 */
public class Glavni3 {

	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
