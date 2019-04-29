package tfg.fractalgenerator.mandelbrotset;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.exportimage.BufferedImageType;

class MandelbrotsetGeneratorThreadTest {

	@Test
	void normalOperationTest() {
		try {
			BufferedImage imageTest = ImageIO.read(new File("imageTest.png"));
			byte[] imageTestPixels = ((DataBufferByte) imageTest.getRaster().getDataBuffer()).getData();
			
			BufferedImage renderedImage = new BufferedImage(3840, 2160, BufferedImageType.getBufferedImageType());
			MandelbrotsetGeneratorThread thread = new MandelbrotsetGeneratorThread(renderedImage, 360, 360, new MandelbrotsetPosition(0, 0, 3, 4d / 1280), 0, 1);
			thread.start();
			thread.join();
			byte[] renderedImagePixels = ((DataBufferByte) renderedImage.getRaster().getDataBuffer()).getData();
			
			assertArrayEquals(imageTestPixels, renderedImagePixels);
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}
}