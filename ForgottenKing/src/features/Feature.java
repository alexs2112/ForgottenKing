package features;

import creatures.Creature;
import javafx.scene.image.Image;
import world.World;

public abstract class Feature {
	protected String name;
	protected String desc;
	protected String type;
	protected Image image;
	public String name() { return name; }
	public String desc() { return desc; }
	public String type() { return type; }
	
	/**
	 * How the player triggers interact
	 * "Bump" : player walks into it
	 * "CanClose" : player press 'c' next to it
	 * "UpStair" and "DownStair" are handled in playscreen
	 */
	public void setType(String t) { type = t; }

	public Feature(String name, String desc, Image image) {
		this.name = name;
		this.desc = desc;
		this.image = image;
		this.type = "";
	}
	
	public Image getImage() { return image; }
	
	public abstract boolean blockLineOfSight();
	
	public abstract boolean blockMovement();

	public abstract void interact(Creature creature, World world, int x, int y, int z);

}
