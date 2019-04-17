package tfg.fractalgenerator.exportimage;

/**
 * A enum that contains all the {@link ImageIO} supported formats on Java 8.
 * 
 * @author -$BOSS$-
 */
public enum ImageFormat {
	/**
	 * Bitmap format. No compression. Creates large files.
	 */
	BMP,
	/**
	 * Joint Photographic Experts Group format (the same as JPG).
	 * <br>
	 * Note: JPEG was used on MacIntosh since they weren't limited to 3
	 * character extensions while Windows used JPG instead.
	 */
	JPEG,
	/**
	 * Joint Photographic Experts Group format (the same as JPEG).
	 * Compressed image format. Not recommended for images where each
	 * individual pixel has valuable information.
	 */
	JPG,
	/**
	 * Portable Network Graphics format. Lossless compression.
	 * A good alternative to the BMP format since it reduces drastically
	 * the file size while maintaining the quality.
	 */
	PNG
}