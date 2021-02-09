package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner; 
import static java.lang.Math.pow;
import static java.lang.Math.toRadians;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

/**
 * A class which builds instances of Lindermayer systems.
 * 
 * @author fabjanvucina
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Map for the inputed commands
	 */
	private Dictionary<String, Command> commands;
	
	/**
	 * Map for the inputed productions
	 */
	private Dictionary<String, String> productions;
	
	/**
	 * Unit length of the new Lindermayer system
	 */
	private double unitLength;
	
	/**
	 * Unit length degree scaler of the new Lindermayer system
	 */
	private double unitLengthDegreeScaler;
	
	/**
	 * Origin vector of the new Lindermayer system
	 */
	private Vector2D origin;
	
	/**
	 * Starting angle for the new Lindermayer system
	 */
	private double angle;
	
	/**
	 * Starting axiom for the new Lindermayer system
	 */
	private String axiom;
	
	/**
	 * A public constructor for the Lindermayer system builder.
	 */
	public LSystemBuilderImpl() {
		this.commands = new Dictionary<>();
		this.productions = new Dictionary<>();
		this.unitLength = 0.1;
		this.unitLengthDegreeScaler = 1;
		this.origin = new Vector2D(0, 0);
		this.angle = 0;
		this.axiom = "";
	}
	
	
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command;
		
		Scanner sc = new Scanner(action);
		String actionType = sc.next();
		
		//find out which action
		switch(actionType) 
        { 
            case "draw": 
    			command = new DrawCommand(sc.nextDouble());
    			sc.close();
                break; 
                
            case "skip": 
            	command = new SkipCommand(sc.nextDouble());
            	sc.close();
                break; 
                
            case "scale": 
            	command = new ScaleCommand(sc.nextDouble());
            	sc.close();
                break; 
                
            case "rotate": 
            	command = new RotateCommand(sc.nextDouble());
            	sc.close();
                break; 
                
            case "push": 
            	command = new PushCommand();
            	sc.close();
                break; 
            case "pop": 
            	command = new PopCommand();
            	sc.close();
                break; 
                
            case "color": 
            	int color = Integer.parseInt(sc.next().trim(), 16);
            	command = new ColorCommand(new Color(color));
            	sc.close();
                break; 
                
            default: 
            	sc.close();
                throw new IllegalArgumentException(action + " is an invalid action");
        } 
		
		//add command to the command dictionary
		commands.put(String.valueOf(symbol), command);
		
		return this;
	}

	
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(String.valueOf(symbol), production);
		
		return this;
	}
	
	
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = toRadians(angle);
		
		return this;
	}

	
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		
		return this;
	}
	

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		
		return this;
	}

	
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		
		//iterate over fractal definition lines
		for(String line : lines) {
			
			//do work only if there is any stuff
			if(!line.equals("")) {
				Scanner sc = new Scanner(line);
				
				//find out which config type
				String configType = sc.next();
				switch(configType) 
		        { 
		            case "origin": 
		    			double x = sc.nextDouble();
		    			double y = sc.nextDouble();
		    			setOrigin(x, y);
		    			sc.close();
		                break; 
		                
		            case "angle": 
		            	setAngle(sc.nextDouble());
		            	sc.close();
		                break; 
		                
		            case "unitLength": 
		            	setUnitLength(sc.nextDouble());
		            	sc.close();
		                break; 
		                
		            case "unitLengthDegreeScaler": 
		            	double unitLengthDegreeScaler;
		            	
		            	//fraction format
		            	if(line.contains("/")) {
		            		sc.useDelimiter("/");
		            		double numerator = Double.parseDouble(sc.next());
		            		double denominator = Double.parseDouble(sc.next());
		            		
		            		unitLengthDegreeScaler = numerator/denominator;
		            	}
		            	
		            	//double format
		            	else {
		            		unitLengthDegreeScaler = sc.nextDouble();
		            	}
		            	
		            	setUnitLengthDegreeScaler(unitLengthDegreeScaler);
		            	
		            	sc.close();
		                break; 
		                
		            case "axiom": 
		            	setAxiom(sc.next());
		            	sc.close();
		                break; 
		                
		            case "command": 
		            	char commandSymbol = sc.next().charAt(0);
		            	String action = sc.next();
		            	if(sc.hasNext()) {
		            		String argument = sc.next();
		            		action = action.concat(" ").concat(argument);
		            	}
		            	
		            	registerCommand(commandSymbol, action);
		            	sc.close();
		                break; 
		                
		            case "production": 
		            	char productionSymbol = sc.next().charAt(0);
		            	String production = sc.next();
		            	registerProduction(productionSymbol, production);
		            	sc.close();
		                break; 
		                
		            default: 
		            	sc.close();
		                throw new IllegalArgumentException(line + " is an invalid configuration line");
		        } 
					
			}
		}
		
		return this;
	}
	
	@Override
	public LSystem build() {
		LSystem lSystem = new LSystem() {

			@Override
			public String generate(int level) {
				
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
			
			@Override
			public void draw(int level, Painter painter) {
				
				//create context and push start position for turtle
				Context context = new Context();
				TurtleState startTurtleState = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), 
															   Color.BLACK, unitLength * pow(unitLengthDegreeScaler, level));
				context.pushState(startTurtleState);
				
				//generate final symbol sequence by applying productions to symbols for specified depth times
				String finalSequence = generate(level);
				
				//iterate over final sequence and execute corresponding action for every symbol
				for(int i=0, n=finalSequence.length(); i<n; i++) {
					String symbol = String.valueOf(finalSequence.charAt(i));
					Command command = commands.get(symbol);
					
					//execute the command if it exists
					if(command != null) {
						command.execute(context, painter);
					}
				}
			}
			
		};
		
		return lSystem;
	}
}
