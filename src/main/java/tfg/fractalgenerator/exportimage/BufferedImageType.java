package tfg.fractalgenerator.exportimage;

import java.awt.image.BufferedImage;

public class BufferedImageType {
	/**
	 * Private constructor. No instances allowed.
	 */
	private BufferedImageType() {
		
	}
	
	public static int getBufferedImageType() {
		return BufferedImage.TYPE_3BYTE_BGR;
	}
}