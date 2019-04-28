package tfg.fractalgenerator.gui.panels.store;

import tfg.fractalgenerator.gui.panels.ExportToFilePanel;
import tfg.fractalgenerator.gui.panels.RealtimeViewPanelScalable;

/**
 * A container class used to pass some information between some panels. In this
 * case the currently active filters status are passed from the
 * {@link RealtimeViewPanelScalable} panel to the {@link ExportToFilePanel} panel.
 * A Singleton pattern will be used to make the access easier.
 * 
 * @author -$BOSS$-
 */
public class ActiveFiltersStore {
	/**
	 * The instance, since a Singleton pattern is used.
	 */
	private static ActiveFiltersStore instance;
	
	/**
	 * The negative filter status.
	 */
	private boolean negativeFilterActive;
	
	/**
	 * The grayscale filter status.
	 */
	private boolean grayscaleFilterActive;
	
	/**
	 * Private constructor. Using Singleton pattern.
	 */
	private ActiveFiltersStore() {
		negativeFilterActive = false;
		grayscaleFilterActive = false;
	}
	
	/**
	 * Returns and creates an instance if needed.
	 * 
	 * @return the one and only instance of {@link Controlador}.
	 */
	public static ActiveFiltersStore getInstance() {
		if (instance == null)
			instance = new ActiveFiltersStore();
		
		return instance;
	}

	/**
	 * Returns the negative filter status.
	 * @return whether the negative filter is active or not.
	 */
	public boolean isNegativeFilterActive() {
		return negativeFilterActive;
	}
	
	/**
	 * Sets the current negative filter status to the given boolean value.
	 * 
	 * @param negativeFilterActive the filter status.
	 */
	public void setNegativeFilterActive(boolean negativeFilterActive) {
		this.negativeFilterActive = negativeFilterActive;
	}
	
	/**
	 * Returns the grayscale filter status.
	 * @return whether the grayscale filter is active or not.
	 */
	public boolean isGrayscaleFilterActive() {
		return grayscaleFilterActive;
	}
	
	/**
	 * Sets the current grayscale filter status to the given boolean value.
	 * 
	 * @param grayscaleFilterActive the filter status.
	 */
	public void setGrayscaleFilterActive(boolean grayscaleFilterActive) {
		this.grayscaleFilterActive = grayscaleFilterActive;
	}
}