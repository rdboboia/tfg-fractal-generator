package tfg.fractalgenerator.gui;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import javax.swing.UIManager;

import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;

class MandelbrotSetGUITest {

	@Test
	void correctLookAndFeelClassNameTest() {
		assertTrue(MandelbrotSetGUI.getInstance().changeLookAndFeel(UIManager.getSystemLookAndFeelClassName()),
				"The look and feel should change correctly");
	}

	@Test
	void incorrectLookAndFeelClassNameTest() {
		assertFalse(MandelbrotSetGUI.getInstance().changeLookAndFeel("!legit-class-name"),
				"The look and feel shoudl fail to change and an error message should appear.");
	}

	@Test
	void changeCardMethodTest() {
		assertTrue(MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME),
				"The panel should change correctly since it exists in the panel list");
	}

	@Test
	void incorrectChangeCardMethodTest() {
		assertThrows(InvalidParameterException.class, () -> MandelbrotSetGUI.getInstance().changeCard("mal"),
				"The method should give an InvalidParameterException since the given panel name doesn't exist in the panels list.");
	}
}
