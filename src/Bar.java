import java.awt.Color;

public class Bar {
	private Color color;
	private int height;
	
	public Bar(int height) {
		int r = (int) (Math.random()*256);
		int g = (int) (Math.random()*256);
		int b = (int) (Math.random()*256);
		color = new Color(r, g, b);
		this.height = height;
	}
	
	public Color getColor() { return color; }
	public int getHeight() { return height; }
	
	public String toString() {
		return "H" + height + "C" + color.getRed();
	}
}
