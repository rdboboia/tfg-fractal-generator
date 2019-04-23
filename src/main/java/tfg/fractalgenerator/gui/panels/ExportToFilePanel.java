package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.gui.FileSaver;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGenerator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.border.TitledBorder;

public class FileOnlyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = FileOnlyPanel.class.getSimpleName();

	public FileOnlyPanel() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		
		JLabel lblStatus = new JLabel("Estado:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblActualStatus = new JLabel("detenido.");
		lblActualStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblResolution = new JLabel("Resolución:");
		
		JSpinner spinnerWidth = new JSpinner();
		spinnerWidth.setModel(new SpinnerNumberModel(new Integer(3840), new Integer(1), null, new Integer(1)));
		
		JLabel lblWidth = new JLabel("(ancho)");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSpinner spinnerHeight = new JSpinner();
		spinnerHeight.setModel(new SpinnerNumberModel(new Integer(2160), new Integer(1), null, new Integer(1)));
		
		JLabel lblHeight = new JLabel("(alto)");
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblDepth = new JLabel("Profundidad:");
		
		JSpinner spinnerDepth = new JSpinner();
		spinnerDepth.setModel(new SpinnerNumberModel(new Integer(360), new Integer(1), null, new Integer(1)));
		
		JLabel lblFormat = new JLabel("Formato:");
		
		JComboBox<ImageFormat> comboBoxFormat = new JComboBox<>();
		comboBoxFormat.setModel(new DefaultComboBoxModel<ImageFormat>(ImageFormat.values()));
		comboBoxFormat.setSelectedIndex(comboBoxFormat.getModel().getSize()-1);
		
		JButton btnGenerateAndExport = new JButton("Generar y exportar");
		btnGenerateAndExport.addActionListener(e -> {
			new Thread() {
				@Override
				public void run() {
					lblActualStatus.setText("creando imagen...");
					BufferedImage image = new BufferedImage((int)spinnerWidth.getValue(), (int)spinnerHeight.getValue(), BufferedImage.TYPE_INT_RGB);
					
					lblActualStatus.setText("generando fractal...");
					MandelbrotsetGenerator.generate(image, (int)spinnerDepth.getValue());
					
					lblActualStatus.setText("exportación de archivo...");
					FileSaver.saveFile(image, (ImageFormat)comboBoxFormat.getSelectedItem());
					
					lblActualStatus.setText("finalizado.");
				}
			}.start();
		});
		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setBorder(new TitledBorder(null, "Propiedades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(propertiesPanel, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
						.addComponent(btnReturn, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblActualStatus, GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnGenerateAndExport, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnReturn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(propertiesPanel, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
					.addComponent(btnGenerateAndExport, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus)
						.addComponent(lblActualStatus))
					.addContainerGap())
		);
		
		
		GroupLayout gl_propertiesPanel = new GroupLayout(propertiesPanel);
		gl_propertiesPanel.setHorizontalGroup(
			gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_propertiesPanel.createSequentialGroup()
					.addContainerGap(498, Short.MAX_VALUE)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_propertiesPanel.createSequentialGroup()
							.addComponent(lblResolution)
							.addGap(10)
							.addComponent(spinnerWidth, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(lblWidth)
							.addGap(4)
							.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(lblHeight)
							.addGap(486))
						.addGroup(Alignment.TRAILING, gl_propertiesPanel.createSequentialGroup()
							.addComponent(lblFormat)
							.addGap(4)
							.addComponent(comboBoxFormat, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addGap(543))
						.addGroup(Alignment.TRAILING, gl_propertiesPanel.createSequentialGroup()
							.addComponent(lblDepth)
							.addGap(4)
							.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(554))))
		);
		gl_propertiesPanel.setVerticalGroup(
			gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_propertiesPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblResolution))
						.addComponent(spinnerWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblWidth))
						.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblHeight)))
					.addGap(18)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDepth))
						.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblFormat))
						.addComponent(comboBoxFormat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(361, Short.MAX_VALUE))
		);
		propertiesPanel.setLayout(gl_propertiesPanel);
		setLayout(groupLayout);
	}
}