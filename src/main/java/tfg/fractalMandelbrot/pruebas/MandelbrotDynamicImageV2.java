package tfg.fractalMandelbrot.pruebas;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tfg.fractalgenerator.exportimage.BufferedImageType;

import javax.swing.JLabel;

public class MandelbrotDynamicImageV2 extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotDynamicImageV2 frame = new MandelbrotDynamicImageV2();
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
	public MandelbrotDynamicImageV2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1900, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(lblNewLabel);

		new Thread() {
			public void run() {
				int width = lblNewLabel.getWidth();
				int height = lblNewLabel.getHeight();
				int max = 256;
//				int max_color = 256 * 256 * 256;

				BufferedImage img = new BufferedImage(width, height, BufferedImageType.getBufferedImageType());

				double x0, y0, x, y, xtemp;
				int iteration;

				double halfWidth = width / 2.0;
				double halfHeight = height / 2.0;
				double scale = 4.0 / width;

				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						x0 = (j - halfWidth) * scale;
						y0 = (i - halfHeight) * scale;
						x = 0;
						y = 0;
						iteration = 0;

						while (x * x + y * y <= 4 && iteration < max) {
							xtemp = x * x - y * y + x0;
							y = 2 * x * y + y0;
							x = xtemp;
							iteration++;
						}

//						img.setRGB(j, i, iteration * (max_color / max));
						img.setRGB(j, i, (int)(iteration * Math.pow(2, 20)));
						lblNewLabel.setIcon(new ImageIcon(img));
					}

					System.out.println(i + " / " + height);
				}
			}
		}.start();
	}
}
