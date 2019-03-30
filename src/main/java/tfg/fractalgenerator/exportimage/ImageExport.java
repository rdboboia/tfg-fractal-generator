package tfg.fractalgenerator.exportimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.InvalidParameterException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * A short module to export images. It's main functionality is based on the
 * {@link javax.imageio.ImageIO ImageIO} class.
 * 
 * @author -$BOSS$-
 */
public class ImageExport {
	/**
	 * Bitmap format. No compression. Creates large files.
	 */
	public static final String BMP = "BMP";
	/**
	 * Joint Photographic Experts Group format (the same as JPG).
	 * <br>
	 * Note: JPEG was used on MacIntosh since they weren't limited to 3
	 * character extensions while Windows used JPG instead.
	 */
	public static final String JPEG = "JPEG";
	/**
	 * Joint Photographic Experts Group format (the same as JPEG).
	 * Compressed image format. Not recommended for images where each
	 * individual pixel has valuable information.
	 */
	public static final String JPG = "JPG";
	/**
	 * Portable Network Graphics format. Lossless compression.
	 * A good alternative to the BMP format since it reduces drastically
	 * the file size while maintaining the quality.
	 */
	public static final String PNG = "PNG";
	/**
	 * Wireless Application Protocol Bitmap format. It's a monochrome
	 * image format. It might lead to interesting results.
	 */
	public static final String WBMP = "WBMP";
	
	/**
	 * Private constructor. Only static methods are provided, so no instance
	 * of this class is needed.
	 */
	private ImageExport() {
	}
	
	/**
	 * Main method of the class {@link ImageExport}. If the override parameter
	 * is false it checks if the file already exists in the given path and
	 * throws an
	 * {@link java.nio.file.FileAlreadyExistsException FileAlreadyExistsException}
	 * if it does. Otherwise, it attempts to write the file using the write
	 * method of the {@link javax.imageio.ImageIO ImageIO} class. It does not
	 * treat it's exception; it is thrown upwards.
	 * 
	 * @param image the image to be written.
	 * @param format the format of the image.
	 * @param folderPath the path to the folder where the file should be created.
	 * @param fileName the name of the file.
	 * @param override whether to override an already existing file.
	 * @throws InvalidParameterException if the given format is not supported by
	 * the {@link javax.imageio.ImageIO ImageIO} class.
	 * @throws FileAlreadyExistsException if the override parameter is false and
	 * the file already exists.
	 * @throws IllegalArgumentException if the image, the format or the file
	 * passed to the {@link javax.imageio.ImageIO ImageIO} write method
	 * {@code null}.
	 * @throws IOException if an error occurs during writing by the
	 * {@link javax.imageio.ImageIO ImageIO} class.
	 */
	public static void export(BufferedImage image, String format, String folderPath, String fileName, boolean override) throws IOException {
		if (Arrays.binarySearch(ImageIO.getWriterFormatNames(), format) < 0) {
			throw new InvalidParameterException("The given format is not supported. Check supported formats.");
		}
		
		File f = new File(folderPath, fileName + "." + format);
		
		if (!override && f.exists())
			throw new FileAlreadyExistsException("A file already exists at " + f.getPath());
		
		ImageIO.write(image, format, f);
	}
	
	/**
	 * Returns an {@code String} array with all the supported image
	 * formats by the {@link javax.imageio.ImageIO ImageIO} write method.
	 * 
	 * @return an array with all the supported image formats. 
	 */
	public static String[] getSupportedImageFormats() {
		return ImageIO.getWriterFormatNames();
	}
}
