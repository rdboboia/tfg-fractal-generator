package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.BufferedImageType;
import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.gui.FileSaver;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.gui.panels.store.ActiveFiltersStore;
import tfg.fractalgenerator.gui.panels.store.GenerationParametersStore;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGeneratorThreadManager;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetPosition;
import tfg.imageprocessor.ImageProcessorManager;
import tfg.imageprocessor.ProcessingMode;

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

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JCheckBox;

public class ExportToFilePanel extends JPanel {
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
	public static final String NAME = ExportToFilePanel.class.getSimpleName();
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public ExportToFilePanel() {
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
		spinnerWidth.setModel(new SpinnerNumberModel(3840, 1, null, 1));
		
		JLabel lblWidth = new JLabel("(ancho)");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSpinner spinnerHeight = new JSpinner();
		spinnerHeight.setModel(new SpinnerNumberModel(2160, 1, null, 1));
		
		JLabel lblHeight = new JLabel("(alto)");
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblDepth = new JLabel("Profundidad:");
		
		JSpinner spinnerDepth = new JSpinner();
		spinnerDepth.setModel(new SpinnerNumberModel(360, 1, null, 1));
		
		JSpinner spinnerColorDepth = new JSpinner();
		spinnerColorDepth.setModel(new SpinnerNumberModel(360, 1, null, 1));
		
		JLabel lblFormat = new JLabel("Formato:");
		
		JComboBox<ImageFormat> comboBoxFormat = new JComboBox<>();
		comboBoxFormat.setModel(new DefaultComboBoxModel<ImageFormat>(ImageFormat.values()));
		comboBoxFormat.setSelectedIndex(comboBoxFormat.getModel().getSize()-1);
		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setBorder(new TitledBorder(null, "Propiedades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel positionPanel = new JPanel();
		positionPanel.setBorder(new TitledBorder(null, "Posici\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblEjeX = new JLabel("Eje x:");
		
		JSpinner spinnerXAxis = new JSpinner();
		spinnerXAxis.setModel(new SpinnerNumberModel(0d, null, null, 1d));
		
		JLabel lblEjeY = new JLabel("Eje y:");
		
		JSpinner spinnerYAxis = new JSpinner();
		spinnerYAxis.setModel(new SpinnerNumberModel(0d, null, null, 1d));
		
		JLabel lblZoom = new JLabel("Zoom:");
		
		JSpinner spinnerZoom = new JSpinner();
		spinnerZoom.setModel(new SpinnerNumberModel(1d, 1d, null, 1d));
		
		JLabel lblEscala = new JLabel("Escala:");
		
		JSpinner spinnerScale = new JSpinner();
		spinnerScale.setModel(new SpinnerNumberModel(0.003125d, null, null, 1d));
		
		JLabel lblColores = new JLabel("Colores:");
		
		JCheckBox chckbxGrayscaleFilter = new JCheckBox("Convert the colors to grayscale");
		
		JCheckBox chckbxNegativeFilter = new JCheckBox("Invert the colors");
		
		JButton btnGenerateAndExport = new JButton("Generar y exportar");
		btnGenerateAndExport.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGenerateAndExport.addActionListener(e -> {
			new Thread() {
				@Override
				public void run() {
					btnGenerateAndExport.setEnabled(false);
					
					lblActualStatus.setText("creando imagen...");
					BufferedImage image = new BufferedImage((int)spinnerWidth.getValue(), (int)spinnerHeight.getValue(), BufferedImageType.getBufferedImageType());
					
					lblActualStatus.setText("generando fractal...");
					MandelbrotsetPosition position = new MandelbrotsetPosition();
					position.setPosx((double)spinnerXAxis.getValue());
					position.setPosy((double)spinnerYAxis.getValue());
					position.setZoom((double)spinnerZoom.getValue());
					position.setScale((double)spinnerScale.getValue());
					MandelbrotsetGeneratorThreadManager.generate(image, (int)spinnerDepth.getValue(), (int)spinnerColorDepth.getValue(), position);
					
					lblActualStatus.setText("aplicando filtros...");
					if (chckbxNegativeFilter.isSelected())
						ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
					if (chckbxGrayscaleFilter.isSelected())
						ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
					
					
					lblActualStatus.setText("exportación de archivo...");
					FileSaver.saveFile(image, (ImageFormat)comboBoxFormat.getSelectedItem());
					
					lblActualStatus.setText("finalizado.");
					
					btnGenerateAndExport.setEnabled(true);
				}
			}.start();
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnGenerateAndExport, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(filtersPanel, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
						.addComponent(btnReturn, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblActualStatus, GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(propertiesPanel, GroupLayout.PREFERRED_SIZE, 627, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(positionPanel, GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnReturn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(propertiesPanel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
						.addComponent(positionPanel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(filtersPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnGenerateAndExport, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus)
						.addComponent(lblActualStatus))
					.addContainerGap())
		);
		
		GroupLayout gl_filtersPanel = new GroupLayout(filtersPanel);
		gl_filtersPanel.setHorizontalGroup(
			gl_filtersPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_filtersPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_filtersPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxNegativeFilter)
						.addComponent(chckbxGrayscaleFilter))
					.addContainerGap(1065, Short.MAX_VALUE))
		);
		gl_filtersPanel.setVerticalGroup(
			gl_filtersPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_filtersPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxNegativeFilter)
					.addGap(18)
					.addComponent(chckbxGrayscaleFilter)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		filtersPanel.setLayout(gl_filtersPanel);
		
		GroupLayout gl_positionPanel = new GroupLayout(positionPanel);
		gl_positionPanel.setHorizontalGroup(
			gl_positionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_positionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblZoom)
						.addComponent(lblEscala)
						.addComponent(lblEjeY)
						.addComponent(lblEjeX))
					.addGap(31)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(spinnerXAxis, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
						.addComponent(spinnerYAxis, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
						.addComponent(spinnerZoom, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
						.addComponent(spinnerScale, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_positionPanel.setVerticalGroup(
			gl_positionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_positionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEjeX)
						.addComponent(spinnerXAxis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEjeY)
						.addComponent(spinnerYAxis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZoom)
						.addComponent(spinnerZoom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEscala)
						.addComponent(spinnerScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(222, Short.MAX_VALUE))
		);
		positionPanel.setLayout(gl_positionPanel);
		
		GroupLayout gl_propertiesPanel = new GroupLayout(propertiesPanel);
		gl_propertiesPanel.setHorizontalGroup(
			gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_propertiesPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addComponent(lblResolution)
							.addGap(50)
							.addComponent(spinnerWidth, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblWidth)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblHeight))
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDepth)
								.addComponent(lblColores))
							.addGap(43)
							.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spinnerDepth, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
								.addComponent(comboBoxFormat, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(spinnerColorDepth)))
						.addComponent(lblFormat))
					.addContainerGap(934, Short.MAX_VALUE))
		);
		gl_propertiesPanel.setVerticalGroup(
			gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_propertiesPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResolution)
						.addComponent(spinnerWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWidth)
						.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHeight))
					.addGap(18)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDepth)
						.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColores)
						.addComponent(spinnerColorDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFormat)
						.addComponent(comboBoxFormat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(222, Short.MAX_VALUE))
		);
		propertiesPanel.setLayout(gl_propertiesPanel);
		setLayout(groupLayout);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				GenerationParametersStore genParamStore = GenerationParametersStore.getInstance();
				
				spinnerWidth.setValue(genParamStore.getWidth());
				spinnerHeight.setValue(genParamStore.getHeight());
				
				spinnerXAxis.setValue(genParamStore.getPosition().getPosx());
				spinnerYAxis.setValue(genParamStore.getPosition().getPosy());
				spinnerZoom.setValue(genParamStore.getPosition().getZoom());
				spinnerScale.setValue(genParamStore.getPosition().getScale());
				
				spinnerDepth.setValue(genParamStore.getDepth());
				spinnerColorDepth.setValue(genParamStore.getColorDepth());
				
				ActiveFiltersStore activeFiltersStore = ActiveFiltersStore.getInstance();
				chckbxNegativeFilter.setSelected(activeFiltersStore.isNegativeFilterActive());
				chckbxGrayscaleFilter.setSelected(activeFiltersStore.isGrayscaleFilterActive());
			}
		});
	}
}