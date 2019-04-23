package tfg.fractalgenerator.exportimage;

/**
 * A class that provides a method to parse from a {@link ImageFormat} enum
 * to its {@link String} representation.
 * 
 * @author -$BOSS$-
 */
public class ImageFormatParser {
	/**
	 * Private constructor. No instances allowed.
	 */
	private ImageFormatParser() {
	}
	
	/**
	 * It parses the {@link ImageFormat} image format enum to its {@link String}
	 * representation.
	 * @param imageFormat the image format of type {@link ImageFormat}
	 * @return the {@link String} representation of the image format.
	 */
	public static String getImageFormat(ImageFormat imageFormat) {
		String format;

		switch (imageFormat) {
			case BMP:
				format = "bmp";
				break;
			case JPEG:
				format = "jpeg";
				break;
			case JPG:
				format = "jpg";
				break;
			case PNG:
				format = "png";
				break;
			default:
				format = "unsupported";
				break;
		}
		
		return format;
	}
}