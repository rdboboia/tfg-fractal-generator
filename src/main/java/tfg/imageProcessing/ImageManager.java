package tfg.imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageManager {
	public enum ProcessingMode {NEGATIVE, GRAYSCALE, CONDITIONAL_NEGATIVE}
	
	private FileManager fileManager;
	
	private BufferedImage image;
	private byte[] pixels;
	private boolean hasAlphaChannel;
	
	// Testing
	long s, f;
	
	public ImageManager(String inputFilePath, String outputFilePath) throws FileNotFoundException, IOException {
		System.out.println("Creando instancia y leyendo datos...");
		s = System.currentTimeMillis();
		
		fileManager = new FileManager(inputFilePath, outputFilePath);
		
		if (fileManager.sameFile())
			throw new IOException("The input and output file are the same!");
		
		image = fileManager.readImage();
		pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		hasAlphaChannel = image.getAlphaRaster() != null;
		
		f = System.currentTimeMillis();
		System.out.println("FIN! Tiempo: " + (f - s) + " ms.");
	}
	
	public boolean processImage(ProcessingMode mode) throws IOException {
		System.out.println("Aplicando filtro...");
		s = System.currentTimeMillis();
		
		ImageProcessor.process(pixels, mode, hasAlphaChannel);
		
		f = System.currentTimeMillis();
		System.out.println("FIN! Tiempo: " + (f - s) + " ms.");
		
		
		
		System.out.println("Escribiendo imagen resultado...");
		s = System.currentTimeMillis();
		
		fileManager.writeImage();
		
		f = System.currentTimeMillis();
		System.out.println("FIN! Tiempo: " + (f - s) + " ms.");
		
		return true;
	}
}