package spells;

import creatures.Creature;
import javafx.scene.image.Image;

public class Effect implements Cloneable {
	protected int duration;
	protected String name;
	public String name() { return name; }
	protected Creature owner;
	public Creature owner() { return owner; }
	public void setOwner(Creature x) { owner = x; }
	private Image image;
	public Image image() { return image; }
	public void setImage(Image x) { image = x; }
	
	public boolean isDone() { return duration < 1; }

	public Effect(String name, int duration) {
		this.duration = duration;
		this.name = name;
	}
	
	public void update(Creature creature){
		duration--;
	}

	public void start(Creature creature){

	}

	public void end(Creature creature){

	}
	
	public Object clone() {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			// This should never happen
			throw new InternalError(e.toString());
		}
	}
	public int duration() { return duration; }
	public void modifyDuration(int x) { duration += x; }
	public void setDuration(int x) { duration = x; }

}
