package tfg.fractalgenerator.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import tfg.fractalgenerator.gui.panels.FileOnlyPanel;
import tfg.fractalgenerator.gui.panels.LanguageSelectionPanel;
import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;
import tfg.fractalgenerator.gui.panels.RealtimeViewPanel;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class MandelbrotSetGUI extends JFrame {
	private static MandelbrotSetGUI instance;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private List<String> panels;
	
	public static final Dimension size = new Dimension(1280, 720);
	
	public static MandelbrotSetGUI getInstance() {
		if (instance == null)
			instance = new MandelbrotSetGUI();
		
		return instance;
	}
	
	public boolean changeCard(String panelName) {
		if (!panels.contains(panelName))
			throw new InvalidParameterException("The panel name provided was not found in the panel names list.");
		
		((CardLayout) contentPanel.getLayout()).show(contentPanel, panelName);
		return true;
	}
	
	boolean changeLookAndFeel(String className) {
		try {
			UIManager.setLookAndFeel(className);
			return true;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(this, "SystemUI Design could not be loaded.", "SystemUI Design error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private MandelbrotSetGUI() {
		this.setSize(size);
		
		changeLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		JPanel modeSelectionPanel = new ModeSelectionPanel();
		contentPanel.add(modeSelectionPanel, ModeSelectionPanel.NAME);
		
		JPanel realtimeViewPanel = new RealtimeViewPanel();
		contentPanel.add(realtimeViewPanel, RealtimeViewPanel.NAME);
		
		JPanel fileOnlyPanel = new FileOnlyPanel();
		contentPanel.add(fileOnlyPanel, FileOnlyPanel.NAME);
		
		JPanel languageSelectionPanel = new LanguageSelectionPanel();
		contentPanel.add(languageSelectionPanel, LanguageSelectionPanel.NAME);
		
		panels = new ArrayList<>();
		Component[] components = contentPanel.getComponents();
		for (int i = 0 ; i < components.length ; i++) {
			panels.add(components[i].getName());
		}
	}
}
