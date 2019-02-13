package tfg.imageProcessing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class InterractiveConsole {
	private Scanner s;
	private String inputFilePath;
	private String outputFilePath;
	
	public InterractiveConsole() {
		s = new Scanner(System.in);
		
		menu();
		getInputFilePath();
		getOutputFilePath();
		
		s.close();
	}
	
	public void menu() {
		int option = -1;
		while (option != 3) {
			System.out.println("Menu:");
			System.out.println("\t1) Invert colors");
			System.out.println("\t2) Convert to gray scale");
			System.out.println("\t3) Exit");
			
			try {
				option = Integer.parseInt(s.nextLine());
				ImageManager.ProcessingMode mode = null;
				
				switch (option) {
					case 1:
						mode = ImageManager.ProcessingMode.NEGATIVE;
						break;
					case 2:
						mode = ImageManager.ProcessingMode.GRAYSCALE;
						break;
					case 3:
						System.out.println("Exiting...");
						System.exit(0);
						break;
				}
				
				getInputFilePath();
				getOutputFilePath();
				ImageManager manager;
				
				
				
				try {
					manager = new ImageManager(inputFilePath, outputFilePath);
					manager.processImage(mode);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			} catch (NumberFormatException e) {
				System.out.println("Only numbers are accepted as an input!");
			}
		}
	}
	
	private void getInputFilePath() {
		System.out.println("Enter the input file path: ");
		inputFilePath = s.nextLine();
	}
	
	private void getOutputFilePath() {
		System.out.println("Enter the output file path: ");
		outputFilePath = s.nextLine();
		
		if(outputFilePath.equals(inputFilePath)) {
			System.out.println("Are you sure you want to override the original file?");
			System.out.println("Type [Y] for yes, [N] for no (both have to me upper case letters):");
			if (s.nextLine().equals("Y"))
				// FIXME
				System.out.println("NOT IMPLEMENTED YET!!!!");
		}
	}
}