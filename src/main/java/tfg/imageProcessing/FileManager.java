package tfg.imageProcessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class FileManager {
	private String inputFilePath;
	private String outputFilePath;

	private File in;
	private File out;

	private BufferedImage image;
	private String format;
	private boolean sameFile;

	public FileManager(String inputFilePath, String outputFilePath) throws FileNotFoundException {
		if (Files.exists(Paths.get(inputFilePath)))
			this.inputFilePath = inputFilePath;
		else
			throw new FileNotFoundException("The input file doesn't exist!");

		this.outputFilePath = outputFilePath;
		this.format = outputFilePath.substring(outputFilePath.lastIndexOf('.') + 1);

		if (inputFilePath.equals(outputFilePath))
			sameFile = true;
		else
			sameFile = false;
	}

	public BufferedImage readImage() throws IOException {
		in = new File(inputFilePath);
		image = ImageIO.read(in);

		return image;
	}

	public boolean writeImage() throws IOException {
		// if (in == null)
		// throw new NullPointerException("No input file detected. An input file must be
		// loaded first!");

		out = new File(outputFilePath);
		ImageIO.write(image, format, out);

		return true;
	}

	public boolean sameFile() {
		return sameFile;
	}
}