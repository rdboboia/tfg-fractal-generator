package tfg.imageprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.exportimage.BufferedImageType;

class ImageProcessorManagerTest {
	private static final String ASSERTION_ERROR_MESSAGE = "Wrong value. Check if the executed mode is the expected one.";

	@Test
	void invertColorsTest() {
		BufferedImage image = new BufferedImage(1000, 1200, BufferedImageType.getBufferedImageType());
		byte[] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		byte[] bytesCopy = bytes.clone();
		
		ImageProcessor.invertColors(bytesCopy, 3);
		ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
		
		assertArrayEquals(bytesCopy, bytes, ASSERTION_ERROR_MESSAGE);
	}

	@Test
	void invertColorsTestWithAplhaChannel() {
		BufferedImage image = new BufferedImage(1000, 1200, BufferedImage.TYPE_4BYTE_ABGR);
		byte[] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		byte[] bytesCopy = bytes.clone();
		
		ImageProcessor.invertColors(bytesCopy, 4);
		ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
		
		assertArrayEquals(bytesCopy, bytes, ASSERTION_ERROR_MESSAGE);
	}
	
	@Test
	void convertToGrayscaleTest() {
		BufferedImage image = new BufferedImage(1000, 1200, BufferedImageType.getBufferedImageType());
		byte[] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		byte[] bytesCopy = bytes.clone();
		
		ImageProcessor.convertToGrayscale(bytesCopy, 3);
		ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
		
		assertArrayEquals(bytesCopy, bytes, ASSERTION_ERROR_MESSAGE);
	}
	
	@Test
	void convertToGrayscaleTestWithAlphaChannel() {
		BufferedImage image = new BufferedImage(1000, 1200, BufferedImage.TYPE_4BYTE_ABGR);
		byte[] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		byte[] bytesCopy = bytes.clone();
		
		ImageProcessor.convertToGrayscale(bytesCopy, 4);
		ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
		
		assertArrayEquals(bytesCopy, bytes, ASSERTION_ERROR_MESSAGE);
	}

}
