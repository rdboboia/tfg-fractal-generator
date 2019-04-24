package tfg.fractalgenerator.gui.panels.store;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.mandelbrotset.MandelbrotsetPosition;

class GenerationParametersStoreTest {

	@Test
	void defaultValuesTest() {
		GenerationParametersStore store = GenerationParametersStore.getInstance();
		MandelbrotsetPosition position = new MandelbrotsetPosition();
		
		assertEquals(360, store.getColorDepth());
		assertEquals(360, store.getDepth());
		assertEquals(2160, store.getHeight());
		assertEquals(3840, store.getWidth());
		assertEquals(position.getPosx(), store.getPosition().getPosx());
		assertEquals(position.getPosy(), store.getPosition().getPosy());
		assertEquals(position.getScale(), store.getPosition().getScale());
		assertEquals(position.getZoom(), store.getPosition().getZoom());
	}
	
	@Test
	void gettersAndSettersTest() {
		GenerationParametersStore store = GenerationParametersStore.getInstance();
		MandelbrotsetPosition position = new MandelbrotsetPosition();
		
		store.setColorDepth(1);
		assertEquals(1, store.getColorDepth());
		
		store.setDepth(1);
		assertEquals(1, store.getDepth());
		
		store.setHeight(1);
		assertEquals(1, store.getHeight());
		
		store.setWidth(1);
		assertEquals(1d, store.getWidth());
		
		store.getPosition().setPosx(1d);
		assertEquals(1d, store.getPosition().getPosx());
		
		store.getPosition().setPosy(1d);
		assertEquals(1d, store.getPosition().getPosy());
		
		store.getPosition().setScale(5d);
		assertEquals(5d, store.getPosition().getScale());
		
		store.getPosition().setZoom(5d);
		assertEquals(5d, store.getPosition().getZoom());
		
		store.setPosition(new MandelbrotsetPosition());
		assertEquals(position.getPosx(), store.getPosition().getPosx());
		assertEquals(position.getPosy(), store.getPosition().getPosy());
		assertEquals(position.getScale(), store.getPosition().getScale());
		assertEquals(position.getZoom(), store.getPosition().getZoom());
	}
}