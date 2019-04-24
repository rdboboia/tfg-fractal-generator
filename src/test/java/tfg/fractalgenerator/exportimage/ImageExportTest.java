package tfg.fractalgenerator.exportimage;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ImageExportTest {
	private static final String NO_EXCEPTION_EXPECTED = "No exception should be thrown.";
	private static final String EXCEPTION_EXPECTED = "An exception should be thrown.";
	
	@Test
	void correctExecutionTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "correctExecutionTest", true);
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\correctExecutionTest.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void incorrectImageSizeTest() {
		try {
			ImageExport.export(new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "imageSizeTest", true);
			fail(EXCEPTION_EXPECTED);
		} catch (IllegalArgumentException e) {
			// Expected
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\imageSizeTest.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void incorrectImageTypeTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, -1), ImageFormat.PNG, "Z:\\", "imageTypeTest", true);
			fail(EXCEPTION_EXPECTED);
		} catch (IllegalArgumentException e) {
			// Expected
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\imageTypeTest.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void incorrectFileFormatTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), null, "Z:\\", "fileFormatTest", true);
			fail(EXCEPTION_EXPECTED);
		} catch (NullPointerException e) {
			// Expected
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\fileFormatTest.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void correctFileFormatTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.BMP, "Z:\\", "fileFormatTest", false);
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.JPEG, "Z:\\", "fileFormatTest", false);
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.JPG, "Z:\\", "fileFormatTest", false);
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "fileFormatTest", false);
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		try {
			assertTrue(Files.deleteIfExists(Paths.get("Z:\\fileFormatTest.bmp")));
			assertTrue(Files.deleteIfExists(Paths.get("Z:\\fileFormatTest.jpeg")));
			assertTrue(Files.deleteIfExists(Paths.get("Z:\\fileFormatTest.jpg")));
			assertTrue(Files.deleteIfExists(Paths.get("Z:\\fileFormatTest.png")));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void incorrectDriveLetterTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Y:\\", "driveLetterTest", true);
			fail(EXCEPTION_EXPECTED);
		} catch (NullPointerException e) {
			// Expected
		} catch (FileNotFoundException e) {
			// Expected
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void incorrectFileDirectoryTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\miCarpeta", "fileDirectotyTest", true);
			fail(EXCEPTION_EXPECTED);
		} catch (NullPointerException e) {
			// Expected
		} catch (FileNotFoundException e) {
			// Expected
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void fileNameTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "", true);
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void overrideTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "override", true);
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "override", true);
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\override.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void noOverrideTest() {
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "alreadyExists", false);
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		try {
			ImageExport.export(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), ImageFormat.PNG, "Z:\\", "alreadyExists", false);
			fail("An FileAlreadyExistsException should be thrown!");
		} catch (FileAlreadyExistsException e) {
			// Expected
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
		
		// Deleting the created file.
		try {
			Files.deleteIfExists(Paths.get("Z:\\alreadyExists.png"));
		} catch (IOException e) {
			fail(NO_EXCEPTION_EXPECTED);
		}
	}
	
	@Test
	void supportedImageFormatsTest() {
		assertArrayEquals(ImageIO.getWriterFormatNames(), ImageExport.getSupportedImageFormats());
	}
}