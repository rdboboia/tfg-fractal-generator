package tfg.fractalgenerator.gui.panels.pruebas;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.BufferedImageType;
import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.gui.FileSaver;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.gui.panels.ModeSelectionPanel;
import tfg.fractalgenerator.gui.panels.RealtimeViewPanelScalable;
import tfg.fractalgenerator.mandelbrotset.pruebas.MandelbrotsetGeneratorScalable;

import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JToggleButton;

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
public class RealtimeViewPanelScalable_OLD extends JPanel {
	/**
	 * Serial version number. Using the default one.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The image where the Mandelbrot's Set will be generated which is the same
	 * image that can be exported later, as well as the image used as a
	 * {@link ImageIcon} for the current state display.
	 */
	private transient BufferedImage image;
	
	private double posx = 0;
	private double posy = 0;
	private double zoom = 0.5;
	private double scale = 4d / 1280;
	
	/**
	 * Static reference to the name of this class which is used as the name
	 * of the component. The name is based on the {@code Class}'s name. Useful
	 * for the card layout since it makes a lot easier to have access to a
	 * component's name. 
	 */
	public static final String NAME = RealtimeViewPanelScalable.class.getSimpleName();
	
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
	private JSpinner spinnerDepth;
	private JToggleButton tglbtnVariabledepthcolor;
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public RealtimeViewPanelScalable_OLD() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		lblImageContainer = new JLabel("");
		lblImageContainer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (btnGenerate.isEnabled()) {
					double centerx = lblImageContainer.getWidth() / 2d;
					double centery = lblImageContainer.getHeight() / 2d;
					
					posx += (e.getX() - centerx) / zoom;
					posy += (e.getY() - centery) / zoom;
					
					actualizarFractal();
				}
			}
		});
		lblImageContainer.addMouseWheelListener(e -> {
			if (btnGenerate.isEnabled()) {
				if (e.getWheelRotation() > 0)
					zoomOut();
				else {
					double centerx = lblImageContainer.getWidth() / 2d;
					double centery = lblImageContainer.getHeight() / 2d;
					
					posx += (e.getX() - centerx) / (zoom*2);
					posy += (e.getY() - centery) / (zoom*2);
					
					zoomIn();
				}
			}
		});
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		btnGenerate = new JButton("Generar");
		btnGenerate.addActionListener(e -> actualizarFractal());
		btnExport = new JButton("Exportar");
		btnExport.setEnabled(false);
		btnExport.addActionListener(e -> FileSaver.saveFile(image, ImageFormat.PNG));
		
		JButton btnZoomIn = new JButton("+");
		btnZoomIn.addActionListener(e -> zoomIn());
		
		JButton btnZoomOut = new JButton("-");
		btnZoomOut.addActionListener(e -> zoomOut());
		
		JButton btnRestoreDefaults = new JButton("Restablecer");
		btnRestoreDefaults.addActionListener(e -> restoreStatus());
		
		JLabel lblDepth = new JLabel("Profunidad:");
		
		spinnerDepth = new JSpinner();
		spinnerDepth.addChangeListener(e -> {
			if (btnGenerate.isEnabled()) {
				actualizarFractal();
			}
		});
		spinnerDepth.setModel(new SpinnerNumberModel(360, 1, null, 32));
		
		tglbtnVariabledepthcolor = new JToggleButton("VariableDepthColor");
		tglbtnVariabledepthcolor.addActionListener(e -> actualizarFractal());
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(118)
							.addComponent(btnGenerate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExport, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addGap(288)
					.addComponent(lblDepth)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
					.addComponent(tglbtnVariabledepthcolor)
					.addGap(109)
					.addComponent(btnRestoreDefaults, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnZoomOut)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnZoomIn))
				.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnReturn)
							.addComponent(btnGenerate)
							.addComponent(btnExport)
							.addComponent(btnZoomIn)
							.addComponent(btnZoomOut)
							.addComponent(btnRestoreDefaults)
							.addComponent(tglbtnVariabledepthcolor))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDepth)
								.addComponent(spinnerDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		actualizarFractal();
		
		addComponentListener(new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			if (btnGenerate != null && btnGenerate.isEnabled())
				actualizarFractal();
		}
	});
	}
	
	private void restoreStatus() {
		posx = 0;
		posy = 0;
		zoom = 0.5;
		spinnerDepth.setValue(360);
		actualizarFractal();
	}

	private void zoomIn() {
		if (btnGenerate.isEnabled()) {
			zoom *= 2;
			actualizarFractal();
		}
	}
	
	private void zoomOut() {
		if (btnGenerate.isEnabled() && zoom > 0.005) {
			zoom /= 2;
			actualizarFractal();
		}
	}
	
	/**
	 * This method takes care of the creation of the needed threads for the
	 * generation and real time display of the Mandelbrot's Set. It disables
	 * the "Generate" button until the process is completed so no more than
	 * the intended {@code Threads} are created.
	 */
	private void actualizarFractal() {
		System.out.println("Zoom: " + zoom);
		System.out.println("X: " + posx);
		System.out.println("Y: " + posy);
		
		if (btnGenerate.isEnabled()) {
			btnGenerate.setEnabled(false);
			lblImageContainer.setSize(this.getWidth(), this.getHeight()-20);
			
			Thread generatorThread = new Thread() {
				@Override
				public void run() {
					image = new BufferedImage(lblImageContainer.getWidth(), lblImageContainer.getHeight(), BufferedImageType.getBufferedImageType());
					MandelbrotsetGeneratorScalable generator;
					if (tglbtnVariabledepthcolor.isSelected())
						generator = new MandelbrotsetGeneratorScalable(image, (int)spinnerDepth.getValue());
					else
						generator = new MandelbrotsetGeneratorScalable(image, (int)spinnerDepth.getValue(), 360);
					
					generator.generate(posx, posy, zoom, scale);
					btnGenerate.setEnabled(true);
					btnExport.setEnabled(true);
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
						lblImageContainer.setIcon(new ImageIcon(image));
					}
				}
			};
			updateImageThread.start();
		}
	}
}
