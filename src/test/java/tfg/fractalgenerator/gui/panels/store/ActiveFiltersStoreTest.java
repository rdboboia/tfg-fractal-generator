package tfg.fractalgenerator.gui.panels.store;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ActiveFiltersStoreTest {

	@Test
	void defaultValuesTest() {
		ActiveFiltersStore store = ActiveFiltersStore.getInstance();
		
		assertEquals(false, store.isGrayscaleFilterActive());
		assertEquals(false, store.isNegativeFilterActive());
	}
	
	@Test
	void gettersAndSettersTest() {
		ActiveFiltersStore store = ActiveFiltersStore.getInstance();
		
		store.setGrayscaleFilterActive(true);
		assertEquals(true, store.isGrayscaleFilterActive());
		
		store.setNegativeFilterActive(true);
		assertEquals(true, store.isNegativeFilterActive());
	}
}
