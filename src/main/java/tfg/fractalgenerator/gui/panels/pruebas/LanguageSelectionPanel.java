package tfg.fractalgenerator.gui.panels.pruebas;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;

public class LanguageSelectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = LanguageSelectionPanel.class.getSimpleName();

	public LanguageSelectionPanel() {
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