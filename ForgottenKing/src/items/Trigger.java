package items;

import creatures.Creature;

public abstract class Trigger implements java.io.Serializable {
	protected static final long serialVersionUID = 7769423305067121315L;
	public abstract void trigger(Creature owner, Creature other);
	
	private String description;
	public String description() { return description; }
	public void setDescription(String s) { description = s; }
}
