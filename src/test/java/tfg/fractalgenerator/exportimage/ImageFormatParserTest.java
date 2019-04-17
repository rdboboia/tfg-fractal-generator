package tfg.fractalgenerator.exportimage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageFormatParserTest {

	@Test
	void perserStringValueTest() {
		assertEquals("bmp", ImageFormatParser.getImageFormat(ImageFormat.BMP), "A 'bmp' String should correspond to the BMP format.");
		assertEquals("jpeg", ImageFormatParser.getImageFormat(ImageFormat.JPEG), "A 'jpeg' String should correspond to the JPEG format.");
		assertEquals("jpg", ImageFormatParser.getImageFormat(ImageFormat.JPG), "A 'jpg' String should correspond to the JPG format.");
		assertEquals("png", ImageFormatParser.getImageFormat(ImageFormat.PNG), "A 'png' String should correspond to the PNG format.");
	}
}