package tfg.imageProcessing;

public class ImageProcessor {
	public static boolean process(byte[] pixels, ImageManager.ProcessingMode mode, boolean hasAlphaChannel) {
		int step;
		if (hasAlphaChannel)
			step = 4;
		else
			step = 3;
		
		switch (mode) {
			case NEGATIVE:
				invertColors(pixels);
				break;
			case GRAYSCALE:
				convertToGrayscale(pixels, step);
				break;
		}
		
		return true;
	}
	
	private static void invertColors(byte[] pixels) {
		for (int i = 0 ; i < pixels.length ; i++) {
			pixels[i] = (byte) (256 - pixels[i]);
		}
	}
	
	private static void convertToGrayscale(byte[] pixels, int step) {
		int sum;
		
		for (int i = 0 ; i < pixels.length ; i += step) {
			sum = 0;
			
			for (int j = 0 ; j < step ; j++)
				sum += (pixels[i+j] & ~-256);
			
			sum /= step;
			
			for (int j = 0 ; j < step ; j++)
				pixels[i+j] = (byte) sum;
		}
	}
}