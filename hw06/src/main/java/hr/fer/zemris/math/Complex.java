package hr.fer.zemris.math;

import static java.lang.Math.PI; 
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Immutable complex number class.
 * 
 * @author fabjanvucina
 */
public class Complex {
	
	/**
	 * Real part of the complex number.
	 */
	private double re;
	
	/**
	 * Imaginary part of the complex number.
	 */
	private double im;
	
	/**
	 * A complex number that represents 0
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * A complex number that represents 1
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * A complex number that represents -1
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * A complex number that represents i
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * A complex number that represents -i
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Public default constructor for a complex number object.
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Public constructor for a complex number.
	 * @param re real part of the new complex number
	 * @param im imaginary part of the new complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * @return absolute value 
	 */
	public double module() {
		return hypot(this.re, this.im);
	}
	
	/**
	 * @return angle
	 */
	private double angle() {
		return atan2(this.im, this.re);
	}
	
	/**
	 * @param c other complex number
	 * @return product complex number
	 * @throws NullPointerException if <code>c == null</code>
	 */
	public Complex multiply(Complex c) {
		if(c == null) throw new NullPointerException("You can't multiply a complex number with null!");
		
		// (x + yi)(u + vi) = (xu − yv) + (xv + yu)i
		return new Complex(this.re * c.re - this.im * c.im,
				   		   this.re * c.im + this.im * c.re);
		
	}
	
	/**
	 * @param c other complex number
	 * @return quotient complex number
	 * @throws NullPointerException if <code>c == null</code>
	 */
	public Complex divide(Complex c) {
		if(c == null) throw new NullPointerException("You can't divide complex number with null");
		if(c.re == 0.0 && c.im == 0.0) throw new IllegalArgumentException("You can't divide with zero");
		
		//1/(u^2 + v^2)
		double factor = 1 / (pow(c.re, 2) + pow(c.im, 2));
		
		// (x + yi)/(u + vi) = 1/(u^2 + v^2) * [(xu + yv) + (xv - yu)i]
		return new Complex(factor * (this.re * c.re + this.im * c.im),
		   		   		   factor * (this.im * c.re - this.re * c.im));	
	}
	
	
	/**
	 * @param c other complex number
	 * @return sum complex number
	 * @throws NullPointerException if <code>c == null</code>
	 */
	public Complex add(Complex c) {
		if(c == null) throw new NullPointerException("You can't add null to a complex number.");
		   
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * @param c other complex number
	 * @return difference complex number
	 * @throws NullPointerException if <code>c == null</code>
	 */
	public Complex sub(Complex c) {
		if(c == null) throw new NullPointerException("You can't subtract null from a complex number.");
		   
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * @return negated complex number
	 */
	public Complex negate() {
		double negatedRe = this.re == 0 ? 0 : this.re * -1.0;
		double negatedIm = this.im == 0 ? 0 : this.im * -1.0;
		
		return new Complex(negatedRe, negatedIm);
	}
	
	/** 
	 * @param n the exponent
	 * @return current complex number raised to the power of <code>n</code>
	 * @throws IllegalArgumentException if <code>n</code> is a negative number
	*/	
	public Complex power(int n) {
		if(n < 0) throw new IllegalArgumentException("The power n should not be a negative number.");
		   
		//z1^n = r1^n * [cos(n*a) + i*sin(n*a)]
		return new Complex(pow(this.module(), n) * cos(n * this.angle()),
				   					pow(this.module(), n) * sin(n * this.angle()));
	}
	
	
	/**
	 * @param n the root radical
	 * @return list of <code>n</code>-th roots
	 * @throws IllegalArgumentException if <code>n</code> is not a positive number
	*/
	public List<Complex> root(int n) {
		if(n < 1) throw new IllegalArgumentException("The root radical n should be a positive number.");
		   
	    List<Complex> roots = new ArrayList<>();
		for(int k=0; k<n; k++) {
			Complex root = new Complex(pow(this.module(), 1.0/n) * cos((this.angle() + 2*k*PI) / n),
									   pow(this.module(), 1.0/n) * sin((this.angle() + 2*k*PI) / n));
			roots.add(root);
		}
		   
		return roots;
	}
	
	@Override
	public String toString() {
		String connector = this.im >= 0 ? "+" : "-";
		return "(" + this.re + connector + "i" + abs(this.im) + ")";
	}
	
	/**
	 * A parser method which generates a new complex number from passed string. <br>
	 * Accepted format is: <code>x + iy<code>
	 * 
	 * @param s string that needs to be parsed
	 * @return new complex number
	 * @throws NullPointerException if <code>s == null</code>
	 */
   public static Complex parse(String s) {
	   if(s == null) throw new NullPointerException("You passed null as a reference to the input string.");
	   
	   boolean negativePrefix = false;
	   
	   //replace all spaces with "" so that 1 + 0i becomes 1+0i
	   s = s.replaceAll("\\s+","");
	   
	   //trim the input string
	   String trimmedInput = s.trim();
	   if(trimmedInput.charAt(0) == '+') {
		   trimmedInput = trimmedInput.substring(1);
	   }
	   if(trimmedInput.charAt(0) == '-') {
		   trimmedInput = trimmedInput.substring(1);
		   negativePrefix = true;
	   }
	   
	   
	   //special cases
	   if(trimmedInput.equals("i") && !negativePrefix) {
		   return Complex.IM;
	   }
	   if(trimmedInput.equals("i") && negativePrefix) {
		   return Complex.IM_NEG;
	   }
	   if(trimmedInput.equals("1") && !negativePrefix) {
		   return Complex.ONE;
	   }
	   if(trimmedInput.equals("1") && negativePrefix) {
		   return Complex.ONE_NEG;
	   }
	   
	   
	   
	   //our delimiter is + or -
	   Scanner sc = new Scanner(trimmedInput);
	   sc.useDelimiter("\\-|\\+");
	   double real = 0;
	   double imaginary = 0;
	   
	   try {
		   
		   //reading first token
		   String firstToken = sc.next().trim();
		   
		   // format: i * <im>
		   if(firstToken.startsWith("i")) {
			   firstToken = firstToken.substring(1);
			   
			   try {
				   imaginary = Double.parseDouble(firstToken);
				   if(negativePrefix) imaginary *= -1.0;
				   negativePrefix = false;
			   }
			   catch(Exception ex) {
				   sc.close();
				   throw new IllegalArgumentException("i<not_a_double> is not a valid complex number format.");
			   }
		   }
		   
		   //first token of input is the real part
		   else {
			   try {
				   real = Double.parseDouble(firstToken);
				   if(negativePrefix) real *= -1.0;
				   negativePrefix = false;
			   }
			   catch(Exception ex) {
				   sc.close();
				   throw new IllegalArgumentException("You have not entered a double number for the real part of complex number.");
			   }
			   
			   //there is something after the real part
			   if(sc.hasNext()) {
				   
				   //check operator
				   sc.reset();
				   String restOfComplexNumber = sc.next().trim();
				   if(restOfComplexNumber.startsWith("+")) {
					   negativePrefix = false;
				   }
				   else if (restOfComplexNumber.startsWith("-")) {
					   negativePrefix = true;
				   }
				   else {
					   sc.close();
					   throw new IllegalArgumentException(restOfComplexNumber.charAt(0) + "is not a valid math operation. Should be + or -");
				   }
				   
				   
				   //there is something after the operator
				   Scanner scIm = new Scanner(restOfComplexNumber.substring(1));
				   if(scIm.hasNext()) {
					   
					   //reading second token
					   String secondToken = scIm.next().trim();
					   
					   //imaginary part is 1
					   if(secondToken.equals("i")) {
						   imaginary = 1.0;
						   if(negativePrefix) imaginary *= -1.0;
					   }
					   
					   //correct format for imaginary part
					   else if(secondToken.startsWith("i")) {
						   secondToken = secondToken.substring(1);
						   
						   try {
							   imaginary = Double.parseDouble(secondToken);
							   if(negativePrefix) imaginary *= -1.0;
						   }
						   catch(Exception ex) {
							   sc.close();
							   throw new IllegalArgumentException("<real> <op> i<not_a_double> is not a valid complex number format.");
						   }
						   
						   
						   //there should not be anything after the imaginary part
						   if(sc.hasNext()) {
							   sc.close();
							   throw new IllegalArgumentException("There should be nothing after the imaginary part.");
						   }
					   }
					   
					   //imaginary part doesn't have "i"
					   else {
						   sc.close();
						   throw new IllegalArgumentException("Imaginary part should contain \"i\"");
					   }
					   
				   }
				   
				   //there is nothing after the operator
				   else {
					   sc.close();
					   throw new IllegalArgumentException("\"<real> <op>\" is an illegal format for a complex number.");
				   }
				 
			   }
			   
			   //only real part in complex number
			   else {
				   sc.close();
			   }
		   }
		   
	   } catch (Exception ex) {
		   sc.close();
		   throw new IllegalArgumentException(ex.getMessage());
	   }
	   
	   return new Complex(real, imaginary);
   }
}
