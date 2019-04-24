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
	
	@Test
	void toStringTest() {
		MandelbrotsetPosition position = new MandelbrotsetPosition();
		assertEquals("MandelbrotsetPosition [posx=0.0, posy=0.0, zoom=256.0, scale=1.0]", position.toString());
		
		position.setPosx(1);
		position.setPosy(1);
		position.setScale(5);
		position.setZoom(5);
		assertEquals("MandelbrotsetPosition [posx=1.0, posy=1.0, zoom=5.0, scale=5.0]", position.toString());
	}
}