package tfg.fractalgenerator.mandelbrotset;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A simple module that generates the Mandelbrot Set and stores it in a given
 * {@link BufferedImage}.
 * <br>
 * It takes the image and the depth as parameters. The height and width is
 * based on the image's size.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotsetGenerator {
	/**
	 * The image where the data will be stored.
	 */
	private BufferedImage img;
	/**
	 * The depth (number of iterations for each pixel).
	 */
	private int depth;
	/**
	 * The height (based on the image's height)
	 */
	private int height;
	/**
	 * The width (based on the image's width)
	 */
	private int width;
	
	/**
	 * Main constructor for the {@link MandelbrotsetGenerator}.
	 * It stores the data in the image that receives.
	 * As for the depth it can be anything, but since the Hue of the HSB color
	 * model has 360 degrees it should be at least 360. However, lower values
	 * can end in some interesting representations of the Mandelbrot set.
	 * @param img the image to write the data on.
	 * @param depth the number of iterations per pixel.
	 */
	public MandelbrotsetGenerator(BufferedImage img, int depth) {
		this.img = img;
		this.depth = depth;
		this.height = img.getHeight();
		this.width = img.getWidth();
	}
	
	/**
	 * Generates each pixel's value of the Mandelbrot set and stores it into
	 * the given image.
	 * It uses the HSBtoRGB method from {@link java.awt.Color} to generate a
	 * nice looking color scheme while performing relatively few iterations
	 * per pixel.
	 */
	public void generate() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double x0 = (j - width / 2d) * 4d / width;
				double y0 = (i - height / 2d) * 4d / width;
				double x = 0;
				double y = 0;
				int iteration = 0;
				
				while (x * x + y * y <= 4 && iteration < depth) {
					double xtemp = x * x - y * y + x0;
					y = 2 * x * y + y0;
					x = xtemp;
					iteration++;
				}
				
				if (iteration < depth)
					img.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
				else
					img.setRGB(j, i, 0);
			}
		}
	}
}
