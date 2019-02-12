package tfg.imageProcessing;

import java.security.InvalidParameterException;

public class App 
{
	/*
	 * How it will be done (Clases to consider):
	 * 1) Set objective (negative, grey scale, etc...)
	 * 	- It may not exist
	 * 2) Read image
	 * 	- It may not exist
	 * 3) Process the input
	 * 4) Write the output
	 * 	- It may fail; keep asking for an output option until either
	 * 	a successful write is performed or the operation is cancelled.
	 */
	
	public static void main(String[] args) {
//		// FIXME: testing purposes
//		Launcher.launchInterractiveConsole();
//		System.exit(0);
		
		if (args.length == 0) {
			Launcher.launchGUI();
		}
		else if (args.length == 1) {
			if (args[0].equals("-c")) {
				Launcher.launchInterractiveConsole();
			}
			else {
				throw new InvalidParameterException("Invalid parameter!");
			}
		}
		else if (args.length == 3) {
			Launcher.launchProcessor(args);
		}
		else {
			throw new InvalidParameterException("Invalid number of parameters! Only 0, 1 and 3 are accepted.");
		}
    }
}
