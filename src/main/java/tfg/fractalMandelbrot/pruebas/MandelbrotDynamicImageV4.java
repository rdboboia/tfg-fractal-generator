package tfg.fractalMandelbrot.pruebas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tfg.fractalgenerator.exportimage.BufferedImageType;

import javax.swing.JLabel;

public class MandelbrotDynamicImageV4 extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotDynamicImageV4 frame = new MandelbrotDynamicImageV4();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MandelbrotDynamicImageV4() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1800, 1060);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(lblNewLabel);

		new Thread() {
			public void run() {
				long s,f;
				s = System.currentTimeMillis();
				int width = lblNewLabel.getWidth();
				int height = lblNewLabel.getHeight();
				int max = 256;
//				int max_color = 256;
				int white = 256*256*256 - 1;

				BufferedImage img = new BufferedImage(width, height, BufferedImageType.getBufferedImageType());

				double x0, y0, x, y, xtemp, ytemp;
				int iteration;

				double halfWidth = width / 2.0;
				double halfHeight = height / 2.0;
				double scale = 4.0 / width;

				int lowerMax = 0;
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						img.setRGB(j, i, white);
					}
					for (int j = 0; j < width; j++) {
						x0 = (j - halfWidth) * scale;
						y0 = (i - halfHeight) * scale;
						x = 0;
						y = 0;
						iteration = 0;

						while (x * x + y * y <= 4 && iteration < max) {
							xtemp = x * x - y * y + x0;
							ytemp = 2 * x * y + y0;
							
							if (x == xtemp && y == ytemp) {
								iteration = max;
							} else {
								y = 2 * x * y + y0;
								x = xtemp;
								iteration++;
							}
						}
						if (iteration < max) {
							img.setRGB(j, i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
							
							if (iteration > lowerMax) {
								lowerMax = iteration;
								System.out.println("NUEVO LOWER MAX!!! => " + lowerMax);
							}
						} else {
							img.setRGB(j, i, 0);
						}

						lblNewLabel.setIcon(new ImageIcon(img));
					}

					System.out.println(i + " / " + height);
				}
				f = System.currentTimeMillis();
				System.out.println("FIN ================================================");
				System.out.println("Lower max: " + lowerMax);
				System.out.println((f - s) + " ms.");
			}
		}.start();
	}
}
