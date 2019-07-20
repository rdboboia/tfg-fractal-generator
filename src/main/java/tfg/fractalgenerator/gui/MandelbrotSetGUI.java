package tfg.fractalgenerator.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tfg.fractalgenerator.gui.panels.ExportToFilePanel;
import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;
import tfg.fractalgenerator.gui.panels.RealtimeViewPanelScalable;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main Window where the application runs. It uses a {@link CardLayout}
 * layout which contains all the different views or sections that the application
 * has, each one defined in its own {@code Class} which extends {@link JPanel}.
 * <br>
 * It uses the Singleton pattern so that only one instance can be made which is
 * meant to be accessed from all the different views since it provides a public
 * method to change between cards or views. This way any view can lead into any
 * other view without much trouble.
 * <br>
 * This class doesn't seem very test friendly, but some slight modifications
 * were made to make testing possible.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotSetGUI extends JFrame {
	/**
	 * Maintains the reference to the one and only instance of the
	 * {@link MandelbrotSetGUI} {@code Class}.
	 */
	private static MandelbrotSetGUI instance;
	
	/**
	 * Serial version number. Using the default one.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initial size for the main panel that is also used in all sub-panels.
	 * <br>
	 * Also useful for development since WindowBuilder has its limitations and
	 * doesn't properly parse values that are unknown in compilation time.
	 */
	public static final Dimension size = new Dimension(1280, 720);
	
	/**
	 * Main method to get the one and only possible instance of
	 * {@link MandelbrotSetGUI} {@code Class}, using the Singleton pattern.
	 * This is, it always returns the one and only instance of
	 * {@link MandelbrotSetGUI} creating it if it doesn't exist yet.
	 * 
	 * @return the {@link MandelbrotSetGUI} instance.
	 */
	public static MandelbrotSetGUI getInstance() {
		if (instance == null)
			instance = new MandelbrotSetGUI();
		
		return instance;
	}
	
	/**
	 * Main content panel for the {@link MandelbrotSetGUI} {@code Frame}.
	 */
	private JPanel contentPanel;
	
	/**
	 * A list with all the panel names that are inside the main panel.
	 */
	private List<String> panels;
	
	/**
	 * Public method designed to facilitate to the programmer the navigation
	 * between views. Making use of the {@link CardLayout}, it changes the
	 * current card to the card whose name is passed as a parameter.
	 * 
	 * @param panelName the name of the card to be displayed.
	 * @return {@code true} if the name was found in the panel names list and
	 * the new card is set to be displayed.
	 * @throws InvalidParameterException if there was no name match in
	 * the panel names list with the given name parameter.
	 */
	public boolean changeCard(String panelName) {
		if (!panels.contains(panelName)) { // Informing both the user and the developer.
			JOptionPane.showMessageDialog(this, "La vista seleccionada no se ha podido encontrar.", "Vista no encontrada", JOptionPane.ERROR_MESSAGE);
			throw new InvalidParameterException("The panel name provided was not found in the panel names list.");
		}
		
		((CardLayout) contentPanel.getLayout()).show(contentPanel, panelName);
		return true;
	}
	
	/**
	 * Initialization of the main Window. It sets its size, changes the
	 * {@code LookAndFeel} for all the views and set some more size and placement
	 * variables. It instantiates all the sub-views (cards) and creates the
	 * list containing all their names. If the system look and feel could not be
	 * loaded a error message is shown to the user.
	 */
	private MandelbrotSetGUI() {
		this.setTitle("Generador del Conjunto de Mandelbrot (v 1.1.3)");
		this.setMinimumSize(new Dimension(0, 80));
		this.setSize(size);
		
		if (!LookAndFeelChanger.useSystemLookAndFeel()) {
			JOptionPane.showMessageDialog(this, "SystemUI Design could not be loaded.", "SystemUI Design error", JOptionPane.ERROR_MESSAGE);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (int)size.getWidth() + 25, (int)size.getHeight());
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		JPanel modeSelectionPanel = new ModeSelectionPanel();
		contentPanel.add(modeSelectionPanel, modeSelectionPanel.getName());
		
		JPanel realtimeViewPanel = new RealtimeViewPanelScalable();
		contentPanel.add(realtimeViewPanel, realtimeViewPanel.getName());
		
		JPanel fileOnlyPanel = new ExportToFilePanel();
		contentPanel.add(fileOnlyPanel, fileOnlyPanel.getName());
		
		panels = new ArrayList<>();
		Component[] components = contentPanel.getComponents();
		for (int i = 0 ; i < components.length ; i++) {
			panels.add(components[i].getName());
		}
	}
}
