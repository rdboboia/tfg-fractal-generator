package tfg.imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageManager {
	private FileManager file;
	
	private boolean hasAlphaChannel;
	
	private BufferedImage image;
	private byte[] pixels;
	
	public ImageManager(String inputFilePath, String outputFilePath) {
		file = new FileManager(inputFilePath, outputFilePath);
		
		image = file.readImage();
		pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	}
}