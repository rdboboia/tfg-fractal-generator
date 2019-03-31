package tfg.fractalgenerator.gui.panels;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;

public class PanelTemplate extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = PanelTemplate.class.getSimpleName();
	
	public PanelTemplate() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnVolver, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1180, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnVolver)
					.addContainerGap(697, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}