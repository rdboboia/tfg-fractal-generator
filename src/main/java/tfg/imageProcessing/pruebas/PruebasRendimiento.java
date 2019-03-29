package tfg.imageProcessing.pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;

import tfg.imageProcessing.ImageManager;
import tfg.imageProcessing.ImageManager.ProcessingMode;

public class PruebasRendimiento {
	public static void main(String[] args) {
		try {
//			ImageManager manager = new ImageManager("Z:\\img.bmp", "Z:\\out.bmp");
//			manager.processImage(ProcessingMode.GRAYSCALE);	
			
			new ImageManager("Z:\\in.bmp", "Z:\\out.png").processImage(ProcessingMode.CONDITIONAL_NEGATIVE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}