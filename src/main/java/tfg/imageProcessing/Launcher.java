package tfg.imageProcessing;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Launcher {
	public static void launchGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Not implemented yet! Bare with me...", "ImageProcessor", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void launchInterractiveConsole() {
		new InterractiveConsole();
	}
	
	public static void launchProcessor(String[] args) {
		
	}
}