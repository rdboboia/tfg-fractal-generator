package tfg.fractalgenerator;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGenerator;

class MandelbrotsetGeneratorTest {

	@Test
	void generatorTest() {
		BufferedImage image = new BufferedImage(19200, 10800, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGenerator.generate(image, 360);
		f = System.currentTimeMillis();

		System.out.println("Tiempo: " + (f - s) + " ms.");
	}
	
	@Disabled
	@Test
	void generatorV2Test() {
		BufferedImage image = new BufferedImage(19200, 10800, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGenerator.generateV2(image, 360);
		f = System.currentTimeMillis();

		System.out.println("Tiempo V2: " + (f - s) + " ms.");
	}
	
	@Test
	void generatorV3Test() {
		BufferedImage image = new BufferedImage(19200, 10800, BufferedImage.TYPE_INT_RGB);
		long s, f;

		s = System.currentTimeMillis();
		MandelbrotsetGenerator.generateV3(image, 360);
		f = System.currentTimeMillis();

		System.out.println("Tiempo V3: " + (f - s) + " ms.");
	}
}