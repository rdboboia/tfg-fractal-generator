package tfg.fractalgenerator.mandelbrotset;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

/**
 * A simple module that generates the Mandelbrot Set and stores it in a given
 * {@link BufferedImage}. <br>
 * It takes the image and the depth as parameters. The height and width is based
 * on the image's size.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotsetGeneratorScalableBigDecimal {
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
	 * Main constructor for the {@link MandelbrotsetGeneratorScalableBigDecimal}. It stores
	 * the data in the image that receives. As for the depth it can be anything, but
	 * since the Hue of the HSB color model has 360 degrees it should be at least
	 * 360. However, lower values can end in some interesting representations of the
	 * Mandelbrot set.
	 * 
	 * @param img   the image to write the data on.
	 * @param depth the number of iterations per pixel.
	 */
	public MandelbrotsetGeneratorScalableBigDecimal(BufferedImage img, int depth) {
		this.img = img;
		this.depth = depth;
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
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		int i, j, iteration;
//		BigDecimal x0, y0, x, y, xtemp;
		
		int bigDecimalScale = 20;
		BigDecimal x0 = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal y0 = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal x = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal y = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal xtemp = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal valorWhile = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal two = BigDecimal.valueOf(2).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal y2 = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal four = BigDecimal.valueOf(4).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal zeroVal = BigDecimal.valueOf(0).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
		BigDecimal posxXzoom = BigDecimal.valueOf(posx * zoom);
		BigDecimal posyXzoom = BigDecimal.valueOf(posy * zoom);
		BigDecimal scaleDzoom = BigDecimal.valueOf(scale / zoom);

		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				x0 = (BigDecimal.valueOf((j - width / 2d))).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				x0 = (x0.add(posxXzoom)).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				x0 = (x0.multiply(scaleDzoom)).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				
				y0 = (BigDecimal.valueOf((i - height / 2d))).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				y0 = (y0.add(posyXzoom)).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				y0 = (y0.multiply(scaleDzoom)).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				
				x = zeroVal;
				y = zeroVal;
				
				iteration = 0;
				valorWhile = x.multiply(x).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				valorWhile = valorWhile.add(y.multiply(y)).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
				while (valorWhile.compareTo(four) <= 0 && iteration < depth) {
//					System.out.println("B1");
					xtemp = x.multiply(x).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
					valorWhile = xtemp;
					y2 = y.multiply(y).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
					valorWhile = valorWhile.add(y2);
					xtemp = xtemp.subtract(y2);
					xtemp = xtemp.add(x0);
					
//					System.out.println("B2");
					
					y = x.multiply(y).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
					y = y.multiply(two).setScale(bigDecimalScale, BigDecimal.ROUND_FLOOR);
					y = y.add(y0);
					
//					System.out.println("B3");
					
					x = xtemp;
					iteration++;
					
//					System.out.println("B4");
					
//					valorWhile = x.multiply(x);
//					valorWhile = valorWhile.add(y.multiply(y));
//					System.out.println(iteration);
				}

				if (iteration < depth)
					img.setRGB(j, i, Color.HSBtoRGB(iteration / (float) depth, 1, iteration / (iteration + 8f)));
				else
					img.setRGB(j, i, 0);
			}

//			if (i % (height/100) == 0)
//				System.out.println(((float)i / height) * 100 + "% completado");
		}
	}
}
