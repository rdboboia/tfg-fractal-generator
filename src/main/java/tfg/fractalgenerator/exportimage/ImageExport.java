package tfg.fractalgenerator.exportimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.InvalidParameterException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import tfg.fractalgenerator.gui.FileSaver;

/**
 * A short module to export images. It's main functionality is based on the
 * {@link javax.imageio.ImageIO ImageIO} class.
 * 
 * @author -$BOSS$-
 */
public class ImageExport {	
	/**
	 * Private constructor. Only static methods are provided, so no instance
	 * of this class is needed.
	 */
	private ImageExport() {
	}
	
	/**
	 * Main method of the class {@link ImageExport}. It checks if the extension
	 * is already included in the file name and removes it if necessary using a 
	 * method from the {@link FileSaver} class. If the override parameter is false
	 * it checks if the file already exists in the given path and throws an
	 * {@link java.nio.file.FileAlreadyExistsException FileAlreadyExistsException}
	 * if it does. Otherwise, it attempts to write the file using the write
	 * method of the {@link javax.imageio.ImageIO ImageIO} class. It does not
	 * treat it's exception; it is thrown upwards.
	 * 
	 * @param image the image to be written.
	 * @param imageFormat the format of the image of type {@link ImageFormat}.
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
	public static void export(BufferedImage image, ImageFormat imageFormat, String folderPath, String fileName, boolean override) throws IOException {
		String format = ImageFormatParser.getImageFormat(imageFormat);
		
		if (!Arrays.asList(ImageIO.getWriterFormatNames()).contains(format)) {
			throw new InvalidParameterException("The given format is not supported. Check supported formats.");
		}
		
		fileName = FileSaver.removeFileExtensionIfAlreadyExists(fileName, format);
		
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
