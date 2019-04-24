package tfg.fractalgenerator.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MandelbrotsetGeneratorAppTest {

	@Test
	void appTest() {
		try {
			new MandelbrotsetGeneratorApp();
			MandelbrotsetGeneratorApp.main(new String[0]);
			
			// Gives at least 1 second of app time since otherwise the thread will end with the JUnit's thread.
			Thread.sleep(1000);
		} catch (Exception e) {
			fail("No exceptions should be thrown.");
		}
	}
}