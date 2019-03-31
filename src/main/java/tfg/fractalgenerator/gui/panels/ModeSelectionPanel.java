package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;

import javax.swing.JButton;

/**
 * This view is meant to be a simple selector for different modes of operation
 * of the application. It's a simple {@link JPanel} with two buttons, so only
 * two options to choose from. Each one leads to another view.
 * 
 * @author -$BOSS$-
 */
public class ModeSelectionPanel extends JPanel {
	/**
	 * Serial version number. Using the default one.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Static reference to the name of this class which is used as the name
	 * of the component. The name is based on the {@code Class}'s name. Useful
	 * for the card layout since it makes a lot easier to have access to a
	 * component's name. 
	 */
	public static final String NAME = ModeSelectionPanel.class.getSimpleName();

	/**
	 * Initialization of the Panel and it's layout.
	 */
	public ModeSelectionPanel() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		JButton btnNewButton = new JButton("Visualización en tiempo real");
		btnNewButton.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(RealtimeViewPanel.NAME));
		
		JButton btnSoloExportarA = new JButton("Solo exportar a imágen");
		btnSoloExportarA.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(FileOnlyPanel.NAME));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(299)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
					.addGap(82)
					.addComponent(btnSoloExportarA, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
					.addGap(299))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(260)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSoloExportarA, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
					.addGap(260))
		);
		setLayout(groupLayout);

	}

}
