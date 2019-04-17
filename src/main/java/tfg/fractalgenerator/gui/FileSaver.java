package tfg.fractalgenerator.gui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import tfg.fractalgenerator.exportimage.ImageExport;
import tfg.fractalgenerator.exportimage.ImageFormat;
import tfg.fractalgenerator.exportimage.ImageFormatParser;

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
	private static void checkFile(String fileDirectory, String fileName, String fileExtension) {
		fileName = removeFileExtensionIfAlreadyExists(fileName, fileExtension);
		if (Paths.get(fileDirectory + "\\" + fileName + "." + fileExtension).toFile().exists())
			JOptionPane.showMessageDialog(null, "El archivo se ha guardado correctamente.", "Guardado completado", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "El archivo no se ha podido guardar.", "Error", JOptionPane.ERROR_MESSAGE);
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
			String directory = d.getDirectory();
			String format = ImageFormatParser.getImageFormat(imageFormat);
			
			try {
				if (Paths.get(directory + "\\" + fileName).toFile().exists()) {
					if (JOptionPane.showConfirmDialog(null, "¿Desea sobreescribir el archivo?", "El archivo ya existe", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						ImageExport.export(image, imageFormat, directory, fileName, true);
						checkFile(d.getDirectory(), d.getFile(), format);
					}
				} else {
					ImageExport.export(image, imageFormat, d.getDirectory(), fileName, false);
					checkFile(d.getDirectory(), d.getFile(), format);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo.", "Error al guardar", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Removes the extension from the file name if it already exists.
	 * @param fileName the file name to be checked.
	 * @param extension the extension to be removed.
	 * @return the file name without the extension.
	 */
	public static String removeFileExtensionIfAlreadyExists(String fileName, String extension) {
		if (fileName.toLowerCase().endsWith(extension)) {
			fileName = fileName.substring(0, fileName.length() - (extension.length() + 1));
		}
		
		return fileName;
	}
}