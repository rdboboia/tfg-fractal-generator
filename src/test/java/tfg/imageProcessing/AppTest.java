package tfg.imageProcessing;

import static org.junit.jupiter.api.Assertions.fail;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AppTest {
	@Disabled
	@Test
	void validParametersTest() {
		try {
			App.main(new String[0]);
		} catch (Exception e) {
			fail("Unexpected exception!");
		}
		
		
		
		try {
			App.main(new String[] {"-c"});
		} catch (InvalidParameterException  e) {
			fail("Unexpected exception!");
		}
	}
	
	@Test
	void invalidParametersTest() {
		try {
			App.main(new String[] {"c"});
			fail("An exception should have been launched!");
		} catch (InvalidParameterException  e) {
			// Nothing to do here; expected behavior
		}
		
		
		
		try {
			App.main(new String[2]);
			fail("An exception should have been launched!");
		} catch (InvalidParameterException  e) {
			// Nothing to do here; expected behavior
		}
		
		
		
		try {
			App.main(new String[4]);
			fail("An exception should have been launched!");
		} catch (InvalidParameterException  e) {
			// Nothing to do here; expected behavior
		}
	}
}