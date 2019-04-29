package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.gui.panels.store.ActiveFiltersStore;
import tfg.fractalgenerator.gui.panels.store.GenerationParametersStore;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGeneratorThreadManager;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetPosition;
import tfg.imageprocessor.ImageProcessorManager;
import tfg.imageprocessor.ProcessingMode;

import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JCheckBox;

/**
 * This view takes care of the Mandelbrot's Set generation and it's real time
 * display making use of {@code Threads}. Three total {@code Threads} are used:
 * the one already existing in {@code Swing}, a second one for the generation
 * and a third one for updating the view with the current state of the image.
 * <br>
 * It has three buttons: one for returning back to the previous view, one for
 * starting the generation and one for exporting the current image in its
 * current state (even unfinished), but this last button stays disabled until
 * at least one generation has completed in order to avoid {@code null} writes
 * to a file.
 * <br>
 * It can be resized and the fractal, once regenerated, will be resized
 * accordingly. This gives a very flexible layout.
 * 
 * @author -$BOSS$-
 */
public class RealtimeViewPanelScalable extends JPanel {
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
	public static final String NAME = RealtimeViewPanelScalable.class.getSimpleName();
	
	/**
	 * The image where the Mandelbrot's Set will be generated which is the same
	 * image that can be exported later, as well as the image used as a
	 * {@link ImageIcon} for the current state display.
	 */
	private transient BufferedImage image;
	
	/**
	 * Contains the current position which is defined by the x and y coordinates,
	 * the zoom and the scale. See {@link MandelbrotsetPosition}.
	 */
	private MandelbrotsetPosition position;
	
	/**
	 * A {@link JLabel} is used as a container {@link Component} to display
	 * the image on the {@link JPanel} view.
	 */
	private JLabel lblImageContainer;
	
	/**
	 * Button for starting the generation process.
	 */
	private JButton btnGenerate;
	
	/**
	 * Button for exporting the image in it's current state to a file which
	 * will display a file selector window to select the path and filename.
	 */
	private JButton btnExport;
	
	/**
	 * Button for restoring the default generation parameters, including depth,
	 * color depth and position.
	 */
	private JButton btnRestoreDefaults;
	
	/**
	 * Button for zooming out (performs a x2 zoom outwards).
	 */
	private JButton btnZoomOut;
	
	/**
	 * Button for zooming in (performs a x2 zoom inwards).
	 */
	private JButton btnZoomIn;
	
	/**
	 * Sets the generation depth (maximum number of iterations per pixel).
	 */
	private JSpinner spinnerDepth;
	
	/**
	 * Sets the generation color depth which can be seen as the number of colors.
	 */
	private JSpinner spinnerColorDepth;
	
	/**
	 * A simple text container to inform the user of the application's current status.
	 */
	private JLabel lblCurrentStatus;
	
	/**
	 * A simple text container.
	 */
	private JLabel lblScale;
	
	/**
	 * Sets the generation scale.
	 */
	private JSpinner spinnerScale;
	
	/**
	 * Toggle for the negative color image filter.
	 */
	private JCheckBox chckbxNegativeFilter;
	
	/**
	 * Toggle for the grayscale image filter.
	 */
	private JCheckBox chckbxGrayscaleFilter;
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public RealtimeViewPanelScalable() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		this.position = new MandelbrotsetPosition();
		
		JLabel lblDepth = new JLabel("Profunidad:");
		JLabel lblColorDepth = new JLabel("Profundidad color:");
		lblColorDepth.setToolTipText("algo");
		JLabel lblStatus = new JLabel("Estado:");
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		
		lblCurrentStatus = new JLabel("finalizado.");
		
		lblImageContainer = new JLabel("");
		lblImageContainer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (btnGenerate.isEnabled()) {
					changeCurrentPosition(e.getPoint());
					updateImage();
				}
			}
		});
		lblImageContainer.addMouseWheelListener(e -> {
			if (btnGenerate.isEnabled()) {
				if (e.getWheelRotation() > 0) {
					zoomOut();
					updateImage();
				} else {
					zoomIn();
					changeCurrentPosition(e.getPoint());
					updateImage();
				}
			}
		});
		
		btnGenerate = new JButton("Generar");
		btnGenerate.addActionListener(e -> {
			if (btnGenerate.isEnabled())
				updateImage();
		});
		
		btnExport = new JButton("Exportar");
		btnExport.setEnabled(false);
		btnExport.addActionListener(e -> {
			GenerationParametersStore genParamStore = GenerationParametersStore.getInstance();
			genParamStore.setWidth(lblImageContainer.getWidth());
			genParamStore.setHeight(lblImageContainer.getHeight());
			genParamStore.setDepth((int)spinnerDepth.getValue());
			genParamStore.setColorDepth((int)spinnerColorDepth.getValue());
			genParamStore.setPosition(position);
			
			ActiveFiltersStore activeFiltersStore = ActiveFiltersStore.getInstance();
			activeFiltersStore.setNegativeFilterActive(chckbxNegativeFilter.isSelected());
			activeFiltersStore.setGrayscaleFilterActive(chckbxGrayscaleFilter.isSelected());
			
			MandelbrotSetGUI.getInstance().changeCard(ExportToFilePanel.NAME);
		});
		
		btnZoomIn = new JButton("+");
		btnZoomIn.addActionListener(e -> {
			if (btnGenerate.isEnabled()) {
				zoomIn();
				updateImage();
			}
		});
		
		btnZoomOut = new JButton("-");
		btnZoomOut.addActionListener(e -> {
			if (btnGenerate.isEnabled()) {
				zoomOut();
				updateImage();
			}
		});
		
		btnRestoreDefaults = new JButton("Restablecer");
		btnRestoreDefaults.addActionListener(e -> {
			restoreDefaultParameters();
			updateImage();
		});
		
		spinnerDepth = new JSpinner();
		spinnerDepth.setModel(new SpinnerNumberModel(360, 1, null, 32));
		spinnerDepth.addChangeListener(e -> {
			if (btnGenerate.isEnabled()) {
				updateImage();
			}
		});
		
		spinnerColorDepth = new JSpinner();
		spinnerColorDepth.setModel(new SpinnerNumberModel(360, 1, null, 32));
		spinnerColorDepth.addChangeListener(e -> {
			if (btnGenerate.isEnabled()) {
				updateImage();
			}
		});
		
		spinnerScale = new JSpinner();
		spinnerScale.setModel(new SpinnerNumberModel(1d, null, null, 1d));
		spinnerScale.addChangeListener(e -> {
			if (btnGenerate.isEnabled()) {
				position.setScale((double) spinnerScale.getValue());
				updateImage();
			}
		});
		
		lblScale = new JLabel("Escala:");
		
		chckbxNegativeFilter = new JCheckBox("Invertir colores");
		chckbxNegativeFilter.addActionListener(e -> {
			if (btnGenerate.isEnabled()) {
				toggleNegativeFilter();
			}
		});
		
		chckbxGrayscaleFilter = new JCheckBox("Escala grises");
		chckbxGrayscaleFilter.addActionListener(e -> {
			if (btnGenerate.isEnabled()) {
				toggleGrayscaleFilter();
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(118)
							.addComponent(btnGenerate))
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExport)
					.addGap(18)
					.addComponent(lblStatus)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCurrentStatus)
					.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
					.addComponent(chckbxGrayscaleFilter)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxNegativeFilter)
					.addGap(18)
					.addComponent(lblScale)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerScale, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblDepth)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblColorDepth)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerColorDepth, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnRestoreDefaults)
					.addGap(18)
					.addComponent(btnZoomOut)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnZoomIn))
				.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReturn)
						.addComponent(btnGenerate)
						.addComponent(btnZoomIn)
						.addComponent(btnZoomOut)
						.addComponent(btnRestoreDefaults)
						.addComponent(btnExport)
						.addComponent(lblColorDepth)
						.addComponent(spinnerColorDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDepth)
						.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinnerScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblScale)
						.addComponent(chckbxNegativeFilter)
						.addComponent(chckbxGrayscaleFilter)
						.addComponent(lblStatus)
						.addComponent(lblCurrentStatus))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (btnGenerate != null && btnGenerate.isEnabled()) {
					updateImage();
				}
			}
		});
		
		updateImage();
	}
	
	/**
	 * Computes the current horizontal center of the image.
	 * @return the current horizontal center as a double value.
	 */
	private double getHorizontalCenter() {
		return lblImageContainer.getWidth() / 2d;
	}
	
	/**
	 * Computes the current vertical center of the image.
	 * @return the current vertical center as a double value.
	 */
	private double getVerticalCenter() {
		return lblImageContainer.getHeight() / 2d;
	}
	
	/**
	 * Restores the initial values for the position, depth and color depth.
	 */
	private void restoreDefaultParameters() {
		position = new MandelbrotsetPosition();
		spinnerDepth.setValue(360);
		spinnerColorDepth.setValue(360);
		chckbxNegativeFilter.setSelected(false);
		chckbxGrayscaleFilter.setSelected(false);
	}
	
	/**
	 * Performs a x2 inwards zoom.
	 */
	private void zoomIn() {
		position.setZoom(position.getZoom() * 2);
	}
	
	/**
	 * Performs a x2 outwards zoom.
	 */
	private void zoomOut() {
		position.setZoom(position.getZoom() / 2);
	}
	
	/**
	 * Changes the current position so that the given p position is now in the
	 * center of the image.
	 * @param p the point to be centered.
	 */
	private void changeCurrentPosition(Point p) {
		position.setPosx(position.getPosx() + (p.getX() - getHorizontalCenter()) / position.getZoom());
		position.setPosy(position.getPosy() + (p.getY() - getVerticalCenter()) / position.getZoom());
	}
	
	/**
	 * Sets the {@code setEnabled} attribute to the given one. Useful for enabling
	 * and disabling controls.
	 * @param setEnabled the state to be set (enabled: true; disabled: false).
	 */
	private void changeControlsState(boolean setEnabled) {
		btnGenerate.setEnabled(setEnabled);
		btnRestoreDefaults.setEnabled(setEnabled);
		btnZoomIn.setEnabled(setEnabled);
		btnZoomOut.setEnabled(setEnabled);
		
		spinnerDepth.setEnabled(setEnabled);
		spinnerColorDepth.setEnabled(setEnabled);
		spinnerScale.setEnabled(setEnabled);
	}
	
	/**
	 * This method applies the current selected filters to the generated image.
	 * It first applies, if needed, the negative filter and then, if needed, the
	 * grayscale filter.
	 */
	private void applyFilters() {
		if (chckbxNegativeFilter.isSelected())
			ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
		if (chckbxGrayscaleFilter.isSelected())
			ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
		
		lblImageContainer.setIcon(new ImageIcon(image));
	}
	
	/**
	 * This method applies the negative filter to the current image and updates
	 * the view panel. It can be used to toggle the negative filter on and off
	 * without regenerating the fractal.
	 */
	private void toggleNegativeFilter() {
		ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
		lblImageContainer.setIcon(new ImageIcon(image));
	}
	
	/**
	 * This method applies the grayscale filter to the current image and updates
	 * the view panel. When the filter is turned on there's no need to generate
	 * again the fractal. However, when disabling this filter, a regeneration is
	 * needed since the grayscale filter looses color information.
	 */
	private void toggleGrayscaleFilter() {
		if (chckbxGrayscaleFilter.isSelected()) {
			ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
			lblImageContainer.setIcon(new ImageIcon(image));
		} else {
			updateImage();
		}
	}
	
	/**
	 * This method takes care of the creation of the needed threads for the
	 * generation and real time display of the Mandelbrot's Set. It disables
	 * the "Generate" button until the process is completed so no more than
	 * the intended {@code Threads} are created.
	 */
	private void updateImage() {
		if (btnGenerate.isEnabled()) {
			changeControlsState(false);
			
			lblCurrentStatus.setText("generando...");
			
			// Updating image size
			lblImageContainer.setSize(this.getWidth(), this.getHeight()-20);
			
			image = new BufferedImage(lblImageContainer.getWidth(), lblImageContainer.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			
			// Generator thread
			Thread generatorThread = new Thread() {
				@Override
				public void run() {
					long s = System.currentTimeMillis(); // Start time
					
					MandelbrotsetGeneratorThreadManager.generate(image, (int)spinnerDepth.getValue(), (int)spinnerColorDepth.getValue(), position);
					
					// Apply the currently selected image filters.
					applyFilters();
					
					long f = System.currentTimeMillis(); // Finish time
					
					lblCurrentStatus.setText("finalizado (" + (f - s) + " ms).");
					btnExport.setEnabled(true);
					
					changeControlsState(true);
				}
			};
			generatorThread.start();
			
			// Image updater thread
			Thread updateImageThread = new Thread() {
				@Override
				public void run() {
					while (generatorThread.isAlive()) {
						try {
							sleep(10);
						} catch (InterruptedException e) {
							interrupt();
						}
						
						lblImageContainer.setIcon(new ImageIcon(image));
					}
				}
			};
			updateImageThread.start();
		}
	}
}
