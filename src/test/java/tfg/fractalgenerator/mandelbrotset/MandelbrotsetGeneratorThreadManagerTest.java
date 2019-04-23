package tfg.fractalgenerator.mandelbrotset;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.exportimage.ImageFormat;

class MandelbrotsetGeneratorThreadManagerTest {

	@Test
	void test() {
		try {
			BufferedImage imageTest = ImageIO.read(new File("imageTest.png"));
			byte[] imageTestPixels = ((DataBufferByte) imageTest.getRaster().getDataBuffer()).getData();
			
			BufferedImage renderedImage = new BufferedImage(3840, 2160, BufferedImage.TYPE_3BYTE_BGR);
			MandelbrotsetGeneratorThreadManager.generate(renderedImage, 360, 360, new MandelbrotsetPosition(0, 0, 3, 4d / 1280));
			byte[] renderedImagePixels = ((DataBufferByte) renderedImage.getRaster().getDataBuffer()).getData();
			
//			ImageExport.export(imageTest, ImageFormat.PNG, "Z:\\", "fractalOriginal", true);
//			ImageExport.export(renderedImage, ImageFormat.PNG, "Z:\\", "fractalNuevo", true);
			
			assertArrayEquals(imageTestPixels, renderedImagePixels);
		} catch (IOException e) {
			fail("Exception thrown!");
		}
	}
	
	@Disabled
	@Test
	void performanceTest() {
		BufferedImage image = new BufferedImage(19200, 10800, BufferedImage.TYPE_INT_RGB);
		MandelbrotsetPosition position = new MandelbrotsetPosition();
		position.setZoom(10);
		
		long s = System.currentTimeMillis();
		
		MandelbrotsetGeneratorThreadManager.generate(image, 3600, 360, position);
		
		long f = System.currentTimeMillis();
		System.out.println((f-s) + " ms.");
		
		try {
			ImageExport.export(image, ImageFormat.PNG, "Z:\\", "fractal", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}