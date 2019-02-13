package tfg.imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageManager {
	public enum ProcessingMode {NEGATIVE, GRAYSCALE}
	
	private FileManager fileManager;
	
	private BufferedImage image;
	private byte[] pixels;
	private boolean hasAlphaChannel;
	
	public ImageManager(String inputFilePath, String outputFilePath) throws FileNotFoundException, IOException {
		fileManager = new FileManager(inputFilePath, outputFilePath);
		
		if (fileManager.sameFile())
			throw new IOException("The input and output file are the same!");
		
		image = fileManager.readImage();
		pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		hasAlphaChannel = image.getAlphaRaster() != null;
	}
	
	public boolean processImage(ProcessingMode mode) throws IOException {
		ImageProcessor.process(pixels, mode, hasAlphaChannel);
		fileManager.writeImage();
		
		return true;
	}
}