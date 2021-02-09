package hr.fer.zemris.math;

public class Demo {

	public static void main(String[] args) {
		
		var crp = new ComplexRootedPolynomial(new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
	    ComplexPolynomial cp1 = crp.toComplexPolynom();
	    
		System.out.println(crp);
		System.out.println(cp1);
		System.out.println(cp1.derive());
		
		var cp2 = new ComplexPolynomial(Complex.ONE_NEG, new Complex(2,1));
		var cp3 = new ComplexPolynomial(new Complex(-6,0), new Complex(5,0));
		System.out.println(cp2.multiply(cp3));
			
	}
}
