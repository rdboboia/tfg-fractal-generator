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
import javax.swing.JOptionPane;
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
	private JSpinner spinnerWidth;
	private JSpinner spinnerHeight;
	private JSpinner spinnerZoom;
	private JLabel lblTime;
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public ExportToFilePanel() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.setToolTipText("Volver al panel de selección de modo.");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		
		JLabel lblStatus = new JLabel("Estado:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblActualStatus = new JLabel("detenido.");
		lblActualStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblResolution = new JLabel("Resolución:");
		lblResolution.setToolTipText("Tamaño en píxeles de la imagen a exportar.");
		
		spinnerWidth = new JSpinner();
		spinnerWidth.setToolTipText("Anchura en píxeles.");
		spinnerWidth.setModel(new SpinnerNumberModel(3840, 1, null, 1));
		
		JLabel lblWidth = new JLabel("(ancho)");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		
		spinnerHeight = new JSpinner();
		spinnerHeight.setToolTipText("Altura en píxeles.");
		spinnerHeight.setModel(new SpinnerNumberModel(2160, 1, null, 1));
		
		JLabel lblHeight = new JLabel("(alto)");
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSpinner spinnerDepth = new JSpinner();
		spinnerDepth.setToolTipText("Máximo número de iteraciones por píxel. Valores más altos requerirán de mayor tiempo de generación.");
		spinnerDepth.setModel(new SpinnerNumberModel(360, 1, null, 1));
		
		JSpinner spinnerColorDepth = new JSpinner();
		spinnerColorDepth.setToolTipText("Define el número de colores y cuándo aparecen en realción a la profunidad.");
		spinnerColorDepth.setModel(new SpinnerNumberModel(360, 1, null, 1));
		
		JLabel lblFormat = new JLabel("Formato:");
		lblFormat.setToolTipText("Formato de la imagen a exportar. Se recomienda PNG ya que es un formato comprimido sin pérdida de calidad.");
		
		JComboBox<ImageFormat> comboBoxFormat = new JComboBox<>();
		comboBoxFormat.setToolTipText("Formato de la imagen a exportar. Se recomienda PNG ya que es un formato comprimido sin pérdida de calidad.");
		comboBoxFormat.setModel(new DefaultComboBoxModel<ImageFormat>(ImageFormat.values()));
		comboBoxFormat.setSelectedIndex(comboBoxFormat.getModel().getSize()-1);
		
		JLabel lblDepth = new JLabel("Profundidad:");
		lblDepth.setToolTipText("Máximo número de iteraciones por píxel. Valores más altos requerirán de mayor tiempo de generación.");
		
		JLabel lblProfundidadColor = new JLabel("Profundidad color:");
		lblProfundidadColor.setToolTipText("Define el número de colores y cuándo aparecen en realción a la profunidad.");
		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setToolTipText("Panel de propiedades. Define las características de la imagen a generar y exportar.");
		propertiesPanel.setBorder(new TitledBorder(null, "Propiedades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel positionPanel = new JPanel();
		positionPanel.setToolTipText("Panel de la posición del fractal. Define la posición y zoom a partir de los cuales se va a generar el fractal. Los valores máximos para la posición son de -2 a +2. Todos los campos pueden tener decimales.");
		positionPanel.setBorder(new TitledBorder(null, "Posici\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setToolTipText("Panel de filtros. Define que filtros se aplicarán una vez generado el fractal.");
		filtersPanel.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblYAxis = new JLabel("Eje x:");
		lblYAxis.setToolTipText("Posición en el eje X.");
		
		JSpinner spinnerXAxis = new JSpinner();
		spinnerXAxis.setToolTipText("Posición en el eje X.");
		spinnerXAxis.setModel(new SpinnerNumberModel(0d, null, null, 1d));
		
		JLabel lblXAxis = new JLabel("Eje y:");
		lblXAxis.setToolTipText("Posición en el eje Y.");
		
		JSpinner spinnerYAxis = new JSpinner();
		spinnerYAxis.setToolTipText("Posición en el eje Y.");
		spinnerYAxis.setModel(new SpinnerNumberModel(0d, null, null, 1d));
		
		JLabel lblZoom = new JLabel("Zoom:");
		lblZoom.setToolTipText("Nivel de zoom.");
		
		spinnerZoom = new JSpinner();
		spinnerZoom.setToolTipText("Nivel de zoom.");
		spinnerZoom.setModel(new SpinnerNumberModel(256d, 1d, null, 1d));
		
		JLabel lblScale = new JLabel("Escala:");
		lblScale.setToolTipText("Nivel de escala.");
		
		JSpinner spinnerScale = new JSpinner();
		spinnerScale.setToolTipText("Nivel de escala.");
		spinnerScale.setModel(new SpinnerNumberModel(1d, null, null, 1d));
		
		JCheckBox chckbxGrayscaleFilter = new JCheckBox("Convertir a escala de grises");
		chckbxGrayscaleFilter.setToolTipText("Habilitar/Deshabilitar el filtro de conversión a escala de grises.");
		
		JCheckBox chckbxNegativeFilter = new JCheckBox("Invertir los colores");
		chckbxNegativeFilter.setToolTipText("Habilitar/Deshabilitar el filtro de inversión de colores.");
		
		JButton btnDouble = new JButton("x2");
		btnDouble.setToolTipText("Duplicar el nivel actual de zoom.");
		btnDouble.addActionListener(e -> {
			spinnerZoom.setValue((double)spinnerZoom.getValue() * 2);
		});
		
		JButton btnHalf = new JButton("/2");
		btnHalf.setToolTipText("Reducir a la mitad el nivel actual de zoom.");
		btnHalf.addActionListener(e -> {
			spinnerZoom.setValue((double)spinnerZoom.getValue() / 2);
		});
		
		JButton btn720p = new JButton("1280 x 720 (720p)");
		btn720p.addActionListener(e -> {
			changeCurrentResolution(1280, 720);
		});
		
		JButton btn1080p = new JButton("1920 x 1080 (1080p)");
		btn1080p.addActionListener(e -> {
			changeCurrentResolution(1920, 1080);
		});
		
		JButton btn1440p = new JButton("2560 x 1440 (1440p)");
		btn1440p.addActionListener(e -> {
			changeCurrentResolution(2560, 1440);
		});
		
		JButton btn4k = new JButton("3840 x 2160 (4K)");
		btn4k.addActionListener(e -> {
			changeCurrentResolution(3840, 2160);
		});
		
		JButton btn8k = new JButton("7680 x 4320 (8K)");
		btn8k.addActionListener(e -> {
			changeCurrentResolution(7680, 4320);
		});
		
		JButton btn16k = new JButton("15360 x 8640 (16K)");
		btn16k.addActionListener(e -> {
			changeCurrentResolution(15360, 8640);
		});
		
		JButton btn32k = new JButton("30720 x 17280 (32K)");
		btn32k.addActionListener(e -> {
			changeCurrentResolution(30720, 17280);
		});
		
		JButton btnGenerateAndExport = new JButton("Generar y exportar");
		btnGenerateAndExport.setToolTipText("Generar y exportar usando los parámetros definidos. Primero se generará el fractal y posteriormente se pedirá la ruta donde guardarlo. Nota: resoluciones muy altas consumen mucha memoria RAM.");
		btnGenerateAndExport.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGenerateAndExport.addActionListener(e -> {
			new Thread() {
				@Override
				public void run() {
					try {
						btnGenerateAndExport.setEnabled(false);
						
						long s;
						
						s = System.currentTimeMillis();
						lblActualStatus.setText("creando imagen...");
						BufferedImage image = new BufferedImage((int) spinnerWidth.getValue(), (int) spinnerHeight.getValue(), BufferedImageType.getBufferedImageType());
						lblTime.setText("(creación de imagen: " + (System.currentTimeMillis() - s) + " ms).");
						
						s = System.currentTimeMillis();
						lblActualStatus.setText("generando fractal...");
						MandelbrotsetPosition position = new MandelbrotsetPosition();
						position.setPosx((double) spinnerXAxis.getValue());
						position.setPosy((double) spinnerYAxis.getValue());
						position.setZoom((double) spinnerZoom.getValue());
						position.setScale((double) spinnerScale.getValue());
						MandelbrotsetGeneratorThreadManager.generate(image, (int) spinnerDepth.getValue(), (int) spinnerColorDepth.getValue(), position);

						lblActualStatus.setText("aplicando filtros...");
						if (chckbxNegativeFilter.isSelected())
							ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
						if (chckbxGrayscaleFilter.isSelected())
							ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
						lblTime.setText("(generación fractal y aplicación filtros: " + (System.currentTimeMillis() - s) + " ms).");
						
						lblActualStatus.setText("exportación de archivo...");
						s = FileSaver.saveFile(image, (ImageFormat) comboBoxFormat.getSelectedItem());
						lblTime.setText("(exportación de archivo: " + s + " ms).");
						
						// Frees up memory. Useful specially when exporting to very high resolution images.
						image = null;
						Runtime.getRuntime().gc();
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(null, "La resolución introducida es demasiado grande.", "Resolución no válida", JOptionPane.ERROR_MESSAGE);
					} catch (OutOfMemoryError e) {
						JOptionPane.showMessageDialog(null, "No hay suficiente memoria para generar el fractal con la resolución indicada.", "Memoria insuficiente", JOptionPane.ERROR_MESSAGE);
					} finally {
						lblActualStatus.setText("finalizado.");
						btnGenerateAndExport.setEnabled(true);
					}
				}
			}.start();
		});
		
		JPanel resolutionPresetsPanel = new JPanel();
		resolutionPresetsPanel.setToolTipText("Panel de resoluciones predefinidas. Se incluyen algunas resoluciones comunes y algunas adicionales de muy alta resolución. Estos botones ajustan automáticamente el nivel de zoom en función de la resolución seleccionada para que el fractal exportado sea el que se está visualizando desde el panel de visualización en tiempo real.");
		resolutionPresetsPanel.setBorder(new TitledBorder(null, "Resoluciones predefinidas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblTime = new JLabel("(0 ms).");
		lblTime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnGenerateAndExport, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(resolutionPresetsPanel, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(filtersPanel, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE))
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblActualStatus, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
							.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resolutionPresetsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnGenerateAndExport, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus)
						.addComponent(lblActualStatus)
						.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		GroupLayout gl_resolutionPresetsPanel = new GroupLayout(resolutionPresetsPanel);
		gl_resolutionPresetsPanel.setHorizontalGroup(
			gl_resolutionPresetsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_resolutionPresetsPanel.createSequentialGroup()
					.addGap(174)
					.addComponent(btn720p, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn1080p, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn1440p, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn4k, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn8k, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn16k, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn32k, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(161))
		);
		gl_resolutionPresetsPanel.setVerticalGroup(
			gl_resolutionPresetsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_resolutionPresetsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_resolutionPresetsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn1080p)
						.addComponent(btn1440p)
						.addComponent(btn4k)
						.addComponent(btn8k)
						.addComponent(btn16k)
						.addComponent(btn32k)
						.addComponent(btn720p))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		resolutionPresetsPanel.setLayout(gl_resolutionPresetsPanel);
		
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
						.addComponent(lblScale)
						.addComponent(lblXAxis)
						.addComponent(lblYAxis))
					.addGap(31)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(spinnerXAxis, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
						.addComponent(spinnerYAxis, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
						.addComponent(spinnerScale, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
						.addGroup(gl_positionPanel.createSequentialGroup()
							.addComponent(btnDouble)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnHalf)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerZoom, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_positionPanel.setVerticalGroup(
			gl_positionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_positionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYAxis)
						.addComponent(spinnerXAxis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXAxis)
						.addComponent(spinnerYAxis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZoom)
						.addComponent(spinnerZoom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDouble)
						.addComponent(btnHalf))
					.addGap(18)
					.addGroup(gl_positionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblScale)
						.addComponent(spinnerScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		positionPanel.setLayout(gl_positionPanel);
		
		GroupLayout gl_propertiesPanel = new GroupLayout(propertiesPanel);
		gl_propertiesPanel.setHorizontalGroup(
			gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_propertiesPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFormat)
						.addGroup(gl_propertiesPanel.createSequentialGroup()
							.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblResolution)
								.addComponent(lblDepth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblProfundidadColor))
							.addGap(18)
							.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBoxFormat, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(spinnerColorDepth)
								.addComponent(spinnerDepth)
								.addGroup(gl_propertiesPanel.createSequentialGroup()
									.addComponent(spinnerWidth, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblWidth)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblHeight)))))
					.addContainerGap(300, Short.MAX_VALUE))
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
						.addComponent(lblProfundidadColor)
						.addComponent(spinnerColorDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFormat)
						.addComponent(comboBoxFormat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(29, Short.MAX_VALUE))
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
	
	private void changeCurrentResolution(int width, int height) {
		spinnerZoom.setValue((double)spinnerZoom.getValue() * getResolutionMultiplier((int)spinnerWidth.getValue(), width));
		spinnerWidth.setValue(width);
		spinnerHeight.setValue(height);
	}
	
	private double getResolutionMultiplier(int oldWidth, int newWidth) {
		return Double.valueOf(newWidth) / Double.valueOf(oldWidth);
	}
}