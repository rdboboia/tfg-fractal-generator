package tfg.imageProcessing.pruebas;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		try {
			File f = new File("Z:\\imgZZZZ.png");
			File f2 = new File("Z:\\img.jpg");
			BufferedImage image = ImageIO.read(f);
			BufferedImage image2 = ImageIO.read(f2);

			int width = image.getWidth();
			int heigh = image.getHeight();
			
			int width2 = image2.getWidth();
			int heigh2 = image2.getHeight();

			System.out.println("Width: " + width + " / Heigh: " + heigh);
			System.out.println("Width: " + width2 + " / Heigh: " + heigh2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
