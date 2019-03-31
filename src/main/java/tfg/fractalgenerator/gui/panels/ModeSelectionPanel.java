package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;

import javax.swing.JButton;

public class ModeSelectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = ModeSelectionPanel.class.getSimpleName();

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
