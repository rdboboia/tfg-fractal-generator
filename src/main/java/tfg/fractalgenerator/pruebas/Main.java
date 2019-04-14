package tfg.fractalgenerator.pruebas;

import java.awt.image.BufferedImage;
import java.io.IOException;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGenerator;

public class Main {
	public static void main(String[] args) {
		System.out.println("Creando imagen...");
		BufferedImage img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
		
		System.out.println("Generando fractal...");
		MandelbrotsetGenerator generator = new MandelbrotsetGenerator(img, 360);
		generator.generate();
		
		try {
			System.out.println("Exportando imagen...");
			ImageExport.export(img, ImageExport.BMP, "Z:", "mandelbrotset", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin");
	}
}