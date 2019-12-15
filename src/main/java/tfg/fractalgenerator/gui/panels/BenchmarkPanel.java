package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.BufferedImageType;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGeneratorThreadManager;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetPosition;
import tfg.imageprocessor.ImageProcessorManager;
import tfg.imageprocessor.ProcessingMode;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

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
public class BenchmarkPanel extends JPanel {
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
	public static final String NAME = BenchmarkPanel.class.getSimpleName();
	
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
	private JButton btnStart;
	
	/**
	 * A simple text container to inform the user of the application's current status.
	 */
	private JLabel lblCurrentStatus;
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public BenchmarkPanel() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		this.position = new MandelbrotsetPosition();
		JLabel lblStatus = new JLabel("Estado:");
		lblStatus.setToolTipText("Estado actual del programa.");
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.setToolTipText("Volver al panel de selección de modo.");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		
		lblCurrentStatus = new JLabel("finalizado.");
		lblCurrentStatus.setToolTipText("Estado actual del programa.");
		
		lblImageContainer = new JLabel("");
		
		btnStart = new JButton("Iniciar");
		btnStart.setToolTipText("Generar nuevamente el fractal.");
		btnStart.addActionListener(e -> {
			if (btnStart.isEnabled())
				updateImage();
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(118)
							.addComponent(btnStart))
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addGap(99)
					.addComponent(lblStatus)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCurrentStatus)
					.addContainerGap(900, Short.MAX_VALUE))
				.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReturn)
						.addComponent(btnStart)
						.addComponent(lblStatus)
						.addComponent(lblCurrentStatus))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	
	/**
	 * Sets the {@code setEnabled} attribute to the given one. Useful for enabling
	 * and disabling controls.
	 * @param setEnabled the state to be set (enabled: true; disabled: false).
	 */
	private void changeControlsState(boolean setEnabled) {
		btnStart.setEnabled(setEnabled);
	}
	
	/**
	 * This method applies the current selected filters to the generated image.
	 * It first applies, if needed, the negative filter and then, if needed, the
	 * grayscale filter.
	 */
	private void applyFilters() {
		long s, f;
		
		s = System.currentTimeMillis(); // Start time
		ImageProcessorManager.process(image, ProcessingMode.COLOR_INVERSION);
		f = System.currentTimeMillis(); // Finish time
		System.out.println("Filtro inversión: " + (f - s) + "ms.");
		
		s = System.currentTimeMillis(); // Start time
		ImageProcessorManager.process(image, ProcessingMode.GRAYSCALE);
		f = System.currentTimeMillis(); // Finish time
		System.out.println("Filtro escala grises: " + (f - s) + "ms.");
	}
	
	/**
	 * This method takes care of the creation of the needed threads for the
	 * generation and real time display of the Mandelbrot's Set. It disables
	 * the "Generate" button until the process is completed so no more than
	 * the intended {@code Threads} are created.
	 */
	private void updateImage() {
		if (btnStart.isEnabled()) {
			changeControlsState(false);
			
			lblCurrentStatus.setText("generando...");
			
			// Updating image size
			lblImageContainer.setSize(1910, 987);
			
			int scaleFactor = 16;
			
			image = new BufferedImage(lblImageContainer.getWidth()*scaleFactor, lblImageContainer.getHeight()*scaleFactor, BufferedImageType.getBufferedImageType());
			
			// Generator thread
			Thread generatorThread = new Thread() {
				@Override
				public void run() {
					long s, f;
					long sTotal, fTotal;
					
					position = new MandelbrotsetPosition();
					position.setZoom(position.getZoom() * scaleFactor);
					
					s = System.currentTimeMillis(); // Start time
					sTotal = s;
					MandelbrotsetGeneratorThreadManager.generate(image, 3600, 360, position, Runtime.getRuntime().availableProcessors());
					f = System.currentTimeMillis(); // Finish time
					System.out.println("Generación: " + (f - s) + "ms.");
					
					// Apply the currently selected image filters.
					applyFilters();
					
					fTotal = System.currentTimeMillis(); // Finish time
					
					lblCurrentStatus.setText("finalizado (" + (fTotal - sTotal) + " ms).");
					System.out.println("Total: " + (fTotal - sTotal) + "ms.");
					
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
							sleep(1000);
						} catch (InterruptedException e) {
							interrupt();
						}
						
						BufferedImage after = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImageType.getBufferedImageType());
						AffineTransform at = new AffineTransform();
						at.scale(1d/scaleFactor, 1d/scaleFactor);
						AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
						after = scaleOp.filter(image, after);
						
						lblImageContainer.setIcon(new ImageIcon(after));
						
						after = null;
						System.gc();
						
//						lblImageContainer.setIcon(new ImageIcon(image));
					}
					
					image = null;
					System.gc();
				}
			};
			updateImageThread.start();
		}
	}
}
