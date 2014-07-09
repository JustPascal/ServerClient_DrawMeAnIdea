package packclient;

import java.awt.Color;
import java.io.Serializable;

public class VoPoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6930206511605667614L;

	private Color color = Color.blue;

	private int size = 10;

	private int x = -10;

	private int y = -10;

	private String type = "CIRCLE";

	public VoPoint() {
	}

	public VoPoint(int x, int y, int size, Color color, String type) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof VoPoint))
			return false;

		VoPoint point = (VoPoint) obj;

		if (this.getX() == point.getX())
			if (this.getY() == point.getY())
				if (this.getColor() == point.getColor())
					if (this.getType().equals(point.getType()))
						if (this.getSize() == point.getSize())
							return true;
		return false;
	}
}