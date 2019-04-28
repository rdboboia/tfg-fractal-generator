package tfg.imageprocessor;

/**
 * An enum that contains all the supported image processing modes.
 * 
 * @author -$BOSS$-
 */
public enum ProcessingMode {
	/**
	 * Inverts all colors in the image (that means the result will be a negative
	 * of the original image).
	 */
	COLOR_INVERSION,
	
	/**
	 * Converts the image to a grayscale image.
	 */
	GRAYSCALE
}