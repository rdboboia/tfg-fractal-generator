package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FileDialog;
import java.awt.Frame;

public class RealtimeViewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private transient BufferedImage image;
	
	public static final String NAME = RealtimeViewPanel.class.getSimpleName();
	private JLabel lblNewLabel;
	private JButton btnGenerar;
	private JButton btnExportar;
	
	public RealtimeViewPanel() {
		this.setSize(MandelbrotSetGUI.size);
		
		lblNewLabel = new JLabel("");
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(e -> {
			btnGenerar.setEnabled(false);
			actualizarFractal();
		});
		btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		btnExportar.addActionListener(e -> saveFile());
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(118)
							.addComponent(btnGenerar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExportar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnVolver, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(956, Short.MAX_VALUE))
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnVolver)
						.addComponent(btnGenerar)
						.addComponent(btnExportar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		
	}

	private void saveFile() {
		try {
			FileDialog d = new FileDialog(new Frame(), "Guardar como");
			d.setVisible(true);

			String fileName = d.getFile();
			if (fileName != null) {
				if (fileName.toLowerCase().endsWith(ImageExport.PNG)) {
					fileName = fileName.substring(0, fileName.length() - 4);
				}
				
				if (Paths.get(d.getDirectory() + "\\" + d.getFile()).toFile().exists()) {
					if (JOptionPane.showConfirmDialog(this, "¿Desea sobreescribir el archivo?", "El archivo ya existe", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						ImageExport.export(image, ImageExport.PNG, d.getDirectory(), fileName, true);
					}
				} else {
					ImageExport.export(image, ImageExport.PNG, d.getDirectory(), fileName, false);
				}
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo.", "Error al guardar", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarFractal() {
		lblNewLabel.setSize(this.getWidth(), this.getHeight()-20);
		
		Thread generatorThread = new Thread() {
			@Override
			public void run() {
				image = new BufferedImage(lblNewLabel.getWidth(), lblNewLabel.getHeight(), BufferedImage.TYPE_INT_RGB);
				MandelbrotsetGenerator generator = new MandelbrotsetGenerator(image, 360);
				generator.generate();
				btnGenerar.setEnabled(true);
				btnExportar.setEnabled(true);
			}
		};
		generatorThread.start();

		Thread updateImageThread = new Thread() {
			@Override
			public void run() {
				while (generatorThread.isAlive()) {
					try {
						sleep(20);
					} catch (InterruptedException e) {
						interrupt();
					}
					
					lblNewLabel.setIcon(new ImageIcon(image));
				}
			}
		};
		updateImageThread.start();
	}
}
