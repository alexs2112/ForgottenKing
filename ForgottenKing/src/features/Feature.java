package features;

import creatures.Creature;
import javafx.scene.image.Image;
import tools.Icon;
import world.World;

public abstract class Feature implements Cloneable, java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	protected String name;
	protected String desc;
	protected Type type;
	protected Icon icon;
	public String name() { return name; }
	public String desc() { return desc; }
	public Type type() { return type; }
	
	public enum Type {
		BUMP,
		CANCLOSE,
		UPSTAIR,
		DOWNSTAIR;
	}
	
	/**
	 * How the player triggers interact
	 * "Bump" : player walks into it
	 * "CanClose" : player press 'c' next to it
	 * "UpStair" and "DownStair" are handled in playscreen
	 */
	public void setType(Type t) { type = t; }

	public Feature(String name, String desc, Icon icon) {
		this.name = name;
		this.desc = desc;
		this.icon = icon;
	}
	
	public Feature clone() {
		try {
			return (Feature)super.clone();
		}
		catch (CloneNotSupportedException e) {
			// This should never happen
			throw new InternalError(e.toString());
		}
	}
	
	public Image getImage() { return icon.image(); }
	
	public abstract boolean blockLineOfSight();
	
	public abstract boolean blockMovement();

	public abstract void interact(Creature creature, World world, int x, int y, int z);
	
	//To be overridden, for features that need to know their location (doors)
	public void setLocation(World world, int x, int y, int z) { }

}
