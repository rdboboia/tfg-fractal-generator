package tfg.fractalgenerator.mandelbrotset.pruebas;

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
public class MandelbrotsetGeneratorPruebasOptimizacion {
	/**
	 * Private constructor. No instances allowed.
	 */
	private MandelbrotsetGeneratorPruebasOptimizacion() {
	}
	
	/**
	 * Generates each pixel's value of the Mandelbrot set and stores it into
	 * the given image.
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
	public static void generate(BufferedImage image, int depth) {
		int height = image.getHeight();
		int width = image.getWidth();
		
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
					image.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
				else
					image.setRGB(j, i, 0);
			}
		}
	}
	
	public static void generateV2(BufferedImage image, int depth) {
		int height = image.getHeight();
		int width = image.getWidth();
		
		double x0, y0, x, y, xtemp;
		int iteration;
		double halfWidth = width / 2d;
		double halfHeight = height / 2d;
		double scale = 4d / width;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				x0 = (j - halfWidth) * scale;
				y0 = (i - halfHeight) * scale;
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
					image.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
				else
					image.setRGB(j, i, 0);
			}
		}
	}
	
	public static void generateV3(BufferedImage image, int depth) {
		int height = image.getHeight();
		int width = image.getWidth();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double x0 = (j - width / 2d) * 4d / width;
				double y0 = (i - height / 2d) * 4d / width;
				double x = 0;
				double y = 0;
				int iteration = 0;
				
				while (x * x + y * y <= 4 && iteration < depth) {
					double xtemp = x * x - y * y + x0;
					double ytemp = 2 * x * y + y0;
					
					if (x == xtemp && y == ytemp)
						iteration = depth;
					
					x = xtemp;
					y = ytemp;
					iteration++;
				}
				
				if (iteration < depth)
					image.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
				else
					image.setRGB(j, i, 0);
			}
		}
	}
	
//	public static void generateV4(BufferedImage image, int depth) {
//		int height = image.getHeight();
//		int width = image.getWidth();
//		
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				double x0 = (j - width / 2d) * 4d / width;
//				double y0 = (i - height / 2d) * 4d / width;
//				double x = 0;
//				double y = 0;
//				int iteration = 0;
//				double xSquared = 0;
//				double ySquared = 0;
//				
//				while (xSquared + ySquared <= 4 && iteration < depth) {
//					double xtemp = xSquared - ySquared + x0;
//					y = 2 * x * y + y0;
//					x = xtemp;
//					
//					xSquared = Math.pow(x, 2);
//					ySquared = Math.pow(y, 2);
//					
//					iteration++;
//				}
//				
//				if (iteration < depth)
//					image.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
//				else
//					image.setRGB(j, i, 0);
//			}
//		}
//	}
	
	public static void generateV4(BufferedImage image, int depth) {
		int height = image.getHeight();
		int width = image.getWidth();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double x0 = (j - width / 2d) * 4d / width;
				double y0 = (i - height / 2d) * 4d / width;
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
				
				if (iteration < depth)
					image.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
				else
					image.setRGB(j, i, 0);
			}
		}
	}
	
	public static void generateV5(BufferedImage image, int depth) {
		int height = image.getHeight();
		int width = image.getWidth();
		
		double scaledZoom = 4d / width;
//		double halfWidth = ;
//		double halfHeight = ;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double x0 = (j - width / 2d) * scaledZoom;
				double y0 = (i - height / 2d) * scaledZoom;
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
				
				if (iteration < depth)
					image.setRGB(j, i, Color.HSBtoRGB(iteration/(float)depth, 1, iteration/(iteration+8f)));
				else
					image.setRGB(j, i, 0);
			}
		}
	}
}
