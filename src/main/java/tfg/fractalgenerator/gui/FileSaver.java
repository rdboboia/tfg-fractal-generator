package tfg.fractalgenerator.gui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.exportimage.ImageFormat;

/**
 * Provides a method to handle file saving with a GUI using the {@link FileDialog},
 * including a warning if an override may occur and a success confirmation.
 * 
 * @author -$BOSS$-
 */
public class FileSaver {
	/**
	 * Private constructor. No instances allowed.
	 */
	private FileSaver() {
	}
	
	/**
	 * Displays a confirmation message when the file is successfully saved.
	 */
	private static void showSuccessMessage() {
		JOptionPane.showMessageDialog(null, "El archivo se ha guardado correctamente.", "Guardado completado", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * It displays a file selector dialog, then it checks if the user selected
	 * a file by checking if the file returned is {@code null}. If a file with
	 * the same name already exists it prompts the user for override confirmation.
	 */
	public static void saveFile(BufferedImage image, ImageFormat imageFormat) {
		FileDialog d = new FileDialog(new Frame(), "Guardar como");
		d.setVisible(true);

		String fileName = d.getFile();
		if (fileName != null) {
			try {
				if (Paths.get(d.getDirectory() + "\\" + d.getFile()).toFile().exists()) {
					if (JOptionPane.showConfirmDialog(null, "¿Desea sobreescribir el archivo?", "El archivo ya existe", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						ImageExport.export(image, imageFormat, d.getDirectory(), fileName, true);
						showSuccessMessage();
					}
				} else {
					ImageExport.export(image, imageFormat, d.getDirectory(), fileName, false);
					showSuccessMessage();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo.", "Error al guardar", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}