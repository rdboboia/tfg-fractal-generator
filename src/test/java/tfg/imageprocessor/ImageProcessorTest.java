package tfg.imageprocessor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageProcessorTest {
	private static final String WRONG_VALUE_MESSAGE = "Wrong value!";
	
	private byte[] bytes;
	private byte[] bytesCopy;
	
	@BeforeEach
	void init() {
		bytes = new byte[1200000];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (Math.random() * 125);
		}

		bytesCopy = bytes.clone();
	}

	@Test
	void invertColorsTest() {
		int step = 3;
		ImageProcessor.invertColors(bytes, step);

		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] != (byte) (255 - bytesCopy[i])) {
				fail(WRONG_VALUE_MESSAGE);
			}
		}
	}

	@Test
	void invertColorsTestWithAplhaChannel() {
		int step = 4;
		ImageProcessor.invertColors(bytes, step);

		for (int i = 0; i < bytes.length; i += step) {
			for (int j = 0; j < 3; j++) {
				if (bytes[i] != (byte) (255 - bytesCopy[i])) {
					fail(WRONG_VALUE_MESSAGE);
				}
			}
			
			if (bytes[i + 3] != bytesCopy[i + 3]) {
				fail("Alpha channel was altered!");
			}
		}
	}
	
	@Test
	void invertColorsReinvertTest() {
		int step = 3;
		ImageProcessor.invertColors(bytes, step);
		ImageProcessor.invertColors(bytes, step);

		assertArrayEquals(bytes, bytesCopy);
	}
	
	@Test
	void invertColorsReinvertTestWithAlphaChannel() {
		int step = 4;
		ImageProcessor.invertColors(bytes, step);
		ImageProcessor.invertColors(bytes, step);

		assertArrayEquals(bytes, bytesCopy);
	}
	
	@Test
	void convertToGrayscaleTest() {
		int step = 3;
		ImageProcessor.convertToGrayscale(bytes, step);
		
		for (int i = 0; i < bytesCopy.length; i += step) {
			int sum = 0;
			
			for (int j = 0 ; j < step ; j++)
				sum += (bytesCopy[i+j] & ~-256);
			
			sum /= step;
			
			for (int j = 0 ; j < step ; j++) {
				bytesCopy[i+j] = (byte) sum;
				
				if (bytes[i+j] != bytesCopy[i+j])
					fail(WRONG_VALUE_MESSAGE);
			}
		}
	}
	
	@Test
	void convertToGrayscaleTestWithAlphaChannel() {
		int step = 4;
		ImageProcessor.convertToGrayscale(bytes, step);
		
		for (int i = 0; i < bytesCopy.length; i += step) {
			int sum = 0;
			
			for (int j = 0 ; j < step ; j++)
				sum += (bytesCopy[i+j] & ~-256);
			
			sum /= step;
			
			for (int j = 0 ; j < step ; j++) {
				bytesCopy[i+j] = (byte) sum;
				
				if (bytes[i+j] != bytesCopy[i+j])
					fail(WRONG_VALUE_MESSAGE);
			}
			
			if (bytes[i + 3] != bytesCopy[i + 3]) {
				fail("Alpha channel was altered!");
			}
		}
	}
}
