package hr.fer.zemris.java.fractals;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A program which displays fractals calculated using the Newton-Raphson iteration. The calculation process was parallelized to improve efficency.
 * The user defines calculation parameters as command-line arguments. <br>
 * 
 * Number of workers threads can be defined with: <code>--workers=N</code> or <code>-w N</code>.<br>
 * Number of jobs can be defined with: <code>--tracks=M</code> or <code>-t M</code>.<br>
 * 
 * If no parameters are defined, default values are used. If both parameters are defined, the workers have to be defined before the tracks.<br>
 * The also has to input at least 2 roots via the console for the fractal to be calculated.
 * 
 * @author fabjanvucina
 */
public class NewtonParallel {
	
	/**
	 * Number of workers used for parallelization.
	 */
	private int numOfWorkers;
	
	/**
	 * Number of jobs that the fractal is split into.
	 */
	private int numOfJobs;
	
	/**
	 * Public constructor.
	 */
	public NewtonParallel() { 
		numOfWorkers = Runtime.getRuntime().availableProcessors();
		numOfJobs = 4 * Runtime.getRuntime().availableProcessors();
	}
	

	public static void main(String[] args) {
		
		NewtonParallel np = new NewtonParallel();
		
		//get arguments from command line
		processCommandLineArguments(args, np);		
		
		//get roots from user
		List<Complex> roots = new ArrayList<>();
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner scRoots = new Scanner(System.in);
		int rootCounter = 0;
		
		while(true) {
			
			//prompt for user to write query
			System.out.print("Root " + (rootCounter + 1) + "> ");
			
			//read command type
			String input = scRoots.nextLine();
			
			//end program if user inputs "done" and there are at least 2 roots entered
			if(input.equals("done") && rootCounter >= 2) {
				System.out.println("Image of fractal will appear shortly. Thank you.");
				System.out.println("Number of workers: " + np.numOfWorkers);
				System.out.println("Number of tracks: " + np.numOfJobs);
				scRoots.close();
				
				//convert roots list to roots array
				Complex[] rootsArray = new Complex[roots.size()];
				roots.toArray(rootsArray);
				
				//display the calculated fractal
				FractalViewer.show(new NewtonProducer(new ComplexRootedPolynomial(Complex.ONE, rootsArray), 
								   np.numOfWorkers, np.numOfJobs));	

				break;
			}
			
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
					scRoots.nextLine();
				}
			}
			
		}
		
	}
	
	/**
	 * Private helper method which processes the command line arguments for number of workers and jobs.
	 * @param args command line arguments array
	 * @param np <code>NewtonParallel</code> object
	 */
	private static void processCommandLineArguments(String[] args, NewtonParallel np) {
		
		// no arguments
		if(args.length == 0) {
			//leave default values
		}
		
		// --tracks=M
		else if(args.length == 1 && args[0].trim().startsWith("--tracks=") && !args[0].trim().equals("--tracks=")) {
			Scanner sc = new Scanner(args[0].trim().substring(9));
			
			try {
				np.numOfJobs = sc.nextInt();
				
				if(np.numOfJobs < 1) {
					throw new IllegalArgumentException("Number of tracks should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of tracks");
			}
			
		}
		
		// -t M
		else if(args.length == 2 && args[0].trim().equals("-t")) {
			Scanner sc = new Scanner(args[1].trim());
			
			try {
				np.numOfJobs = sc.nextInt();
				
				if(np.numOfJobs < 1) {
					throw new IllegalArgumentException("Number of tracks should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of tracks");
			}
		}
		
		// --workers=N
		else if(args.length == 1 && args[0].trim().startsWith("--workers=") && !args[0].trim().equals("--workers=")) {
			Scanner sc = new Scanner(args[0].trim().substring(10));
			
			try {
				np.numOfWorkers = sc.nextInt();
				
				if(np.numOfWorkers < 1) {
					throw new IllegalArgumentException("Number of workers should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of workers");
			}
		}
		
		// --workers=N --tracks=M 
		else if(args.length == 2 && args[0].trim().startsWith("--workers=") && !args[0].trim().equals("--workers=")
								 && args[1].trim().startsWith("--tracks=") && !args[1].trim().equals("--tracks=")) {
			
			Scanner sc1 = new Scanner(args[0].trim().substring(10));
			Scanner sc2 = new Scanner(args[1].trim().substring(9));
			
			try {
				np.numOfWorkers = sc1.nextInt();
				
				if(np.numOfWorkers < 1) {
					throw new IllegalArgumentException("Number of workers should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of workers");
			}
			
			try {
				np.numOfJobs = sc2.nextInt();
				
				if(np.numOfJobs < 1) {
					throw new IllegalArgumentException("Number of tracks should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of tracks");
			}
		}
		
		// --workers=N -t M
		else if(args.length == 3 && args[0].trim().startsWith("--workers=") && !args[0].trim().equals("--workers=")
				                 && args[1].trim().equals("-t")) {
			
			Scanner sc1 = new Scanner(args[0].trim().substring(10));
			Scanner sc2 = new Scanner(args[2].trim());
			
			try {
				np.numOfWorkers = sc1.nextInt();
				
				if(np.numOfWorkers < 1) {
					throw new IllegalArgumentException("Number of workers should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of workers");
			}
			
			try {
				np.numOfJobs = sc2.nextInt();
				
				if(np.numOfJobs < 1) {
					throw new IllegalArgumentException("Number of tracks should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of tracks");
			}
		}
		
		// -w N
		else if(args.length == 2 && args[0].trim().equals("-w")) {
			Scanner sc = new Scanner(args[1].trim());
			
			try {
				np.numOfWorkers = sc.nextInt();
				
				if(np.numOfWorkers < 1) {
					throw new IllegalArgumentException("Number of workers should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of workers");
			}
		}
		
		// -w N --tracks=M
		else if(args.length == 3 && args[0].trim().equals("-w")
				 				 && args[2].trim().startsWith("--tracks=") && !args[2].trim().equals("--tracks=")) {
			
			Scanner sc1 = new Scanner(args[1].trim());
			Scanner sc2 = new Scanner(args[1].trim().substring(9));
			
			try {
				np.numOfWorkers = sc1.nextInt();
				
				if(np.numOfWorkers < 1) {
					throw new IllegalArgumentException("Number of workers should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of workers");
			}
			
			try {
				np.numOfJobs = sc2.nextInt();
				
				if(np.numOfJobs < 1) {
					throw new IllegalArgumentException("Number of tracks should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of tracks");
			}
		}
		
		// -w N -t M
		else if(args.length == 4 && args[0].trim().equals("-w")
				 				 && args[2].trim().equals("-t")) {
			
			Scanner sc1 = new Scanner(args[1].trim());
			Scanner sc2 = new Scanner(args[3].trim());
			
			try {
				np.numOfWorkers = sc1.nextInt();
				
				if(np.numOfWorkers < 1) {
					throw new IllegalArgumentException("Number of workers should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of workers");
			}
			
			try {
				np.numOfJobs = sc2.nextInt();
				
				if(np.numOfJobs < 1) {
					throw new IllegalArgumentException("Number of tracks should not be less than 1.");
				}
			}
			catch(Exception ex){
				throw new IllegalArgumentException("You have not defined an integer as number of tracks");
			}
		}
		
		//bad command line arguments format
		else {
			throw new IllegalArgumentException("Invalid arguments. Valid format: {--workers=N, -w N} [{--tracks=M, -t M}]");
		}
	}
	
	/**
	 * A class which represents a job that calculates a part of the fractal.
	 * 
	 * @author fabjanvucina
	 */
	public static class CalculationJob implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public final static CalculationJob NO_JOB = new CalculationJob();
		ComplexRootedPolynomial rootedPolynomial;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		double convergenceTreshold;
		double rootTreshold;
		
		/**
		 * Private default constructor for the red pill job.
		 */
		private CalculationJob() {}
		
		/**
		 * Public constructor for a CalculationJob.
		 */
		public CalculationJob(double reMin, double reMax, double imMin, double imMax, 
							  int width, int height, int yMin, int yMax, 
							  int m, short[] data, AtomicBoolean cancel,
							  ComplexRootedPolynomial rootedPolynomial, ComplexPolynomial polynomial, 
							  ComplexPolynomial derived, double convergenceTreshold, double rootTreshold) {
			
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.rootedPolynomial = rootedPolynomial;
			this.polynomial = polynomial;
			this.derived = derived;
			this.convergenceTreshold = convergenceTreshold;
			this.rootTreshold = rootTreshold;		
		}
		
		@Override
		public void run() {
			
			//starting offset for the job
			int offset = yMin * width;
			
			//iterate over specific lines in canvas
			for(int y = yMin; y <= yMax; y++) {
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
						
						//our previous zn becomes zn+1 (index is actually the same, but n is smaller now because of derivation)
						Complex znOld = zn;
						
						//zn = zn – f(zn)/f'(zn);
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
					    zn = zn.sub(fraction);
					    					    
						module = znOld.sub(zn).module();
						iters++;
						
					    						
					} while(module > convergenceTreshold && iters < m);
					
					//find the index of the root closest to zn
					int index = rootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
					
					//put index in data matrix, the GUI observer will map it into a color
					data[offset++] = (short) (index + 1);
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
		 * Number of worker threads used to produce the fractal.
		 */
		public int numOfWorkers;
		
		/**
		 * We are splitting the fractal calculation into this ammount of jobs.
		 */
		public int numOfJobs;
		
		/**
		 * Public constructor for NewtonProducer.
		 * @param polynomial for Newton-Raphson iterations.
		 * @param numOfWorkers number of worker threads working on the calculation
		 * @param numOfJobs number of jobs that the calculation is split into
		 */
		public NewtonProducer(ComplexRootedPolynomial rootedPolynomial, int numOfWorkers, int numOfJobs) {
			this.rootedPolynomial = rootedPolynomial;
			this.polynomial = rootedPolynomial.toComplexPolynom();
			this.derived = this.polynomial.derive();
			this.numOfWorkers = numOfWorkers;
			this.numOfJobs = numOfJobs;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
							int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			//max number of iterations, number large enough to be certain of the calculations
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			//every job has to calculate at least 1 line
			numOfJobs = min(numOfJobs, height);
			int numOfLinesPerJob = height / numOfJobs;
			
			//our worker threads will take jobs from the job queue when they finish the previous jobs
			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();

			//create worker threads
			Thread[] workers = new Thread[numOfWorkers];
			for(int i = 0; i < numOfWorkers ; i++) {
				workers[i] = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if(p==CalculationJob.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			//start the worker threads
			for(int i = 0; i < numOfWorkers; i++) {
				workers[i].start();
			}
			
			
			//create calculation jobs
			for(int i = 0; i < numOfJobs; i++) {
				
				//find the line limits for every job
				int yMin = i*numOfLinesPerJob;
				int yMax = (i+1)*numOfLinesPerJob - 1;
				
				if(i == numOfJobs - 1) {
					yMax = height-1;
				}
				
				//create job and put it into the queue
				CalculationJob posao = new CalculationJob(reMin, reMax, imMin, imMax,
														  width, height, yMin, yMax, m, data, cancel,
														  rootedPolynomial, polynomial, derived,
														  convergenceTreshold, rootTreshold);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			//put red pills at the end of the job queue
			//their function is to break the workers threads from endless loops when there are no jobs left
			//there needs to be a red pill for every worker thread
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(CalculationJob.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			
			//main thread calls workers[i].join() and freezes itself until the workers finish their jobs
			//surrounding with try-catch because a worker thread could be interruped by another one causing an InterruptedException
			for(int i = 0; i < numOfWorkers; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			//send data to GUI
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
}
