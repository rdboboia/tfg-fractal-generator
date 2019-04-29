package tfg.fractalgenerator.mandelbrotset;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

/**
 * A simple module that generates the Mandelbrot Set and stores it in a given
 * {@link BufferedImage}. It extends {@link Thread} since it is designed to be
 * run in a thread pool. Each thread will be assigned a row based on the thread
 * id and the total number of threads to be run.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotsetGeneratorThread extends Thread {
	/**
	 * The image where the Mandelbrot's Set fractal will be generated.
	 */
	private BufferedImage image;
	
	/**
	 * The maximum number of iterations per pixel.
	 */
	private int depth;
	
	/**
	 * It affects maximum number of colors as well as when each color may appear
	 * based on the number of iterations.
	 */
	private int colorDepth;
	
	/**
	 * The x and y axis position, as well as the zoom and scale modifiers.
	 */
	private MandelbrotsetPosition position;
	
	/**
	 * Each thread's ID (from 0 to numThreads-1).
	 */
	private int threadID;
	
	/**
	 * Total number of threads intended to generate the fractal.
	 */
	private int numThreads;
	
	/**
	 * Since this class extends {@link Thread} and the {@code start()} method
	 * doesn't allow parameters, this constructor is needed in order to set the
	 * generation parameters such as the position, depth and color depth. It also
	 * receives the number of threads that will concurrently generate the fractal
	 * as well as the thread id or number (from 0 to n-1) in order to decide
	 * which row each thread will be generating (load balancing).
	 * 
	 * @param image the image where the fractal will be generated to.
	 * @param depth the maximum number of iterations per pixel.
	 * @param colorDepth the number of colors (also affects when each color will
	 * be used since the actual color depends on the number of iterations and the
	 * color depth).
	 * @param position the x and y axis position, as well as the zoom and scale
	 * modifiers.
	 * @param threadID the thread id (from 0 to numThreads-1).
	 * @param numThreads the total number of threads that will generate the fractal.
	 */
	public MandelbrotsetGeneratorThread(BufferedImage image, int depth, int colorDepth, MandelbrotsetPosition position, int threadID, int numThreads) {
		this.image = image;
		this.depth = depth;
		this.colorDepth = colorDepth;
		this.position = position;
		this.threadID = threadID;
		this.numThreads = numThreads;
	}

	@Override
	public void run() {
		generate();
	}
	
	/**
	 * Generates each pixel's value of the given image based on the Mandelbrot's
	 * Set formula. Since it is designed to be used as one of many threads, it
	 * only generates some rows: it generates one row every number of threads rows.
	 * If only one thread is correctly used, then it will successfully generate
	 * all image just by itself (since for 1 thread it will generate all rows).
	 * <br>
	 * It uses the HSBtoRGB method from {@link java.awt.Color}
	 * to generate a nice looking color scheme without needing too much depth.
	 * As for the depth it can be anything, but since the Hue of the HSB color
	 * model has 360 degrees it should be at least 360. However, lower values
	 * can end in some interesting representations of the Mandelbrot set. Higher
	 * values may become handy when zooming in.
	 * @param image the image where the data will be stored.
	 * @param depth the number of iterations for each pixel.
	 */
	public void generate() { // Using V5
		byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		double scaledZoom = position.getScale() / position.getZoom();
		double factorX = position.getPosx() * position.getZoom();
		double factorY = position.getPosy() * position.getZoom();
		double halfWidth = width / 2d;
		double halfHeight = height / 2d;

		for (int i = threadID; i < height; i += numThreads) {
			for (int j = 0; j < width; j++) {
				double x0 = (factorX + (j - halfWidth)) * scaledZoom;
				double y0 = (factorY + (i - halfHeight )) * scaledZoom;
				double x = 0;
				double y = 0;
				double xSquared = 0;
				double ySquared = 0;
				int iteration = 0;

				while (xSquared + ySquared <= 4 && iteration < depth) {
					double xtemp = xSquared - ySquared + x0;
					double ytemp = 2 * x * y + y0;

					if (x == xtemp && y == ytemp)
						iteration = depth;

					x = xtemp;
					y = ytemp;
					iteration++;

					xSquared = x * x;
					ySquared = y * y;
				}

				if (iteration < depth) {
					int value = Color.HSBtoRGB(iteration / (float) colorDepth, 1, iteration / (iteration + 8f));
					pixelData[i * 3 * width + 3 * j] = (byte) (value & 0xFF);
					pixelData[i * 3 * width + 3 * j + 1] = (byte) (value >> 8 & 0xFF);
					pixelData[i * 3 * width + 3 * j + 2] = (byte) (value >> 16 & 0xFF);
					
//					pixelData[i * 3 * width + 3 * j] = (byte) (value >> 16 & 0xFF);
//					pixelData[i * 3 * width + 3 * j + 1] = (byte) (value >> 8 & 0xFF);
//					pixelData[i * 3 * width + 3 * j + 2] = (byte) (value & 0xFF);
				}
				else {
					pixelData[i * 3 * width + 3 * j] = 0;
					pixelData[i * 3 * width + 3 * j + 1] = 0;
					pixelData[i * 3 * width + 3 * j + 2] = 0;
				}
			}
		}
	}
}
