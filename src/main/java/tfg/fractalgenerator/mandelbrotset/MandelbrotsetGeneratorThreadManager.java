package tfg.fractalgenerator.mandelbrotset;

import java.awt.image.BufferedImage;

/**
 * A class to manage the {@link MandelbrotsetGeneratorThread} generator threads.
 * Its purpose is to abstract the fact that threads are used. It will use 90%
 * of the available threads of the processor (as an integer number) by default.
 * It automatically creates and launches the needed threads.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotsetGeneratorThreadManager {
	/**
	 * Private constructor. No instances allowed.
	 */
	private MandelbrotsetGeneratorThreadManager() {
		
	}
	
	/**
	 * Main method of the {@link MandelbrotsetGeneratorThreadManager} class.
	 * It creates and launches all the needed threads, using up to 90% (as an
	 * integer value) of the processor's number of threads. Finally, it waits for
	 * all threads to finish and returns true if there was no
	 * {@link InterruptedException} while waiting.
	 * 
	 * @param image the image where the fractal will be generated to.
	 * @param depth the maximum number of iterations per pixel.
	 * @param colorDepth the number of colors (also affects when each color will
	 * be used since the actual color depends on the number of iterations and the
	 * color depth).
	 * @param position the x and y axis position, as well as the zoom and scale
	 * modifiers.
	 * @return {@code true} if the threads executed and finished correctly.
	 * {@code False} if an {@link InterruptedException} was thrown.
	 */
	public static boolean generate(BufferedImage image, int depth, int colorDepth, MandelbrotsetPosition position) {
		// Using only 90% of the total available threads in order to avoid locking up the system.
		int numThreads = (int) Math.max(1, Runtime.getRuntime().availableProcessors() * 0.9);
		
		return generate(image, depth, colorDepth, position, numThreads);
	}
	
	/**
	 * Additional method which allows to take control of the number of threads
	 * which will be used for the generation. It creates and launches all the
	 * needed threads and waits for all threads to finish.
	 * 
	 * @param image the image where the fractal will be generated to.
	 * @param depth the maximum number of iterations per pixel.
	 * @param colorDepth the number of colors (also affects when each color will
	 * be used since the actual color depends on the number of iterations and the
	 * color depth).
	 * @param position the x and y axis position, as well as the zoom and scale
	 * modifiers.
	 * @return {@code true} if the threads executed and finished correctly.
	 * {@code False} if an {@link InterruptedException} was thrown.
	 */
	public static boolean generate(BufferedImage image, int depth, int colorDepth, MandelbrotsetPosition position, int numThreads) {
		// Creating and launching threads.
		MandelbrotsetGeneratorThread[] t = new MandelbrotsetGeneratorThread[numThreads];
		for (int i = 0 ; i < numThreads ; i++) {
			t[i] = new MandelbrotsetGeneratorThread(image, depth, colorDepth, position, i, numThreads);
			t[i].start();
		}
		
		// Waiting for all threads to finish.
		for (int i = 0 ; i < numThreads ; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				t[i].interrupt();
				return false;
			}
		}
		
		return true;
	}
}