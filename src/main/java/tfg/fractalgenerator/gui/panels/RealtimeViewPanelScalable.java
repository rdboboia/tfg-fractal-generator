package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGeneratorScalable;

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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JToggleButton;
import java.awt.event.ActionEvent;

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
	private JLabel lblImageCointainer;
	
	/**
	 * Button for starting the generation process.
	 */
	private JButton btnGenerar;
	/**
	 * Button for exporting the image in it's current state to a file which
	 * will display a file selector window to select the path and filename.
	 */
	private JButton btnExportar;
	private JSpinner spinner;
	private JToggleButton tglbtnVariabledepthcolor;
	
	/**
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public RealtimeViewPanelScalable() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		lblImageCointainer = new JLabel("");
		lblImageCointainer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (btnGenerar.isEnabled()) {
					double centerx = lblImageCointainer.getWidth() / 2d;
					double centery = lblImageCointainer.getHeight() / 2d;
					
					posx += (e.getX() - centerx) / zoom;
					posy += (e.getY() - centery) / zoom;
					
					actualizarFractal();
				}
			}
		});
		lblImageCointainer.addMouseWheelListener(e -> {
			if (btnGenerar.isEnabled()) {
				if (e.getWheelRotation() > 0)
					zoomOut();
				else {
					double centerx = lblImageCointainer.getWidth() / 2d;
					double centery = lblImageCointainer.getHeight() / 2d;
					
					posx += (e.getX() - centerx) / (zoom*2);
					posy += (e.getY() - centery) / (zoom*2);
					
					zoomIn();
				}
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(e -> actualizarFractal());
		btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		btnExportar.addActionListener(e -> saveFile());
		
		JButton buttonZoomIn = new JButton("+");
		buttonZoomIn.addActionListener(e -> zoomIn());
		
		JButton buttonZoomOut = new JButton("-");
		buttonZoomOut.addActionListener(e -> zoomOut());
		
		JButton btnRestablecer = new JButton("Restablecer");
		btnRestablecer.addActionListener(e -> restoreStatus());
		
		JLabel lblProfunidad = new JLabel("Profunidad:");
		
		spinner = new JSpinner();
		spinner.addChangeListener(e -> {
			if (btnGenerar.isEnabled()) {
				actualizarFractal();
			}
		});
		spinner.setModel(new SpinnerNumberModel(360, 1, null, 32));
		
		tglbtnVariabledepthcolor = new JToggleButton("VariableDepthColor");
		tglbtnVariabledepthcolor.addActionListener(e -> actualizarFractal());
		
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
					.addGap(288)
					.addComponent(lblProfunidad)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
					.addComponent(tglbtnVariabledepthcolor)
					.addGap(109)
					.addComponent(btnRestablecer, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(buttonZoomOut)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonZoomIn))
				.addComponent(lblImageCointainer, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnVolver)
							.addComponent(btnGenerar)
							.addComponent(btnExportar)
							.addComponent(buttonZoomIn)
							.addComponent(buttonZoomOut)
							.addComponent(btnRestablecer)
							.addComponent(tglbtnVariabledepthcolor))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblProfunidad)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImageCointainer, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		actualizarFractal();
		
		addComponentListener(new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			if (btnGenerar != null && btnGenerar.isEnabled())
				actualizarFractal();
		}
	});
	}
	
	private void restoreStatus() {
		posx = 0;
		posy = 0;
		zoom = 0.5;
		spinner.setValue(360);
		actualizarFractal();
	}

	private void zoomIn() {
		if (btnGenerar.isEnabled()) {
			zoom *= 2;
			actualizarFractal();
		}
	}
	
	private void zoomOut() {
		if (btnGenerar.isEnabled() && zoom > 0.005) {
			zoom /= 2;
			actualizarFractal();
		}
	}

	/**
	 * Somewhat complex function (too many branches) to take care of the file
	 * saving process. It displays a file selector dialog, then it checks if
	 * the user selected a file by checking if the file returned is {@code null}.
	 * It checks if the extension is already included in the file name and
	 * removes it if necessary. If a file with the same name already exists it
	 * prompts the user for override confirmation.
	 */
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
		
		if (btnGenerar.isEnabled()) {
			btnGenerar.setEnabled(false);
			lblImageCointainer.setSize(this.getWidth(), this.getHeight()-20);
			
			Thread generatorThread = new Thread() {
				@Override
				public void run() {
					image = new BufferedImage(lblImageCointainer.getWidth(), lblImageCointainer.getHeight(), BufferedImage.TYPE_INT_RGB);
					MandelbrotsetGeneratorScalable generator;
					if (tglbtnVariabledepthcolor.isSelected())
						generator = new MandelbrotsetGeneratorScalable(image, (int)spinner.getValue());
					else
						generator = new MandelbrotsetGeneratorScalable(image, (int)spinner.getValue(), 360);
					
					generator.generate(posx, posy, zoom, scale);
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
						lblImageCointainer.setIcon(new ImageIcon(image));
					}
				}
			};
			updateImageThread.start();
		}
	}
}
