package tfg.imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {
	public enum ProcessingMode {negative, grayscale}
	
	private int height;
	private int width;
	private int imageType;
	private boolean hasAlphaChannel;
	
	private String inputImagePath;
	private String outputImagePath;
	
	private BufferedImage imageData;
	private byte[] pixels;
	
	
	public void process(String inputImagePath, String outputImagePath, ProcessingMode mode) {
		this.inputImagePath = inputImagePath;
		this.outputImagePath = outputImagePath;
		
		pixels = getImagePixelArray();
		
		switch (mode) {
			case negative:
				neg();
				break;
			case grayscale:
				System.out.println("Not implemented yet");
				break;
		}
		
		exportResult(pixels);
	}
	
	
	
	private byte[] getImagePixelArray() {
		try {
			File inputFile = new File(inputImagePath);
			imageData = ImageIO.read(inputFile);
			
//			height = imageData.getHeight();
//			width = imageData.getWidth();
//			imageType = imageData.getType();
			hasAlphaChannel = imageData.getAlphaRaster() != null;
			
			return ((DataBufferByte) imageData.getRaster().getDataBuffer()).getData();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void neg() {
		for (int i = 0 ; i < pixels.length ; i++) {
//			pixels[i] = (byte) (255 - (pixels[i] % 256));
			pixels[i] = (byte) (255 - pixels[i]);
		}
	}
	
	private void exportResult(byte[] pixels) {
		try {
			File outputFile = new File(outputImagePath);
			ImageIO.write(imageData, "bmp", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}