package tools;

import application.Main;
import javafx.scene.image.Image;

/**
 * JavaFX Images are not serializable. This class is created to help keep track of persistent images
 * across savegames
 */

public class Icon implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	transient private javafx.scene.image.Image icon;
	private String path;
	public String path() { return path; }
	private boolean cropped;
	private int sx;
	private int sy;
	private int width;
	private int height;
	
	private boolean resized;
	private int newSize;
	
	public Icon(String path) {
		this.path = path;
		this.cropped = false;
	}
	public Icon(String path, int sx, int sy) {
		this.path = path;
		this.cropped = true;
		this.sx = sx;
		this.sy = sy;
		this.width = 32;
		this.height = 32;
	}
	public Icon(String path, int sx, int sy, int width, int height) {
		this.path = path;
		this.cropped = true;
		this.sx = sx;
		this.sy = sy;
		this.width = width;
		this.height = height;
	}
	public void setCrop(int sx, int sy, int width, int height) {
		this.cropped = true;
		this.sx = sx;
		this.sy = sy;
		this.width = width;
		this.height = height;
	}
	public Icon resize(int newSize) {
		this.resized = true;
		this.newSize = newSize;
		return this;
	}
	
	/**
	 * Returns the correct icon, loads it if it is unloaded
	 */
	public Image image() {
		if (icon == null) {
			//Can add a try / catch block here, to load a default ? image or something like that
			try {
				if (resized)
					icon = new Image(Main.class.getResourceAsStream("resources/" + path), newSize, newSize, true, false);
				else
					icon = new Image(Main.class.getResourceAsStream("resources/" + path));
				
				if (cropped)
					icon = ImageCrop.cropImage(icon, sx, sy, width, height);
			} catch (Exception e) {
				icon = new Image(Main.class.getResourceAsStream("resources/tiles/unknown.png"));
				System.out.println("Can't load " + path);
			}
		}
		return icon;
	}

}
