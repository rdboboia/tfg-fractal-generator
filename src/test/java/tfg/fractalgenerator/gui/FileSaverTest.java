package tfg.fractalgenerator.gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FileSaverTest {

	@Test
	void removeFileExtensionIfAlreadyExistsTest() {
		// No extension test
		assertEquals("myFile", FileSaver.removeFileExtensionIfAlreadyExists("myFile", "png"),
				"No changes should be made since the file name has no extension included.");
		
		// Has extension test
		assertEquals("myFile", FileSaver.removeFileExtensionIfAlreadyExists("myFile.png", "png"),
				"The '.png' extension should be removed since it exists in the file name.");
		
		// Extension larger than 3 test
		assertEquals("myFile", FileSaver.removeFileExtensionIfAlreadyExists("myFile.jpeg", "jpeg"),
				"The '.jpeg' extension should be removed since it exists in the file name.");
		
		// Duplicate extension test
		assertEquals("myFile.png", FileSaver.removeFileExtensionIfAlreadyExists("myFile.png.png", "png"),
				"Since the extension is the last part of a file, only the last '.png' should be removed.");

		// More extensions test
		assertEquals("myFile.imgs", FileSaver.removeFileExtensionIfAlreadyExists("myFile.imgs.png", "png"),
				"Only the '.png' extension should be removed.");
		
		// Extension not at the end test
		assertEquals("myFile.img.png", FileSaver.removeFileExtensionIfAlreadyExists("myFile.img.png", "img"),
				"No changes should be made since the specified extension isn't located at the end of the file (where extensions should be).");
	}

}