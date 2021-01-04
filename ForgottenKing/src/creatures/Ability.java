package creatures;

import tools.Icon;
import javafx.scene.image.Image;
import spells.TargetType;

public class Ability implements java.io.Serializable {
	protected static final long serialVersionUID = 7769423305067121315L;
	private String name;
	public String name() { return name; }
	private Icon icon;
	public Image icon() { return icon.image(); }
	private String description;
	public String description() { return description; }
	public void setDescription(String desc) { description = desc; }
	private String useText;
	public String useText() { return useText; }
	public void setUseText(String s) { useText = s; } 
	private int cooldown;
	public int cooldown() { return cooldown; }
	private int time;
	public int time() { return time; }
	public void modifyTime(int x) { time = Math.max(0, time += x); }
	public void setTime(int x) { time = x; }
	public void refreshTime() { time = cooldown(); }
	private TargetType targetType;
	public TargetType targetType() { return targetType; }
	public void setTargetType(TargetType t) { targetType = t; }
	private int range;
	public int range() { return range; }
	public void setRange(int x) { range = x; }
	private int radius;
	public int radius() { return radius; }
	public void setRadius(int x) { radius = x; }
	
	public boolean self;
	
	//For tags that grant more powerful abilities, such as polearm master
	private Ability upgradedAbility;
	private Tag prerequisiteTag;
	public Ability upgradedAbility() { return upgradedAbility; }
	public Tag prerequisiteTag() { return prerequisiteTag; }
	public void setUpgrade(Ability ability, Tag prerequisite) { upgradedAbility = ability; prerequisiteTag = prerequisite; }
	
	public Ability(String name, Icon icon, int cooldown) {
		this.name = name;
		this.icon = icon;
		this.cooldown = cooldown;
	}
	
	//To be overridden
	public void activate(Creature owner) { }
	public void activate(Creature owner, Creature other) { }	//If using targettype self with a radius of 0, use this for buffs
	
}
