package tfg.fractalgenerator.gui.panels;

import javax.swing.JPanel;

import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.gui.FileSaver;
import tfg.fractalgenerator.gui.MandelbrotSetGUI;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetGenerator;

import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;

import java.awt.event.ActionListener;

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
public class RealtimeViewPanel extends JPanel {
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
	
	/**
	 * Static reference to the name of this class which is used as the name
	 * of the component. The name is based on the {@code Class}'s name. Useful
	 * for the card layout since it makes a lot easier to have access to a
	 * component's name. 
	 */
	public static final String NAME = RealtimeViewPanel.class.getSimpleName();
	
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
	 * Initialization of the Panel and it's layout.
	 * It also contains the {@link ActionListener} for each button.
	 */
	public RealtimeViewPanel() {
		this.setSize(MandelbrotSetGUI.size);
		this.setName(NAME);
		
		lblImageContainer = new JLabel("");
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(e -> MandelbrotSetGUI.getInstance().changeCard(ModeSelectionPanel.NAME));
		btnGenerate = new JButton("Generar");
		btnGenerate.addActionListener(e -> {
			btnGenerate.setEnabled(false);
			actualizarFractal();
		});
		btnExport = new JButton("Exportar");
		btnExport.setEnabled(false);
		btnExport.addActionListener(e -> FileSaver.saveFile(image, ImageFormat.PNG));
		
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
					.addContainerGap(956, Short.MAX_VALUE))
				.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReturn)
						.addComponent(btnGenerate)
						.addComponent(btnExport))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImageContainer, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		
	}
	
	/**
	 * This method takes care of the creation of the needed threads for the
	 * generation and real time display of the Mandelbrot's Set. It disables
	 * the "Generate" button until the process is completed so no more than
	 * the intended {@code Threads} are created.
	 */
	private void actualizarFractal() {
		lblImageContainer.setSize(this.getWidth(), this.getHeight()-20);
		
		Thread generatorThread = new Thread() {
			@Override
			public void run() {
				image = new BufferedImage(lblImageContainer.getWidth(), lblImageContainer.getHeight(), BufferedImage.TYPE_INT_RGB);
				MandelbrotsetGenerator.generate(image, 360);
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
