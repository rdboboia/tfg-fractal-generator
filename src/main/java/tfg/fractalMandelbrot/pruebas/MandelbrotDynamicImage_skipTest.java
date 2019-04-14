package tfg.fractalMandelbrot.pruebas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class MandelbrotDynamicImage_skipTest extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotDynamicImage_skipTest frame = new MandelbrotDynamicImage_skipTest();
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
	public MandelbrotDynamicImage_skipTest() {
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
				long s, f;
				s = System.currentTimeMillis();
				int width = lblNewLabel.getWidth();
				int height = lblNewLabel.getHeight();
				int max = 256 * 256;

				BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

				double x0, y0, x, y, xtemp;
				int iteration = 0;
				int tolerancia = 4;
				int iteracionesEnTolerancia = 0;

				double halfWidth = width / 2.0;
				double halfHeight = height / 2.0;
				double scale = 4.0 / width;
				
				
				
				
//				for (int i = height-1; i >= 0; i--) {
//					iteration = 0;
//					for (int j = width-1; j-(height-1-i) >= 0 && j >= 0 && iteration < max; j--) {
//						x0 = (j-(height-1-i) - halfWidth) * scale;
//						y0 = (i - halfHeight) * scale;
//						x = 0;
//						y = 0;
//						iteration = 0;
//
//						while (x * x + y * y <= 4 && iteration < max) {
//							xtemp = x * x - y * y + x0;
//							y = 2 * x * y + y0;
//							x = xtemp;
//							iteration++;
//						}
////						img.setRGB(j, i, iteration);
//						
//						if (iteration < max)
//							img.setRGB(j-(height-1-i), i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
//						else
//							img.setRGB(j-(height-1-i), i, 0);
//						
//						lblNewLabel.setIcon(new ImageIcon(img));
//					}
//				}
				
				
				
				for (int i = 0; i < height; i++) {
					iteration = 0;
					iteracionesEnTolerancia = 0;
					for (int j = 0; j+i < height && j < width && iteracionesEnTolerancia < tolerancia; j++) {
						x0 = (j - halfWidth) * scale;
						y0 = (i+j - halfHeight) * scale;
						x = 0;
						y = 0;
						iteration = 0;

						while (x * x + y * y <= 4 && iteration < max) {
							xtemp = x * x - y * y + x0;
							y = 2 * x * y + y0;
							x = xtemp;
							iteration++;
						}
//						img.setRGB(j, i, iteration);
						
						if (iteration < max)
							img.setRGB(j, i+j, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
						else
							img.setRGB(j, i+j, 0);
						
						if (!(iteration < max))
							iteracionesEnTolerancia++;
						else // tolerancia de MAX seguidos
							iteracionesEnTolerancia = 0;
						
						lblNewLabel.setIcon(new ImageIcon(img));
					}
				}
				
				for (int j = 0; j < width; j++) {
					iteration = 0;
					iteracionesEnTolerancia = 0;
					for (int i = 0; j+i < width && i < height && iteracionesEnTolerancia < tolerancia; i++) {
						if (img.getRGB(j, i) < 0) {
							x0 = (j+i - halfWidth) * scale;
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
	//						img.setRGB(j, i, iteration);
							
							if (iteration < max)
								img.setRGB(j+i, i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
							else
								img.setRGB(j+i, i, 0);
							
							if (!(iteration < max))
								iteracionesEnTolerancia++;
							else // tolerancia de MAX seguidos
								iteracionesEnTolerancia = 0;
							
							lblNewLabel.setIcon(new ImageIcon(img));
						}
					}
				}
				
				
				
				for (int i = 0; i < height; i++) {
					iteration = 0;
					iteracionesEnTolerancia = 0;
					for (int j = 0; j < width && iteracionesEnTolerancia < tolerancia; j++) {
						if (img.getRGB(j, i) < 0) {
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
	//						img.setRGB(j, i, iteration);
							
							if (iteration < max)
								img.setRGB(j, i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
							else
								img.setRGB(j, i, 0);
							
							if (!(iteration < max))
								iteracionesEnTolerancia++;
							else // tolerancia de MAX seguidos
								iteracionesEnTolerancia = 0;
						
							lblNewLabel.setIcon(new ImageIcon(img));
						}
					}
				}

				for (int j = 0; j < width; j++) {
					iteration = 0;
					iteracionesEnTolerancia = 0;
					for (int i = 0; i < height && iteracionesEnTolerancia < tolerancia; i++) {
						if (img.getRGB(j, i) < 0) {
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
//							img.setRGB(j, i, iteration);
							
							if (iteration < max)
								img.setRGB(j, i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
							else
								img.setRGB(j, i, 0);
							
							if (!(iteration < max))
								iteracionesEnTolerancia++;
							else // tolerancia de MAX seguidos
								iteracionesEnTolerancia = 0;
							
							lblNewLabel.setIcon(new ImageIcon(img));
						}
					}
				}
				
				for (int i = height-1; i > 0; i--) {
					iteration = 0;
					iteracionesEnTolerancia = 0;
					for (int j = width-1; j > 0 && iteracionesEnTolerancia < tolerancia; j--) {
						if (img.getRGB(j, i) < 0) {
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
//							img.setRGB(j, i, iteration);
							
							if (iteration < max)
								img.setRGB(j, i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
							else
								img.setRGB(j, i, 0);
							
							if (!(iteration < max))
								iteracionesEnTolerancia++;
							else // tolerancia de MAX seguidos
								iteracionesEnTolerancia = 0;
							
							lblNewLabel.setIcon(new ImageIcon(img));
						}
					}
				}
				
				for (int j = width-1; j > 0; j--) {
					iteration = 0;
					iteracionesEnTolerancia = 0;
					for (int i = height-1; i > 0 && iteracionesEnTolerancia < tolerancia; i--) {
						if (img.getRGB(j, i) < 0) {
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
//							img.setRGB(j, i, iteration);
							
							if (iteration < max)
								img.setRGB(j, i, Color.HSBtoRGB(iteration/256f, 1, iteration/(iteration+8f)));
							else
								img.setRGB(j, i, 0);
							
							if (!(iteration < max))
								iteracionesEnTolerancia++;
							else // tolerancia de MAX seguidos
								iteracionesEnTolerancia = 0;
							
							lblNewLabel.setIcon(new ImageIcon(img));
						}
					}
				}
				
				f = System.currentTimeMillis();
				System.out.println("FIN ================================================");
				System.out.println((f - s) + " ms.");
				
				// Exportar imagen a png
				try {
					ImageIO.write(img, "png", new File("Z:\\mandelbrot_v2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}.start();
	}
}
