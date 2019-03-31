package tfg.fractalgenerator.app;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;

/**
 * Main class for the Mandelbrot Set generation application.
 * 
 * @author -$BOSS$-
 */
public class App {
	/**
	 * Main method for starting the application.
	 * It launches the GUI which for now is the only way to use the application.
	 * @param args the arguments are ignored (for now).
	 */
	public static void main(String[] args) {
		MandelbrotSetGUI gui = MandelbrotSetGUI.getInstance();
		gui.setVisible(true);
	}
}