package items;

import creatures.Creature;

public abstract class Trigger {
	public abstract void trigger(Creature owner, Creature other);
	
	private String description;
	public String description() { return description; }
	public void setDescription(String s) { description = s; }
}
