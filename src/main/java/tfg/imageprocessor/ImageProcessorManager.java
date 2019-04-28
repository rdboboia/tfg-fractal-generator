package tfg.imageprocessor;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * A class to manage the {@link ImageProcessor} module. It takes an
 * {@link BufferedImage} and extracts the needed data that the
 * {@link ImageProcessor} will use to process the image. It also receives the
 * {@link ProcessingMode} in order to decide which processing method should be
 * called.
 * 
 * @author -$BOSS$-
 */
public class ImageProcessorManager {
	/**
	 * Private constructor. No instances allowed.
	 */
	private ImageProcessorManager() {
	}
	
	/**
	 * It extracts the RGB(A) byte array data from the image and, based on the
	 * {@link ProcessingMode} mode, calls the needed method of the
	 * {@link ImageProcessor} class.
	 * 
	 * @param image the image to be processed.
	 * @param mode the desired processing mode of type {@link ProcessingMode}.
	 */
	public static void process(BufferedImage image, ProcessingMode mode) {
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		
		int step;
		if (image.getAlphaRaster() != null)
			step = 4;
		else
			step = 3;
		
		switch (mode) {
			case COLOR_INVERSION:
				ImageProcessor.invertColors(pixels, step);
				break;
			case GRAYSCALE:
				ImageProcessor.convertToGrayscale(pixels, step);
				break;
		}
	}
}