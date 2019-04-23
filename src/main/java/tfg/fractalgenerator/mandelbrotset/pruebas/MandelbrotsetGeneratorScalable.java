package tfg.fractalgenerator.mandelbrotset.pruebas;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A simple module that generates the Mandelbrot Set and stores it in a given
 * {@link BufferedImage}. <br>
 * It takes the image and the depth as parameters. The height and width is based
 * on the image's size.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotsetGeneratorScalable {
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
	
	private int colorDepth;

	/**
	 * Main constructor for the {@link MandelbrotsetGeneratorScalable}. It stores
	 * the data in the image that receives. As for the depth it can be anything, but
	 * since the Hue of the HSB color model has 360 degrees it should be at least
	 * 360. However, lower values can end in some interesting representations of the
	 * Mandelbrot set.
	 * 
	 * @param img   the image to write the data on.
	 * @param depth the number of iterations per pixel.
	 */
	public MandelbrotsetGeneratorScalable(BufferedImage img, int depth) {
		this.img = img;
		this.depth = depth;
		this.height = img.getHeight();
		this.width = img.getWidth();
		
		this.colorDepth = depth;
	}
	
	public MandelbrotsetGeneratorScalable(BufferedImage img, int depth, int colorDepth) {
		this.img = img;
		this.depth = depth;
		this.colorDepth = colorDepth;
		this.height = img.getHeight();
		this.width = img.getWidth();
	}

	/**
	 * Generates each pixel's value of the Mandelbrot set and stores it into the
	 * given image. It uses the HSBtoRGB method from {@link java.awt.Color} to
	 * generate a nice looking color scheme while performing relatively few
	 * iterations per pixel.
	 */
	public void generate(double posx, double posy, double zoom, double scale) {
		int i, j, iteration;
		double x0, y0, x, y, xtemp;

		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				x0 = (posx * zoom + (j - width / 2d)) * scale / zoom;
				y0 = (posy * zoom + (i - height / 2d)) * scale / zoom;
				x = 0;
				y = 0;
				iteration = 0;

				while (x * x + y * y <= 4 && iteration < depth) {
					xtemp = x * x - y * y + x0;
					y = 2 * x * y + y0;
					x = xtemp;
					iteration++;
				}

				if (iteration < depth)
					img.setRGB(j, i, Color.HSBtoRGB(iteration / (float) colorDepth, 1, iteration / (iteration + 8f)));
				else
					img.setRGB(j, i, 0);
			}

//			if (i % (height/100) == 0)
//				System.out.println(((float)i / height) * 100 + "% completado");
		}
	}
}
