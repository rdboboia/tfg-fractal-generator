package tfg.fractalgenerator.mandelbrotset;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MandelbrotsetPositionTest {

	@Test
	void defaultValuesTest() {
		MandelbrotsetPosition position = new MandelbrotsetPosition();
		
		assertEquals(0d, position.getPosx());
		assertEquals(0d, position.getPosy());
		assertEquals(1d, position.getScale());
		assertEquals(256d, position.getZoom());
	}
	
	@Test
	void gettersAndSettersTest() {
		MandelbrotsetPosition position = new MandelbrotsetPosition();
		
		position.setPosx(1d);
		assertEquals(1d, position.getPosx());
		
		position.setPosy(1d);
		assertEquals(1d, position.getPosy());
		
		position.setScale(5d);
		assertEquals(5d, position.getScale());
		
		position.setZoom(5d);
		assertEquals(5d, position.getZoom());
	}
}