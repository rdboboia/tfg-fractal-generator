package tfg.fractalgenerator.gui.panels;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;

/**
 * Template for each of the views of the card layout group. It contains a
 * button to go to another view (previous one, for example).
 * 
 * @author -$BOSS$-
 */
public class PanelTemplate extends JPanel {
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
	public static final String NAME = PanelTemplate.class.getSimpleName();
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public PanelTemplate() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1180, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnReturn)
					.addContainerGap(697, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}