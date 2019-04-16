package tfg.fractalgenerator.gui;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;

class MandelbrotSetGUITest {
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