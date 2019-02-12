package tfg.imageProcessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileManager {
	private String inputFilePath;
	private String outputFilePath;
	
	private File in;
	private File out;
	
	private BufferedImage image;
	private String format;
	private boolean sameFile;
	
	public FileManager(String inputFilePath, String outputFilePath) {
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		
		format = outputFilePath.substring(outputFilePath.lastIndexOf('.') + 1);
		
		if (inputFilePath.equals(outputFilePath))
			sameFile = true;
		else
			sameFile = false;
	}
	
	public BufferedImage readImage() {
		try {
			in = new File(inputFilePath);
			image = ImageIO.read(in);
			
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public boolean writeImage() {
		try {
			out = new File(outputFilePath);
			ImageIO.write(image, format, out);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	public boolean sameFile() {
		return sameFile;
	}
}