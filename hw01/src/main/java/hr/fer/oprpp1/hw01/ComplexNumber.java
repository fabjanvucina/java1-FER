package hr.fer.oprpp1.hw01;

import static java.lang.Math.sin;   

import java.util.Scanner;

import static java.lang.Math.cos;
import static java.lang.Math.atan2;
import static java.lang.Math.hypot;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.PI;

/**
 * Immutable complex number class.
 * 
 * @author fabjanvucina
 */
public class ComplexNumber {
	
	/**
	 * Real part of the complex number.
	 */
	private double real;
	
	/**
	 * Imaginary part of the complex number.
	 */
	private double imaginary;
	
	/**
	 * Public constructor.
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	
	/**
	 * Static method which creates a new complex number: <code>(real, 0)</code>.
	 * @param real
	 * @return new complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	
	/**
	 * Static method which creates a new complex number: <code>(0, imaginary)</code>.
	 * @param imaginary
	 * @return new complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	
	/**
	 * Static method which creates a new complex number using the set magnitude and angle.
	 * @param magnitude absolute value of the new complex number
	 * @param angle
	 * @return new complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	
	/**
	 * Method which parses an input string into a complex number object.
	 * @param input
	 * @return new complex number
	 * @throws NullPointerException if <code>input == null</code>
	 */
   public static ComplexNumber parse(String input) {
	   if(input == null) throw new NullPointerException("You passed null as a reference to the input string.");
	   
	   boolean negativePrefix = false;
	   
	   //trim the input string
	   String trimmedInput = input.trim();
	   if(trimmedInput.charAt(0) == '+') {
		   trimmedInput = trimmedInput.substring(1);
	   }
	   if(trimmedInput.charAt(0) == '-') {
		   trimmedInput = trimmedInput.substring(1);
		   negativePrefix = true;
	   }
	    
	   //special cases
	   if(trimmedInput.equals("i") && !negativePrefix) {
		   return new ComplexNumber(0, 1);
	   }
	   if(trimmedInput.equals("i") && negativePrefix) {
		   return new ComplexNumber(0, -1);
	   }
	   
	   
	   Scanner sc1 = new Scanner(trimmedInput);
	   double real = 0;
	   double imaginary = 0;
	   
	   try {
		   
		   //reading first double
		   sc1.useDelimiter("\\-|\\+|i");
		   double token1 = sc1.nextDouble();
		   if(negativePrefix) token1 *= -1.0;
		   sc1.reset();
		   
		   //if correct format, there is an imaginary part
		   if(sc1.hasNext()) {
			   
			   String next = sc1.next();
			   
			   //only "i" after number
			   if(next.equals("i")) {
				   //the first token is the imaginary part
				   imaginary = token1;
			   }
			   
			   else if(next.charAt(0) == '+') {
				   //first token is the real part
				   real = token1;
				   Scanner sc2 = new Scanner(next.substring(1));
				   
				   //there is something after "+"
				   if(sc2.hasNext()) {
					   
					   String next2 = sc2.next();
					   sc2.close();
					   
					   //imaginary part is 1
					   if(next2.equals("i")) {
						   imaginary = 1;
					   }
					   
					   //"i" isn't after +
					   else {
						   
						   Scanner sc3 = new Scanner(next2);
						   
						   //reading second double
						   sc3.useDelimiter("i");
						   double token2 = sc3.nextDouble();
						   sc3.reset();
						   
						   //there is only "i" after the second double
						   if(sc3.next().equals("i")) {
							   //second token is the imaginary part
							   imaginary = token2;
							   
							   sc3.close();
						   }
						   
						   //something invalid after the second double
						   else {
							   sc3.close();
							   throw new IllegalArgumentException();
						   }
					   }
				   }
				   
				   //number+ is an illegal format
				   else {
					   sc2.close();
					   throw new IllegalArgumentException();
				   }
				    
			   }
			   
			   else if(next.charAt(0) == '-') {
				   //first token is the real part
				   real = token1;
				   Scanner sc2 = new Scanner(next.substring(1));
				   
				   //there is something after "-"
				   if(sc2.hasNext()) {
					   
					   String next2 = sc2.next();
					   sc2.close();
					   
					   //imaginary part is 1
					   if(next2.equals("i")) {
						   imaginary = -1;
					   }
					   
					   //"i" isn't after +
					   else {
						   
						   Scanner sc3 = new Scanner(next2);
						   
						   //reading second double
						   sc3.useDelimiter("i");
						   double token2 = sc3.nextDouble();
						   token2 *= -1.0;
						   sc3.reset();
						   
						   //there is only "i" after the second double
						   if(sc3.next().equals("i")) {
							   //second token is the imaginary part
							   imaginary = token2;
							   
							   sc3.close();
						   }
						   else {
							   sc3.close();
							   throw new IllegalArgumentException();
						   }
					   }
				   }
				   
				 //number+ is an illegal format
				   else {
					   sc2.close();
					   throw new IllegalArgumentException();
				   }
			   }
			   
			   //number not followed by "+", "-" or "i"
			   else {
				   sc1.close();
				   throw new IllegalArgumentException();
			   } 
			   
			   sc1.close();
		   }
		   
		   //only one number without i
		   else {
			   //first token is the real part
			   real = token1;
			   
			   sc1.close();
		   }
		   
	   } catch (Exception ex) {
		   System.out.println(ex.getLocalizedMessage());
		   throw new IllegalArgumentException("Invalid format of complex number");
	   }
	   
	   return new ComplexNumber(real, imaginary);
	}
   
   
   /**
	 * @return the real
	 */
	public double getReal() {
		return real;
	}


	/**
	 * @return the imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}


   /**
    * @return the magnitude
    */
   public double getMagnitude() {
	   return hypot(this.real, this.imaginary);
   }
   
   
   /**
    * @return the angle
    */
   public double getAngle() {
	   return atan2(this.imaginary, this.real);
   }
   
   
   /**
    * Method which adds the passed complex number to the this complex number and returns it as a new complex number.
    * @param other
    * @return new complex number
    * @throws NullPointerException if <code>other == null</code>
    */
   public ComplexNumber add(ComplexNumber other) {
	   if(other == null) throw new NullPointerException("You passed null as a reference to an object.");
	   
	   return new ComplexNumber(this.real + other.getReal(), this.imaginary + other.getImaginary());
   }
   
   
   /**
    * Method which subtracts the passed complex number from the existing complex number and returns it as a new complex number.
    * @param other
    * @return new complex number
    * @throws NullPointerException if <code>other == null</code>
    */
   public ComplexNumber sub(ComplexNumber other) {
	   if(other == null) throw new NullPointerException("You passed null as a reference to an object.");
	   
	   return new ComplexNumber(this.real - other.getReal(), this.imaginary - other.getImaginary());
   }

   
   /**
    * Method which multiplies the passed complex number with the existing complex number and returns it as a new complex number.
    * @param other
    * @return new complex number
    * @throws NullPointerException if <code>other == null</code>
    */
   public ComplexNumber mul(ComplexNumber other) {
	   if(other == null) throw new NullPointerException("You passed null as a reference to an object.");
	   	   
	   //z1*z2 = r1*r2 * [cos(a+b) + i*sin(a+b)]
	   return new ComplexNumber(this.getMagnitude() * other.getMagnitude() * cos(this.getAngle() + other.getAngle()),
			   					this.getMagnitude() * other.getMagnitude() * sin(this.getAngle() + other.getAngle()));
   }

   
   /**
    * Method which divides the existing complex number with the passed complex number and returns it as a new complex number.
    * @param other
    * @return new complex number
    * @throws NullPointerException if <code>other == null</code>
    */
   public ComplexNumber div(ComplexNumber other) {
	   if(other == null) throw new NullPointerException("You passed null as a reference to an object.");
	   
	   //z1/z2 = r1/r2 * [cos(a-b) + i*sin(a-b)]
	   return new ComplexNumber(this.getMagnitude() / other.getMagnitude() * cos(this.getAngle() - other.getAngle()),
			   					this.getMagnitude() / other.getMagnitude() * sin(this.getAngle() - other.getAngle()));
   }
   
   
   /**
    * Method which raises the existing complex number to the power of the passed argument and returns it as a new complex number.
    * @param exp
    * @return new complex number
    * @throws IllegalArgumentException if <code>exp</code> is a negative number
    */
   public ComplexNumber power(int exp) {
	   if(exp < 0) throw new IllegalArgumentException("The power n should not be a negative number.");
	   
	   //z1^n = r1^n * [cos(n*a) + i*sin(n*a)]
	   return new ComplexNumber(pow(this.getMagnitude(), exp) * cos(exp * this.getAngle()),
			   					pow(this.getMagnitude(), exp) * sin(exp * this.getAngle()));
   }
   
   
   /**
    * Method which returns an array of roots of this complex number.
    * @param n root radical
    * @return array of new complex numbers
    * @throws IllegalArgumentException if <code>n</code> is not a positive number
    */
   public ComplexNumber[] root(int n) {
	   if(n < 1) throw new IllegalArgumentException("The root radical n should be a positive number.");
	   
	   ComplexNumber[] roots = new ComplexNumber[n];
	   for(int k=0; k<n; k++) {
		   roots[k] = new ComplexNumber(pow(this.getMagnitude(), 1.0/n) * cos((this.getAngle() + 2*k*PI) / n),
  										pow(this.getMagnitude(), 1.0/n) * sin((this.getAngle() + 2*k*PI) / n));
	   }
	   
	   return roots;
   }


   @Override
   public String toString() {
	   String connector = this.imaginary >= 0 ? "+" : "-";
	   return this.real + connector + abs(this.imaginary) + "i";
   }


   @Override
   public int hashCode() {
	   return Double.valueOf(this.real).hashCode() + Double.valueOf(this.imaginary).hashCode();
   }


   @Override
   public boolean equals(Object obj) {
	   if (this == obj)
		   return true;
	   if (!(obj instanceof ComplexNumber))
		   return false;
	   ComplexNumber other = (ComplexNumber) obj;
		
	   if(abs(this.real-other.real) < 1E-8 && abs(this.imaginary-other.imaginary) < 1E-8) {
		   return true;
	   }
		
	   return false;
   }
}