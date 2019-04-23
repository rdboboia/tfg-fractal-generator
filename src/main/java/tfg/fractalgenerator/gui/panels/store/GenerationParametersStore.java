package tfg.fractalgenerator.gui.panels.store;

import tfg.fractalgenerator.gui.panels.ExportToFilePanel;
import tfg.fractalgenerator.gui.panels.RealtimeViewPanelScalable;
import tfg.fractalgenerator.mandelbrotset.MandelbrotsetPosition;

/**
 * A container class used to pass some information between some panels. That is,
 * to pass the width, height, depth, color depth and position from the
 * {@link RealtimeViewPanelScalable} panel to the {@link ExportToFilePanel} panel.
 * A Singleton pattern will be used to make the access easier.
 * 
 * @author -$BOSS$-
 */
public class GenerationParametersStore {
	/**
	 * The instance, since a Singleton pattern is used.
	 */
	private static GenerationParametersStore instance;
	
	/**
	 * The image width.
	 */
	private int width;
	
	/**
	 * The image height.
	 */
	private int height;
	
	/**
	 * The maximum number of iterations per pixel.
	 */
	private int depth;
	
	/**
	 * It affects maximum number of colors as well as when each color may appear
	 * based on the number of iterations.
	 */
	private int colorDepth;
	
	/**
	 * The x and y axis position, as well as the zoom and scale modifiers.
	 */
	private MandelbrotsetPosition position;
	
	/**
	 * Private constructor. Using Singleton pattern.
	 */
	private GenerationParametersStore() {
		width = 3840;
		height = 2160;
		depth = 360;
		colorDepth = 360;
		position = new MandelbrotsetPosition();
	}
	
	/**
	 * Returns and creates an instance if needed.
	 * 
	 * @return the one and only instance of {@link Controlador}.
	 */
	public static GenerationParametersStore getInstance() {
		if (instance == null)
			instance = new GenerationParametersStore();
		
		return instance;
	}
	
	/**
	 * Returns the image width.
	 * 
	 * @return the image width.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets the width field to the given value as parameter.
	 * 
	 * @param width the width value.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Returns the image height.
	 * 
	 * @return the image height.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the height field to the given value as parameter.
	 * 
	 * @param heigth the height value.
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Returns the image's pixel depth (maximum number of iterations per pixel).
	 * 
	 * @return the pixel.
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Sets the depth field to the given value as parameter.
	 * 
	 * @param depth the depth value.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	/**
	 * Returns image's color depth which can be seen as the number of colors (also
	 * affects when each color will be used since the actual color depends on the
	 * number of iterations and the color depth).
	 * 
	 * @return the image's color depth.
	 */
	public int getColorDepth() {
		return colorDepth;
	}
	
	/**
	 * Sets the color depth field to the given value as parameter.
	 * 
	 * @param colorDepth the color depth value.
	 */
	public void setColorDepth(int colorDepth) {
		this.colorDepth = colorDepth;
	}
	
	/**
	 * Returns the current {@link MandelbrotsetPosition} position.
	 * 
	 * @return the current {@link MandelbrotsetPosition} position.
	 */
	public MandelbrotsetPosition getPosition() {
		return position;
	}
	
	/**
	 * Sets the {@link MandelbrotsetPosition} position field to the given value
	 * as parameter.
	 * 
	 * @param position the {@link MandelbrotsetPosition} position value.
	 */
	public void setPosition(MandelbrotsetPosition position) {
		this.position = position;
	}
}