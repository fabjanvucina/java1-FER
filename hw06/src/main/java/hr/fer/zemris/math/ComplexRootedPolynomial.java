package hr.fer.zemris.math;

import java.util.ArrayList; 
import java.util.List;

/**
 * A class which models a polynomial of complex numbers with the following function: <br>
 * <code>f(z) =  z0 * (z-z1) * (z-z2) * ... * (z-zn)</code>
 * 
 * @author fabjanvucina
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Complex number which represents <code>z0</code> in <code>f(z) =  z0 * (z-z1) * (z-z2) * ... * (z-zn)</code>
	 */
	private Complex constant;
	
	/**
	 * Complex numbers which represent function zeros of the <code>f(z) =  z0 * (z-z1) * (z-z2) * ... * (z-zn)</code> function.
	 */
	private List<Complex> roots;
	
	/**
	 * Public constructor.
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if(constant == null) throw new NullPointerException("You passed a null reference for constant z0.");
	
		this.constant = constant;
		
		this.roots = new ArrayList<>();
		for (Complex root : roots) {
			if(root == null) throw new NullPointerException("You passed a null reference for a root.");
			
			this.roots.add(root);
		}
	}
	
	/**
	 * @param z
	 * @return polynomial value
	 * @throws NullPointerException if <code>z == null</code>.
	 */
	public Complex apply(Complex z) {
		if(z == null) throw new NullPointerException("You passed a null reference for constant z.");
		
		//f(z) = z0
		Complex value = constant;
		
		//f(z) = f(z) * (z - zi)
		for(int i = 0; i < roots.size(); i++) {
			Complex element = z.sub(roots.get(i));
			value = value.multiply(element);
		}
		
		return value;
	}
	
	/**
	 * @return this polynomial in as an instance of the <code>ComplexPolyomial</code> class.
	 */
	public ComplexPolynomial toComplexPolynom() {
		
		// f(z) = z0 
		ComplexPolynomial result = new ComplexPolynomial(constant);
		
		// treat every root as separate complex polynomial function and multiply them
		// f(z) = z0 * [f0(z) * f1(z) * ... ]
		for(int i = 0; i < roots.size(); i++) {
			
			// fi(z) = z - zi
			result = result.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(constant);
		
		for(int i = 0; i < roots.size(); i++) {
			sb.append(" * ");
			
			sb.append("(z-");
			sb.append(roots.get(i));
			sb.append(")");
		}
		
		return sb.toString();
	}
	
	/**
	 * @param z 
	 * @param treshold the distance between root and <code>z</code> should be less than this
	 * @return index of the root that is closest to <code>z</code> under the defined treshold, -1 if no roots satisfy the condition
	 * @throws NullPointerException if <code>z == null</code>
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if(z == null) throw new NullPointerException("You passed a null reference for constant z.");
		
		//start values, if no root is "closer" than the treshold, these are correct results
		double smallestDistance = treshold;
		int candidateIndex = -1;
		
		for(int i = 0; i < roots.size(); i++) {
			double distance = roots.get(i).sub(z).module();
			
			//calculating the smallest distance and the index of the corresponding root(first root has index 0)
			if(distance < smallestDistance) {
				smallestDistance = distance;
				candidateIndex = i;
			}
		}
		
		return candidateIndex;
	}
}
