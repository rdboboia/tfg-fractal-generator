package tfg.imageProcessing;

import java.util.Scanner;

import tfg.imageProcessing.ImageProcessor.ProcessingMode;

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
		while (option < 1 || option > 3) {
			System.out.println("Menu:");
			System.out.println("\t1) Invert colors");
			System.out.println("\t2) Convert to gray scale");
			System.out.println("\t3) Exit");
			
			try {
				option = Integer.parseInt(s.nextLine());
				
				switch (option) {
					case 1:
						// TODO
						getInputFilePath();
						getOutputFilePath();
						new ImageProcessor().process(inputFilePath, outputFilePath, ProcessingMode.negative);
						break;
					case 2:
						// TODO
						System.out.println("Not yet implemented!");
						break;
					case 3:
						System.out.println("Exiting...");
						System.exit(0);
						break;
			
					default:
						System.out.println("Wrong option. Choose again!");
						break;
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