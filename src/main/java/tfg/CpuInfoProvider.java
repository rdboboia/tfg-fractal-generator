package tfg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CpuInfoProvider {
	private static final Logger LOGGER = Logger.getLogger(CpuInfoProvider.class.getSimpleName());
	
	/**
	 * Private constructor. No instances allowed.
	 */
	private CpuInfoProvider() {
		
	}
	
	private static String getField(String wmicCpuAttribute) {
		try {
			Process exec = Runtime.getRuntime().exec("cmd /c wmic cpu get " + wmicCpuAttribute);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
			
			String line = "";
			boolean tagRead = false;
			while (line != null) {
				if (!line.isEmpty()) {
					if (tagRead) {
						return line.trim();
					} else {
						tagRead = true;
					}
				}
				
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error", e);
		}
		
		return null;
	}
	
	public static String getCpuName() {
		return getField("name");
	}
	
	public static String getCpuCoreCount() {
		return getField("numberOfCores");
	}
	
	public static String getCpuThreadCount() {
		return getField("numberOfLogicalProcessors");
	}
}