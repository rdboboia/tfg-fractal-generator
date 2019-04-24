package tfg.fractalgenerator.mandelbrotset;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.mandelbrotset.pruebas.MandelbrotsetGeneratorPruebasOptimizacion;
import tfg.fractalgenerator.mandelbrotset.pruebas.MandelbrotsetGeneratorThread_Optimizations;

class MandelbrotsetGeneratorTest_DISABLED {
	private static final int WIDTH = 1920 * 4;
	private static final int HEIGHT = 1080 * 4;
	private static final int DEPTH = 360 * 1;
	
	@BeforeAll
	static void sleep() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Disabled
	@Test
	void generatorTest() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGeneratorPruebasOptimizacion.generate(image, DEPTH);
		f = System.currentTimeMillis();

		System.out.println("Tiempo: " + (f - s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "mandel", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Disabled
	@Test
	void generatorV2Test() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGeneratorPruebasOptimizacion.generateV2(image, DEPTH);
		f = System.currentTimeMillis();

		System.out.println("Tiempo V2: " + (f - s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "mandelv2", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Disabled
	@Test
	void generatorV3Test() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGeneratorPruebasOptimizacion.generateV3(image, DEPTH);
		f = System.currentTimeMillis();

		System.out.println("Tiempo V3: " + (f - s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "mandelv3", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Disabled
	@Test
	void generatorV4Test() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGeneratorPruebasOptimizacion.generateV4(image, DEPTH);
		f = System.currentTimeMillis();

		System.out.println("Tiempo V4: " + (f - s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "mandelv4", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Disabled
	@Test
	void generatorV5Test() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGeneratorPruebasOptimizacion.generateV5(image, DEPTH);
		f = System.currentTimeMillis();

		System.out.println("Tiempo V5: " + (f - s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "mandelv5", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Disabled
	@Test
	void generatorV4parTest() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		
		MandelbrotsetGeneratorThread_Optimizations[] t = new MandelbrotsetGeneratorThread_Optimizations[4];
		for (int i = 0 ; i < t.length ; i++) {
			t[i] = new MandelbrotsetGeneratorThread_Optimizations(image, DEPTH, i, t.length);
			t[i].start();
		}
		for (int i = 0 ; i < t.length ; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		f = System.currentTimeMillis();

		System.out.println("Tiempo V4par: " + (f - s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "mandelv4par", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}