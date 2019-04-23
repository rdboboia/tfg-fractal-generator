package tfg.fractalgenerator.gui;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;

class MandelbrotSetGUITest {
	@Test
	void changeCardMethodTest() {
		assertTrue(MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME),
				"The panel should change correctly since it exists in the panel list");
	}
	
	@Disabled // Since the method launches a JOptionPane there's no way to automate this test; human interaction is needed.
	@Test
	void incorrectChangeCardMethodTest() {
		assertThrows(InvalidParameterException.class, () -> MandelbrotSetGUI.getInstance().changeCard("very legit panel name"),
				"The method should throw an InvalidParameterException since the given panel name doesn't exist in the panels list.");
	}
}