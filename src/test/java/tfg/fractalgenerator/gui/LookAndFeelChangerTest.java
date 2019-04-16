package tfg.fractalgenerator.gui;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.UIManager;

import org.junit.jupiter.api.Test;

class LookAndFeelChangerTest {

	@Test
	void correctLookAndFeelClassNameTest() {
		assertTrue(LookAndFeelChanger.changeLookAndFeel(UIManager.getSystemLookAndFeelClassName()),
				"The look and feel should change correctly.");
	}
	
	@Test
	void incorrectLookAndFeelClassNameTest() {
		assertFalse(LookAndFeelChanger.changeLookAndFeel("!legit-class-name"),
				"The look and feel shoudl fail to change and an error message should appear.");
	}
	
	@Test
	void thisShouldWorkTest() {
		assertTrue(LookAndFeelChanger.useSystemLookAndFeel(), "The look and feel should change correctly.");
	}
}