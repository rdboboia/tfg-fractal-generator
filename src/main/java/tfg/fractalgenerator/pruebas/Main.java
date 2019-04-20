package tfg.fractalgenerator.pruebas;

import java.awt.image.BufferedImage;
import java.io.IOException;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGenerator;

public class Main {
	public static void main(String[] args) {
		System.out.println("Creando imagen...");
		BufferedImage img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
		
		System.out.println("Generando fractal...");
		MandelbrotsetGenerator.generate(img, 360);
		
		try {
			System.out.println("Exportando imagen...");
			ImageExport.export(img, ImageFormat.PNG, "Z:", "mandelbrotset", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin");
	}
}