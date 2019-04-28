package tfg.imageprocessor;

/**
 * A simple module that provides methods for image processing or filtering, such
 * generation of a negative color image or conversion to grayscale image.
 * 
 * @author -$BOSS$-
 */
public class ImageProcessor {
	/**
	 * Private constructor. No instances allowed.
	 */
	private ImageProcessor() {
	}
	
	/**
	 * Assuming that the received byte array corresponds to the RGB(A) channels
	 * of an image, it inverts its colors by inverting every single byte except
	 * for the alpha channel (if exists). That is, it subtracts the actual byte
	 * value from 255 and stores that new value into it's position.
	 * 
	 * @param pixels the RGB(A) channels of an image.
	 * @param step the number of bytes that defines a pixel (3 for RGB, 4 for RGBA).
	 */
	public static void invertColors(byte[] pixels, int step) {
		for (int i = 0 ; i < pixels.length ; i += step) {
			pixels[i] = (byte) (255 - pixels[i]);
			pixels[i+1] = (byte) (255 - pixels[i+1]);
			pixels[i+2] = (byte) (255 - pixels[i+2]);
		}
	}
	
	/**
	 * Assuming that the received byte array corresponds to the RGB(A) channels
	 * of an image, it generates a grayscale image by computing the average
	 * value of the RGB channels and storing that value into every channel.
	 * 
	 * @param pixels the RGB(A) channels of an image.
	 * @param step the number of bytes that defines a pixel (3 for RGB, 4 for RGBA).
	 */
	public static void convertToGrayscale(byte[] pixels, int step) {
		for (int i = 0 ; i < pixels.length ; i += step) {
			int sum = 0;
			
			for (int j = 0 ; j < step ; j++)
				sum += (pixels[i+j] & ~-256);
			
			sum /= step;
			
			for (int j = 0 ; j < step ; j++)
				pixels[i+j] = (byte) sum;
		}
	}
}