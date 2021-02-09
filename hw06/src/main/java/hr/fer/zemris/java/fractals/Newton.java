package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A program which displays fractals calculated sequentially using the Newton-Raphson iteration. <br>
 * The user has to enter at least 2 roots via the console to produce a fractal.
 * 
 * @author fabjanvucina
 */
public class Newton {

	public static void main(String[] args) {
		
		List<Complex> roots = new ArrayList<>();
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner sc = new Scanner(System.in);
		int rootCounter = 0;
		
		while(true) {
			
			//prompt for user to write root
			System.out.print("Root " + (rootCounter + 1) + "> ");
			
			//read command type
			String input = sc.nextLine();
			
			//end program if user inputs "done" and there are at least 2 roots entered
			if(input.equals("done") && rootCounter >= 2) {
				System.out.println("Image of fractal will appear shortly. Thank you.");
				sc.close();
				
				//convert roots list to roots array
				Complex[] rootsArray = new Complex[roots.size()];
				roots.toArray(rootsArray);
				
				//display the calculated fractal
				FractalViewer.show(new NewtonProducer(new ComplexRootedPolynomial(Complex.ONE, rootsArray)));	

				break;
			}
			
			//not enough roots
			else if(input.equals("done") && rootCounter < 2){
				System.out.println("You need to enter at least 2 roots. You need to enter " + (2 - rootCounter) + " more.");
			}
			
			//parse root
			else  {
				try {
					roots.add(Complex.parse(input));
					rootCounter++;
				}
				catch(Exception ex){
					System.out.println(ex.getMessage());
					System.out.println("Press any key to continue");
					sc.nextLine();
				}
			}
			
		}
	
	}
	
	/**
	 * A class that produces a fractal derived from Newton-Raphson iteration.
	 * 
	 * @author fabjanvucina
	 */
	public static class NewtonProducer implements IFractalProducer {
		
		/**
		 * The rooted polynomial on which we are performing Newton-Raphson iterations.
		 */
		ComplexRootedPolynomial rootedPolynomial;
		
		/**
		 * The polynomial in standard form.
		 */
		ComplexPolynomial polynomial;
		
		/**
		 * The first derivative of the polynomial on which we are performing Newton-Raphson iterations.
		 */
		ComplexPolynomial derived;
		
		/**
		 * The convergence treshold for module of |znOld - zn|
		 */
		double convergenceTreshold = 0.001;
		
		/**
		 * The treshold for finding closest root to given complex number.
		 */
		double rootTreshold = 0.002;
		
		/**
		 * Public constructor for NewtonProducer.
		 * @param rootedPolynomial for Newton-Raphson iterations.
		 */
		public NewtonProducer(ComplexRootedPolynomial rootedPolynomial) {
			this.rootedPolynomial = rootedPolynomial;
			this.polynomial = rootedPolynomial.toComplexPolynom();
			this.derived = this.polynomial.derive();
		}
		
		/**
		 * @param reMin minimum real for our complex plane
		 * @param reMax maximum real for our complex plane
		 * @param imMin minimum imaginary for our complex plane
		 * @param imMax maximum imaginary for our complex plane
		 * @param width width of canvas
		 * @param height height of canvas
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			//max number of iterations, number large enough to be certain of the calculations
			int m = 16*16*16;
			
			//we will store indexes into this "2D" array
			int offset = 0;
			short[] data = new short[width * height];
			
			//iterate over lines in canvas
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				
				//iterate over columns in every line
				for(int x = 0; x < width; x++) {
										
					//map canvas point to complex plane point
					double Creal = x / (width-1.0) *  (reMax - reMin) + reMin;
					double Cimag = (height-1.0-y) / (height-1)  *  (imMax - imMin) + imMin;
					
					//zn = c
					Complex zn = new Complex(Creal, Cimag);
					
					double module = 0;
					int iters = 0;
					
					do {
						
						//our previous zn becomes zn+1
						//value of index remains the same, we are storing this number because it is about to be derived
						Complex znOld = zn;
						
						//zn = zn – f(zn)/f'(zn);
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
					    zn = zn.sub(fraction);
					    			
					    // |zn+1 - zn|
						module = znOld.sub(zn).module();
						iters++;
						
					    						
					} while(module > convergenceTreshold && iters < m);
					
					//find the index of the root closest to zn
					int index = rootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
					
					//put index in data matrix, the GUI observer will map it into a color
					data[offset++] = (short) (index + 1);
				}
			}
			
			//send data to GUI
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
}
