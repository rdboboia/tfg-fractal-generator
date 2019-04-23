package tfg.fractalgenerator.mandelbrotset;

import java.io.Serializable;

/**
 * A container class for some generation parameters of the
 * {@link MandelbrotsetGeneratorThread} such as the x and y axis, and the zoom and
 * scale modifiers.
 * 
 * @author -$BOSS$-
 */
public class MandelbrotsetPosition implements Serializable {
	/**
	 * Serial version number. Using the default one.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The x axis position as double.
	 */
	private double posx;
	
	/**
	 * The x axis position as double.
	 */
	private double posy;
	
	/**
	 * The zoom modifier as double.
	 */
	private double zoom;
	
	/**
	 * The scale position as double.
	 */
	private double scale;

	/**
	 * Creates a new instance of the {@link MandelbrotsetPosition} using the default
	 * values for all the fields.
	 */
	public MandelbrotsetPosition() {
		posx = 0;
		posy = 0;
		zoom = 256;
		scale = 1;
	}
	
	/**
	 * Creates a new instance of the {@link MandelbrotsetPosition} using the given
	 * parameters as values for each field.
	 * 
	 * @param posx the x axis position.
	 * @param posy the y axis position.
	 * @param zoom the zoom modifier.
	 * @param scale the scale modifier.
	 */
	public MandelbrotsetPosition(double posx, double posy, double zoom, double scale) {
		this.posx = posx;
		this.posy = posy;
		this.zoom = zoom;
		this.scale = scale;
	}
	
	/**
	 * Returns the x axis position field as double.
	 * 
	 * @return the x axis position field.
	 */
	public double getPosx() {
		return posx;
	}
	
	/**
	 * Sets the x axis position field to the given value as parameter.
	 * @param posx the x axis position value.
	 */
	public void setPosx(double posx) {
		this.posx = posx;
	}
	
	/**
	 * Returns the y axis position field as double.
	 * 
	 * @return the y axis position field.
	 */
	public double getPosy() {
		return posy;
	}
	
	/**
	 * Sets the y axis position field to the given value as parameter.
	 * @param posy the y axis position value.
	 */
	public void setPosy(double posy) {
		this.posy = posy;
	}
	
	/**
	 * Returns the zoom modifier field as double.
	 * 
	 * @return the zoom modifier field.
	 */
	public double getZoom() {
		return zoom;
	}
	
	/**
	 * Sets the zoom modifier field to the given value as parameter.
	 * @param zoom the zoom modifier value.
	 */
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	/**
	 * Returns the scale modifier field as double.
	 * 
	 * @return the scale modifier field.
	 */
	public double getScale() {
		return scale;
	}
	
	/**
	 * Sets the scale modifier field to the given value as parameter.
	 * @param scale the zoom modifier value.
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	@Override
	public String toString() {
		return "MandelbrotsetPosition [posx=" + posx + ", posy=" + posy + ", zoom=" + zoom + ", scale=" + scale + "]";
	}
}