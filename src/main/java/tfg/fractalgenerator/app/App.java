package tfg.fractalgenerator.app;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;

public class App {
	public static void main(String[] args) {
		MandelbrotSetGUI gui = MandelbrotSetGUI.getInstance();
		gui.setVisible(true);
	}
}