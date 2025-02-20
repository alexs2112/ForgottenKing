package spells;

import creatures.Creature;
import javafx.scene.image.Image;
import tools.Icon;

public class Hazard implements Cloneable, java.io.Serializable {
	protected static final long serialVersionUID = 7769423305067121315L;
	private String name;
	public String name() { return name; }
	private String description;
	public String description() { return description; }
	public void setDescription(String s) { description = s; }
	private Effect effect;
	public Effect effect() { return effect; }
	private int timer;
	private int time;
	private int variance;
	public int variance() { return variance; }
	public void setVariance(int x) { variance = x; }
	public int time() { return time; }
	public void modifyTime(int x) { time += x; }
	private Icon icon;
	public Image image() { return icon.image(); }
	public void update() {
		timer++;
		if (timer >= 10) {
			timer -= 10;
			time--;
		}
	}
	public Hazard(String name, int duration, Effect effect, Icon icon) {
		this.name = name;
		this.time = duration;
		this.effect = effect;
		this.icon = icon;
	}
	
	public Hazard clone() {
		try {
			return (Hazard)super.clone();
		}
		catch (CloneNotSupportedException e) {
			// This should never happen
			throw new InternalError(e.toString());
		}
	}
	
	/**
	 * A method to be overwritten, this returns true if the creature gets affected or false otherwise when it is called
	 */
	public boolean takeEffect(Creature c) { return true; }
}
