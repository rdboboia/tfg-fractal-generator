package tfg.fractalgenerator.exportimage;

import java.awt.image.BufferedImage;

/**
 * A simple container for the image type used in the {@link BufferedImage} which
 * is meant to make the images consistent, since mixing image types ends in errors.
 * 
 * @author -$BOSS$-
 */
public class BufferedImageType {
	/**
	 * Private constructor. No instances allowed.
	 */
	private BufferedImageType() {
		
	}
	
	/**
	 * Returns the image type that will be used. {@link BufferedImage} types
	 * are used.
	 * 
	 * @return an int corresponding to the image type of the {@link BufferedImage}
	 * class.
	 */
	public static int getBufferedImageType() {
		// Since the underlying data structure is an byte array, less elements can be stored before the array size limit is exceeded.
		// So, there is a restriction in the image size.
		return BufferedImage.TYPE_3BYTE_BGR;
		
		// More pixels can be stored, but they're more difficult to work with while applying filters.
//		return BufferedImage.TYPE_INT_RGB;
	}
}