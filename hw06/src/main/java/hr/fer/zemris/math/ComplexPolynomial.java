package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class which models a polynomial of complex numbers with the following function:<br> 
 * <code>f(z) = zn*z^n + zn-1*z^n-1 + ... + z2*z^2 + z1*z + z0</code>
 * 
 * @author fabjanvucina
 */
public class ComplexPolynomial {

	/**
	 * Complex numbers which represent Zi factors of the <code>f(z) = zn*z^n + zn-1*z^n-1 + ... + z2*z^2 + z1*z + z0</code> function.
	 */
	private List<Complex> factors;
	
	/**
	 * Public constructor which takes the polynomial parameters in ascending order.
	 * @param factors
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = new ArrayList<>();
		
		for(Complex factor : factors) {
			if(factor == null) throw new NullPointerException("You passed a null reference for a factor.");
			
			this.factors.add(factor);
		}
	}
	
	/**
	 * A function which returns the order of the polynom, a.k.a. the biggest power in the polynomal.
	 * @return order of the polynomial
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}
	
	/**
	 * @param p other polynomial
	 * @return multiplied polynomials
	 * @throws NullPointerException if <code>p == null</code>.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		
		//initialize multipliedFactors array and fill it with zeroes
		Complex[] multipliedFactors = new Complex[this.order() + p.order() + 1];
		Arrays.fill(multipliedFactors, Complex.ZERO);
		
		//multiply each element from this polynomial with each element in the other polynomial
		for(int i = 0; i < this.order() + 1; i++) {
			for(int j = 0; j < p.order() + 1; j++) {
				
				//add chunk with power of i+j to current sum of chunks of that power
				Complex calculatedChunk = this.factors.get(i).multiply(p.factors.get(j));
				multipliedFactors[i+j] = multipliedFactors[i+j].add(calculatedChunk);
			}
		}
		
		return new ComplexPolynomial(multipliedFactors);
	}
	
	/**
	 * @return the first derivative of the current polynomial
	 */
	public ComplexPolynomial derive() {
		
		//initialize derivedFactors list
		Complex[] derivedFactors = new Complex[this.order()];
		
		// (4 * z^3)' = (12 * 3) * z^2
		// z3 is used to calculate new z2
		// z0 is lost
		for(int exp = this.order(); exp >= 1; exp--) {
			Complex derivedFactor = this.factors.get(exp).multiply(new Complex(exp, 0));
			derivedFactors[exp - 1] =  derivedFactor;
		}
		
		return new ComplexPolynomial(derivedFactors);
	}
	
	/**
	 * @param z
	 * @return polynomial value
	 * @throws NullPointerException if <code>z == null</code>.
	 */
	public Complex apply(Complex z) {
		if(z == null) throw new NullPointerException("You passed a null reference for z.");
		
		Complex value = Complex.ZERO;
		
		//f(z) = f(z) + Zi * z^i
		for(int i = factors.size() - 1; i >= 0; i--) {
			Complex element = factors.get(i).multiply(z.power(i));
			value = value.add(element);
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
				
		for(int i = order(); i >= 0; i--) {
			sb.append(factors.get(i));
			
			if(i != 0) {
				sb.append("*z^");
				sb.append(i);
				sb.append(" + ");
			}
		}
		
		return sb.toString();
	}
}
