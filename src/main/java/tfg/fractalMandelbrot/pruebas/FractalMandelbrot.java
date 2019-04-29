package tfg.fractalMandelbrot.pruebas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tfg.fractalgenerator.exportimage.BufferedImageType;

public class FractalMandelbrot {

	public static void main(String[] args) {
		int width = 1920;
		int height = 1080;
		int max = 256;
		
		BufferedImage img = new BufferedImage(width, height, BufferedImageType.getBufferedImageType());
		
		for (int i = 0 ; i < height ; i++) {
			for (int j = 0 ; j < width ; j++) {
				double x0 = (Double.valueOf(j) - width/2)*4.0/width;
				double y0 = (Double.valueOf(i) - height/2)*4.0/width;
//				double x0 = (Double.valueOf(j)/width) - ((width/2.0)/width);
//				double y0 = ((height/2.0)/height) - (Double.valueOf(i)/height);
				double x = 0;
				double y = 0;
				int iteration = 0;
				
				while (x*x + y*y <= 2*2 && iteration < max) {
					double xtemp = x*x - y*y + x0;
					y = 2*x*y + y0;
					x = xtemp;
					iteration++;
				}
				
				img.setRGB(j, i, iteration * 256);
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
