package tfg.fractalgenerator.gui;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Static class that provides two methods for changing the application's design
 * or look and feel. It can set a given look and feel or it can try to apply
 * the system's look and feel.
 * @author -$BOSS$-
 */
public class LookAndFeelChanger {
	/**
	 * Private constructor. No instances allowed.
	 */
	private LookAndFeelChanger() {
	}
	
	/**
	 * Sets the environment design or look and feel to the one corresponding
	 * to the parameter.
	 * @param className the desired look and feel class name.
	 * @return {@code true} if a look and feel was found bases on the name
	 * provided and it was changed. Otherwise, {@code false} is returned and
	 * a {@link JOptionPane} with an error message is shown.
	 */
	static boolean changeLookAndFeel(String className) {
		try {
			UIManager.setLookAndFeel(className);
			return true;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			return false;
		}
	}
	
	/**
	 * Sets the current design theme to the host system design theme.
	 * @return false if the system look and feel could not be found or applied;
	 * true otherwise.
	 */
	public static boolean useSystemLookAndFeel() {
		return changeLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
}