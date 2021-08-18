import java.awt.Color;

import processing.core.PVector;

public class Util {

	public static float random(double min, double max) {
		return (float) (Math.random()*(max-min)+min);
	}
	
	public static float random(double max) {
		return (float) (Math.random()*max);
	}
	
	public static Color setColor() {
		int r = (int) (128);
		int g = (int) (128);
		int b = (int) (128);
		
		return new Color(r,g,b);
	}
	
	public static PVector randomPVector(int maxX, int maxY) {		
		return new PVector((float)random(maxX), (float)random(maxY));	
	}
	
	public static PVector randomPVector(float magnitude) {
		return PVector.random2D().mult(magnitude);
	}
}
