package tfg.fractalMandelbrot.pruebas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FractalMandelbrot_PrimeraOptimizacion {

	public static void main(String[] args) {
		int width = 19200;
		int height = 10800;
		int max = 256;
		int max_color = 256 * 256 * 256;
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		double x0, y0, x, y, xtemp;
		int iteration;
		
		double halfWidth = width/2.0;
		double halfHeight = height/2.0;
		double scale = 4.0/width;
		
		for (int i = 0 ; i < height ; i++) {
			for (int j = 0 ; j < width ; j++) {
				x0 = (j - halfWidth)*scale;
				y0 = (i - halfHeight)*scale;
				x = 0;
				y = 0;
				iteration = 0;
				
				while (x*x + y*y <= 4 && iteration < max) {
					xtemp = x*x - y*y + x0;
					y = 2*x*y + y0;
					x = xtemp;
					iteration++;
				}
				
				img.setRGB(j, i, iteration * (max_color / max));
			}
			
			System.out.println(i + " / " + height);
		}
		
		try {
			ImageIO.write(img, "png", new File("Z:\\mandelbrot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
